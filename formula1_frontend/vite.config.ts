//@ts-expect-error unkknown path
import path from "path";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      //@ts-expect-error unkknown path
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
