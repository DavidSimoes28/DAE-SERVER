package exceptions;

import java.io.Serializable;

public class MyEntityNotFoundException  extends Exception implements Serializable {
    public MyEntityNotFoundException() {
    }

    public MyEntityNotFoundException(String msg) {
        super(msg);
    }
}
