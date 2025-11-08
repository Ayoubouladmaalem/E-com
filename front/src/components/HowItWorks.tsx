import { motion } from 'framer-motion';

const steps = [
  {
    number: '01',
    title: 'Browse & Discover',
    description: 'Explore millions of products with AI-powered search and recommendations',
    icon: '🔍',
  },
  {
    number: '02',
    title: 'Get Personalized Suggestions',
    description: "Our AI learns your preferences and suggests items you'll love",
    icon: '✨',
  },
  {
    number: '03',
    title: 'Checkout & Enjoy',
    description: 'Secure payment and fast delivery to your doorstep',
    icon: '🚀',
  },
];

export function HowItWorks() {
  return (
    <section id="how" className="relative py-20 px-4 sm:px-6 lg:px-8">
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
              How It Works
            </span>
          </h2>
          <p className="text-slate-400 text-lg">Three simple steps to smarter shopping</p>
        </motion.div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 relative">
          <div className="hidden md:block absolute top-24 left-0 right-0 h-1 bg-gradient-to-r from-purple-500/0 via-purple-500/50 to-cyan-500/0"></div>

          {steps.map((step, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: index * 0.1 }}
              viewport={{ once: true }}
              className="relative"
            >
              <div className="glass rounded-xl p-8 border border-slate-700/50 h-full">
                <div className="flex items-center justify-between mb-6">
                  <div className="w-16 h-16 rounded-full bg-gradient-to-br from-purple-500 to-cyan-500 flex items-center justify-center text-3xl">
                    {step.icon}
                  </div>
                  <div className="text-4xl font-bold text-slate-700">{step.number}</div>
                </div>
                <h3 className="text-xl font-semibold text-white mb-3">{step.title}</h3>
                <p className="text-slate-400">{step.description}</p>
              </div>
            </motion.div>
          ))}
        </div>
      </div>
    </section>
  );
}

