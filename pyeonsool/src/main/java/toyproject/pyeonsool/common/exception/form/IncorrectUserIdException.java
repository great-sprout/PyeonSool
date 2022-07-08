package toyproject.pyeonsool.common.exception.form;

public class IncorrectUserIdException extends PyeonSoolFieldException{
    public IncorrectUserIdException(String message, String errorCode, String field) {
        super(message, errorCode, field);
    }
}
