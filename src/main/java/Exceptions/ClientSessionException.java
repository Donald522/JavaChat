package Exceptions;

/**
 * Created by lew on 9/2/16.
 */
public class ClientSessionException extends Exception {
    public ClientSessionException() {
    }

    public ClientSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
