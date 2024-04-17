package com.github.leosilvadev.detectorapi.controller.docs;

import com.github.leosilvadev.detectorapi.controller.contracts.ApiValidationErrors;
import com.github.leosilvadev.detectorapi.controller.contracts.DetectionRegistration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface DetectionRegistrar {

    @Operation(
            description = "Register a new vehicle detection",
            responses = {
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiValidationErrors.class),
                                    examples = @ExampleObject(value = """
                                          {"errors":[{"field":"speed","error":"must be greater than or equal to 1"},{"field":"id","error":"must not be null"},{"field":"equipmentId","error":"must not be null"}]}
                                          """)
                            )
                    )
            }
    )
    public void registerDetection(DetectionRegistration registration);

}
