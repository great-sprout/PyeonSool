package toyproject.pyeonsool.common.exception.form;

public class DuplicateUserIdException extends PyeonSoolFieldException {
    public DuplicateUserIdException(String message, String errorCode, String field) {
        super(message, errorCode, field);
    }
}