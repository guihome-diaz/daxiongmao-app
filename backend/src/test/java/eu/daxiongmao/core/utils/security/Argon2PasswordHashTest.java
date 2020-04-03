package eu.daxiongmao.core.utils.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

/**
 * Test suite for the PASSWORD HASH
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
@Log4j2
public class Argon2PasswordHashTest {

    /** class to test */
    private Argon2PasswordHash argon2PasswordHash = new Argon2PasswordHash();

    /** To decode a base64 String salt representation into binary (byte[]) */
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    /** Hash ASCII password */
    @Test
    public void hashAsciiPassword() {
        final byte[] salt = decoder.decode("RzeFOkS458OKAY3s5VXw5WgNWYo0e0LnbkvGvMghJMQRKCwwMMdVDIgXO9LOqfNFHmyRX79pecmZEy93KRKpKoX8pI05ZfDEQliggvdbqggdr3PovFcH1-2ZcUxuEOymYucAmm3_HmgRTJ1AAHzia5llQh_SwM7DgLOhCr0bUUg=");
        final String password = "Hell0-TH3re.It1s@simple(very=simple]PassWORD)";
        final int hashSize = 256;
        final String expectation = "9cxYHndBAo7CMWoEFNI1D-o_nZkjIcksLVa0sYU4GWQbDkm4XHVaN2eqg1-L-cdrRQU9xLqWsuvUbqo0rUpxZckv7kMAuvCpkIJdu2Uvy89HPc2k2d6y2XH5slCL9ADlXB2151tUphY5pxmrq3jBjFeAuag-suTNeK80o7l_NVYax47voQy5dY-7MDAUQUTMVRvTCJ2sThhYR6X4hILasLqkdcVKub1zJwrJSHxDDIhT3AWfLRZL0pmqmCgDTKh1OHtMzQEeu0Jdp8mo0vhGfd9e_1cH1m_SEvegTebfdfgFpJRc8J1fNUg9C1gFVwXl7ZrtQ69wNZ2_cBzUB--rmQ==";

        // Do hash
        final String hash = argon2PasswordHash.hashPassword(password, hashSize, salt);
        Assertions.assertEquals(expectation, hash);
        Assertions.assertEquals(344, hash.length());
    }

    /** Hash European languages password */
    @Test
    public void hashEuropeanPassword() {
        final byte[] salt = decoder.decode("RzeFOkS458OKAY3s5VXw5WgNWYo0e0LnbkvGvMghJMQRKCwwMMdVDIgXO9LOqfNFHmyRX79pecmZEy93KRKpKoX8pI05ZfDEQliggvdbqggdr3PovFcH1-2ZcUxuEOymYucAmm3_HmgRTJ1AAHzia5llQh_SwM7DgLOhCr0bUUg=");
        final String password = "Hell0-TH3re.ILétaitUn€FÕâêîôûÂÊÎÔÛs@simpleÄËÏÖÜŸ)";
        final String passwordVariant = "Hell0-TH3re.ILétaitUn€FÕâêîôûÂÊÎÔÛs@simpleAËÏÖÜŸ)";
        final String asciiPassword = "Hell0-TH3re.ILetaitUneFOaeiouAEIOUs@simpleAEIOUY)";
        final int hashSize = 256;

        // expectations
        // --> each password with special characters has 2 expectations because the hash result depends on the tool: Intellij / Maven
        //     this is probably related to encoding handling. On windows maven build UTF-8 binaries but run with CP512
        final String expectationPasswordIntellij = "MefLlsFE6UAWrSfd7OF3PcMqQVMvWltO1tw4a4xaO5P5QwDDOcLnMf9dNrAnzwH7V4XMEw-PBPFNqdyB8Yb3gUZQCP8eRo0CX5wAi6Sy_cKI7eab5lRL9R3CaVAqska0vqIsMon_jhrO4NZDne2lLry9QSbw8fSCGEYF2ZXP5ueTcYkQOPeZtDOED_hRaDvHrqO3AFvUiII4zd_VEQiCHjdcqjarP3Esu2yi3AsxccVoD-F5Ku30F2aPNUsmGpVMFXTdieZirEZT8b8RguzFZo63gXrwvyunWCsLA5ZiINqbKcPIQF5A4_nC-akV1KODOPlCZ3RUFR4HKO2xY_x5jA==";
        final String expectationPasswordMaven = "RGCwjSmlf6lhyz_F-LtzshpmCCFRyxrjQm7eXMdT_kcXjKrHmwt92IQUnI0I4BkNHecm9_F0UjCfARbFXK7-E_45XgxZXrTntNI2XKyzvlEK4t8VRDfPVJSzmZEy1AnaUnF1j5Qw-lzoVHk0Vo5Y4ICkDGOYOhxvbAPCAWtk_2aAIxSNmI1QMI5E-4IkpURZ6QMdysFQXlYkHFY77Pbgr0rCbMOhSODnQUrAJZLj-GMQ2b2KfOogbGZnV_8fj1wiyDJqyaufaL97zgt-EiH0RCZPhtf158kU1Wmu2ZShEMuVFn6yFamfl7abMYpw_w2VT1u0A_03orjMssU_6Q_mUg==";
        final String expectationPasswordVariantIntellij = "nmzqbMY8b_oucMp-WgmPD9Wz-FJGvIB-ScSfIqyrrYHpbH31AX0Je68gCVF7pyOXQ9xNOQL-K50pocekydRfd3DdXBtbRmq1PNcGu4GojmxCI5I2d27KjVdIrOPmpqDFQ2-Rt6nKQeqo4zU6pclSPkBp5AAhvLX9fmcdkyF-Pr_gW5YCURkoKAi72jewauwfFug9NQSafuCecCZr1lVLXHLqezsq76ABOARWU6L8Qu9vEvzvcjxUR1hl3_k4cF-X4ze0lDcHlhnfmEBPbhcX9gSoAJAacU2aMgLFM4JS2WoJSCvO5VFwUfgf28Jy518paneE-7mS9LkPEwUbo2uEQA==";
        final String expectationPasswordVariantMaven = "gL0FK3KtLq6VRqP6Fak_9ngKfipYv9625a6DlTYUUKDOuw205TKb2NlhdsoZAJiZQgi7yPeFZX7zHhAq01jERnfd_QE55jxwspq7wnH2SSUAdQaIs78cg7ILoFFrz2vVVAD7h-TmN0sDsZKoFdGeFUJi2lAycnJ6Ex8hKIb2oGpNCSRJkr7ezxkGpN0bNcUhFpd7-TAscpVnjipjU7v2WjKWHIFAn00AEXSn50mINmJXgO9VRnhvqZQHcKflZacLCYK0dUUXSM9A5ffebnyYqnzdeoHWCVtS1_xmaLHSFjNHnDfTjKe-EHgx5Yjyi_bMMXrj3Zx_SnIv-eW2Cg5cyw==";
        final String expectationAsciiPassword = "UP55-Y_-ECheHpTEqttFt5J6WeNdxtn5mMLh7u6KOa27jZWW1TE5PWwOJgx2IwzYT8tM-EEDYsfkrIqxe7jpMsULBf7X54HSKgvpRpVY-eXJOME20t2HdmvGfdak5N2mK8KFr5ytUOi533-wQailjiHceMDMnROqFkiTzzaWbI5JU70qcodP812VsRbjTrngAODUETrA5tyNwZUa43qGSm72lFcBZ-jHN8bwgqv27D5uj0QJ88JQtX4--8c3WHxKajo31I1e3SNCzTaOP9a56CvIVxyX67inDq_eKkB-0d4dgmMggF6bdoVKgYuK91d_p7ZBAR3bEuk2NEPaOV6Pgw==";

        // Do hash
        final String hashPassword = argon2PasswordHash.hashPassword(password, hashSize, salt);
        final String hashPasswordVariant = argon2PasswordHash.hashPassword(passwordVariant, hashSize, salt);
        final String hashAsciiPassword = argon2PasswordHash.hashPassword(asciiPassword, hashSize, salt);
        // assertions
        Assertions.assertTrue(hashPassword.equals(expectationPasswordIntellij) || hashPassword.equals(expectationPasswordMaven));
        Assertions.assertEquals(344, hashPassword.length());
        Assertions.assertTrue(hashPasswordVariant.equals(expectationPasswordVariantIntellij) || hashPasswordVariant.equals(expectationPasswordVariantMaven));
        Assertions.assertEquals(344, hashPasswordVariant.length());
        Assertions.assertEquals(expectationAsciiPassword, hashAsciiPassword);
        Assertions.assertEquals(344, hashAsciiPassword.length());
        Assertions.assertNotEquals(hashPassword, hashPasswordVariant);
        Assertions.assertNotEquals(hashPassword, hashAsciiPassword);
        Assertions.assertNotEquals(hashPasswordVariant, hashAsciiPassword);
    }

    /** Hash European languages password */
    @Test
    public void hashChinesePassword() {
        final byte[] salt = decoder.decode("RzeFOkS458OKAY3s5VXw5WgNWYo0e0LnbkvGvMghJMQRKCwwMMdVDIgXO9LOqfNFHmyRX79pecmZEy93KRKpKoX8pI05ZfDEQliggvdbqggdr3PovFcH1-2ZcUxuEOymYucAmm3_HmgRTJ1AAHzia5llQh_SwM7DgLOhCr0bUUg=");
        final String password = "20200331西班牙卫生部应急与预警协调中心副主任玛利亚·何塞·谢拉31日在新闻发布会上说";
        final int hashSize = 256;
        // expectations
        final String expectationPasswordIntellij = "If8HbtW0Ax5l-JbvKO8Wu4DS7V4xWgMAU8Ccf6QqJkrfJ-Vod_o31MAMzck_cUjWyZ5LvcdcRkfF1whWU4tXbARIChaguWawJepni6FjHVC4QVSV6fV0u7T65411r_-LEF0KA941CptsHOpLB2R0Q57PPs2XdhQtvrkj8aa_LwrR3_AsXLVpMBo1dTKASxVkLDdjPLcY3Q1TKNdEU28Av1No0MNWa40z9cMJz0VDVc39sX7TrSicYATERJNqhzzD6jX3ILKxrbyxJ9aSvqzrgmiylh7RHmh569NVI3kJdMxRFs8zX_yBf9wPtQ33cPvHEYsXJmsM7BOcWfovp0vzEw==";
        final String expectationPasswordMaven = "i5-UZ_P0zVN7ZZ6lkX1l8Buf0ViDIsp7E7_tBteoTSyUWApKGGnz1LN3c1fwn7wicD_VkCfPVc8JEe0_n_YUCdJozbOXrx8aeoqay8duUH7O4ynLLpTC_zoVH55eUctSjYWx1w17pR-VaEZkReMT34dUrl_DjyiYgb1WhAo7735UtLrOq7txc7oQ8ypGeKfZd_JoDgDZ-m7ZRkATwjA60ounOMZkWoENlq7-SZ6ifZcDhZtX8-I7gjYAcEqRAPK9QugSWPaLjNEtYgjhYUb2h1IIvf7xcRMTWkz4eJMsgzCNyGnf5OjgLRKaJOGe-j6qrk92P5TXCBnv1y7VXNA7tw==";

        // Do hash
        final String hashPassword = argon2PasswordHash.hashPassword(password, hashSize, salt);
        // assertions
        Assertions.assertTrue(hashPassword.equals(expectationPasswordIntellij) || hashPassword.equals(expectationPasswordMaven));
        Assertions.assertEquals(344, hashPassword.length());
    }

    @Test
    public void getAlgorithm() {
        final String expectation = "Password hash: {Algorithm: Argon2i | Provider: BouncyCastle | Version: 19 | Iterations: 3 | Memory/thread: 128 kb | Threads: 4 | with salt: yes}, base64 encoding, config 2020-03";
        Assertions.assertEquals(expectation, argon2PasswordHash.getHashAlgorithm());
    }
}
