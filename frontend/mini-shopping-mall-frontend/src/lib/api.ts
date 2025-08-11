const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080/api';

export async function apiRequest<T>(
  endpoint: string,
  options: RequestInit = {}
): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;
  
  const config: RequestInit = {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  };

  const response = await fetch(url, config);
  
  if (!response.ok) {
    throw new Error(`API request failed: ${response.status}`);
  }
  
  return response.json();
}

// API 함수들
export const userApi = {
  getUser: (id: number) => apiRequest<User>(`/users/${id}`),
};

export const productApi = {
  getAllProducts: () => apiRequest<Product[]>('/products'),
  getProduct: (id: number) => apiRequest<Product>(`/products/${id}`),
};

export const orderApi = {
  createOrder: (order: OrderRequest) => 
    apiRequest<Order>('/orders', {
      method: 'POST',
      body: JSON.stringify(order),
    }),
  getOrder: (id: number) => apiRequest<Order>(`/orders/${id}`),
};

// 타입 정의
export interface User {
  id: number;
  email: string;
  name: string;
}

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
  category: string;
  imageUrl: string;
}

export interface OrderRequest {
  userId: number;
  productId: number;
  quantity: number;
}

export interface Order {
  id: number;
  userId: number;
  totalAmount: number;
  status: string;
  createdAt: string;
}