package eu.daxiongmao.core.utils.security;

import lombok.extern.log4j.Log4j2;

import java.security.*;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import sun.security.provider.MoreDrbgParameters;

/**
 * <p>
 * To generate and decode application SALT.<br>
 * For security reasons some parameters are hard-coded.<br>
 * <strong>Security over performances</strong>: to ensure the random numbers always are random this class is thread-safe.
 * Unfortunately it uses a synchronized block, see {@link #getSecureRandom()}<br>
 * Do NOT remove that synchronized block.
 * </p>
 * <p>This code is the summary of my understanding of crypto recommendations (study 2020/03).
 * I cannot be held responsible for this code behaviour and side effects. Please review before you use it.
 * </p>
 * @version 1.0
 * @since 2020/03, application's creation
 * @author Guillaume Diaz.
 */
@Log4j2
@Component
public class SaltGeneratorUtil {

    /** Custom note to put on SALT algorithm name */
    static final String SECURE_RANDOM_COMMENTS = "base64 encoding, config 2020-03";

    /**
     * Current algorithm to generate secure random strings for SALT.<br>
     *     <strong>Deterministic random bit generator (DRBG)</strong><br>
     *     Definition from <a href="https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r4.pdf">US department of commerce # National Institute of Standards and Techology (NIST). See bottom of page 20</a><br>
     *     This algorithm was introduced in Java 9<br>
     *     A random bit generator that includes a DRBG algorithm and (at least initially) has access to a source of randomness.
     *     The DRBG produces a sequence of bits from a secret initial value called a seed, along with other possible inputs.
     *     A cryptographic DRBG has the additional property that the output is unpredictable, given that the seed is not known.
     *     A DRBG is sometimes also called a Pseudo-random Number Generator (PRNG) or a deterministic random number generator.
     */
    static final String SECURE_RANDOM_GENERATOR_TYPE = "DRBG";

    /**
     * Type of DBRG algo to use. Block Cipher based: CTR_DRBG
     */
    static final String SECURE_RANDOM_MECHANISM = "CTR_DRBG";

    /**
     * For block cipher algorithm (CTR_DBRG) you need a strong encryption algorithm.
     */
    static final String SECURE_RANDOM_ALGORITHM = "AES-256";

    /**
     * Number of seeds to generate before resetting the secure random.
     * -> Once the number of usage has been reach everything will be reset.
     * Be careful: the reset operation is expensive: it should not occur too often.
     */
    static final int NUMBER_OF_ITERATIONS_BEFORE_RESEED = 2048;

    /** Based on current recommendations (bouncy castle, Oracle and various researchers)
     * minimum salt size should be 32 bytes.
     */
    static final int MINIMUM_SALT_SIZE = 32;

    /** Current {@link java.security.SecureRandom}
     * Object to use to generate random numbers */
    private final SecureRandom secureRandom = generateSecureRandom();

    /** Number of seeds that have been generated with the current Secure Random. A new Random value will have to be issued after some seeds generation. */
    private AtomicInteger numberOfSeedGenerated = new AtomicInteger(0);

    /**
     * To generate a new SALT using current Secure Random algorithm.
     * @param saltSize salt size. <br>
     *                 The salt needs to be long, so that there are many possible salts.<br>
     *                 -> As a rule of thumb, make your salt at least as long as the hash function's output.
     * @return new SALT encoded in Base64
     * @throws IllegalArgumentException requested SALT length does not match minimum security requirements. See {@link #MINIMUM_SALT_SIZE}
     */
    public String generateSalt(final int saltSize) {
        // Security: ensure requested salt is not too small
        if (saltSize < MINIMUM_SALT_SIZE) {
            throw new IllegalArgumentException("Request salt size is too small. Valid SALT are, at least, " + MINIMUM_SALT_SIZE + " bytes long.");
        }

        // Security: Reseed now and then
        if (numberOfSeedGenerated.get() == 0 || numberOfSeedGenerated.get() % NUMBER_OF_ITERATIONS_BEFORE_RESEED == 0) {
            numberOfSeedGenerated.set(0);
            this.secureRandom.reseed();
        }

        // Generate salt
        byte[] randomValue = new byte[saltSize];
        getSecureRandom().nextBytes(randomValue);

        // Encode result
        final String salt = encodeSalt(randomValue);

        // Increment counter
        numberOfSeedGenerated.incrementAndGet();

        return salt;
    }

