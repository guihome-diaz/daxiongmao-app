package eu.daxiongmao.core.utils.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test suite for the SALT generator
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public class SaltGeneratorTest {

    private SaltGeneratorUtil saltGeneratorUtil = new SaltGeneratorUtil();

    /**
     * To generate a lot of salt, enough to force reseed.
     */
    @Test
    public void generateSaltsAndTriggerReseed() {
        final int saltSize = 128;
        Set<String> salts = new HashSet<>();
        for (int i = 0 ; i < (SaltGeneratorUtil.NUMBER_OF_ITERATIONS_BEFORE_RESEED + 2); i++) {
            final String newSalt = saltGeneratorUtil.generateSalt(saltSize);
            // Ensure each salt is unique
            Assertions.assertFalse(salts.contains(newSalt));
            salts.add(saltGeneratorUtil.generateSalt(saltSize));
        }
    }

    /**
     * To generate a SALT, decode and re-encode it to ensure it is not changed by encoding functions
     */
    @Test
    public void generateAndDecodeSalt() {
        final int saltSize = 128;
        final String newSalt = saltGeneratorUtil.generateSalt(saltSize);
        Assertions.assertNotNull(newSalt);

        final byte[] decodeSalt = saltGeneratorUtil.decodeSalt(newSalt);
        Assertions.assertNotNull(decodeSalt);

        // re-encode and check results
        final String reEncodedSalt = saltGeneratorUtil.encodeSalt(decodeSalt);
        Assertions.assertNotNull(reEncodedSalt);
        Assertions.assertEquals(newSalt, reEncodedSalt);
    }

    @Test
    public void getSaltAlgorithmName() {
        final String saltAlgorithm = saltGeneratorUtil.getSaltAlgorithm();
        Assertions.assertNotNull(saltAlgorithm);
        System.out.println(saltAlgorithm);
        Assertions.assertTrue(saltAlgorithm.contains(SaltGeneratorUtil.SECURE_RANDOM_MECHANISM));
        Assertions.assertTrue(saltAlgorithm.contains(SaltGeneratorUtil.SECURE_RANDOM_GENERATOR_TYPE));
        Assertions.assertTrue(saltAlgorithm.contains(SaltGeneratorUtil.SECURE_RANDOM_ALGORITHM));
        Assertions.assertTrue(saltAlgorithm.contains(SaltGeneratorUtil.SECURE_RANDOM_COMMENTS));
    }

    @Test
    public void invalidSaltSize() {
        // no salt
        try {
            saltGeneratorUtil.generateSalt(0);
            Assertions.fail("Failure expected: too short SALT size");
        } catch (IllegalArgumentException e) {
            // OK
        }

        // not enough size
        try {
            saltGeneratorUtil.generateSalt(SaltGeneratorUtil.MINIMUM_SALT_SIZE - 1);
            Assertions.fail("Failure expected: too short SALT size");
        } catch (IllegalArgumentException e) {
            // OK
        }

        // Success
        try {
            final String salt = saltGeneratorUtil.generateSalt(SaltGeneratorUtil.MINIMUM_SALT_SIZE);
            Assertions.assertNotNull(salt);
        } catch (IllegalArgumentException e) {
            Assertions.fail("If should not fail");
        }
    }

}
