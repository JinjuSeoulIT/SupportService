// src/components/layout/AppLayout.tsx
"use client";

import { Box } from "@mui/material";
import React from "react";

const AppLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <Box sx={{ minHeight: "100vh", bgcolor: "#f5f7fb" }}>
      {children}
    </Box>
  );
};

export default AppLayout;