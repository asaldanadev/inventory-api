# Inventory API 📦

API REST para control de inventario con validación de stock en tiempo real.

## Stack

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| PostgreSQL | 15 |
| Lombok | 1.18.30 |
| SpringDoc OpenAPI | 2.3.0 |
| Render | Deploy |

## Endpoints

### Productos `/api/products`

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/` | Listar todos |
| GET | `/{id}` | Buscar por ID |
| POST | `/` | Crear producto |

### Movimientos `/api/movements`

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/` | Registrar movimiento |
| GET | `/product/{id}` | Historial por producto |

## Ejecución local

**Requisitos:** Java 17, Docker

```bash
docker run --name inventory-db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=inventorydb \
  -p 5432:5432 \
  -d postgres:15

./mvnw clean spring-boot:run
```

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Demo

API desplegada en Render (free tier — primer request puede tardar ~50s):

🔗 [Swagger UI]()

## Autor

[asaldanadev](https://github.com/asaldanadev)