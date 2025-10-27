import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';

export function Navigation() {
  return (
    <motion.nav
      initial={{ y: -100 }}
      animate={{ y: 0 }}
      transition={{ duration: 0.5 }}
      className="fixed top-0 w-full z-50 glass"
    >
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex items-center justify-between">
        <Link to="/" className="flex items-center gap-2">
          <div className="w-8 h-8 bg-gradient-to-br from-purple-500 to-cyan-500 rounded-lg flex items-center justify-center">
            <span className="text-white font-bold text-sm">C</span>
          </div>
          <span className="text-xl font-bold bg-gradient-to-r from-purple-400 to-cyan-400 bg-clip-text text-transparent">
            Cartiva
          </span>
        </Link>
        <div className="hidden md:flex items-center gap-8">
          <a href="#features" className="text-sm text-slate-300 hover:text-white transition">
            Features
          </a>
          <a href="#showcase" className="text-sm text-slate-300 hover:text-white transition">
            AI Showcase
          </a>
          <a href="#how" className="text-sm text-slate-300 hover:text-white transition">
            How It Works
          </a>
        </div>
        <Link
          to="/register"
          className="px-6 py-2 rounded-full bg-gradient-to-r from-purple-500 to-cyan-500 text-white text-sm font-medium hover:shadow-lg hover:shadow-purple-500/50 transition"
        >
          Get Started
        </Link>
      </div>
    </motion.nav>
  );
}

