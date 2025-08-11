'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { userApi } from '@/lib/api';

export default function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const router = useRouter();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const response = await userApi.login({ email, password });

            // localStorage에 토큰과 사용자 정보 저장
            localStorage.setItem('accessToken', response.token);
            localStorage.setItem('user', JSON.stringify({
                id: response.userId,
                email: response.email,
                name: response.name
            }));

            // 커스텀 이벤트 발생시켜서 Navbar 업데이트
            window.dispatchEvent(new Event('auth-change'));

            // 메인 페이지로 이동
            router.push('/');
        } catch (err) {
            setError('로그인에 실패했습니다. 이메일과 패스워드를 확인해주세요.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mx-auto p-8">
            <div className="max-w-md mx-auto">
                <h1 className="text-3xl font-bold mb-8 text-center">로그인</h1>

                <form onSubmit={handleSubmit} className="bg-white border rounded-lg p-6 shadow-lg">
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                            이메일
                        </label>
                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:border-blue-500"
                            placeholder="이메일을 입력하세요"
                        />
                    </div>

                    <div className="mb-6">
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-2">
                            패스워드
                        </label>
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:border-blue-500"
                            placeholder="패스워드를 입력하세요"
                        />
                    </div>

                    {error && (
                        <div className="mb-4 p-3 bg-red-100 border border-red-300 text-red-700 rounded">
                            {error}
                        </div>
                    )}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:bg-gray-400"
                    >
                        {loading ? '로그인 중...' : '로그인'}
                    </button>
                </form>

                <div className="mt-4 text-center">
                    <p className="text-gray-600 text-sm">
                        테스트 계정: user1@example.com / 1234
                    </p>
                </div>
            </div>
        </div>
    );
}
