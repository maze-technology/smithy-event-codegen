# smithy-event-codegen

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=coverage)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=bugs)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=maze-technology_smithy-event-codegen&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=maze-technology_smithy-event-codegen)

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

### Publish (GitLab Package Registry)

Set the following environment variables:

- `GITLAB_MAVEN_USER` (default `gitlab-ci-token`) (use `x-access-token` in CI)
- `GITLAB_TOKEN` (PAT with `read_api` + `read_package_registry`; CI uses `CI_JOB_TOKEN` to publish)
- Optional: `GITLAB_MAVEN_URL`, `GITLAB_MAVEN_USER`, `GITLAB_MAVEN_PASSWORD` (group Deploy Token for local reads)
- `GITHUB_REPOSITORY` (e.g. `maze-technology/smithy-event-codegen`)

Then publish:

```bash
./gradlew publish
```

To publish a tagged version:

```bash
./gradlew -Pversion=0.0.1 publish
```
