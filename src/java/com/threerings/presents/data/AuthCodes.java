//
// $Id: AuthCodes.java,v 1.2 2002/07/17 18:10:04 mdb Exp $

package com.threerings.presents.data;

/**
 * Basic authentication response codes.
 */
public interface AuthCodes
{
    /** A code indicating that no user exists with the specified
     * username. */
    public static final String NO_SUCH_USER = "m.no_such_user";

    /** A code indicating that the supplied password was invalid. */
    public static final String INVALID_PASSWORD = "m.invalid_password";

    /** A code indicating that an internal server error occurred while
     * trying to log the user on. */
    public static final String SERVER_ERROR = "m.server_error";

    /** A code indicating that the server is not available at the moment. */
    public static final String SERVER_UNAVAILABLE = "m.server_unavailable";
}
