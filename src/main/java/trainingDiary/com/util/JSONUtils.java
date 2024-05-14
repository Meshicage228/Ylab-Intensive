package trainingDiary.com.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, создающий Scanner во всем приложении
 */
@Component
public class JSONUtils {

    public String formJsonErrorMessage(String message) {
        String errorMessage = "{\"error\": \"%s\"}";
        return String.format(errorMessage, message);
    }
    public String fromBindingResultToJson(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, List<String>> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
            });

            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(errors);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "{}";
    }
}
