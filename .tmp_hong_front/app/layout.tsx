// src/app/layout.tsx
import React from "react";
import Providers from "./providers";
import AppLayout from "../components/layout/AppLayout";
import { AppRouterCacheProvider } from "@mui/material-nextjs/v14-appRouter";

export const metadata = {
  title: "진료지원 시스템",
  description: "Next.js + Redux-Saga + Spring Boot",
};

const RootLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <html lang="ko">
      <body>
        {/* 🔹 MUI + Emotion SSR용 캐시 Provider */}
        <AppRouterCacheProvider options={{ key: "mui" }}>
          <Providers>
            <AppLayout>{children}</AppLayout>
          </Providers>
        </AppRouterCacheProvider>
      </body>
    </html>
  );
};

export default RootLayout;
