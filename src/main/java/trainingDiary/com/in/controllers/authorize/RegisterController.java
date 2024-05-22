package trainingDiary.com.in.controllers.authorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import trainingDiary.com.dto.LoginUserDto;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.exceptions.InappropriateDataException;
import trainingDiary.com.exceptions.NotUniqueUserNameException;
import trainingDiary.com.service.AuthenticationService;
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
public class RegisterController {
    private final AuthenticationService service;

    @Operation(
            description = "Will register new user in application",
            summary = "register new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "saved user",
                            content = {
                                    @Content(schema = @Schema(implementation = UserDto.class),
                                            mediaType = "application/json")
                            }
                    ),
            }
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
