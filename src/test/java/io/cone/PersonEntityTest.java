package io.cone;

import io.cone.db.Person;
import io.cone.db.Status;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonEntityTest {
    @Test
    @Order(0)
    @Transactional
    void test() throws IOException {
        List<Person> persons = Person.listAll();
        Assertions.assertEquals(0,persons.size());

        Person person = new Person();
        person.name = "Mortal";
        person.birth = LocalDate.of(1910, Month.FEBRUARY, 1);
        person.status = Status.Alive;
        person.persist();

        persons = Person.listAll();
        Assertions.assertEquals(1, persons.size());

        int updatedCount = Person.update("name = 'Mortal' where status = ?1", Status.Alive);
        Assertions.assertEquals(1, updatedCount);

        persons = Person.listAll();

        Assertions.assertEquals(1, persons.size());

        person = Person.find("name", "Mortal").firstResult();
        Assertions.assertEquals("Mortal",person.name);

        person.delete();
        persons = Person.listAll();
        Assertions.assertEquals(0, persons.size());
    }
}
