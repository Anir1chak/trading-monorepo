// tailwind.config.js
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",   // scan all React source files
    "./public/index.html"           // scan the HTML template
  ],
  theme: {
    extend: {
      // you can add custom colors, spacing, etc. here
      colors: {
        primary: "#2563eb",
      },
    },
  },
  plugins: [],
};
