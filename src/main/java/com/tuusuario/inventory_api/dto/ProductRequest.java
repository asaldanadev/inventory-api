package com.tuusuario.inventory_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
    @NotBlank String name,
    @NotBlank String sku,
    String description,
    @Min(0) Integer initialStock
) {}