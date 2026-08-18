# API Contracts

This is the promise each service makes to its consumers — written down so
another developer can integrate against either service without opening its
entity classes or database code (Day 2 acceptance criteria).

## User Service — `http://localhost:8081`

### `GET /api/users/{id}`

| Outcome | Status | Body |
|---|---|---|
| User exists | `200 OK` | `UserResponse` |
| User does not exist | `404 Not Found` | `ErrorResponse` (`error: "USER_NOT_FOUND"`) |
| `{id}` isn't a number | `400 Bad Request` | `ErrorResponse` (`error: "INVALID_REQUEST"`) |

**`UserResponse`**
```json
{
  "id": 1,
  "fullName": "Asha Rao",
  "email": "asha.rao@example.com"
}
```
Internal-only fields (e.g. account `status`) are never included — this is
the whole point of having a dedicated response model instead of returning
the `User` entity.

---

## Order Service — `http://localhost:8082`

### `GET /api/orders/{id}`

Internally calls User Service's `GET /api/users/{id}` and folds the result
into the response. This endpoint therefore has **four** distinct outcomes,
not two — the core Day 2 lesson is not collapsing them into one generic
error:

| Outcome | Status | Body | Why |
|---|---|---|---|
| Order + user both found | `200 OK` | `OrderResponse` (with nested `user`) | happy path |
| Order id doesn't exist | `404 Not Found` | `ErrorResponse` (`ORDER_NOT_FOUND`) | Order Service's own data is missing |
| Order exists, its user doesn't | `404 Not Found` | `ErrorResponse` (`RELATED_USER_NOT_FOUND`) | User Service answered — the answer was "no such user" |
| User Service unreachable, erroring, or too slow | `503 Service Unavailable` | `ErrorResponse` (`USER_SERVICE_UNAVAILABLE`) | infrastructure failure, not a business outcome |
| `{id}` isn't a number | `400 Bad Request` | `ErrorResponse` (`INVALID_REQUEST`) | malformed request |

**`OrderResponse`**
```json
{
  "orderId": 101,
  "product": "Mechanical Keyboard",
  "amount": 89.99,
  "user": {
    "id": 1,
    "fullName": "Asha Rao",
    "email": "asha.rao@example.com"
  }
}
```

`user` is an Order-Service-owned `UserSummary`, not User Service's
`UserResponse` reused — each service defines the shape from its own point
of view, even though the two currently look similar.

---

## Shared error shape

Both services return the same `ErrorResponse` shape so a consumer only
needs to learn one error format:

```json
{
  "error": "USER_NOT_FOUND",
  "message": "User not found with id: 42",
  "status": 404,
  "timestamp": "2026-08-18T09:12:03.512Z"
}
```
