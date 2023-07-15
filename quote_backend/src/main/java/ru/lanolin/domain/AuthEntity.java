package ru.lanolin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "auth_entity")
public class AuthEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid_v7_gen")
    @GenericGenerator(
            name = "uuid_v7_gen",
            type = ru.lanolin.config.UUIDv7Generator.class
    )
    @Column(name = "id", nullable = false)
    private UUID id;

    private String login;

    private String email;

    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthEntity that = (AuthEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}