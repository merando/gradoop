package org.gradoop.model.impl.operators.summarization.functions.aggregation;

import org.gradoop.model.impl.properties.PropertyValue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base class for aggregator functions.
 *
 * @param <IN> input type for the specific aggregation function
 */
public abstract class BaseAggregator<IN> implements Aggregator<IN> {

  /**
   * Class version for serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Property key to access the values.
   */
  private final String propertyKey;

  /**
   * Property key to use for storing the final aggregate.
   */
  private final String aggregatePropertyKey;

  /**
   * Creates new aggregator.
   *
   * @param propertyKey           used to fetch property value from elements
   * @param aggregatePropertyKey  used to store the final aggregate value
   */
  protected BaseAggregator(String propertyKey, String aggregatePropertyKey) {
    this.propertyKey          = checkNotNull(propertyKey);
    this.aggregatePropertyKey = checkNotNull(aggregatePropertyKey);
  }

  @Override
  public String getPropertyKey() {
    return propertyKey;
  }

  @Override
  public String getAggregatePropertyKey() {
    return aggregatePropertyKey;
  }

  @Override
  public PropertyValue getAggregate() {
    return isInitialized() ?
      getAggregateInternal() : PropertyValue.NULL_VALUE;
  }

  /**
   * Checks if the internal aggregate has been initialized.
   *
   * @return true, iff the internal value is initialized
   */
  protected abstract boolean isInitialized();

  /**
   * Initializes the internal aggregate.
   *
   * @param value first property value.
   */
  protected abstract void initializeAggregate(IN value);

  /**
   * Adds the given value to the internal aggregate.
   *
   * @param value property value
   */
  protected abstract void aggregateInternal(IN value);

  /**
   * Returns the aggregate.
   *
   * @return aggregate
   */
  protected abstract PropertyValue getAggregateInternal();
}