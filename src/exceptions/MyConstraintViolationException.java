package exceptions;

import java.io.Serializable;

public class MyConstraintViolationException extends Exception implements Serializable {

    public MyConstraintViolationException() {
    }

    public MyConstraintViolationException(String msg) {
        super(msg);
    }
}
