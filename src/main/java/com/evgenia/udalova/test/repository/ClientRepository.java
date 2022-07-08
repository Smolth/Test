package com.evgenia.udalova.test.repository;

import com.evgenia.udalova.test.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    ClientEntity getByClientID(Long clientID);
    ClientEntity getByClientName(String clientName);
    ClientEntity findByClientName(String clientName);

}
