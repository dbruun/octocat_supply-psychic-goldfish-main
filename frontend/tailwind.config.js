/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#76B852',
        'dark': '#0A0A0A',
        'light': '#F5F5F5',
        'accent': '#8BC34A',
        'gray': {
          100: '#f5f5f5',
          200: '#e5e5e5',
          300: '#d4d4d4',
          400: '#a3a3a3',
          500: '#737373',
          600: '#525252',
          700: '#404040',
          800: '#262626',
          900: '#171717',
        },
        // Agency Design System tokens
        'it-blue': {
          400: '#3B82F6',
          500: '#2563EB',
          600: '#1E40AF',
        },
        'it-gray': {
          50: '#F9FAFB',
          100: '#F3F4F6',
          200: '#E5E7EB',
          400: '#9CA3AF',
          500: '#6B7280',
          700: '#374151',
        },
        'it-amber': {
          500: '#F59E0B',
        },
        'it-green': {
          600: '#16A34A',
        },
        'it-red': {
          500: '#EF4444',
          600: '#DC2626',
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
        mono: ['JetBrains Mono', 'monospace'],
      },
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
      },
      width: {
        '7/8': '87.5%'
      }
    },
  },
  plugins: [],
  darkMode: 'class', // Enables dark mode variants with the 'dark' class
}