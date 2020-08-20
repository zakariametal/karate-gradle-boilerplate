## Description

Automation tests (API tests / integration tests / performance tests) using the [Karate](https://intuit.github.io/karate/) framework. Reuse your `.feature` files between different types of tests!

## Run mocks

Karate comes with the ability to setup mocks. Mocks are artifacts shared between a service producer and consumer for an API contract. A producer can run API tests against the mocks to ensure that consumer mocks stay up to date with the contract.

To run the example mock:

```
./gradlew :example:run
```

To publish the mock to local [docker](https://www.docker.com/get-starte) daemon:

```
./gradlew jibDockerBuild
```

To publish the mock to a docker registry, you first need to configure [jib](./example/build.gradle). Then:

```
./gradlew jib
```

## Execute API tests / integration tests

Options:

- `SIM_FEATURE` - Which karate feature files to use for loadtesting. Default = everything

```
./gradlew clean test
```

Or with selected feature files:

```
./gradlew clean test -DSIM_FEATURE=automation/example.feature
```

- `Dkarate.env` - With which environment configuration to run test.Details of environment name and configuration can be found in karate-config.js.

```
./gradlew clean test -DSIM_FEATURE=automation/example.feature -Dkarate.env=stg
```

View the generated cucumber report in your browser 💰

## Execute performance tests

Options:

- `SIM_NAME` - You can create different settings in different simulation files
- `SIM_USERS` - No. of users concurrently. Default = 10
- `SIM_PERIOD` - Load test period. Default = 10
- `SIM_FEATURE` - Which karate feature files to use for loadtesting. Default = automation/example.feature
- `Dkarate.env` - For which environment you want to run. You can add new configuration for any environment in karate-config file.

```
./gradlew -PSIM_NAME=performance.GatlingRunner -DSIM_USERS=10 -DSIM_PERIOD=10 -DSIM_FEATURE=automation/example.feature loadtest
```

View the generated gatling loadtest report! 💰💰💰

## Development

Just add a new `.feature` file in the `automation/` folder.
