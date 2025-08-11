'use client';

import { useState, useEffect } from 'react';

interface User {
    id: number;
    email: string;
    name: string;
}

export const useAuth = () => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // 컴포넌트 마운트 시 localStorage에서 사용자 정보 로드
        if (typeof window === 'undefined') {
            setLoading(false);
            return;
        }

        const token = localStorage.getItem('accessToken');
        const userData = localStorage.getItem('user');

        if (token && userData) {
            try {
                setUser(JSON.parse(userData));
            } catch (error) {
                // 파싱 에러 시 토큰 제거
                localStorage.removeItem('accessToken');
                localStorage.removeItem('user');
            }
        }
        setLoading(false);
    }, []);

    const login = (userData: User, token: string) => {
        if (typeof window === 'undefined') return;
        localStorage.setItem('accessToken', token);
        localStorage.setItem('user', JSON.stringify(userData));
        setUser(userData);
    };

    const logout = () => {
        if (typeof window === 'undefined') return;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('user');
        setUser(null);
        window.location.href = '/login';
    };

    const isAuthenticated = () => {
        if (typeof window === 'undefined') return false;
        return !!user && !!localStorage.getItem('accessToken');
    };

    return {
        user,
        loading,
        login,
        logout,
        isAuthenticated
    };
};
