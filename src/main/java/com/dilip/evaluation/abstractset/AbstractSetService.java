package com.dilip.evaluation.abstractset;

import org.springframework.stereotype.Service;

@Service
public class AbstractSetService {
    AbstractSetImpl set = new AbstractSetImpl();

    public boolean HasItem(String item) {
        return set.HasItem(item);
    }
}
