---
description: "Agency design system tokens and component patterns for frontend code."
applyTo: "frontend/src/**"
---
# Agency Design System

Apply these design tokens and patterns when generating or modifying frontend components.

## Color Palette

Use Agency brand colors via Tailwind custom classes. Never use raw hex values in JSX — always use the Tailwind class names defined in `tailwind.config.js`.

| Token | Tailwind Class | Hex | Usage |
|-------|---------------|-----|-------|
| Primary | `it-blue-600` | `#1E40AF` | Buttons, links, active states, primary actions |
| Primary Light | `it-blue-500` | `#2563EB` | Hover states on primary elements |
| Primary Lighter | `it-blue-400` | `#3B82F6` | Focus rings, selected items |
| Secondary | `it-gray-700` | `#374151` | Body text, headings |
| Background | `it-gray-50` | `#F9FAFB` | Page backgrounds, card backgrounds |
| Surface | `it-gray-100` | `#F3F4F6` | Table rows (alternating), input backgrounds |
| Accent | `it-amber-500` | `#F59E0B` | Highlights, badges, warnings |
| Success | `it-green-600` | `#16A34A` | Success states, confirmed deliveries |
| Error | `it-red-600` | `#DC2626` | Validation errors, destructive actions, failed states |
| Border | `it-gray-200` | `#E5E7EB` | Card borders, dividers, table borders |

## Typography

- **Font family:** Inter for all UI text. JetBrains Mono for code blocks only.
- **Headings:** `font-semibold tracking-tight` — use `text-2xl` (h1), `text-xl` (h2), `text-lg` (h3).
- **Body text:** `text-base` (16px), `text-it-gray-700`, `leading-relaxed`.
- **Small text / labels:** `text-sm`, `text-it-gray-500`.
- **Monospace:** `font-mono text-sm` — only for code snippets, IDs, or technical values.

## Spacing

- Use Tailwind's default 4px spacing scale.
- **Page padding:** `p-6` on desktop (`md:p-6`), `p-4` on mobile.
- **Section gap:** `space-y-6` between major sections.
- **Card internal padding:** `p-4`.
- **Card gap in grids:** `gap-4` between cards, `gap-6` between sections.

## Component Naming Conventions

- **Shared / reusable components:** Prefix with `IT` — e.g., `ITButton`, `ITCard`, `ITBadge`, `ITModal`, `ITTable`.
- **Page components:** `<Entity>Page.tsx` — e.g., `DeliveryPage.tsx`, `SupplierPage.tsx`.
- **Entity list components:** `<Entity>List.tsx` — e.g., `ProductList.tsx`.
- **Entity detail components:** `<Entity>Detail.tsx` — e.g., `OrderDetail.tsx`.
- **Entity form components:** `<Entity>Form.tsx` — e.g., `SupplierForm.tsx`.
- **Place shared components in:** `frontend/src/components/shared/`.

## Component Patterns

### Buttons

```tsx
// Primary action
<button className="bg-it-blue-600 hover:bg-it-blue-500 text-white font-semibold py-2 px-4 rounded-lg transition-colors focus:outline-none focus:ring-2 focus:ring-it-blue-400 focus:ring-offset-2">
  Save Changes
</button>

// Destructive action
<button className="bg-it-red-600 hover:bg-it-red-500 text-white font-semibold py-2 px-4 rounded-lg transition-colors">
  Delete
</button>

// Secondary / outline
<button className="border border-it-gray-200 text-it-gray-700 hover:bg-it-gray-50 font-semibold py-2 px-4 rounded-lg transition-colors">
  Cancel
</button>
```

### Status Badges

```tsx
// Use semantic colors for status indicators
const statusColors = {
  active:      'bg-it-green-600 text-white',
  pending:     'bg-it-amber-500 text-white',
  inactive:    'bg-it-gray-400 text-white',
  failed:      'bg-it-red-600 text-white',
  'in-transit': 'bg-it-blue-600 text-white',
};
```

### Cards

```tsx
<div className="bg-white border border-it-gray-200 rounded-lg p-4 shadow-sm hover:shadow-md transition-shadow">
  {/* card content */}
</div>
```

## Accessibility Requirements

- **Contrast:** All text must meet WCAG 2.1 AA (4.5:1 for normal text, 3:1 for large text).
- **Focus indicators:** All interactive elements must have a visible focus ring (`focus:ring-2 focus:ring-it-blue-400`).
- **ARIA labels:** Buttons with only icons must have `aria-label`. Status badges should have `role="status"`.
- **Keyboard navigation:** All interactive elements must be keyboard-accessible. Use semantic HTML (`button`, `a`, `input`) over `div` with click handlers.
- **Color alone:** Never convey information by color alone — pair colors with text labels or icons.

## Icons

- Use [Heroicons](https://heroicons.com/) (`@heroicons/react`) for all icons.
- Size icons consistently: `h-5 w-5` inline with text, `h-6 w-6` for standalone.
- Color icons to match adjacent text: `text-it-gray-500` for muted, `text-it-blue-600` for interactive.

## Dark Mode

- Dark mode is enabled via the `dark` class on `<html>`.
- Use dark mode variants: `dark:bg-gray-900`, `dark:text-gray-100`, `dark:border-gray-700`.
- Ensure all Agency colors have sufficient contrast in both light and dark modes.
