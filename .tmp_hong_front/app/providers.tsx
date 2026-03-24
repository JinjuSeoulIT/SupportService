// src/app/providers.tsx
"use client";

import { Provider } from "react-redux";
import { store } from "../store/store";
import { CssBaseline, ThemeProvider, createTheme } from "@mui/material";
import React from "react";

const theme = createTheme({
  palette: {
    mode: "light",
  },
});

const Providers = ({ children }: { children: React.ReactNode }) => {
  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </ThemeProvider>
    </Provider>
  );
};

export default Providers;