    /**
     * To get current salt's algorithm
     * @return current salt algorithm
     */
    public String getSaltAlgorithm() {
        return String.format("SecureRandom: {Algorithm: %s | Provider: %s | Parameters: %s | Misc: %s, %s}, Comments: %s", getSecureRandom().getAlgorithm(), getSecureRandom().getProvider(), getSecureRandom().getParameters(), SECURE_RANDOM_MECHANISM, SECURE_RANDOM_ALGORITHM, SECURE_RANDOM_COMMENTS);
    }

    /**
     * To encode the salt into String representation.
     * <p>
     * This will encode the salt in Base64 for String representation.
     * It use Java <code>URL encoder</code> to NOT process special characters such as "\n"
     * (i) don't forget to adjust the {@link #decodeSalt(String)} method if you change that part
     * </p>
     * @param saltBytes byte array value to encode
     * @return SALT representation as a String
     */
    String encodeSalt(final byte[] saltBytes) {
        if (saltBytes == null || saltBytes.length < MINIMUM_SALT_SIZE) {
            throw new IllegalArgumentException("SALT must be, at least, " + MINIMUM_SALT_SIZE + " bytes long");
        }

        final Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(saltBytes);
    }
    
    /**
     * To decode the salt into byte array for hash computation
     * (i) don't forget to adjust the {@link #encodeSalt(byte[])} method if you change that part
     * @param encodedSalt encode salt
     * @return corresponding salt as byte array
     */
    public byte[] decodeSalt(final String encodedSalt) {
        if (StringUtils.isBlank(encodedSalt)) {
            throw new IllegalArgumentException("Cannot decode NULL or empty salt. You must provide a valid value to decode");
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        return decoder.decode(encodedSalt);
    }

    /** To retrieve the random generator */
    private synchronized SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    /** To initialize the secure random algorithm.
     * User will be able to generate random number after initialization.
     * For security reason it is recommended to reseed the algorithm periodically.
     * -> This can be time based or usage based (current solution)
     * @return secure random utility to use to generate random numbers
     */
    private SecureRandom generateSecureRandom() {
        try {
            // 2020-03: this configuration is based on various articles, including
            //          * excellent explanation of DRBG: https://metebalci.com/blog/everything-about-javas-securerandom/
            //          * Recommendation from US government: https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r4.pdf

            // Key points:
            // * Overall / Advanced # Use a block Cipher based: CTR_DRBG (.. slower than hash function but a bit better according to scientists)
            // * Generic # Secure random algorithm: DRBG, available since Java 9
            // * Generic # Strength: 256 bytes. This is the highest strength available (.. and the slower as well)
            // * Generic # Capabilities: Use an implementation that resist to prediction (.. slower), and allow runtime reseed
            // * Generic # Custom suffix (optional) to add to the current secureRandom.toString() function
            // * Advanced # Seed: file / data to use as randomness source. If NULL (current case) => let the Operating System choose
            // * Advanced # Force use of derivation (only applicable to CTR_DRBG)
            Security.setProperty("securerandom.drbg.config", "CTR_DRBG");
            final DrbgParameters.Instantiation genericDbrgParameters = DrbgParameters.instantiation(256, DrbgParameters.Capability.PR_AND_RESEED, SECURE_RANDOM_COMMENTS.getBytes());
            final SecureRandomParameters advanceDbrgParameters = new MoreDrbgParameters(null, SECURE_RANDOM_MECHANISM, SECURE_RANDOM_ALGORITHM, null, true, genericDbrgParameters);

            // Select random algorithm
            return SecureRandom.getInstance(SECURE_RANDOM_GENERATOR_TYPE, advanceDbrgParameters);
        } catch (Exception e) {
            log.error("Failed to initialize the secure random generator", e);
            throw new RuntimeException(e);
        }
    }

}
