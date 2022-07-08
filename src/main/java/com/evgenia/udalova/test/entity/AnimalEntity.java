package com.evgenia.udalova.test.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "animals")
@Setter
@Getter
@ToString
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_generator")
    @SequenceGenerator(name="animal_generator", sequenceName = "animal_seq", allocationSize = 1, initialValue = 100)
    private Long animalNumber;
    private String name;
    private String age;
    private String sex;
    private String about;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClientEntity client;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnimalEntity entity = (AnimalEntity) o;
        return animalNumber != null && Objects.equals(animalNumber, entity.animalNumber);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
