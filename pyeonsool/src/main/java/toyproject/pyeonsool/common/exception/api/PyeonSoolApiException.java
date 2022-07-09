package toyproject.pyeonsool.common.exception.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import toyproject.pyeonsool.common.exception.PyeonSoolException;

@Getter
public class PyeonSoolApiException extends PyeonSoolException {
    private HttpStatus status;

    public PyeonSoolApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
