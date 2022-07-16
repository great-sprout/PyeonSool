package toyproject.pyeonsool.common.exception.form;

public class DuplicatePhoneException extends PyeonSoolFieldException{
    public DuplicatePhoneException(String message, String errorCode, String field) {
        super(message, errorCode, field);
    }
}
