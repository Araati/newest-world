package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.AbstractObject;
import com.newestworld.content.facade.AbstractObjectFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/abstract_object")
@RequiredArgsConstructor
public class AbstractObjectController {

    private final AbstractObjectFacade facade;

    @GetMapping("/{id}")
    public AbstractObject findById(@PathVariable final long id)    {
        return facade.findById(id);
    }

}
