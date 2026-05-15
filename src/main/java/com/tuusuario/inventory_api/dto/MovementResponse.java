package com.tuusuario.inventory_api.dto;

import com.tuusuario.inventory_api.model.MovementType;
import java.time.LocalDateTime;

public record MovementResponse(
    Long id,
    Long productId,
    String productName,
    MovementType type,
    Integer quantity,
    String reason,
    Integer stockAfterMovement,
    LocalDateTime createdAt
) {}