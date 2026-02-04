package tech.maze.smithy.event.codegen;

import java.util.Map;
import java.util.TreeMap;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.build.PluginContext;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.Trait;

public final class EventTypesGenerator {
  private static final ShapeId EVENT_TRAIT_ID = ShapeId.from("tech.maze.events#event");

  void generate(PluginContext context) {
    final ObjectNode settings = context.getSettings();
    final String packageName = settings.getStringMember("package")
        .map(StringNode::getValue)
        .orElseThrow(() -> new IllegalArgumentException("'package' setting is required"));
    final String className = settings.getStringMember("className")
        .map(StringNode::getValue)
        .orElse("EventTypes");

    final Map<String, String> eventTypes = collectEventTypes(context.getModel());
    final String javaSource = renderJava(packageName, className, eventTypes);

    final FileManifest manifest = context.getFileManifest();
    final String path = packageName.replace('.', '/') + "/" + className + ".java";
    manifest.writeFile(path, javaSource);
  }

  private Map<String, String> collectEventTypes(Model model) {
    final Map<String, String> eventTypes = new TreeMap<>();

    for (Shape shape : model.shapes().toList()) {
      if (!shape.hasTrait(EVENT_TRAIT_ID)) {
        continue;
      }

      final Trait trait = shape.getTrait(EVENT_TRAIT_ID).orElse(null);
      if (trait == null) {
        continue;
      }

      final ObjectNode node = trait.toNode().expectObjectNode();
      final String type = node.getStringMember("type")
          .map(StringNode::getValue)
          .orElse(null);
      if (type == null || type.isBlank()) {
        continue;
      }

      final String constantName = toConstant(shape.getId().getName());
      eventTypes.put(constantName, type);
    }

    return eventTypes;
  }

  private String renderJava(String packageName, String className, Map<String, String> eventTypes) {
    final StringBuilder builder = new StringBuilder();
    builder.append("package ").append(packageName).append(";\n\n");
    builder.append("/**\n");
    builder.append(" * Generated event type constants.\n");
    builder.append(" */\n");
    builder.append("public final class ").append(className).append(" {\n");
    builder.append("  private ").append(className).append("() {}\n\n");

    for (Map.Entry<String, String> entry : eventTypes.entrySet()) {
      builder.append("  public static final String ")
          .append(entry.getKey())
          .append(" = \"")
          .append(entry.getValue())
          .append("\";\n");
    }

    builder.append("}\n");
    return builder.toString();
  }

  private String toConstant(String name) {
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < name.length(); i++) {
      char ch = name.charAt(i);
      if (Character.isUpperCase(ch) && i > 0) {
        result.append('_');
      }
      result.append(Character.toUpperCase(ch));
    }
    return result.toString();
  }
}
