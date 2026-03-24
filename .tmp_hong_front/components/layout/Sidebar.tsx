// src/components/layout/Sidebar.tsx
"use client";

import { Box, Button, Typography } from "@mui/material";
import Link from "next/link";

const Sidebar = () => {
  return (
    <Box
      sx={{
        width: 220,
        flex: "0 0 220px",
        bgcolor: "#020617",
        color: "#e5e7eb",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        py: 2,
        minHeight: "calc(100vh - 64px)", // AppBar 높이 제외
      }}
    >
      <Box>
        <Box sx={{ px: 2, mb: 3 }}>
          <Typography variant="subtitle1" fontWeight={700}>
            메뉴
          </Typography>
        </Box>

        <Box>
          <Button
            component={Link}
            href="/"
            fullWidth
            sx={{
              justifyContent: "flex-start",
              px: 2.5,
              py: 1,
              color: "#9ca3af",
              borderRadius: 0,
              "&:hover": { bgcolor: "#111827" },
            }}
          >
            대시보드
          </Button>
          <Button
            component={Link}
            href="/employees"
            fullWidth
            sx={{
              justifyContent: "flex-start",
              px: 2.5,
              py: 1,
              color: "#f97316",
              bgcolor: "#111827",
              borderRadius: 0,
              "&:hover": { bgcolor: "#1f2937" },
            }}
          >
            직원 관리
          </Button>
        </Box>
      </Box>

      <Box sx={{ px: 6, pb: 20}}>
        <Typography sx={{ fontSize: 20, color: "#6b7280" }}>
          Made by 현석
        </Typography>
      </Box>
    </Box>
  );
};

export default Sidebar;
