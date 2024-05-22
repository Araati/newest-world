package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.AbstractObject;
import com.newestworld.content.facade.AbstractObjectFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Find an AbstractObject by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the AbstractObject",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractObject.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "AbstractObject not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public AbstractObject findById(@PathVariable final long id)    {
        return facade.findById(id);
    }

}
