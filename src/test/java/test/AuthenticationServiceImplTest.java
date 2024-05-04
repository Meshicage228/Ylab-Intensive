package test;

import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Класс, тестирующий {@link AuthenticationServiceImpl} класс аунтификации */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест процесса регистрации")
class AuthenticationServiceImplTest {
    @Spy
    private AuthenticationServiceImpl service;

    @Test
    @DisplayName("Неудачная регистрация пользователя")
    void registrationProcessFail(){
        Mockito.doReturn(true).when(service).userIsExists(Mockito.anyString());

        assertThrows(NotUniqueUserNameException.class, () -> service.registrationProcess(Mockito.anyString(), Mockito.anyString()));
    }

}