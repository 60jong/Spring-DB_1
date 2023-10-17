package site._60jong.jdbc.lecture.respository.exception;

public class MyDbException extends RuntimeException {

    public MyDbException() {
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }
}
