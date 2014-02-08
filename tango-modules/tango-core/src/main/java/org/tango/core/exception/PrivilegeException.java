package org.tango.core.exception;

/**
 * User: tango
 * Date: 13-6-5
 * Time: 下午6:02
 */
public class PrivilegeException extends RuntimeException {
    public PrivilegeException() {
    }

    public PrivilegeException(String message) {
        super(message);
    }

    public PrivilegeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrivilegeException(Throwable cause) {
        super(cause);
    }
}
