package meta;

import org.junit.jupiter.params.aggregator.AggregateWith;
import provider.SystemAgregator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AggregateWith(SystemAgregator.class)
public @interface CsvToPair {
}
