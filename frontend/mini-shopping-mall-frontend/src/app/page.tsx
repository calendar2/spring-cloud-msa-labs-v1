'use client';

import Link from 'next/link';
import { useAuth } from '@/hooks/useAuth';

export default function Home() {
  const { user, loading, isAuthenticated } = useAuth();

  if (loading) {
    return <div className="container mx-auto p-8 text-center">로딩 중...</div>;
  }

  return (
    <div className="container mx-auto p-8">
      <div className="text-center">
        <h1 className="text-4xl font-bold mb-8">MSA 쇼핑몰</h1>
        <p className="text-xl text-gray-600 mb-12">
          Spring Cloud + Next.js + JWT 인증으로 구축한 마이크로서비스 쇼핑몰
        </p>

        {isAuthenticated() && (
          <div className="mb-8">
            <div className="bg-green-100 border border-green-300 rounded-lg p-4 mb-4">
              <p className="text-green-800">
                안녕하세요, <strong>{user?.name}</strong>님! 환영합니다!
              </p>
            </div>
          </div>
        )}

        <div className="max-w-md mx-auto space-y-4">
          <Link
            href="/products"
            className="block bg-blue-500 text-white px-8 py-4 rounded-lg text-lg hover:bg-blue-600 transition-colors"
          >
            상품 둘러보기
          </Link>

          {isAuthenticated() && (
            <Link
              href="/orders"
              className="block bg-purple-500 text-white px-8 py-4 rounded-lg text-lg hover:bg-purple-600 transition-colors"
            >
              내 주문내역
            </Link>
          )}
        </div>
      </div>
    </div>
  );
}
