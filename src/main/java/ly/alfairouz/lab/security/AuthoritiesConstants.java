package ly.alfairouz.lab.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";


    public static final String RECEPTION = "ROLE_RECEPTION";

    public static final String TYPING = "ROLE_TYPING";

    public static final String EMBEDDING = "ROLE_EMBEDDING";

    public static final String MICROTOME = "ROLE_MICROTOME";

    public static final String STAINING = "ROLE_STAINING";

    public static final String REVISION = "ROLE_REVISION";

    public static final String TECHNICIAN = "ROLE_TECHNICIAN";


    public static final String GROSSING_DOCTOR = "ROLE_GROSSING_DOCTOR";

    public static final String PATHOLOGIST_DOCTOR = "ROLE_PATHOLOGIST_DOCTOR";

    public static final String REFERRING_DOCTOR = "ROLE_REFERRING_DOCTOR";

    public static final String REFERRING_CENTER = "ROLE_REFERRING_CENTER";


    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
