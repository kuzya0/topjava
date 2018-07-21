package ru.javawebinar.topjava.web;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    @Qualifier("InMemoryUserRepositoryImpl")
    private InMemoryUserRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        System.out.println("INIT");
        repository.init();
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(USER_ID);
        Collection<User> users = controller.getAll();
        assertEquals(1, users.size());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(10);
    }

    @Test
    public void  add(){
        User u = new User();
        u.setRoles(Arrays.asList(Role.ROLE_USER));
        u.setCaloriesPerDay(1200);
        u.setEmail("emeil");
        u.setPassword("pass");
        User user = controller.create(u);
        assertNotNull(user);
    }

    @Test
    public void  update(){
        User user = controller.get(USER_ID);
        user.setEmail("newEmeail");
        controller.update(user,USER_ID);

        User user1 = controller.get(USER_ID);
        assertEquals(user.getEmail(), user1.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public  void  updateNotFound(){
        User user = controller.get(USER_ID);
        user.setEmail("newEmeail");
        controller.update(user,ADMIN_ID);
    }
}
