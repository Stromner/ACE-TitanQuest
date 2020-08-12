package tq.character.editor.core.errors;

import org.springframework.core.NestedCheckedException;

public class IllegalPlayerDataException extends NestedCheckedException {
    public IllegalPlayerDataException(String msg) {
        super(msg);
    }

    public IllegalPlayerDataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
