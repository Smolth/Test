package com.evgenia.udalova.test.repository;

import com.evgenia.udalova.test.entity.AnimalEntity;
import com.evgenia.udalova.test.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends CrudRepository<AnimalEntity, Long> {
    AnimalEntity getByAnimalNumber(long number);
    AnimalEntity getByName(String name);

    @Query(
            value="SELECT name FROM animals  WHERE animalNumber = ?1",
            nativeQuery = true
            )
    String getNameById(long number);
    @Query(
            value="SELECT age FROM animals  WHERE animalNumber = ?1",
            nativeQuery = true
    )
    String getAgeById(long number);
    @Query(
            value="SELECT sex FROM animals  WHERE animalNumber = ?1",
            nativeQuery = true
    )
    String getSexById(long number);
    @Query(
            value="SELECT about FROM animals  WHERE animalNumber = ?1",
            nativeQuery = true
    )
    String getAboutById(long number);

    AnimalEntity findByName(String name);
    AnimalEntity findByAbout(String about);
    List<AnimalEntity> getAllByClient(ClientEntity clientEntity);

}
