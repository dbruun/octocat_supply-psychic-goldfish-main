import { test, expect } from '@playwright/test';

test.describe('Login error messaging', () => {
  test('renders query parameter errors as text instead of HTML', async ({ page }) => {
    const payload = '<strong id="injected">Injected</strong>';

    await page.goto(`/login?error=${encodeURIComponent(payload)}`);

    const alert = page.getByRole('alert');

    await expect(alert).toContainText(payload);
    await expect(page.locator('#injected')).toHaveCount(0);
  });
});
