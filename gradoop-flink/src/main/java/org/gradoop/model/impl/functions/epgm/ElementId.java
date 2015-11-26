package org.gradoop.model.impl.functions.epgm;

import org.apache.flink.api.common.functions.MapFunction;
import org.gradoop.model.api.EPGMElement;
import org.gradoop.model.impl.id.GradoopId;

public class ElementId<EL extends EPGMElement>
  implements MapFunction<EL, GradoopId> {

  @Override
  public GradoopId map(EL element) throws Exception {
    return element.getId();
  }
}