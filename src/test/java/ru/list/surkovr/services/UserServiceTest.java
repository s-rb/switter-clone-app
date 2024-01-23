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

        verify(userRepository, times(1)).save(user);;
    }

    @Test
    public void userFailTest() {
        User user = new User();

        user.setUsername("John");

        doReturn(new User())
                .when(userRepository).findByUsername("John");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertFalse(isUserCreated);
        verify(userRepository, times(0)).save(any(User.class));;
    }
}