package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

public class AbstractUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserService userService;
}
