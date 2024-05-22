package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.facade.AbstractObjectStructureFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/abstract_object/structure")
@RequiredArgsConstructor
public class AbstractObjectStructureController {

    private final AbstractObjectStructureFacade facade;

    @Operation(summary = "Create an AbstractObjectStructure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AbstractObjectStructure created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractObjectStructure.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "AbstractObjectStructure with this name already exists",
                    content = @Content)

    })
    @PostMapping
    public AbstractObjectStructure create(@Valid @RequestBody final AbstractObjectStructureCreateDTO request) {
        return facade.create(request);
    }

    // todo: update

    @Operation(summary = "Delete an AbstractObjectStructure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AbstractObjectStructure deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "AbstractObjectStructure not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        facade.delete(id);
    }

    @Operation(summary = "Find an AbstractObjectStructure by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the AbstractObjectStructure",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractObjectStructure.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "AbstractObjectStructure not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public AbstractObjectStructure findById(@PathVariable final long id)    {
        return facade.findById(id);
    }

}
