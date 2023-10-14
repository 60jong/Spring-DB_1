package site._60jong.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class UnCheckedTest {

    @Test
    void repository_throw_checked() {
        Repository repository = new Repository();

        assertThrows(
                MyUncheckedException.class,
                () -> repository.call()
        );
    }

    @Test
    void service_checked_catch() {
        Service service = new Service();

        assertDoesNotThrow(
                () -> service.callCatch()
        );
    }

    @Test
    void service_checked_throw() {
        Service service = new Service();

        assertThrows(
                MyUncheckedException.class,
                () -> service.callThrow()
        );
    }

    /**
     * Unchecked 예외는 잡지 않아도 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리 message : {}", e.getMessage(), e);
            }
        }

        public void callThrow() {
            repository.call();
        }
    }

    static class Repository {

        public void call()  {
            throw new MyUncheckedException("ex");
        }
    }
}
