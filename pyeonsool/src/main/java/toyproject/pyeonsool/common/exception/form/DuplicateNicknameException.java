package toyproject.pyeonsool.common.exception.form;

public class DuplicateNicknameException extends PyeonSoolFieldException {
    public DuplicateNicknameException(String message, String errorCode, String field) {
        super(message, errorCode, field);
    }
}