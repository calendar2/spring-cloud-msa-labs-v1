'use client';

import { useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import Link from 'next/link';
import { Order, orderApi } from '@/lib/api';

export default function OrderSuccessPage() {
  const [order, setOrder] = useState<Order | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  
  const searchParams = useSearchParams();
  const orderId = searchParams.get('orderId');

  useEffect(() => {
    const fetchOrder = async () => {
      if (!orderId) {
        setError('주문 ID가 없습니다.');
        setLoading(false);
        return;
      }
      
      try {
        const data = await orderApi.getOrder(Number(orderId));
        setOrder(data);
      } catch (err) {
        setError('주문 정보를 불러올 수 없습니다.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchOrder();
  }, [orderId]);

  if (loading) return <div className="p-8">로딩 중...</div>;
  if (error) return <div className="p-8 text-red-500">{error}</div>;
  if (!order) return <div className="p-8">주문 정보를 찾을 수 없습니다.</div>;

  return (
    <div className="container mx-auto p-8">
      <div className="max-w-lg mx-auto text-center">
        <div className="bg-green-100 border border-green-300 rounded-lg p-8 mb-8">
          <h1 className="text-3xl font-bold text-green-800 mb-4">주문 완료!</h1>
          <p className="text-green-700 mb-4">
            주문이 성공적으로 처리되었습니다.
          </p>
        </div>
        
        <div className="bg-white border rounded-lg p-6 shadow-lg mb-8">
          <h2 className="text-xl font-semibold mb-4">주문 상세 정보</h2>
          
          <div className="space-y-3 text-left">
            <div className="flex justify-between">
              <span className="font-medium">주문 번호:</span>
              <span>#{order.id}</span>
            </div>
            
            <div className="flex justify-between">
              <span className="font-medium">주문 금액:</span>
              <span className="font-bold text-blue-600">
                ₩{order.totalAmount.toLocaleString()}
              </span>
            </div>
            
            <div className="flex justify-between">
              <span className="font-medium">주문 상태:</span>
              <span className="text-green-600 font-medium">{order.status}</span>
            </div>
            
            <div className="flex justify-between">
              <span className="font-medium">주문 시간:</span>
              <span>{new Date(order.createdAt).toLocaleString()}</span>
            </div>
          </div>
        </div>
        
        <div className="space-y-4">
          <Link 
            href="/products"
            className="block bg-blue-500 text-white px-6 py-3 rounded hover:bg-blue-600"
          >
            계속 쇼핑하기
          </Link>
          
          <Link 
            href="/"
            className="block bg-gray-500 text-white px-6 py-3 rounded hover:bg-gray-600"
          >
            홈으로
          </Link>
        </div>
      </div>
    </div>
  );
}