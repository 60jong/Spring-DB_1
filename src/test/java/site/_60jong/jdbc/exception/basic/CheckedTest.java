package site._60jong.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CheckedTest {

    @Test
    void repository_throw_checked() {
        Repository repository = new Repository();

        assertThrows(
                MyCheckedException.class,
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
                MyCheckedException.class,
                () -> service.callThrow()
        );
    }

    /**
     * Exception을 상속받은 예외는 체크 예외
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // 예외 처리 로직
                log.info("예외 처리 message : {}", e.getMessage(), e);
            }
        }

        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {

        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }
}

