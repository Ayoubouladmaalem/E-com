import { useState } from 'react';
import type { LoginRequest, RegisterRequest } from '../types';

export const useAuth = () => {
  const [loading] = useState(false);
  const [error] = useState<string | null>(null);

  const login = async (_credentials: LoginRequest) => {
    // TODO: Implement login logic
  };

  const register = async (_userData: RegisterRequest) => {
    // TODO: Implement registration logic
  };

  const logout = async () => {
    // TODO: Implement logout logic
  };

  return {
    login,
    register,
    logout,
    loading,
    error,
  };
};
