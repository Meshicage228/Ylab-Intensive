package first_task.com.in.controllers.authorize;

import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.exceptions.InappropriateDataException;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Api(value = "/register", tags = "Authorization Controller")
public class RegisterController {
    private final AuthenticationService service;

    @ApiOperation(
            value = "Registers user to the app",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PostMapping
    public ResponseEntity<UserDto> authorize(@Valid @RequestBody LoginUserDto loginUserDto,
                                             BindingResult bindingResult) throws NotUniqueUserNameException, InappropriateDataException {
        if (bindingResult.hasErrors()) {
            throw new InappropriateDataException(bindingResult);
        }
        UserDto userDto = service.registrationProcess(loginUserDto.getUsername(), loginUserDto.getPassword());
        return ResponseEntity.status(CREATED).body(userDto);
    }
}
