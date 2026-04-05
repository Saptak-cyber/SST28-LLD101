// Strategy interface for distribution
public interface DistributionStrategy {
    int getNodeIndex(String key, int totalNodes);
}
