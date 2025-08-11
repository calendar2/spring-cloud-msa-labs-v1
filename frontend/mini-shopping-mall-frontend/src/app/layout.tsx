import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Navbar from '@/components/Navbar';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: "MSA 쇼핑몰",
  description: "Spring Cloud + Next.js JWT 인증 쇼핑몰",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className={`${inter.className} bg-gray-50 min-h-screen`}>
        <Navbar />
        <main>{children}</main>
      </body>
    </html>
  );
}
