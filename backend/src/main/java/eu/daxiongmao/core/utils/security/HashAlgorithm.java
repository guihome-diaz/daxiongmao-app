package eu.daxiongmao.core.utils.security;

/**
 * List of hash algorithms supported by the application.
 * @version 1.0 - 2020/04
 * @since 2020/04
 * @author Guillaume Diaz
 */
public enum HashAlgorithm {

    /**
     * <p>
     * <strong>Argon2i</strong> uses data-independent memory access, which is recommended for
     * password hashing and password-based key derivation.<br>
     * Argon2i is optimized for more dangerous settings, where the adversary possibly can access
     * the same machine, use its CPU or mount cold-boot attacks.</p>
     * <p>see <a href="https://password-hashing.net/argon2-specs.pdf">Argon2 specifications</a></p>
     */
    ARGON_2I;

}
