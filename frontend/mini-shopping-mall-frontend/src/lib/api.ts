const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080/api';

export async function apiRequest<T>(
    endpoint: string,
    options: RequestInit = {}
): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;

  // localStorage에서 토큰 가져오기 (클라이언트 환경에서만)
  let token = null;
  if (typeof window !== 'undefined') {
    token = localStorage.getItem('accessToken');
  }

  const config: RequestInit = {
    headers: {
      'Content-Type': 'application/json',
      // 토큰이 있으면 Authorization 헤더 추가
      ...(token && { 'Authorization': `Bearer ${token}` }),
      ...options.headers,
    },
    ...options,
  };

  console.log('API Request:', url, 'Token:', token ? 'Present' : 'Missing'); // 디버그용

  const response = await fetch(url, config);

  console.log('API Response:', response.status); // 디버그용

  // 401 에러 시 로그아웃 처리
  if (response.status === 401) {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    throw new Error('Authentication failed');
  }

  if (!response.ok) {
    throw new Error(`API request failed: ${response.status}`);
  }

  return response.json();
}

// 로그인 응답 인터페이스
export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  userId: number;
  email: string;
  name: string;
}

// API 함수들
export const userApi = {
  getUser: (id: number) => apiRequest<User>(`/users/${id}`),
  login: (credentials: LoginRequest) =>
      apiRequest<LoginResponse>('/users/login', {
        method: 'POST',
        body: JSON.stringify(credentials),
      }),
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
  getMyOrders: () => apiRequest<Order[]>('/orders/my'), // 새로 추가
};

// 기존 타입 정의들...
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
