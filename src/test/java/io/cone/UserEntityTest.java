package io.cone;

import io.cone.db.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserEntityTest {

    @Test
    @Order(0)
    @Transactional
    void test() throws IOException {
       List<User> users = User.listAll();
        Assertions.assertEquals(users.size(), 0);


       User user = new User();
       user.setEmail("abc@abc.com");
       user.setName("abc");
       user.setPassword("123");

       // Insert
       user.persist();

        users = User.listAll();
        Assertions.assertEquals(users.size(), 1);

        // update
        /*int i = User.update("password='1234' where email = 'abc@abc.com'");
        Assertions.assertEquals(1, i);*/

        // update user password
        user.setPassword("1234");
        user.persist();

        User u = User.find("email", "abc@abc.com").firstResult();
        Assertions.assertEquals("1234", u.getPassword());

        // delete
        user.delete();
        users = User.listAll();
        Assertions.assertEquals(users.size(), 0);

    }

}
