package com.evgenia.udalova.test.service;

import com.evgenia.udalova.test.entity.AnimalEntity;
import com.evgenia.udalova.test.entity.ClientEntity;
import com.evgenia.udalova.test.model.AnimalModel;
import com.evgenia.udalova.test.model.ClientModel;
import com.evgenia.udalova.test.model.Role;
import com.evgenia.udalova.test.repository.AnimalRepository;
import com.evgenia.udalova.test.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestService {
    ClientRepository clientRepository;
    AnimalRepository animalRepository;

    public ClientEntity addClient(ClientModel client) {
        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setClientName(client.getName());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setRole(Role.USER);

        return clientRepository.save(clientEntity);
    }

    public ClientEntity login(String name) {
        ClientEntity client;
        client = clientRepository.getByClientName(name);
        return client;
    }

    public AnimalEntity addAnimal(AnimalModel animal, String clientName) {
        ClientEntity client = clientRepository.getByClientName(clientName);

        String about = animal.getAbout();
        String name = animal.getName();
        if (animalRepository.findByName(name) != null) {
            return null;
        } else if (animalRepository.findByAbout(about) != null) {
            return null;
        } else {
            AnimalEntity animalEntity = new AnimalEntity();
            animalEntity.setName(name);
            animalEntity.setAge(animal.getAge());
            animalEntity.setSex(animal.getSex());
            animalEntity.setAbout(about);
            animalEntity.setClient(client);
            return animalRepository.save(animalEntity);
        }
    }
    
    public AnimalEntity updateAnimal(AnimalModel animal, String clientName, long number) {
        ClientEntity client = clientRepository.getByClientName(clientName);

        animalRepository.deleteById(number);

            AnimalEntity animalEntity = new AnimalEntity();
            animalEntity.setAnimalNumber(number);
            animalEntity.setName(animal.getName());
            animalEntity.setAge(animal.getAge());
            animalEntity.setSex(animal.getSex());
            animalEntity.setAbout(animal.getAbout());
            animalEntity.setClient(client);

            return animalRepository.save(animalEntity);

    }
}
