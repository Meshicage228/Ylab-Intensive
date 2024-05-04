package first_task.com.in.servlets.workout;

import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/workoutType")
public class WorkoutTypeController {
    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity workoutType(@RequestBody WorkoutTypeDto workoutType, @PathVariable int userId) {
        try {
            WorkoutTypeDto answer = workoutService.saveWorkoutType(userId, workoutType);
            return ResponseEntity.status(CREATED).body(answer);
        } catch (NotUniqueTypeTitleException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity workoutTypes() {
        return ResponseEntity.ok(workoutService.getAllTypes());
    }
}
