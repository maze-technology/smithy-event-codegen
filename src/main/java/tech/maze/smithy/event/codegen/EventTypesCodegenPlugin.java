package tech.maze.smithy.event.codegen;

import software.amazon.smithy.build.PluginContext;
import software.amazon.smithy.build.SmithyBuildPlugin;

public final class EventTypesCodegenPlugin implements SmithyBuildPlugin {
  @Override
  public String getName() {
    return "maze-event-types";
  }

  @Override
  public void execute(PluginContext context) {
    new EventTypesGenerator().generate(context);
  }
}
