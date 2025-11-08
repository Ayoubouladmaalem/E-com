# Frontend-Backend DTO Mapping

## Overview

This document maps Data Transfer Objects (DTOs) from the backend to TypeScript interfaces for the frontend.

---

## Customer Service

### Address

**Backend DTO (JSON)**
```json
{
  "street": "string",
  "houseNumber": "string",
  "zipCode": "string"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface Address {
  street?: string;
  houseNumber?: string;
  zipCode?: string;
}
```

---

### CustomerRequest

**Backend DTO (JSON)**
```json
{
  "id": "string (optional)",
  "firstname": "string (required)",
  "lastname": "string (required)",
  "email": "string (required, valid email)",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string"
  }
}
```

**Frontend Interface (TypeScript)**
```typescript
import { Address } from './customer.types';

export interface CustomerRequest {
  id?: string;
  firstname: string;
  lastname: string;
  email: string;
  address?: Address;
}
```

---

### CustomerResponse

**Backend DTO (JSON)**
```json
{
  "id": "string",
  "firstname": "string",
  "lastname": "string",
  "email": "string",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string"
  }
}
```

**Frontend Interface (TypeScript)**
```typescript
import { Address } from './customer.types';

export interface CustomerResponse {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  address?: Address;
}
```

---

## Product Service

### ProductRequest

**Backend DTO (JSON)**
```json
{
  "id": "integer (optional)",
  "name": "string (required)",
  "description": "string (required)",
  "availableQuantity": "double (required, positive)",
  "price": "BigDecimal (required, positive)",
  "categoryId": "integer (required)"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface ProductRequest {
  id?: number;
  name: string;
  description: string;
  availableQuantity: number;
  price: number; 
  categoryId: number;
}
```

---

### ProductResponse

**Backend DTO (JSON)**
```json
{
  "id": "integer",
  "name": "string",
  "description": "string",
  "availableQuantity": "double",
  "price": "BigDecimal",
  "categoryId": "integer",
  "categoryName": "string",
  "categoryDescription": "string"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  availableQuantity: number;
  price: number;
  categoryId: number;
  categoryName: string;
  categoryDescription: string;
}
```

---

### ProductPurchaseRequest

**Backend DTO (JSON)**
```json
{
  "productId": "integer (required)",
  "quantity": "double (required, positive)"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface ProductPurchaseRequest {
  productId: number;
  quantity: number;
}
```

---

### ProductPurchaseResponse

**Backend DTO (JSON)**
```json
{
  "productId": "integer",
  "name": "string",
  "description": "string",
  "price": "BigDecimal",
  "quantity": "double"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface ProductPurchaseResponse {
  productId: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
}
```

---

## Order Service

### PaymentMethod (Enum)

**Backend Enum**
```
PAYPAL
CREDIT_CARD
VISA
MASTER_CARD
BITCOIN
```

**Frontend TypeScript Type**
```typescript
export type PaymentMethod = 
  | "PAYPAL"
  | "CREDIT_CARD"
  | "VISA"
  | "MASTER_CARD"
  | "BITCOIN";
```

---

### PurchaseRequest (Nested in OrderRequest)

**Backend DTO (JSON)**
```json
{
  "productId": "integer (required)",
  "quantity": "double (required, positive)"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface PurchaseRequest {
  productId: number;
  quantity: number;
}
```

---

### OrderRequest

**Backend DTO (JSON)**
```json
{
  "id": "integer (optional)",
  "reference": "string",
  "amount": "BigDecimal (required, positive)",
  "paymentMethod": "enum (required) [PAYPAL, CREDIT_CARD, VISA, MASTER_CARD, BITCOIN]",
  "customerId": "string (required, not empty, not blank)",
  "products": [
    {
      "productId": "integer (required)",
      "quantity": "double (required, positive)"
    }
  ]
}
```

**Frontend Interface (TypeScript)**
```typescript
import { PaymentMethod, PurchaseRequest } from './order.types';

export interface OrderRequest {
  id?: number;
  reference?: string; // Often generated on backend
  amount: number;
  paymentMethod: PaymentMethod;
  customerId: string;
  products: PurchaseRequest[];
}
```

---

### OrderResponse

**Backend DTO (JSON)**
```json
{
  "id": "integer",
  "reference": "string",
  "amount": "BigDecimal",
  "paymentMethod": "enum [PAYPAL, CREDIT_CARD, VISA, MASTER_CARD, BITCOIN]",
  "customerId": "string"
}
```

**Frontend Interface (TypeScript)**
```typescript
import { PaymentMethod } from './order.types';

export interface OrderResponse {
  id: number;
  reference: string;
  amount: number;
  paymentMethod: PaymentMethod;
  customerId: string;
}
```

---

### OrderLineRequest

**Backend DTO (JSON)**
```json
{
  "id": "integer",
  "orderId": "integer",
  "productId": "integer",
  "quantity": "double"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface OrderLineRequest {
  id?: number;
  orderId?: number;
  productId: number;
  quantity: number;
}
```

---

### OrderLineResponse

**Backend DTO (JSON)**
```json
{
  "id": "integer",
  "quantity": "double"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface OrderLineResponse {
  id: number;
  quantity: number;
}
```

---

## Payment Service

### Customer (Nested in PaymentRequest)

**Backend DTO (JSON)**
```json
{
  "id": "string",
  "firstname": "string (required)",
  "lastname": "string (required)",
  "email": "string (required, valid email format)"
}
```

**Frontend Interface (TypeScript)**
```typescript
export interface PaymentCustomer {
  id?: string;
  firstname: string;
  lastname: string;
  email: string;
}
```

---

### PaymentRequest

**Backend DTO (JSON)**
```json
{
  "id": "integer",
  "amount": "BigDecimal",
  "paymentMethod": "enum [PAYPAL, CREDIT_CARD, VISA, MASTER_CARD, BITCOIN]",
  "orderId": "integer",
  "orderReference": "string",
  "customer": {
    "id": "string",
    "firstname": "string (required)",
    "lastname": "string (required)",
    "email": "string (required, valid email)"
  }
}
```

**Frontend Interface (TypeScript)**
```typescript
import { PaymentMethod } from './order.types'; // Import shared type
import { PaymentCustomer } from './payment.types';

export interface PaymentRequest {
  id?: number;
  amount: number;
  paymentMethod: PaymentMethod;
  orderId: number;
  orderReference: string;
  customer: PaymentCustomer;
}
```

---

## Type Mapping Notes

| Backend Type | TypeScript Type | Notes |
|--------------|-----------------|-------|
| `integer` | `number` | Standard numeric type |
| `double` | `number` | JavaScript doesn't distinguish |
| `BigDecimal` | `number` | JSON serialization converts to number |
| `string` | `string` | Direct mapping |
| `enum` | `type` literal union | Use string literal unions |
| `List<T>` | `T[]` | Array of type T |
| `Object` | `interface` | Define as interface |

---

## Best Practices

1. **Optional Fields**: Use `?` for optional fields in TypeScript
2. **Shared Types**: Import `PaymentMethod` from a shared location to avoid duplication
3. **Naming Convention**: Keep interface names consistent with backend DTOs
4. **BigDecimal Handling**: Always use `number` type for monetary values in TypeScript
5. **Validation**: Implement client-side validation matching backend constraints

