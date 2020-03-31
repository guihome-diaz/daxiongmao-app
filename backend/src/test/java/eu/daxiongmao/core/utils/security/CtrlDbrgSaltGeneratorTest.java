package eu.daxiongmao.core.utils.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Test suite for the SALT generator
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
@Log4j2
public class CtrlDbrgSaltGeneratorTest {

    private CtrDrbgSaltGenerator saltGeneratorUtil = new CtrDrbgSaltGenerator();

    /**
     * To generate a lot of salt, enough to force reseed.
     */
    @Test
    public void generateSaltsAndTriggerReseed() {
        final int saltSize = 128;
        Set<String> salts = new HashSet<>();
        for (int i = 0; i < (CtrDrbgSaltGenerator.NUMBER_OF_ITERATIONS_BEFORE_RESEED + 2); i++) {
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
        final String reEncodedSalt = saltGeneratorUtil.encoder.encodeToString(decodeSalt);
        Assertions.assertNotNull(reEncodedSalt);
        Assertions.assertEquals(newSalt, reEncodedSalt);
    }

    /**
     * To generate a SALT and check its length
     */
    @Test
    public void generateSalt() {
        final int saltSize = 128;
        final String newSalt = saltGeneratorUtil.generateSalt(saltSize);
        Assertions.assertNotNull(newSalt);
        log.debug("example of generated salt: {}", newSalt);
        Assertions.assertEquals( 172, newSalt.length());
    }

    @Test
    public void getSaltAlgorithmName() {
        final String saltAlgorithm = saltGeneratorUtil.getSaltAlgorithm();
        Assertions.assertNotNull(saltAlgorithm);
        Assertions.assertTrue(saltAlgorithm.contains(CtrDrbgSaltGenerator.SECURE_RANDOM_MECHANISM));
        Assertions.assertTrue(saltAlgorithm.contains(CtrDrbgSaltGenerator.SECURE_RANDOM_GENERATOR_TYPE));
        Assertions.assertTrue(saltAlgorithm.contains(CtrDrbgSaltGenerator.SECURE_RANDOM_ALGORITHM));
        Assertions.assertTrue(saltAlgorithm.contains(CtrDrbgSaltGenerator.SECURE_RANDOM_COMMENTS));
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
            saltGeneratorUtil.generateSalt(CtrDrbgSaltGenerator.MINIMUM_SALT_SIZE - 1);
            Assertions.fail("Failure expected: too short SALT size");
        } catch (IllegalArgumentException e) {
            // OK
        }

        // Success
        try {
            final String salt = saltGeneratorUtil.generateSalt(CtrDrbgSaltGenerator.MINIMUM_SALT_SIZE);
            Assertions.assertNotNull(salt);
        } catch (IllegalArgumentException e) {
            Assertions.fail("If should not fail");
        }
    }

}
