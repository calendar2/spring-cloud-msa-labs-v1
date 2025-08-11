import Link from 'next/link';

export default function Home() {
  return (
    <div className="container mx-auto p-8">
      <div className="text-center">
        <h1 className="text-4xl font-bold mb-8">MSA 쇼핑몰</h1>
        <p className="text-xl text-gray-600 mb-12">
          Spring Cloud + Next.js로 구축한 마이크로서비스 쇼핑몰
        </p>
        
        <div className="max-w-md mx-auto space-y-4">
          <Link 
            href="/products"
            className="block bg-blue-500 text-white px-8 py-4 rounded-lg text-lg hover:bg-blue-600"
          >
            상품 둘러보기
          </Link>
        </div>
      </div>
    </div>
  );
}