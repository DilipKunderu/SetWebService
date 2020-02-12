package com.dilip.evaluation.abstractset;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractSetController {
    Logger logger = LoggerFactory.getLogger(AbstractSetController.class);

    @Autowired
    ISetService<String> setService;

    @RequestMapping(value = "/item/{item}", method = RequestMethod.GET)
    public boolean HasItem(@PathVariable("item") String item) {
        logger.info("HasItem called on: " + item );
        return setService.HasItem(item);
    }

    @RequestMapping(value = "/item/{item}", method = RequestMethod.POST)
    public boolean AddItem(@PathVariable("item") String item) {
        logger.info("AddItem called on: " + item );
        return setService.AddItem(item);
    }

    @RequestMapping(value = "/item/{item}", method = RequestMethod.DELETE)
    public boolean DeleteItem(@PathVariable("item") String item) {
        logger.info("DeleteItem called on: " + item );
        return setService.RemoveItem(item);
    }
}
