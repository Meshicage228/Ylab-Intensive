package test;

import com.service.AuthenticationService;
import com.exceptions.NotUniqueUserNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Класс, тестирующий {@link AuthenticationService} класс аунтификации */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест процесса регистрации")
class AuthenticationServiceTest {
    @Spy
    private AuthenticationService service;

    @Test
    @DisplayName("Неудачная регистрация пользователя")
    void registrationProcessFail(){
        Mockito.doReturn(true).when(service).userIsExists(Mockito.any(), Mockito.any());

        Assertions.assertThrows(NotUniqueUserNameException.class, () -> service.registrationProcess(Mockito.anyString(), Mockito.anyString()));
    }

}