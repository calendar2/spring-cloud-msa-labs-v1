'use client';

import Link from 'next/link';
import { useState, useEffect } from 'react';

interface User {
  id: number;
  email: string;
  name: string;
}

export default function Navbar() {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const checkAuth = () => {
      const token = localStorage.getItem('accessToken');
      const userData = localStorage.getItem('user');
      
      if (token && userData) {
        try {
          setUser(JSON.parse(userData));
        } catch {
          localStorage.removeItem('accessToken');
          localStorage.removeItem('user');
          setUser(null);
        }
      } else {
        setUser(null);
      }
    };

    checkAuth();
    
    // 스토리지 변경 감지
    const handleStorageChange = () => checkAuth();
    const handleAuthChange = () => checkAuth();
    
    window.addEventListener('storage', handleStorageChange);
    window.addEventListener('auth-change', handleAuthChange);
    
    return () => {
      window.removeEventListener('storage', handleStorageChange);
      window.removeEventListener('auth-change', handleAuthChange);
    };
  }, []);

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('user');
    setUser(null);
    window.dispatchEvent(new Event('auth-change'));
    window.location.href = '/login';
  };

  const isAuthenticated = () => {
    return !!user && !!localStorage.getItem('accessToken');
  };

  return (
    <nav className="bg-blue-600 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          {/* 로고 */}
          <Link href="/" className="text-xl font-bold">
            MSA 쇼핑몰
          </Link>

          {/* 네비게이션 링크 */}
          <div className="flex items-center space-x-6">
            <Link 
              href="/products" 
              className="hover:text-blue-200 transition-colors"
            >
              상품목록
            </Link>

            {isAuthenticated() ? (
              <>
                <Link 
                  href="/orders" 
                  className="hover:text-blue-200 transition-colors"
                >
                  주문내역
                </Link>
                <div className="flex items-center space-x-4">
                  <span className="text-blue-200">
                    안녕하세요, {user?.name}님
                  </span>
                  <button
                    onClick={logout}
                    className="bg-red-500 hover:bg-red-600 px-3 py-1 rounded transition-colors"
                  >
                    로그아웃
                  </button>
                </div>
              </>
            ) : (
              <Link 
                href="/login"
                className="bg-green-500 hover:bg-green-600 px-4 py-2 rounded transition-colors"
              >
                로그인
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}