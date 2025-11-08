import { Navigation } from '../components/Navigation';
import { Hero } from '../components/Hero';
import { FeaturesGrid } from '../components/FeaturesGrid';
import { AIShowcase } from '../components/AIShowcase';
import { HowItWorks } from '../components/HowItWorks';
import { Footer } from '../components/Footer';
import { FloatingAI } from '../components/FloatingAI';

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-background overflow-hidden">
      <Navigation />
      <Hero />
      <FeaturesGrid />
      <AIShowcase />
      <HowItWorks />
      <Footer />
      <FloatingAI />
    </div>
  );
}
