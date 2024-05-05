package first_task.com.in.servlets.authorize;

import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Api(value = "/login", tags = "Login controller")
public class LoginController {
    private final AuthenticationService service;

    @ApiOperation(
            value = "Login existing user to the app",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PostMapping
    public ResponseEntity login(@Valid @RequestBody LoginUserDto loginUserDto,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<UserDto> user = service.logIn(loginUserDto);

            return user.<ResponseEntity>
                    map(userDto -> ResponseEntity.status(OK).body(userDto))
                    .orElseGet(() -> ResponseEntity.status(UNAUTHORIZED).build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
