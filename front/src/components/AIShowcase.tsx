import { motion } from 'framer-motion';

export function AIShowcase() {

  return (
    <section id="showcase" className="relative py-20 px-4 sm:px-6 lg:px-8 overflow-hidden">
      <div className="max-w-7xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
          viewport={{ once: true }}
          className="text-center mb-16"
        >
          <h2 className="text-4xl sm:text-5xl font-bold mb-4">
            <span className="bg-gradient-to-r from-purple-400 to-cyan-400 bg-clip-text text-transparent">
              AI-Powered Intelligence
            </span>
          </h2>
          <p className="text-slate-400 text-lg">Real-time product recommendations and insights</p>
        </motion.div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          <motion.div
            initial={{ opacity: 0, x: -40 }}
            whileInView={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.8 }}
            viewport={{ once: true }}
            className="glass rounded-2xl p-6 border border-slate-700/50 h-96 flex flex-col"
          >
            <div className="flex items-center gap-3 mb-4 pb-4 border-b border-slate-700/50">
              <div className="w-10 h-10 rounded-full bg-gradient-to-br from-purple-500 to-cyan-500 flex items-center justify-center">
                <span className="text-white text-lg">🤖</span>
              </div>
              <div>
                <div className="text-sm font-semibold text-white">Cartiva AI Assistant</div>
                <div className="text-xs text-slate-400">Online • Ready to help</div>
              </div>
            </div>
            <div className="flex-1 overflow-y-auto space-y-4 mb-4">
              <div className="flex gap-2">
                <div className="w-8 h-8 rounded-full bg-gradient-to-br from-purple-500 to-cyan-500 flex-shrink-0 flex items-center justify-center text-xs">
                  🤖
                </div>
                <div className="glass rounded-lg p-3 max-w-xs">
                  <p className="text-sm text-slate-300">What are you looking for today?</p>
                </div>
              </div>
              <div className="flex gap-2 justify-end">
                <div className="glass rounded-lg p-3 max-w-xs bg-gradient-to-r from-purple-500/20 to-cyan-500/20">
                  <p className="text-sm text-slate-200">Show me trending electronics</p>
                </div>
                <div className="w-8 h-8 rounded-full bg-slate-700/50 border border-slate-600 flex-shrink-0 flex items-center justify-center text-xs">
                  👤
                </div>
              </div>
              <div className="flex gap-2">
                <div className="w-8 h-8 rounded-full bg-gradient-to-br from-purple-500 to-cyan-500 flex-shrink-0 flex items-center justify-center text-xs">
                  🤖
                </div>
                <div className="glass rounded-lg p-3 max-w-xs">
                  <p className="text-sm text-slate-300">Found 2,847 items matching your interests...</p>
                </div>
              </div>
            </div>
            <div className="flex gap-2">
              <input
                type="text"
                placeholder="Ask me anything..."
                className="flex-1 bg-slate-800/50 border border-slate-700 rounded-lg px-4 py-2 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500/50"
              />
              <button className="px-4 py-2 bg-gradient-to-r from-purple-500 to-cyan-500 rounded-lg text-white text-sm font-medium hover:shadow-lg hover:shadow-purple-500/50 transition">
                Send
              </button>
            </div>
          </motion.div>

          <motion.div
            initial={{ opacity: 0, x: 40 }}
            whileInView={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.8 }}
            viewport={{ once: true }}
            className="space-y-6"
          >
            <div className="glass rounded-xl p-6 border border-slate-700/50">
              <div className="flex items-start gap-4">
                <div className="w-12 h-12 rounded-lg bg-gradient-to-br from-purple-500/20 to-purple-600/20 flex items-center justify-center text-2xl flex-shrink-0">
                  🎯
                </div>
                <div>
                  <h3 className="text-lg font-semibold text-white mb-2">Smart Recommendations</h3>
                  <p className="text-slate-400 text-sm">
                    Advanced AI algorithms analyze your preferences to suggest products you'll love
                  </p>
                </div>
              </div>
            </div>

            <div className="glass rounded-xl p-6 border border-slate-700/50">
              <div className="flex items-start gap-4">
                <div className="w-12 h-12 rounded-lg bg-gradient-to-br from-cyan-500/20 to-cyan-600/20 flex items-center justify-center text-2xl flex-shrink-0">
                  ⚡
                </div>
                <div>
                  <h3 className="text-lg font-semibold text-white mb-2">Lightning Fast</h3>
                  <p className="text-slate-400 text-sm">
                    Real-time processing delivers instant results and personalized experiences
                  </p>
                </div>
              </div>
            </div>

            <div className="glass rounded-xl p-6 border border-slate-700/50">
              <div className="flex items-start gap-4">
                <div className="w-12 h-12 rounded-lg bg-gradient-to-br from-blue-500/20 to-blue-600/20 flex items-center justify-center text-2xl flex-shrink-0">
                  🧠
                </div>
                <div>
                  <h3 className="text-lg font-semibold text-white mb-2">Always Learning</h3>
                  <p className="text-slate-400 text-sm">
                    Our AI continuously improves to provide better recommendations over time
                  </p>
                </div>
              </div>
            </div>

            <div className="pt-4">
              <p className="text-slate-400 text-sm text-center">
                <span className="text-purple-400 font-semibold">Powered by Advanced AI</span> • Real-time processing • Continuous improvement
              </p>
            </div>
          </motion.div>
        </div>
      </div>
    </section>
  );
}

