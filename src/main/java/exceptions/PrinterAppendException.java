package exceptions;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class PrinterAppendException extends Exception {
    public PrinterAppendException() {
    }

    public PrinterAppendException(String message) {
        super(message);
    }

    public PrinterAppendException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrinterAppendException(Throwable cause) {
        super(cause);
    }

    public PrinterAppendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
