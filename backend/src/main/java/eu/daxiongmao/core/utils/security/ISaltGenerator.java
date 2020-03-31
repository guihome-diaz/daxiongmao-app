package eu.daxiongmao.core.utils.security;

/**
 * <p>
 * To generate and decode application SALT.<br>
 * <strong>Security over performances</strong>: to ensure the random numbers always are random this class is thread-safe.
 * </p>
 * <p>This code is the summary of my understanding of crypto recommendations (study 2020/03).
 * I cannot be held responsible for this code behaviour and side effects. Please review before you use it.
 * </p>
 * @version 1.0
 * @since 2020/03, application's creation
 * @author Guillaume Diaz.
 */
public interface ISaltGenerator {

    /**
     * To generate a new SALT using current Secure Random algorithm.
     * @param saltSize salt size. <br>
     *                 The salt needs to be long, so that there are many possible salts.<br>
     *                 -> As a rule of thumb, make your salt at least as long as the hash function's output.
     * @return new SALT encoded in Base64.<br>
     *         careful: result string is longer that requested size: result_size = (3 * (LengthInCharacters / 4)) - (numberOfPaddingCharacters)
     * @throws IllegalArgumentException requested SALT length does not match minimum security requirements
     */
    String generateSalt(int saltSize);

    /**
     * To get current salt algorithm
     * @return current salt algorithm description
     */
    String getSaltAlgorithm();

    /**
     * To decode the salt into byte array for hash computation
     * @param encodedSalt encode salt
     * @return corresponding salt as byte array
     */
    byte[] decodeSalt(final String encodedSalt);

}
