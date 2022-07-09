package toyproject.pyeonsool.common.exception.api.httpstatus;

import org.springframework.http.HttpStatus;
import toyproject.pyeonsool.common.exception.api.PyeonSoolApiException;

public class NotFoundException extends PyeonSoolApiException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
