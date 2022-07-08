package com.evgenia.udalova.test.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class RazminkaService {
    private final List list = new ArrayList();
    public String reverse(String str) {
        String res = new StringBuffer(str).reverse().toString();
        return res;
    }
}
