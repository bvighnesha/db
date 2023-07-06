package io.cone;

import io.cone.db.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserEntityTest {

    @Test
    @Order(0)
    @Transactional
    void test() throws IOException {
       List<User> users = User.listAll();

        Assertions.assertEquals(users.size(), 0);
    }

}
