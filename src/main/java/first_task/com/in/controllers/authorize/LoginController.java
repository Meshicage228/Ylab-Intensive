package first_task.com.in.controllers.authorize;

import first_task.com.dto.CurrentUser;
import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.exceptions.InappropriateDataException;
import first_task.com.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Api(value = "/login", tags = "Login controller")
public class LoginController {
    private final AuthenticationService service;
    private final CurrentUser userDto;

    @ApiOperation(
            value = "Login existing user to the app",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PostMapping
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginUserDto loginUserDto,
                                         BindingResult bindingResult) throws InappropriateDataException {
        if (bindingResult.hasErrors()) {
            throw new InappropriateDataException(bindingResult);
        }

        Optional<UserDto> user = service.logIn(loginUserDto);

        if(user.isPresent()) {
            UserDto dto = user.get();
            userDto.setId(dto.getId());
            userDto.setRole(dto.getRole());
            userDto.setUsername(dto.getUsername());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(UNAUTHORIZED).build();
    }
}
