package exceptions;

import java.io.Serializable;

public class MyEntityCantBeDeletedException extends Exception implements Serializable {
    public MyEntityCantBeDeletedException() {
    }

    public MyEntityCantBeDeletedException(String message) {
        super(message);
    }
}
