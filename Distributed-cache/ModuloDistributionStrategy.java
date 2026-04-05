// Modulo-based distribution strategy
public class ModuloDistributionStrategy implements DistributionStrategy {
    
    @Override
    public int getNodeIndex(String key, int totalNodes) {
        return Math.abs(key.hashCode()) % totalNodes;
    }
}
