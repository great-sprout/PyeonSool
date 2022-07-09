package toyproject.pyeonsool.common.exception.api.httpstatus;

import org.springframework.http.HttpStatus;
import toyproject.pyeonsool.common.exception.api.PyeonSoolApiException;

public class ForbiddenException extends PyeonSoolApiException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
