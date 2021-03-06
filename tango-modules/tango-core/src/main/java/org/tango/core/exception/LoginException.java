package org.tango.core.exception;

/**
 * User: tango
 * Date: 13-6-5
 * Time: 下午6:02
 */
public class LoginException extends RuntimeException {

    private Error error;

    public static enum Error {

        UN_EXISTS(-1), ACCOUNT_ERROR(2), IP_MAC_ERROR(3), STATE_ERROR(4);

        int value;

        private Error(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public LoginException() {
    }

    public LoginException(Error error) {
        this.error = error;
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return error.toString().toLowerCase();
    }
}
