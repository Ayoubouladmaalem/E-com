import { motion } from 'framer-motion';

const features = [
  {
    icon: '🤖',
    title: 'AI Assistant',
    description: 'Get personalized recommendations and instant support',
    gradient: 'from-purple-500/20 to-purple-600/20',
    borderColor: 'border-purple-500/30',
  },
  {
    icon: '🛍️',
    title: 'Smart Shopping',
    description: 'Intelligent search and product recommendations',
    gradient: 'from-cyan-500/20 to-cyan-600/20',
    borderColor: 'border-cyan-500/30',
  },
  {
    icon: '⚡',
    title: 'Fast Delivery',
    description: 'Quick and reliable shipping options',
    gradient: 'from-blue-500/20 to-blue-600/20',
    borderColor: 'border-blue-500/30',
  },
  {
    icon: '💳',
    title: 'Secure Payments',
    description: 'Multiple payment methods with secure transactions',
    gradient: 'from-pink-500/20 to-pink-600/20',
    borderColor: 'border-pink-500/30',
  },
];

export function FeaturesGrid() {
  return (
    <section id="features" className="relative py-20 px-4 sm:px-6 lg:px-8">
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
              Powerful Features
            </span>
          </h2>
          <p className="text-slate-400 text-lg">Everything you need for an exceptional shopping experience</p>
        </motion.div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {features.map((feature, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: index * 0.1 }}
              viewport={{ once: true }}
              className={`glass rounded-xl p-6 border ${feature.borderColor} glass-hover group`}
            >
              <div
                className={`w-12 h-12 rounded-lg bg-gradient-to-br ${feature.gradient} flex items-center justify-center text-2xl mb-4 group-hover:scale-110 transition`}
              >
                {feature.icon}
              </div>
              <h3 className="text-lg font-semibold mb-2 text-white">{feature.title}</h3>
              <p className="text-slate-400 text-sm">{feature.description}</p>
              <div className="mt-4 h-1 w-0 bg-gradient-to-r from-purple-500 to-cyan-500 group-hover:w-full transition-all duration-300"></div>
            </motion.div>
          ))}
        </div>
      </div>
    </section>
  );
}

