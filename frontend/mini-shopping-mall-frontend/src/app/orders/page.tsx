'use client';

import { useState, useEffect } from 'react';
import { useAuth } from '@/hooks/useAuth';
import { orderApi, Order } from '@/lib/api';
import { useRouter } from 'next/navigation';

export default function OrdersPage() {
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated, loading: authLoading, user } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (authLoading) return; // 인증 로딩 중이면 아무것도 하지 않음
    
    if (!user) {
      router.push('/login');
      return;
    }

    fetchMyOrders();
  }, [authLoading, user]);

  const fetchMyOrders = async () => {
    try {
      setLoading(true);
      const data = await orderApi.getMyOrders();
      setOrders(data);
    } catch (err) {
      setError('주문 내역을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="container mx-auto p-8">
        <div className="text-center">로딩 중...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mx-auto p-8">
        <div className="text-center text-red-600">{error}</div>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-8">
      <h1 className="text-3xl font-bold mb-8">주문 내역</h1>

      {orders.length === 0 ? (
        <div className="text-center py-16">
          <p className="text-gray-600 text-lg">주문 내역이 없습니다.</p>
          <a
            href="/products"
            className="mt-4 inline-block bg-blue-500 text-white px-6 py-3 rounded hover:bg-blue-600"
          >
            상품 둘러보기
          </a>
        </div>
      ) : (
        <div className="space-y-6">
          {orders.map((order) => (
            <div
              key={order.id}
              className="bg-white border rounded-lg p-6 shadow-sm"
            >
              <div className="flex justify-between items-start">
                <div>
                  <h3 className="text-lg font-semibold">
                    주문 #{order.id}
                  </h3>
                  <p className="text-gray-600">
                    주문일: {new Date(order.createdAt).toLocaleDateString('ko-KR')}
                  </p>
                  <p className="text-gray-600">
                    상태: <span className={`font-medium ${
                      order.status === 'COMPLETED' ? 'text-green-600' :
                      order.status === 'PENDING' ? 'text-yellow-600' : 'text-gray-600'
                    }`}>
                      {order.status}
                    </span>
                  </p>
                </div>
                <div className="text-right">
                  <p className="text-2xl font-bold text-blue-600">
                    ₩{order.totalAmount.toLocaleString()}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}