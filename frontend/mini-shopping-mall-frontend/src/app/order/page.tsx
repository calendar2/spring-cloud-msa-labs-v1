'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { Product, productApi, orderApi } from '@/lib/api';

export default function OrderPage() {
  const [product, setProduct] = useState<Product | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [userId, setUserId] = useState(2); // 임시 사용자 ID
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  
  const router = useRouter();
  const searchParams = useSearchParams();
  
  const productId = Number(searchParams.get('productId'));
  const initialQuantity = Number(searchParams.get('quantity')) || 1;

  useEffect(() => {
    setQuantity(initialQuantity);
    
    const fetchProduct = async () => {
      try {
        const data = await productApi.getProduct(productId);
        setProduct(data);
      } catch (err) {
        setError('상품 정보를 불러올 수 없습니다.');
        console.error(err);
      }
    };

    if (productId) {
      fetchProduct();
    }
  }, [productId, initialQuantity]);

  const handleSubmitOrder = async () => {
    if (!product) return;
    
    setLoading(true);
    setError(null);
    
    try {
      const order = await orderApi.createOrder({
        userId,
        productId: product.id,
        quantity,
      });
      
      router.push(`/order/success?orderId=${order.id}`);
    } catch (err) {
      setError('주문 처리 중 오류가 발생했습니다.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (!product) {
    return <div className="p-8">상품 정보를 불러오는 중...</div>;
  }

  const totalAmount = product.price * quantity;

  return (
    <div className="container mx-auto p-8">
      <div className="max-w-lg mx-auto">
        <h1 className="text-3xl font-bold mb-8">주문하기</h1>
        
        <div className="bg-white border rounded-lg p-6 shadow-lg">
          <h2 className="text-xl font-semibold mb-4">주문 정보</h2>
          
          <div className="space-y-4 mb-6">
            <div>
              <label className="block text-sm font-medium text-gray-700">상품</label>
              <p className="text-lg">{product.name}</p>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700">가격</label>
              <p className="text-lg">₩{product.price.toLocaleString()}</p>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700">수량</label>
              <input
                type="number"
                min="1"
                max={product.stockQuantity}
                value={quantity}
                onChange={(e) => setQuantity(Number(e.target.value))}
                className="border border-gray-300 rounded px-3 py-2 w-full"
              />
            </div>
            
            <div className="border-t pt-4">
              <div className="flex justify-between items-center">
                <span className="text-lg font-semibold">총 금액:</span>
                <span className="text-xl font-bold text-blue-600">
                  ₩{totalAmount.toLocaleString()}
                </span>
              </div>
            </div>
          </div>
          
          {error && (
            <div className="mb-4 p-3 bg-red-100 border border-red-300 text-red-700 rounded">
              {error}
            </div>
          )}
          
          <div className="flex space-x-4">
            <button
              onClick={handleSubmitOrder}
              disabled={loading}
              className="flex-1 bg-green-500 text-white px-6 py-3 rounded hover:bg-green-600 disabled:bg-gray-400"
            >
              {loading ? '주문 처리 중...' : '주문 확정'}
            </button>
            
            <button
              onClick={() => router.back()}
              className="bg-gray-500 text-white px-6 py-3 rounded hover:bg-gray-600"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}