# Technical Sheet

Summary table of services, ports, and access URLs for the frontend dev.

| Application Name      | Port   | Access URL (via Gateway)                   |
| :---                 | :---   | :---                                       |
| **Gateway Service**      | `8222`  | `http://localhost:8222`                       |
| **Customer Service**     | `8090`  | `http://localhost:8222/api/v1/customers`      |
| **Product Service**      | `8050`  | `http://localhost:8222/api/v1/products`       |
| **Order Service**        | `8070`  | `http://localhost:8222/api/v1/orders`         |
| **Payment Service**      | `8060`  | `http://localhost:8222/api/v1/payments`       |
| **Config Server**        | `8888`  | `http://localhost:8888`                       |
| **Discovery Service**    | `8761`  | `http://localhost:8761`                       |
| **Notification Service** | `8040`  | `http://localhost:8040` (No REST API)         |