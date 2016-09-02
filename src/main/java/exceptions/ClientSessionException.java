package exceptions;

/**
 * Created by lew on 9/2/16.
 */
public class ClientSessionException extends Exception {
    public ClientSessionException() {
        super();
    }

    public ClientSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
