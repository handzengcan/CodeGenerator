package tech.jebsun.codegenerator.exceptions;

/**
 * Created by JebSun on 2018/3/5.
 */
public class AppException extends Exception {

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
