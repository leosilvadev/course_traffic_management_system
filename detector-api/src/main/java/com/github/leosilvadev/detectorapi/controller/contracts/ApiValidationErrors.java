package com.github.leosilvadev.detectorapi.controller.contracts;

import java.util.List;

public record ApiValidationErrors(List<ApiValidationError> errors) {

    public record ApiValidationError(String field, String error) {

    }
}
