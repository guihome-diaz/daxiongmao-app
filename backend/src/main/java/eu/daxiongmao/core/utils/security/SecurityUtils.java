package eu.daxiongmao.core.utils.security;


/**
 * <p>
 * Set of utilities to perform secure operations.
 * </p>
 *
 * @author Guillaume Diaz.
 * @version 1.0
 * @since 2020/04, application's creation
 */
public class SecurityUtils {

    private SecurityUtils() {
        // private factory
    }

    /**
     * <p>To compare 2 data in 'length-constant' time, this is especially useful for hash comparison.</p>
     * <p>Comparing the hashes in "length-constant" time ensures that an attacker cannot extract the hash of a
     * password in an on-line system using a timing attack, then crack it off-line.</p>
     * <p>The standard way to check if two sequences of bytes (strings) are the same is to compare the first
     * byte, then the second, then the third, and so on. As soon as you find a byte that isn't the same for
     * both strings, you know they are different and can return a negative response immediately.
     * If you make it through both strings without finding any bytes that differ, you know the strings are
     * the same and can return a positive result.
     * This means that comparing two strings can take a different amount of time depending on how much
     * of the strings match.
     * </p>
     * <p>
     * For example, a standard comparison of the strings "<code>xyzabc</code>" and "<code>abcxyz</code>" would
     * immediately see that the first character is different and wouldn't bother to check the rest of the string.
     * On the other hand, when the strings "<code>aaaaaaaaaaB</code>" and "<code>aaaaaaaaaaZ</code>" are compared,
     * the comparison algorithm scans through the block of "<code>a</code>" before it determines the strings are
     * unequal.
     * </p>
     * <p>It might seem like it would be impossible to run a timing attack over a network.
     * However, it has been done, and has been shown to be practical.
     * That's why the code on this page compares strings in a way that takes the same amount of time no matter
     * how much of the strings match. </p>
     * <p>This method is based on <a href="https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html">OWASP</a>
     *  and <a href="https://crackstation.net/hashing-security.htm">Defuse security # hashing security</a></p>
     * @param a left data block
     * @param b right data block
     * @return true if blocks are identical
     */
    protected static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
