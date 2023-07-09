package io.cone;

import io.cone.db.Person;
import io.cone.db.Status;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
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

        Person personA = new Person();
        personA.name = "A";
        personA.birth = LocalDate.of(1910, Month.FEBRUARY, 1);
        personA.status = Status.Alive;
        personA.persist();

        Person personB = new Person();
        personB.name = "B";
        personB.birth = LocalDate.of(1910, Month.FEBRUARY, 1);
        personB.status = Status.Alive;
        personB.persist();

        persons = Person.listAll();
        Assertions.assertEquals(3, persons.size());

        person.name = "ABC";
        person.persist();

        person = Person.find("name", "ABC").firstResult();
        Assertions.assertEquals("ABC",person.name);

        /*person.delete();
        persons = Person.listAll();
        Assertions.assertEquals(2, persons.size());*/

        PanacheQuery<Person> livingPersons = Person.find("status", Status.Alive);
        livingPersons.page(Page.ofSize(1));

        List<Person> list = livingPersons.list();
        Assertions.assertEquals(1, list.size());

        List<Person> list2 = livingPersons.nextPage().list();
        Assertions.assertEquals(1, list2.size());

       List<Person> personsPageFrom1 = livingPersons.page(Page.of(1,1)).list();
       Assertions.assertEquals(1, personsPageFrom1.size());

        int pageCount = livingPersons.pageCount();
        Assertions.assertEquals(3, pageCount);

        long count = livingPersons.count();
        Assertions.assertEquals(count, 3);

        List<Person> orderedPersons = Person.list("order by name");
        Assertions.assertEquals("A",orderedPersons.get(0).name);

        Person p = Person.findByName("A");
        Assertions.assertEquals("A", p.name);
        Assertions.assertEquals(Status.Alive, p.status);

        long statusP1 = Person.countByStatus(Status.Alive);
        Assertions.assertEquals(3, statusP1);


        //List<Person> sortedPersons = Person.list(Sort.by("name"));
        //Assertions.assertEquals("A", sortedPersons.get(0).name);
    }
}
