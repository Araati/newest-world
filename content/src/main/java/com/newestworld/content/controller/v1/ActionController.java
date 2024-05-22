package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.Action;
import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.facade.ActionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/action")
@RequiredArgsConstructor
public class ActionController {

    private final ActionFacade actionFacade;

    @Operation(summary = "Create an Action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Action.class)) }),
            @ApiResponse(responseCode = "404", description = "ActionStructure with this name doesn't exist",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Field validation error",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Field missing",
                    content = @Content)

    })
    @PostMapping
    public Action create(@Valid @RequestBody final ActionCreateDTO request) {
        return actionFacade.create(request);
    }

    // TODO: 25.11.2023 update

    @Operation(summary = "Delete an Action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Action not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        actionFacade.delete(id);
    }

    @Operation(summary = "Find an Action by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Action",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Action.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Action not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public Action findById(@PathVariable final long id)    {
        return actionFacade.findById(id);
    }

}
