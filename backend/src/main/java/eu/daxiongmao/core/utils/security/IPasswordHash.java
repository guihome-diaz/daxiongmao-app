package eu.daxiongmao.core.utils.security;


/**
 * <p>
 * To hash a password.<br>
 * <strong>Security over performances</strong>: hash depends on password value + custom salt.
 * </p>
 * <p>This code is the summary of my understanding of crypto recommendations (study 2020/03).
 * I cannot be held responsible for this code behaviour and side effects. Please review before you use it.
 * </p>
 * @version 1.0
 * @since 2020/03, application's creation
 * @author Guillaume Diaz.
 */
public interface IPasswordHash {

    /**
     * To hash a password
     * @param password password to hash
     * @param hashSize hash size to generate
     * @param salt a unique, randomly generated string that is added to each password as part of the hashing process. As the salt is unique for every user, an attacker has to crack hashes one at a time using the respective salt, rather than being able to calculate a hash once and compare it against every stored hash. This makes cracking large numbers of hashes significantly harder, as the time required grows in direct proportion to the number of hashes.
     * @return password hash encoded in Base64.<br>
     *         careful: result string is longer that requested size: result_size = (3 * (LengthInCharacters / 4)) - (numberOfPaddingCharacters)
     */
    String hashPassword(String password, int hashSize, byte[] salt);


    /**
     * To get current hash algorithm
     * @return current hash algorithm description
     */
    String getHashAlgorithm();

}
