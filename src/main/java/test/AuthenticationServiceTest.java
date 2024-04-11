package test;

import firstTask.com.exceptions.NotUniqueUserNameException;
import firstTask.com.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Класс, тестирующий {@link AuthenticationService} класс аунтификации */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Spy
    private AuthenticationService service;

    @Test
    void registrationProcessFail(){
        Mockito.doReturn(true).when(service).userIsExists(Mockito.any());

        assertThrows(NotUniqueUserNameException.class, () -> service.registrationProcess(Mockito.anyString(), Mockito.anyString()));
    }

}