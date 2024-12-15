const { fontFamily } = require("tailwindcss/defaultTheme");

/** @type {import('tailwindcss').Config} */
export default {
  darkMode: ["class"],
  content: ["./index.html", "./src/**/*.{ts,tsx,js,jsx}"],
  theme: {
    extend: {
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      colors: {
        bg_accent: "#F3F3F4",
        accent: "#E00400",
        dark: "#15151E",
      },
      fontFamily: {
        ...fontFamily,
        f1: ["Audiowide", ...fontFamily["sans"]],
      },
      backgroundImage: {
        "line-pattern": "url('/gray-pattern.png')",
      },
    },
  },
  plugins: [require("tailwindcss-animate")],
};
