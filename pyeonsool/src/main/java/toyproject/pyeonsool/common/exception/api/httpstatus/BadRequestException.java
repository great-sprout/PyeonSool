package toyproject.pyeonsool.common.exception.api.httpstatus;

import org.springframework.http.HttpStatus;
import toyproject.pyeonsool.common.exception.api.PyeonSoolApiException;

public class BadRequestException extends PyeonSoolApiException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
