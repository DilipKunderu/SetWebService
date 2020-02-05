package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractSetController {
    @Autowired
    AbstractSetService abstractSetService;

    @RequestMapping(value = "/item/{item}", method = RequestMethod.GET)
    public boolean HasItem(@PathVariable("item") String item) {
        return abstractSetService.HasItem(item);
    }
}
