package com.evgenia.udalova.test.controller;

import com.evgenia.udalova.test.entity.AnimalEntity;
import com.evgenia.udalova.test.entity.ClientEntity;
import com.evgenia.udalova.test.model.ClientModel;
import com.evgenia.udalova.test.repository.AnimalRepository;
import com.evgenia.udalova.test.repository.ClientRepository;
import com.evgenia.udalova.test.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@AllArgsConstructor
@Slf4j
public class ClientController {
    TestService testService;
    ClientRepository clientRepository;
    AnimalRepository animalRepository;
    public static int block = 0;

    @GetMapping("/wrongData")
    public ResponseEntity exception(){
        return ResponseEntity.badRequest().body("Пользователь с такими данными уже существует.");
    }

    @GetMapping("/blocking")
    public ResponseEntity blockClient(){
        return ResponseEntity.badRequest().body("Достигнуто максимальное число попыток авторизации. Следующая попытка будет доступна по истечению часа.");
    }

    @GetMapping("/registration")
    public String reg(){
        return "registration/login";
    }

    @GetMapping("/")
    public String login() {
        log.info("Start app");
        return "registration/login";
    }

    @GetMapping("/reg")
    public String clientRegistration(@ModelAttribute("client") ClientModel client) {
        log.info("registration");
        return "registration/registrationClient";
    }

    @PostMapping("/addClient")
    public String saveClient(@ModelAttribute("client") ClientModel clientModel) {
        log.info("Registration of client");
        if (clientRepository.findByClientName(clientModel.getName()) != null) {
            return "redirect:/wrongData";
        } else {
            ClientEntity client = testService.addClient(clientModel);
            log.info(client.toString());
            return "redirect:/";
        }
    }

    @PostMapping("/auth/login")
    public String account(@RequestParam String username, @RequestParam String password, Model model){
        Runnable operation = new Runnable() {
            @Override
            public void run() {
                block = 0;
            }
        };

        if (block == 10) {
            return "redirect:/blocking";
        } else {
            log.info("Searching user");
            ClientEntity entity = testService.login(username);
            if (entity == null || !entity.getPassword().equals(password)) {
                block++;
                if (block == 10) {
                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                    executor.schedule(operation, 60, TimeUnit.MINUTES);
                    return "redirect:/blocking";
                }
                return "redirect:/registration";
            } else {
                log.info(entity.toString());
                model.addAttribute("client", entity);
                return "userPage";
            }
        }
    }

    @GetMapping("/auth/animalList/{owner}")
    public String listOfPets(Model model, @PathVariable String owner ) {
        log.info("Output of client's animals");
        ClientEntity client = testService.login(owner);
        List<AnimalEntity> animalEntityList  = animalRepository.getAllByClient(client);
        model.addAttribute("animals", animalEntityList);
        return "auth/animalList";
    }

}
