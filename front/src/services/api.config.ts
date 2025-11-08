const GATEWAY_URL = import.meta.env.VITE_GATEWAY_URL || 'http://localhost:8222';

export const API_CONFIG = {
  GATEWAY: GATEWAY_URL,
  CUSTOMERS: `${GATEWAY_URL}/api/v1/customers`,
  PRODUCTS: `${GATEWAY_URL}/api/v1/products`,
  ORDERS: `${GATEWAY_URL}/api/v1/orders`,
} as const;

