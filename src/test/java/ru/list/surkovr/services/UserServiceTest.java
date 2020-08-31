package ru.list.surkovr.services;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.list.surkovr.domain.User;
import ru.list.surkovr.enums.Role;
import ru.list.surkovr.repositories.UserRepository;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MailSender mailSender;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("some@mail.ru");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(
                Collections.singleton(Role.USER)
        ));

        verify(userRepository, times(1)).save(user);
        verify(mailSender, times(1))
                .send(ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("activation code"),
                        ArgumentMatchers.contains("Welcome to Switter"));
    }

    @Test
    public void userFailTest() {
        User user = new User();

        user.setUsername("John");

        doReturn(new User())
                .when(userRepository).findByUsername("John");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertFalse(isUserCreated);
        verify(userRepository, times(0)).save(any(User.class));
        verify(mailSender, times(0))
                .send(anyString(), anyString(), anyString());
    }

    @Test
    public void activateUser() {
        String activateCode = "activate";
        User user = new User();
        user.setActivationCode("some another code");
        doReturn(user)
                .when(userRepository)
                .findByActivationCode(activateCode);

        boolean isUserActivated = userService.activateUser(activateCode);

        Assert.assertTrue(isUserActivated);
        Assert.assertNull(user.getActivationCode());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void activateFailTest() {
        boolean isUserActivated = userService.activateUser("activate me");

        Assert.assertFalse(isUserActivated);

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test(expected = ArithmeticException.class)
    public void error() {
        int i = 0;
        int s = 1 / i;
    }
}