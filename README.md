# Ecommerce API

Podstawowy system obsługi zamówień w sklepie internetowym.
Projekt został zrealizowany w **Spring Boot + Spring Data JPA** z bazą danych **PostgreSQL**.

## Funkcjonalności
- Rejestracja klienta
- Dodanie produktu
- Złożenie zamówienia
- Pobranie szczegółów zamówienia po numerze zamówienia

### Uruchomienie z docker-compose

`docker-compose up --build`

Aplikacja będzie dostępna pod adresem: `http://localhost:8080`

### Endpointy REST API
- `POST /api/customers` - Rejestracja klienta
- `POST /api/products` - Dodanie produktu
- `POST /api/orders` - Złożenie zamówienia
- `GET /api/orders/{orderNumber}` - Pobranie szczegółów zamówienia po numerze zamówienia

### Testy
Aby uruchomić testy:

`./mvnw test`

Przetestować API można za pomocą **Postman**, przygotowana kolekcja jest dołączona do projektu.


