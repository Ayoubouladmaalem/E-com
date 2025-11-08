
export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstname: string;
  lastname: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  user: {
    id: string;
    firstname: string;
    lastname: string;
    email: string;
  };
}

export interface User {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
}

