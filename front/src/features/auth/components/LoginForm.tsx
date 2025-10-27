import { useState, type FormEvent } from 'react';
import { Link } from 'react-router-dom';
import type { LoginRequest } from '../types';

interface LoginFormProps {
  onSubmit?: (data: LoginRequest) => void;
}

export default function LoginForm({ onSubmit }: LoginFormProps) {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [errors, setErrors] = useState<{ email?: string; password?: string }>({});

  const validateForm = () => {
    const newErrors: { email?: string; password?: string } = {};

    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Email is invalid';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 8) {
      newErrors.password = 'Password must be at least 8 characters';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      if (onSubmit) {
        onSubmit(formData);
      } else {
        // TODO: API call will go here
        console.log('Login data:', formData);
      }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name as keyof typeof errors]) {
      setErrors(prev => ({ ...prev, [name]: undefined }));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {/* Email Field */}
      <div>
        <label htmlFor="email" className="block text-sm font-medium text-slate-300 mb-2">
          Email
        </label>
        <input
          type="email"
          id="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          className={`w-full bg-slate-800/50 border ${
            errors.email ? 'border-red-500' : 'border-slate-700'
          } rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500/50 transition`}
          placeholder="you@example.com"
        />
        {errors.email && <p className="text-red-400 text-sm mt-1">{errors.email}</p>}
      </div>

      {/* Password Field */}
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-slate-300 mb-2">
          Password
        </label>
        <input
          type="password"
          id="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          className={`w-full bg-slate-800/50 border ${
            errors.password ? 'border-red-500' : 'border-slate-700'
          } rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500/50 transition`}
          placeholder="••••••••"
        />
        {errors.password && <p className="text-red-400 text-sm mt-1">{errors.password}</p>}
      </div>

      {/* Remember Me & Forgot Password */}
      <div className="flex items-center justify-between">
        <label className="flex items-center cursor-pointer">
          <input
            type="checkbox"
            className="w-4 h-4 rounded border-slate-700 bg-slate-800/50 text-purple-500 focus:ring-purple-500 focus:ring-offset-0"
          />
          <span className="ml-2 text-sm text-slate-400">Remember me</span>
        </label>
        <Link to="/forgot-password" className="text-sm text-cyan-400 hover:text-cyan-300 transition">
          Forgot password?
        </Link>
      </div>

      {/* Submit Button */}
      <button
        type="submit"
        className="w-full bg-gradient-to-r from-purple-500 to-cyan-500 text-white font-semibold py-3 rounded-lg hover:shadow-lg hover:shadow-purple-500/50 transition"
      >
        Sign in
      </button>

      {/* Divider */}
      <div className="relative my-6">
        <div className="absolute inset-0 flex items-center">
          <div className="w-full border-t border-slate-700"></div>
        </div>
        <div className="relative flex justify-center text-sm">
          <span className="px-2 bg-slate-800/50 text-slate-400">Or continue with</span>
        </div>
      </div>

      {/* Social Login Button */}
      <button type="button" className="w-full flex items-center justify-center gap-2 px-4 py-3 border border-slate-700 rounded-lg hover:bg-slate-800/50 transition text-slate-300">
        <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
          <path d="M12.545,10.239v3.821h5.445c-0.712,2.315-2.647,3.972-5.445,3.972c-3.332,0-6.033-2.701-6.033-6.032s2.701-6.032,6.033-6.032c1.498,0,2.866,0.549,3.921,1.453l2.814-2.814C17.503,2.988,15.139,2,12.545,2C7.021,2,2.543,6.477,2.543,12s4.478,10,10.002,10c8.396,0,10.249-7.85,9.426-11.748L12.545,10.239z"/>
        </svg>
        Continue with Google
      </button>

      {/* Sign up link */}
      <p className="text-center text-sm text-slate-400 mt-6">
        Don't have an account?{' '}
        <Link to="/register" className="text-cyan-400 hover:text-cyan-300 transition font-semibold">
          Sign up
        </Link>
      </p>
    </form>
  );
}

