package eu.daxiongmao.core.utils.security;

import lombok.extern.log4j.Log4j2;

/**
 * This is the Argon2 password utility.
 * Current settings are based on https://password-hashing.net/argon2-specs.pdf
 *
 * Implementation relies on Bouncy Castle (https://www.bouncycastle.org/java.html)
 * Bouncy castle Argon2 test have been used as API guideline: https://github.com/bcgit/bc-java/blob/master/core/src/test/java/org/bouncycastle/crypto/test/Argon2Test.java
 */
@Log4j2
public class Argon2PasswordHashUtil {

    /** Tradeoff resilience.
     *  Despite high performance, Argon2 provides reasonable level of tradeoff resilience.
     *  With default number of passes over memory (1 forArgon2d, 3 forArgon2i) an ASIC-equipped
     *  adversary can not decrease the time-area product if the memory is reduced by the factor of 4 or more.
     */
    private static final int NUMBER_OF_ITERATIONS = 3;

}
