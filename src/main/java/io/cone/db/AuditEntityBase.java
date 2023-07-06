package io.cone.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AuditEntityBase extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;

    @CreationTimestamp
    @JsonProperty("created_datetime")
    @Column(name = "created_datetime", updatable = false)
    private ZonedDateTime createDateTime;

    @UpdateTimestamp
    @JsonProperty("updated_datetime")
    @Column(name = "updated_datetime")
    private ZonedDateTime updatedDateTime;
}
