package io.cone.db;

import java.time.LocalDate;

import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.getByName", query = "from Person where name = ?1"),
        @NamedQuery(name = "Person.countByStatus", query = "select count(*) from Person p where p.status = :status"),
        @NamedQuery(name = "Person.updateStatusById", query = "update Person p set p.status = :status where p.id = :id"),
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = ?1")
})
public class Person extends PanacheEntity {
    public String name;
    public LocalDate birth;
    public Status status;
    public static Person findByName(String name){
        return find("#Person.getByName", name).firstResult();
    }

    public static long countByStatus(Status status) {
        return count("#Person.countByStatus", Parameters.with("status", status).map());
    }

    public static long updateStatusById(Status status, long id) {
        return update("#Person.updateStatusById", Parameters.with("status", status).and("id", id));
    }

    public static long deleteById(long id) {
        return delete("#Person.deleteById", id);
    }

}