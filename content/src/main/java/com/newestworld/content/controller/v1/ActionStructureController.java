package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.ActionStructure;
import com.newestworld.content.dto.ActionStructureCreateDTO;
import com.newestworld.content.facade.ActionStructureFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/action/structure")
@RequiredArgsConstructor
public class ActionStructureController {

    private final ActionStructureFacade actionStructureFacade;

    @Operation(summary = "Create an ActionStructure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ActionStructure created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActionStructure.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "ActionStructure with this name already exists",
                    content = @Content)

    })
    @PostMapping
    public ActionStructure create(@Valid @RequestBody final ActionStructureCreateDTO request) {
        return actionStructureFacade.create(request);
    }

    // TODO: 27.04.2022 update

    @Operation(summary = "Delete an ActionStructure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ActionStructure deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "ActionStructure not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        actionStructureFacade.delete(id);
    }

    @Operation(summary = "Find an ActionStructure by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the ActionStructure",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActionStructure.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "ActionStructure not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ActionStructure findById(@PathVariable final long id)    {
        return actionStructureFacade.findById(id);
    }

}
