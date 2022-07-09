package toyproject.pyeonsool.common.exception.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handlePyeonSoolApiException(PyeonSoolApiException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ExceptionResponse(e.getStatus().value(), e.getMessage()));
    }
}
