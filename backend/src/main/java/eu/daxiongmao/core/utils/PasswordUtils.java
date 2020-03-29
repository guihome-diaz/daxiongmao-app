package eu.daxiongmao.core.utils;

import eu.daxiongmao.core.model.dto.UserDTO;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.util.Strings;

/**
 * Set of utilities to validate / generate passwords.
 * This is conform to OWASP recommendations (see <a href="https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/Authentication_Cheat_Sheet.md">OWASP authentication cheat sheet</a>)
 * @author Guillaume Diaz
 * @version 1.0 - 2019/11
 */
public class PasswordUtils {

    /** Argon2i uses data-independent memory access,  which is recommended for password hashing and password-based key derivation */
    private static final int ARGON2_MODE = Argon2Parameters.ARGON2_i;



}
