package first_task.com.in.controllers.handlres;

import first_task.com.exceptions.InappropriateDataException;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.exceptions.NotUniqueWorkoutException;
import first_task.com.util.JSONUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler {
    private final JSONUtils jsonUtils;

    @ExceptionHandler(InappropriateDataException.class)
    public ResponseEntity<String> handleInappropriateDataException(InappropriateDataException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(jsonUtils.fromBindingResultToJson(e.getResult()));
    }

    @ExceptionHandler(NotUniqueUserNameException.class)
    public ResponseEntity<String> handleException(NotUniqueUserNameException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotUniqueWorkoutException.class)
    public ResponseEntity<String> handleException(NotUniqueWorkoutException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(jsonUtils.formJsonErrorMessage("Smth went wrong!"));
    }

    @ExceptionHandler(NotUniqueTypeTitleException.class)
    public ResponseEntity<String> handleException(NotUniqueTypeTitleException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }
}
