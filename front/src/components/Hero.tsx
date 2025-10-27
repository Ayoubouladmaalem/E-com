import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';

export function Hero() {
  return (
    <section className="relative min-h-screen flex items-center justify-center pt-20 overflow-hidden">
      {/* Animated gradient orbs */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute top-0 left-1/4 w-96 h-96 bg-purple-500/20 rounded-full blur-3xl animate-gradient"></div>
        <div
          className="absolute bottom-0 right-1/4 w-96 h-96 bg-cyan-500/20 rounded-full blur-3xl animate-gradient"
          style={{ animationDelay: '2s' }}
        ></div>
        <div
          className="absolute top-1/2 right-0 w-96 h-96 bg-blue-500/10 rounded-full blur-3xl animate-gradient"
          style={{ animationDelay: '4s' }}
        ></div>
      </div>

      {/* Floating particles */}
      <div className="absolute inset-0 overflow-hidden">
        {[...Array(20)].map((_, i) => (
          <motion.div
            key={i}
            className="absolute w-1 h-1 bg-purple-400/50 rounded-full"
            animate={{
              y: [0, -100, 0],
              x: [0, Math.random() * 100 - 50, 0],
              opacity: [0, 1, 0],
            }}
            transition={{
              duration: 3 + Math.random() * 2,
              repeat: Infinity,
              delay: Math.random() * 2,
            }}
            style={{
              left: `${Math.random() * 100}%`,
              top: `${Math.random() * 100}%`,
            }}
          />
        ))}
      </div>

      <div className="relative z-10 max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.8 }}>
          <h1 className="text-5xl sm:text-6xl lg:text-7xl font-bold mb-6 text-balance">
            <span className="bg-gradient-to-r from-purple-400 via-cyan-400 to-blue-400 bg-clip-text text-transparent">
              Shop Smarter with AI
            </span>
          </h1>
          <p className="text-lg sm:text-xl text-slate-300 mb-8 text-balance max-w-2xl mx-auto">
            Experience intelligent recommendations and personalized shopping powered by cutting-edge artificial
            intelligence
          </p>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8, delay: 0.2 }}
          className="flex flex-col sm:flex-row gap-4 justify-center"
        >
          <Link
            to="/products"
            className="px-8 py-3 rounded-full bg-white text-slate-900 font-semibold hover:shadow-lg hover:shadow-white/20 transition"
          >
            Browse Products
          </Link>
          <Link
            to="/register"
            className="px-8 py-3 rounded-full border border-slate-600 text-white font-semibold hover:border-cyan-500 hover:shadow-lg hover:shadow-cyan-500/20 transition"
          >
            Get Started
          </Link>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 40 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8, delay: 0.4 }}
          className="mt-16 relative"
        >
          <div className="glass rounded-2xl p-8 max-w-2xl mx-auto border border-slate-700/50 hover:border-cyan-500/50 transition">
            <div className="grid grid-cols-3 gap-4 text-center">
              <div>
                <div className="text-2xl font-bold text-purple-400">AI-Powered</div>
                <div className="text-sm text-slate-400">Smart Search</div>
              </div>
              <div>
                <div className="text-2xl font-bold text-cyan-400">Instant</div>
                <div className="text-sm text-slate-400">Recommendations</div>
              </div>
              <div>
                <div className="text-2xl font-bold text-blue-400">24/7</div>
                <div className="text-sm text-slate-400">AI Assistant</div>
              </div>
            </div>
          </div>
        </motion.div>
      </div>
    </section>
  );
}

