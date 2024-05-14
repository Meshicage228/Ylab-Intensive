package trainingDiary.com.exceptions.handlres;

import trainingDiary.com.exceptions.InappropriateDataException;
import trainingDiary.com.exceptions.NotUniqueTypeTitleException;
import trainingDiary.com.exceptions.NotUniqueUserNameException;
import trainingDiary.com.exceptions.NotUniqueWorkoutException;
import trainingDiary.com.util.JSONUtils;
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
                .status(UNAUTHORIZED)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotUniqueWorkoutException.class)
    public ResponseEntity<String> handleException(NotUniqueWorkoutException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotUniqueTypeTitleException.class)
    public ResponseEntity<String> handleException(NotUniqueTypeTitleException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(jsonUtils.formJsonErrorMessage(e.getMessage()));
    }
}
