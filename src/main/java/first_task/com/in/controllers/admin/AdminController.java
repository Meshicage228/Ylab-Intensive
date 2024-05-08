package first_task.com.in.controllers.admin;

import first_task.com.annotations.AdminAccessCheck;
import first_task.com.dto.UserDto;
import first_task.com.service.UserActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/admin")
@Api(value = "/admin", tags = "Admin's controller")
@AdminAccessCheck
public class AdminController {
    private final UserActionService userService;

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
}
