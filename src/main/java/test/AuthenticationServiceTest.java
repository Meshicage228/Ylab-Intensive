package test;

import firstTask.com.exceptions.NotUniqueUserNameException;
import firstTask.com.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertThrows(NotUniqueUserNameException.class, () -> service.registrationProcess(Mockito.anyString(), Mockito.anyString()));
    }

}