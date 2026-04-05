import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// Main implementation of distributed cache
public class DistributedCacheImpl implements DistributedCache {
    private final List<CacheNode> nodes;
    private final DistributionStrategy distributionStrategy;
    private final Database database;
    
    public DistributedCacheImpl(
            int numberOfNodes,
            int capacityPerNode,
            Supplier<EvictionPolicy> evictionPolicyFactory,
            DistributionStrategy distributionStrategy,
            Database database) {
        
        this.nodes = new ArrayList<>(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode(capacityPerNode, evictionPolicyFactory.get()));
        }
        this.distributionStrategy = distributionStrategy;
        this.database = database;
    }
    
    @Override
    public String get(String key) {
        int nodeIndex = distributionStrategy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);
        
        // Try to get from cache
        String value = node.get(key);
        
        // Cache miss - fetch from database
        if (value == null) {
            value = database.fetch(key);
            if (value != null) {
                node.put(key, value);
            }
        }
        
        return value;
    }
    
    @Override
    public void put(String key, String value) {
        // Update database (assumption: database is updated)
        database.save(key, value);
        
        // Update cache
        int nodeIndex = distributionStrategy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);
        node.put(key, value);
    }
}
