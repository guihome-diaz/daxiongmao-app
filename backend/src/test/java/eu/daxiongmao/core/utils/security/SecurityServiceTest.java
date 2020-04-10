package eu.daxiongmao.core.utils.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.UUID;

/**
 * Unit tests of security services utilities
 * @author Guillaume Diaz
 * @since 2020/04
 * @version 1.0
 */
@Log4j2
public class SecurityServiceTest {

    /** To decode a base64 String salt representation into binary (byte[]) */
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    /**
     * To validate the slow equals method and ensure that wrong or correct password take about the same time to be processed.<br>
     * To be pertinent this test must be done on a huge amount of trial, at least 250 000.<br>
     * Timing attacks are proven possible in maths, but very very hard in real life due to network latency.
     */
    @Test
    public void testSlowCompare() {
        // Configuration
        final int testTrials = 1000000;
        final int saltSize = 128;
        final int hashSize = 256;
        final String dumbPassword = "#{2020.04-T3stPwd..Dur1nGC%viD-19}";

        // Generate salt
        final CtrDrbgSaltGenerator saltGeneratorUtil = new CtrDrbgSaltGenerator();
        final String salt = saltGeneratorUtil.generateSalt(saltSize);
        Assertions.assertNotNull(salt);

        // Encrypt password
        final Argon2PasswordHash argon2PasswordHash = new Argon2PasswordHash();
        final String hash = argon2PasswordHash.hashPassword(dumbPassword, hashSize, decoder.decode(salt));

        // **************** Attempt to validate password many, many times *********************
        long minimumTimeInNanoSecs = Long.MAX_VALUE;
        long maximumTimeInNanoSecs = 0L;
        long totalExecutionTimeInNanoSecs = 0L;
        int numberOfTrials = 0;
        for (int trial = 0 ; trial < testTrials; trial++) {
            final String randomPassword = (trial % 1000 == 0 ? UUID.randomUUID().toString() : "test");
            long startTime = System.nanoTime();
            SecurityService.slowEquals(randomPassword.getBytes(), hash.getBytes());
            long endTime = System.nanoTime();
            long testRun = endTime - startTime;
            totalExecutionTimeInNanoSecs += testRun;
            numberOfTrials++;
            if (testRun > maximumTimeInNanoSecs) {
                log.debug("trial {} # max time (ns)={}", trial, testRun);
                maximumTimeInNanoSecs = testRun;
            }
            if (testRun < minimumTimeInNanoSecs) {
                log.debug("trial {} # min time (ns)={}", trial, testRun);
                minimumTimeInNanoSecs = testRun;
            }
        }
        long averageTime = (long) (totalExecutionTimeInNanoSecs / numberOfTrials);

        // Real password
        long startTime = System.nanoTime();
        SecurityService.slowEquals(dumbPassword.getBytes(), hash.getBytes());
        long endTime = System.nanoTime();
        long realPasswordTime = endTime - startTime;

        log.info("Number of password tested: {} | Times in nano-seconds: average={}, max={}, min={}, real={}", numberOfTrials, averageTime, maximumTimeInNanoSecs, minimumTimeInNanoSecs, realPasswordTime);

        Assertions.assertTrue(realPasswordTime <= maximumTimeInNanoSecs && realPasswordTime >= minimumTimeInNanoSecs);
    }
}
