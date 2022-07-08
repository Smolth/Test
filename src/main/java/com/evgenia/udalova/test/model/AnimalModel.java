package com.evgenia.udalova.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnimalModel {
    private String name;
    private String age;
    private String sex;
    private String about;
}
