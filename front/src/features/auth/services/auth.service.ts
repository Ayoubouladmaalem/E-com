import type { LoginRequest, RegisterRequest, AuthResponse } from '../types';

export const authService = {
  login: async (_credentials: LoginRequest): Promise<AuthResponse> => {
    // TODO: Implement login API call
    throw new Error('not implemented yet');
  },

  register: async (_userData: RegisterRequest): Promise<AuthResponse> => {
    // TODO: Implement register API call
    throw new Error('not implemented yet');
  },

  logout: async (): Promise<void> => {
    // TODO: Implement logout logic
    // Clear token from localStorage
  },

  getCurrentUser: async () => {
    // TODO: Implement get current user API call
    throw new Error('not implemented yet');
  },

  refreshToken: async (): Promise<string> => {
    // TODO: Implement token refresh API call
    throw new Error('not implemented yet');
  },
};

