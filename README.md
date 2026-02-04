# smithy-event-codegen

Smithy build plugin that generates Java constants for `@event` traits.

## What It Generates

Given Smithy shapes annotated with `tech.maze.events#event`, the plugin emits a Java class containing event type constants. This keeps runtime code free of hard-coded event type strings.

Example (generated):

```java
package tech.maze.dtos.helloworld.events;

public final class EventTypes {
  private EventTypes() {}

  public static final String ADD_REQUEST = "tech.maze.helloworld.add.request";
}
```

## Usage

Add the plugin JAR to Smithy build dependencies and enable it in `smithy-build.json`:

```json
{
  "version": "1.0",
  "sources": ["src/main/smithy"],
  "maven": {
    "dependencies": [
      "tech.maze:smithy-event-codegen:0.0.1",
      "tech.maze:smithy-event-traits:0.0.2"
    ]
  },
  "projections": {
    "resolved": {
      "plugins": {
        "maze-event-types": {
          "package": "tech.maze.dtos.helloworld.events",
          "className": "EventTypes"
        }
      }
    }
  }
}
```

## Development

### Requirements

- JDK 21

### Build

```bash
./gradlew build
```

### Publish (GitHub Packages)

Set the following environment variables:

- `GITHUB_USERNAME` (use `x-access-token` in CI)
- `GITHUB_TOKEN` (PAT with `read:packages` + `write:packages`)
- `GITHUB_REPOSITORY` (e.g. `maze-technology/smithy-event-codegen`)

Then publish:

```bash
./gradlew publish
```

To publish a tagged version:

```bash
./gradlew -Pversion=0.0.1 publish
```
