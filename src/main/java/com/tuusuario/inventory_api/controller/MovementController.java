package com.tuusuario.inventory_api.controller;

import com.tuusuario.inventory_api.dto.MovementRequest;
import com.tuusuario.inventory_api.dto.MovementResponse;
import com.tuusuario.inventory_api.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<MovementResponse> registerMovement(
            @Valid @RequestBody MovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(stockService.registerMovement(request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<MovementResponse>> getMovementsByProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getMovementsByProduct(productId));
    }
}