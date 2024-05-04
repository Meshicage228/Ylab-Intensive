package first_task.com.in.servlets.authorize;

import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthenticationService service;

    @PostMapping
    public ResponseEntity login(@RequestBody LoginUserDto loginUserDto) {
        try {
            UserDto userDto = service.registrationProcess(loginUserDto.getUsername(), loginUserDto.getPassword());

            return ResponseEntity.status(CREATED).body(userDto);
        } catch (NotUniqueUserNameException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
