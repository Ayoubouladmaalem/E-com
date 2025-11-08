import { motion, AnimatePresence } from 'framer-motion';
import { useState } from 'react';

export function FloatingAI() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="fixed bottom-8 right-8 z-40">
      <motion.button
        initial={{ scale: 0 }}
        animate={{ scale: 1 }}
        transition={{ delay: 1, type: 'spring' }}
        whileHover={{ scale: 1.1 }}
        whileTap={{ scale: 0.95 }}
        onClick={() => setIsOpen(!isOpen)}
        className="w-16 h-16 rounded-full bg-gradient-to-br from-purple-500 to-cyan-500 flex items-center justify-center text-2xl shadow-lg shadow-purple-500/50 hover:shadow-purple-500/75 transition animate-glow"
      >
        💬
      </motion.button>

      <AnimatePresence>
        {isOpen && (
          <motion.div
            initial={{ opacity: 0, y: 20, scale: 0.9 }}
            animate={{ opacity: 1, y: 0, scale: 1 }}
            exit={{ opacity: 0, y: 20, scale: 0.9 }}
            transition={{ type: 'spring', damping: 20 }}
            className="absolute bottom-20 right-0 w-80 glass rounded-2xl p-6 border border-slate-700/50 shadow-2xl"
          >
            <div className="flex items-center justify-between mb-4">
              <h3 className="font-semibold text-white">Cartiva AI Assistant</h3>
              <button onClick={() => setIsOpen(false)} className="text-slate-400 hover:text-white transition">
                ✕
              </button>
            </div>
            <p className="text-slate-300 text-sm mb-4">Hi! How can I help you find the perfect product today?</p>
            <input
              type="text"
              placeholder="Ask me anything..."
              className="w-full bg-slate-800/50 border border-slate-700 rounded-lg px-4 py-2 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500/50"
            />
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}

