package com.tuusuario.inventory_api.dto;

import com.tuusuario.inventory_api.model.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MovementRequest(
    @NotNull Long productId,
    @NotNull MovementType type,
    @Min(1) Integer quantity,
    String reason
) {}