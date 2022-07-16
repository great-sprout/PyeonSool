package toyproject.pyeonsool.common.exception.form;

public class IncorrectPasswordException extends PyeonSoolFieldException {
    public IncorrectPasswordException(String message, String errorCode, String field) {
        super(message, errorCode, field);
    }
}
