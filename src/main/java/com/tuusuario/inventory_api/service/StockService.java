package com.tuusuario.inventory_api.service;

import com.tuusuario.inventory_api.dto.MovementRequest;
import com.tuusuario.inventory_api.dto.MovementResponse;
import com.tuusuario.inventory_api.dto.ProductRequest;
import com.tuusuario.inventory_api.exception.InsufficientStockException;
import com.tuusuario.inventory_api.model.MovementType;
import com.tuusuario.inventory_api.model.Product;
import com.tuusuario.inventory_api.model.StockMovement;
import com.tuusuario.inventory_api.repository.ProductRepository;
import com.tuusuario.inventory_api.repository.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductRepository productRepository;
    private final StockMovementRepository movementRepository;

    @Transactional
    public Product createProduct(ProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException(
                "Ya existe un producto con el SKU: " + request.sku()
            );
        }
        Product product = new Product();
        product.setName(request.name());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setCurrentStock(
            request.initialStock() != null ? request.initialStock() : 0
        );
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Producto no encontrado con id: " + id
            ));
    }

    @Transactional
    public MovementResponse registerMovement(MovementRequest request) {
        Product product = productRepository.findById(request.productId())
            .orElseThrow(() -> new EntityNotFoundException(
                "Producto no encontrado con id: " + request.productId()
            ));

        if (request.type() == MovementType.EXIT) {
            if (product.getCurrentStock() < request.quantity()) {
                throw new InsufficientStockException(
                    "Stock insuficiente. Disponible: " + product.getCurrentStock()
                    + ", Solicitado: " + request.quantity()
                );
            }
            product.setCurrentStock(product.getCurrentStock() - request.quantity());
        } else {
            product.setCurrentStock(product.getCurrentStock() + request.quantity());
        }

        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(request.type());
        movement.setQuantity(request.quantity());
        movement.setReason(request.reason());
        movement.setCreatedAt(LocalDateTime.now());
        movementRepository.save(movement);

        return new MovementResponse(
            movement.getId(),
            product.getId(),
            product.getName(),
            movement.getType(),
            movement.getQuantity(),
            movement.getReason(),
            product.getCurrentStock(),
            movement.getCreatedAt()
        );
    }

    public List<MovementResponse> getMovementsByProduct(Long productId) {
        return movementRepository.findByProductId(productId).stream()
            .map(m -> new MovementResponse(
                m.getId(),
                m.getProduct().getId(),
                m.getProduct().getName(),
                m.getType(),
                m.getQuantity(),
                m.getReason(),
                m.getProduct().getCurrentStock(),
                m.getCreatedAt()
            )).toList();
    }
}