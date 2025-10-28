package provider;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.security.KeyPair;
import java.util.Map;


public class SystemAgregator implements ArgumentsAggregator {

    @Override
    public Map.Entry<String, String> aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
        return null;
    }
}
