/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_API_URL: string;
  readonly VITE_GATEWAY_URL: string;
  // ajoute ici d'autres variables si besoin
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
