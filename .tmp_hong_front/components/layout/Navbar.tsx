// src/components/layout/Navbar.tsx
"use client";

import { AppBar, Toolbar, Typography, Box, Button } from "@mui/material";
import Link from "next/link";

const Navbar = () => {
  return (
    <AppBar position="static" color="default" elevation={1}>
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1, fontWeight: 700 }}>
          직원관리 시스템
        </Typography>

        <Box sx={{ display: "flex", gap: 1 }}>
          <Button component={Link} href="/" color="inherit">
            홈
          </Button>
          <Button component={Link} href="/employees" color="inherit">
            직원 관리
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
