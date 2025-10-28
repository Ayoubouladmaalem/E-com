# MongoDB Clusters Configuration

## Overview

This document contains information about the deployed MongoDB Atlas clusters for Cartiva.

---

## 🗄️ Database Clusters

### Cluster Information
- **Cluster Name**: Cartiva
- **Provider**: MongoDB Atlas

---

## 📊 Service Databases

### 1. Customer Service Database

**Username**: `gr3_customer_user`

**Password**: 🔒 Ask tech lead for password

**Connection String**:
```
mongodb+srv://gr3_customer_user:<db_password>@cartiva.ixvhlvf.mongodb.net/
```

**Used by**:
- Customer Service (Spring Boot)

---

### 2. Notification Service Database

**Username**: `gr3_notification_user`

**Password**: 🔒 Ask tech lead for password

**Connection String**:
```
mongodb+srv://gr3_notification_user:<db_password>@cartiva.ixvhlvf.mongodb.net/
```

**Used by**:
- Notification Service (Spring Boot)

---

## 🔧 Configuration

### Spring Boot Application Properties

**For Customer Service** (`application.yml`):
```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://gr3_customer_user:<PASSWORD>@cartiva.ixvhlvf.mongodb.net/customer-db
      database: customer-db
```

**For Notification Service** (`application.yml`):
```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://gr3_notification_user:<PASSWORD>@cartiva.ixvhlvf.mongodb.net/notification-db
      database: notification-db
```

---

## 🔐 Security Notes

1. **Never commit passwords to Git**
2. Use environment variables for sensitive data
3. Store passwords in:
   - `.env` files (add to `.gitignore`)
   - Spring Config Server (encrypted)
   - Secret management tools (AWS Secrets Manager, Azure Key Vault, etc.)

---

## 📝 Getting Access

**To get database passwords:**
- Contact the tech lead
- Passwords are shared securely (not via Git/public channels)

---