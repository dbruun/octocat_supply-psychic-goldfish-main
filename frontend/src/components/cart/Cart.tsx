import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../../context/CartContext';
import { useTheme } from '../../context/ThemeContext';

const SHIPPING_COST = 10;
const COUPON_CODES: Record<string, number> = { SAVE5: 0.05, SAVE10: 0.1, SAVE20: 0.2 };

export default function Cart() {
  const { items, removeFromCart, updateQuantity } = useCart();
  const { darkMode } = useTheme();
  const [couponCode, setCouponCode] = useState('');
  const [appliedCoupon, setAppliedCoupon] = useState<string | null>(null);
  const [couponError, setCouponError] = useState('');

  const subtotal = items.reduce((sum, item) => {
    const effectivePrice = item.discount ? item.price * (1 - item.discount) : item.price;
    return sum + effectivePrice * item.quantity;
  }, 0);

  const discountRate = appliedCoupon ? COUPON_CODES[appliedCoupon] : 0;
  const discountAmount = subtotal * discountRate;
  const grandTotal = subtotal - discountAmount + (items.length > 0 ? SHIPPING_COST : 0);

  const handleApplyCoupon = () => {
    const code = couponCode.trim().toUpperCase();
    if (COUPON_CODES[code]) {
      setAppliedCoupon(code);
      setCouponError('');
    } else {
      setAppliedCoupon(null);
      setCouponError('Invalid coupon code');
    }
  };

  const cellClass = `px-6 py-4 ${darkMode ? 'text-light' : 'text-gray-800'}`;
  const headerClass = `px-6 py-4 text-left text-sm font-semibold uppercase tracking-wider ${darkMode ? 'text-gray-300' : 'text-gray-600'}`;

  if (items.length === 0) {
    return (
      <div className={`min-h-screen ${darkMode ? 'bg-dark' : 'bg-gray-100'} pt-20 pb-16 px-4 transition-colors duration-300`}>
        <div className="max-w-7xl mx-auto flex flex-col items-center justify-center py-20">
          <svg xmlns="http://www.w3.org/2000/svg" className={`h-20 w-20 mb-6 ${darkMode ? 'text-gray-600' : 'text-gray-400'}`} fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 100 4 2 2 0 000-4z" />
          </svg>
          <h2 className={`text-2xl font-bold mb-2 ${darkMode ? 'text-light' : 'text-gray-800'}`}>Your cart is empty</h2>
          <p className={`mb-6 ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Add some products to get started!</p>
          <Link to="/products" className="bg-primary hover:bg-accent text-white px-6 py-3 rounded-lg font-medium transition-colors">
            Browse Products
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className={`min-h-screen ${darkMode ? 'bg-dark' : 'bg-gray-100'} pt-20 pb-16 px-4 transition-colors duration-300`}>
      <div className="max-w-7xl mx-auto">
        <h1 className={`text-3xl font-bold mb-8 ${darkMode ? 'text-light' : 'text-gray-800'}`}>Shopping Cart</h1>

        <div className="flex flex-col lg:flex-row gap-8">
          {/* Cart Table */}
          <div className="flex-1">
            <div className={`rounded-lg overflow-hidden border ${darkMode ? 'border-gray-700 bg-gray-800/50' : 'border-gray-200 bg-white'} shadow-lg`}>
              <table className="w-full">
                <thead>
                  <tr className={`${darkMode ? 'bg-gray-800 border-b border-gray-700' : 'bg-gray-50 border-b border-gray-200'}`}>
                    <th className={headerClass}>S. No.</th>
                    <th className={headerClass}>Product Image</th>
                    <th className={headerClass}>Product Name</th>
                    <th className={headerClass}>Unit Price</th>
                    <th className={headerClass}>Quantity</th>
                    <th className={headerClass}>Total</th>
                    <th className={headerClass}>Remove</th>
                  </tr>
                </thead>
                <tbody>
                  {items.map((item, index) => {
                    const effectivePrice = item.discount ? item.price * (1 - item.discount) : item.price;
                    return (
                      <tr key={item.productId} className={`border-b ${darkMode ? 'border-gray-700' : 'border-gray-200'} transition-colors`}>
                        <td className={`${cellClass} text-center font-medium`}>{index + 1}</td>
                        <td className={`${cellClass} text-center`}>
                          <div className={`w-24 h-24 mx-auto rounded-lg overflow-hidden ${darkMode ? 'bg-gray-700' : 'bg-gray-100'}`}>
                            <img src={`/${item.imgName}`} alt={item.name} className="w-full h-full object-contain p-1" />
                          </div>
                        </td>
                        <td className={`${cellClass} font-medium`}>{item.name}</td>
                        <td className={`${cellClass} text-center`}>${effectivePrice.toFixed(2)}</td>
                        <td className={`${cellClass} text-center`}>
                          <input
                            type="number"
                            min="1"
                            value={item.quantity}
                            onChange={(e) => updateQuantity(item.productId, parseInt(e.target.value) || 1)}
                            className={`w-16 text-center rounded-md border py-1 ${darkMode ? 'bg-gray-700 border-gray-600 text-light' : 'bg-white border-gray-300 text-gray-800'} focus:border-primary focus:ring-1 focus:ring-primary focus:outline-none`}
                            aria-label={`Quantity for ${item.name}`}
                          />
                        </td>
                        <td className={`${cellClass} text-center font-semibold`}>
                          ${(effectivePrice * item.quantity).toFixed(2)}
                        </td>
                        <td className={`${cellClass} text-center`}>
                          <button
                            onClick={() => removeFromCart(item.productId)}
                            className="text-red-400 hover:text-red-300 transition-colors p-2"
                            aria-label={`Remove ${item.name} from cart`}
                          >
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                            </svg>
                          </button>
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>

              {/* Coupon + Update Cart row */}
              <div className={`flex flex-col sm:flex-row items-center justify-between px-6 py-4 gap-4 ${darkMode ? 'border-t border-gray-700' : 'border-t border-gray-200'}`}>
                <div className="flex items-center gap-2">
                  <input
                    type="text"
                    placeholder="Coupon Code"
                    value={couponCode}
                    onChange={(e) => setCouponCode(e.target.value)}
                    className={`px-4 py-2 rounded-md border ${darkMode ? 'bg-gray-700 border-gray-600 text-light placeholder-gray-400' : 'bg-white border-gray-300 text-gray-800 placeholder-gray-500'} focus:border-primary focus:ring-1 focus:ring-primary focus:outline-none`}
                    aria-label="Coupon code"
                  />
                  <button
                    onClick={handleApplyCoupon}
                    className="bg-primary hover:bg-accent text-white px-5 py-2 rounded-md font-medium transition-colors whitespace-nowrap"
                  >
                    Apply Coupon
                  </button>
                </div>
                {couponError && <span className="text-red-400 text-sm">{couponError}</span>}
                {appliedCoupon && <span className="text-primary text-sm font-medium">Coupon "{appliedCoupon}" applied!</span>}
                <Link to="/products" className="bg-primary hover:bg-accent text-white px-5 py-2 rounded-md font-medium transition-colors whitespace-nowrap">
                  Update Cart
                </Link>
              </div>
            </div>
          </div>

          {/* Order Summary */}
          <div className="lg:w-80">
            <div className={`rounded-lg border ${darkMode ? 'border-gray-700 bg-gray-800/50' : 'border-gray-200 bg-white'} shadow-lg p-6`}>
              <h2 className={`text-2xl font-bold mb-6 text-center ${darkMode ? 'text-light' : 'text-gray-800'}`}>
                Order Summary
              </h2>
              <div className="space-y-4">
                <div className={`flex justify-between ${darkMode ? 'text-gray-300' : 'text-gray-700'}`}>
                  <span className="font-medium">Subtotal</span>
                  <span className="font-semibold">${subtotal.toFixed(2)}</span>
                </div>
                {discountRate > 0 && (
                  <div className="flex justify-between text-red-400">
                    <span className="font-medium">Discount({Math.round(discountRate * 100)}%)</span>
                    <span className="font-semibold">-${discountAmount.toFixed(2)}</span>
                  </div>
                )}
                <div className={`flex justify-between ${darkMode ? 'text-gray-300' : 'text-gray-700'}`}>
                  <span className="font-medium">Shipping</span>
                  <span className="font-semibold">${SHIPPING_COST.toFixed(2)}</span>
                </div>
                <div className={`border-t pt-4 ${darkMode ? 'border-gray-700' : 'border-gray-200'}`}>
                  <div className={`flex justify-between text-lg font-bold ${darkMode ? 'text-light' : 'text-gray-800'}`}>
                    <span>Grand Total</span>
                    <span>${grandTotal.toFixed(2)}</span>
                  </div>
                </div>
              </div>
              <button className="w-full mt-6 bg-primary hover:bg-accent text-white py-3 rounded-full font-semibold transition-colors text-lg">
                Proceed To Checkout
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
