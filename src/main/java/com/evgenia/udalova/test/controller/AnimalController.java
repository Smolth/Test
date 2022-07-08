package com.evgenia.udalova.test.controller;

import com.evgenia.udalova.test.entity.AnimalEntity;
import com.evgenia.udalova.test.model.AnimalModel;
import com.evgenia.udalova.test.repository.AnimalRepository;
import com.evgenia.udalova.test.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AnimalController {
    TestService testService;
    AnimalRepository animalRepository;


    @GetMapping("/wrong")
    public ResponseEntity wrong(){
        return ResponseEntity.badRequest().body("Питомец с такими данными уже существует");
    }

    @GetMapping("/notFound")
    public ResponseEntity notFound(){
        return ResponseEntity.badRequest().body("Данный питомец не зарегистрирован");
    }

    @GetMapping("/regAnimal/{owner}")
    public String regAnimal(@PathVariable String owner, Model model){
        model.addAttribute("clientName", owner);
        return "registration/registrationAnimal";
    }

    @PostMapping("/regAnimal/{owner}")
    @PreAuthorize("hasAuthority('developers:read')")
    public String addPet(@RequestParam String name, @RequestParam String age, @RequestParam String sex, @RequestParam String about, @PathVariable String owner){
        log.info("Adding animal ");
        AnimalModel animalModel = new AnimalModel(name, age, sex, about);
        AnimalEntity animalEntity = testService.addAnimal(animalModel, owner);
        if (animalEntity == null) {
            return "redirect:/auth/wrong";
        } else {
            return "redirect:/auth/animal/" + animalEntity.getAnimalNumber();
        }
    }

    @GetMapping("/animal/{animalNumber}")
    public String animalCard(@PathVariable long animalNumber, Model model) {
        log.info("Review animal " + animalNumber);
        AnimalEntity animalEntity = animalRepository.getByAnimalNumber(animalNumber);
        if (animalEntity != null) {
            model.addAttribute("animal", animalEntity);
            return "auth/animal";
        } else return "redirect:/auth/notFound";
    }

    @GetMapping("/delAnimal/{animalNumber}")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity delete(@PathVariable long animalNumber) {
        log.info("Delete animal " + animalNumber);
        animalRepository.deleteById(animalNumber);
        return ResponseEntity.ok("Питомец удалён");
    }

    @GetMapping("/editAnimal/{animalNumber}")
    @PreAuthorize("hasAuthority('developers:read')")
    public String editAnimal(@PathVariable long animalNumber, Model model){
        log.info("Load edit animal " + animalNumber);
        model.addAttribute("animal", animalRepository.getByAnimalNumber(animalNumber));
        return "auth/animalEdit";
    }

    @PostMapping("/editAnimal/{animalNumber}/{owner}")
    @PreAuthorize("hasAuthority('developers:read')")
    public String edit(@PathVariable long animalNumber, @RequestBody AnimalModel animalModel, @PathVariable String owner) {
        log.info("Edit animal " + animalNumber);
        AnimalEntity animalEntity = testService.updateAnimal(animalModel, owner, animalNumber);
        return "redirect:/auth/animal/" + animalEntity.getAnimalNumber();
    }

}
