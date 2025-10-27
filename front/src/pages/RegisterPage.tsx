import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { RegisterForm } from '../features/auth/components';
import { useAuth } from '../features/auth/hooks';

export default function RegisterPage() {
  const { register, loading, error } = useAuth();

  return (
    <div className="min-h-screen bg-background flex items-center justify-center px-4 py-12 relative overflow-hidden">
      {/* Background gradient orbs */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute top-0 right-1/4 w-96 h-96 bg-cyan-500/20 rounded-full blur-3xl"></div>
        <div className="absolute bottom-0 left-1/4 w-96 h-96 bg-purple-500/20 rounded-full blur-3xl"></div>
      </div>

      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="w-full max-w-md relative z-10"
      >
        {/* Logo */}
        <Link to="/" className="flex items-center justify-center gap-2 mb-8">
          <div className="w-10 h-10 bg-gradient-to-br from-purple-500 to-cyan-500 rounded-lg flex items-center justify-center">
            <span className="text-white font-bold">C</span>
          </div>
          <span className="text-2xl font-bold bg-gradient-to-r from-purple-400 to-cyan-400 bg-clip-text text-transparent">
            Cartiva
          </span>
        </Link>

        {/* Register Form */}
        <div className="glass rounded-2xl p-8 border border-slate-700/50">
          <h2 className="text-2xl font-bold text-white mb-2">Create account</h2>
          <p className="text-slate-400 mb-6">Start your journey with us</p>
          
          {error && (
            <div className="mb-4 p-3 rounded-lg bg-red-500/10 border border-red-500/50">
              <p className="text-red-400 text-sm">{error}</p>
            </div>
          )}
          
          <RegisterForm onSubmit={register} />
          
          {loading && (
            <div className="mt-4 text-center">
              <p className="text-slate-400 text-sm">Creating account...</p>
            </div>
          )}
        </div>
      </motion.div>
    </div>
  );
}

