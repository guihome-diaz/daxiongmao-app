package eu.daxiongmao.core.utils.security;

import lombok.extern.log4j.Log4j2;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * <p>
 * This is the <u>Argon2</u> password algorithm + BouncyCastle implementation.
 * </p>
 * <ul>
 *     <li>Current settings are based on <a href="https://password-hashing.net/argon2-specs.pdf">Argon2 specifications</a></li>
 *     <li>Implementation relies on Bouncy Castle (<a href="https://www.bouncycastle.org/java.html">BouncyCastle java</a>)<br>
 *         Bouncy castle Argon2 test have been used as API guideline, see <a href="https://github.com/bcgit/bc-java/blob/master/core/src/test/java/org/bouncycastle/crypto/test/Argon2Test.java">Argon2Test</a>
 *     </li>
 * </ul>
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
@Log4j2
@Component
public class Argon2PasswordHash implements IPasswordHash {

    /**
     * <p>
     * <strong>Argon2i</strong> uses data-independent memory access, which is recommended for password hashing and password-based key derivation.<br>
     * Argon2i is optimized for more dangerous settings, where the adversary possibly can access the same machine,
     * use its CPU or mount cold-boot attacks.
     * We use three passes to get rid entirely of the password in the memory. </p>
     * <p>We suggest the following settings:</p>
     * <ul>
     *     <li>Key  derivation  for  hard-drive  encryption,  that  takes  3  seconds  on  a  2  GHz  CPU  using  2  cores — Argon2i with 4 lanes and 6 GB of RAM;
     *     </li>Frontend server authentication, that takes 0.5 seconds on a 2 GHz CPU using 2 cores — Argon2i with 4 lanes and 1 GB of RAM
     * </ul>
     * <p><a href="https://password-hashing.net/argon2-specs.pdf">Argon2 specifications</a></p>
     */
    private static final int ARGON2_MODE = Argon2Parameters.ARGON2_i;

    /** Algorithm version. the higher, the better. */
    private static final int ARGON2_VERSION = Argon2Parameters.ARGON2_VERSION_13;

    /** <p>
     * Number of iterations to perform.
     * Designers recommends 3 iterations, at least, to be memory proof.</p>
     * </p>
     * <p>this is also called <strong>Tradeoff resilience.</strong><br>
     *  Despite high performance, Argon2 provides reasonable level of tradeoff resilience.
     *  With default number of passes over memory (1 forArgon2d, 3 forArgon2i) an ASIC-equipped
     *  adversary can not decrease the time-area product if the memory is reduced by the factor of 4 or more.
     */
    private static final int ITERATIONS = 3;

    /** Size of allowed memory per thread for Argon2 algorithm.
     * Original designers recommend at least 32 kb - maximum of 256kb according to literature (2020/03).
     * => Rule of thumb: allocated the same amount of memory as the salt length
     */
    private static final int ALLOWED_MEMORY_IN_KB = 128;

    /** Number of core that algorithm can use (== number of threads to use for computation).
     * BE CAREFUL: total memory consumption = (number of threads) * (max memory allowed per thread)
     */
    private static final int NUMBER_OF_THREADS = 4;

    /** To encode binary salt (byte[]) in base64 String format. */
    private final Base64.Encoder encoder = Base64.getUrlEncoder();

    /** To decode a base64 String salt representation into binary (byte[]) */
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Override
    public String hashPassword(final String password, final int hashSize, final byte[] salt) {
        // algorithm setup
        final Argon2Parameters.Builder builder = new Argon2Parameters.Builder(ARGON2_MODE)
                .withVersion(ARGON2_VERSION)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(ALLOWED_MEMORY_IN_KB)
                .withParallelism(NUMBER_OF_THREADS)
                .withSalt(salt);

        // To instantiate algorithm with requested parameters
        final Argon2BytesGenerator digester = new Argon2BytesGenerator();
        digester.init(builder.build());

        // To hash the password
        byte[] pwdHash = new byte[hashSize];
        digester.generateBytes(password.getBytes(), pwdHash);

        // encode in hexadecimal
        return encoder.encodeToString(pwdHash);
    }

    @Override
    public String getHashAlgorithm() {
        return String.format("Password hash: {Algorithm: Argon2i | Provider: BouncyCastle | Version: %s | Iterations: %s | Memory/thread: %s kb | Threads: %s | with salt: yes}, base64 encoding, config 2020-03", ARGON2_VERSION, ITERATIONS, ALLOWED_MEMORY_IN_KB, NUMBER_OF_THREADS);
    }
}
