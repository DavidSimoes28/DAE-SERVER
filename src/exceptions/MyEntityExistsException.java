package exceptions;

import java.io.Serializable;

public class MyEntityExistsException extends Exception implements Serializable {

    public MyEntityExistsException() {
    }

    public MyEntityExistsException(String msg) {
        super(msg);
    }
}
