package toyproject.pyeonsool.common.exception.form;

import lombok.Getter;

@Getter
public class PyeonSoolFieldException extends PyeonSoolFormException {
    private String field;

    public PyeonSoolFieldException(String message, String errorCode, String field) {
        super(message, errorCode);
        this.field = field;
    }
}
