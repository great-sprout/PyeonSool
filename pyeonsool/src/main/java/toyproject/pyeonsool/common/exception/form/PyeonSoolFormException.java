package toyproject.pyeonsool.common.exception.form;

import lombok.Getter;
import toyproject.pyeonsool.common.exception.PyeonSoolException;

@Getter
public class PyeonSoolFormException extends PyeonSoolException {
    private String errorCode;

    public PyeonSoolFormException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
