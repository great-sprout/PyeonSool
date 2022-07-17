package toyproject.pyeonsool.common.exception.api.httpstatus;

import org.springframework.http.HttpStatus;
import toyproject.pyeonsool.common.exception.api.PyeonSoolApiException;

public class UnauthorizedException extends PyeonSoolApiException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
