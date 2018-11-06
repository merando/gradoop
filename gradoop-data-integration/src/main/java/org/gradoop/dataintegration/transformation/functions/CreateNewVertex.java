/*
 * Copyright © 2014 - 2018 Leipzig University (Database Research Group)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradoop.dataintegration.transformation.functions;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.common.model.impl.pojo.Vertex;
import org.gradoop.common.model.impl.pojo.VertexFactory;
import org.gradoop.common.model.impl.properties.PropertyValue;

import java.util.Collections;
import java.util.List;

/**
 * Creates a new {@link Vertex} containing the given attribute {@link PropertyValue} and its origin.
 */
public class CreateNewVertex implements FlatMapFunction<Tuple2<PropertyValue, GradoopId>, Tuple2<Vertex, List<GradoopId>>> {
  /** The new vertex label. */
  private final String newVertexLabel;

  /** The new property key */
  private final String newPropertyName;

  /** The Factory the vertices are created with. */
  private final VertexFactory vertexFactory;

  /**
   * The constructor for creating new vertices with their origin Ids.
   * @param newVertexLabel The new vertex Label.
   * @param newPropertyName The new property key.
   */
  public CreateNewVertex(String newVertexLabel, String newPropertyName) {
    this.vertexFactory = new VertexFactory();
    this.newVertexLabel = newVertexLabel;
    this.newPropertyName = newPropertyName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flatMap(Tuple2<PropertyValue, GradoopId> value,
                      Collector<Tuple2<Vertex, List<GradoopId>>> out) {
    Vertex vertex = vertexFactory.createVertex(newVertexLabel);
    vertex.setProperty(newPropertyName, value.f0);

    out.collect(new Tuple2<>(vertex, Collections.singletonList(value.f1)));
  }
}
