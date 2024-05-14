package trainingDiary.com.in.controllers.admin;

import org.springframework.web.bind.annotation.*;
import trainingDiary.com.annotations.AdminAccessCheck;
import trainingDiary.com.dto.AuditDto;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.service.AuditLogService;
import trainingDiary.com.service.UserActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import trainingDiary.com.service.WorkoutService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor

@RestController
@RequestMapping("/admin")
@Api(value = "/admin", tags = "Admin's controller")
@AdminAccessCheck
public class AdminController {
    private final UserActionService userService;
    private final WorkoutService workoutService;
    private final AuditLogService auditLogService;

    @ApiOperation(
            value = "Get all users with their workouts",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @ApiOperation(
            value = "Delete users workout for admin",
            httpMethod = "DELETE",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @DeleteMapping("/users/{userId}/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable("userId") int userId,
                                              @PathVariable("workoutId") int workoutId) {
        workoutService.deleteWorkout(userId, workoutId);
        return ResponseEntity.status(OK).build();
    }

    @ApiOperation(
            value = "Gives all workouts of specific user for admin",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @GetMapping("/users/{userId}")
    public ResponseEntity<ArrayList<WorkoutDto>> getAllUsersWorkouts(@PathVariable("userId") int userId) {
        ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);
        return ResponseEntity.ok(workoutDtos);
    }

    @ApiOperation(
            value = "Gives all audit logs of application",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @GetMapping("/audit")
    public ResponseEntity<ArrayList<AuditDto>> getAllAuditLogs() {
        ArrayList<AuditDto> logEntries = auditLogService.getLogEntries();
        return ResponseEntity.ok(logEntries);
    }
}
