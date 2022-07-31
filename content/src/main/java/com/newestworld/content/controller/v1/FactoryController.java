package com.newestworld.content.controller.v1;

import com.newestworld.content.dto.Factory;
import com.newestworld.content.dto.FactoryCreateDTO;
import com.newestworld.content.dto.FactoryUpdateDTO;
import com.newestworld.content.facade.FactoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// TODO: 01.08.2022 Взаимодействие пользователя только через Action. Удалить
@RestController
@RequestMapping("/v1/factory")
@RequiredArgsConstructor
public class FactoryController {

    private final FactoryFacade factoryFacade;

    @PostMapping
    public Factory create(@Valid @RequestBody final FactoryCreateDTO request) {
        return factoryFacade.create(request);
    }

    @PostMapping("/{id}")
    public Factory update(@RequestBody final FactoryUpdateDTO request, @PathVariable final long id)  {
        return factoryFacade.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        factoryFacade.delete(id);
    }

    @GetMapping("/{id}")
    public Factory findById(@PathVariable final long id)    {
        return factoryFacade.findById(id);
    }
}
