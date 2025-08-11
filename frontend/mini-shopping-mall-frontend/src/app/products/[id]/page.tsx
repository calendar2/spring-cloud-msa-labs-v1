'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { Product, productApi } from '@/lib/api';

export default function ProductDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [productId, setProductId] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    const getParams = async () => {
      const resolvedParams = await params;
      setProductId(resolvedParams.id);
    };
    getParams();
  }, [params]);

  useEffect(() => {
    if (!productId) return;

    const fetchProduct = async () => {
      try {
        const data = await productApi.getProduct(Number(productId));
        setProduct(data);
      } catch (err) {
        setError('상품을 불러오는 중 오류가 발생했습니다.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [productId]);

  const handleOrder = () => {
    if (product) {
      router.push(`/order?productId=${product.id}&quantity=${quantity}`);
    }
  };

  if (loading) return <div className="p-8">로딩 중...</div>;
  if (error) return <div className="p-8 text-red-500">{error}</div>;
  if (!product) return <div className="p-8">상품을 찾을 수 없습니다.</div>;

  return (
    <div className="container mx-auto p-8">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-3xl font-bold mb-4">{product.name}</h1>
        
        <div className="bg-white border rounded-lg p-6 shadow-lg">
          <p className="text-gray-700 mb-4">{product.description}</p>
          
          <div className="space-y-2 mb-6">
            <p className="text-2xl font-bold text-blue-600">
              ₩{product.price.toLocaleString()}
            </p>
            <p className="text-sm text-gray-500">카테고리: {product.category}</p>
            <p className="text-sm text-gray-500">재고: {product.stockQuantity}개</p>
          </div>
          
          <div className="flex items-center space-x-4 mb-6">
            <label htmlFor="quantity" className="text-sm font-medium">
              수량:
            </label>
            <input
              id="quantity"
              type="number"
              min="1"
              max={product.stockQuantity}
              value={quantity}
              onChange={(e) => setQuantity(Number(e.target.value))}
              className="border border-gray-300 rounded px-3 py-1 w-20"
            />
          </div>
          
          <div className="flex space-x-4">
            <button
              onClick={handleOrder}
              disabled={product.stockQuantity === 0}
              className="bg-green-500 text-white px-6 py-2 rounded hover:bg-green-600 disabled:bg-gray-400"
            >
              주문하기
            </button>
            
            <button
              onClick={() => router.back()}
              className="bg-gray-500 text-white px-6 py-2 rounded hover:bg-gray-600"
            >
              돌아가기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}