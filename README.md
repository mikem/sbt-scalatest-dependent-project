# Background

This is a small project to demonstrate the issue described in the StackOverflow question ["How to configure sbt/ScalaTest to load test config from dependent project?"](http://stackoverflow.com/q/38056815/2576)

# Details

There are two projects, `projecta` and `projectb`.

`projecta` has `src/main/resources/application.conf` which defines three values. One of those values is overridden in `src/test/resources/application.conf`.

Each project has a single test which asserts that the test configuration is being read.

## Running the Tests

To run the tests, simply change into the project and run `sbt test`:

```bash
# from the root of this repo
(cd projecta; sbt test)
(cd projectb; sbt test)
```

**Expected outcome:** both tests pass.

**Actual outcome:** only the tests run from `projecta` pass; the tests in `projectb` fail.

## The Classpath

Each test will print all of `projecta`'s entries to `stdout` when it runs. In `projecta`, the test directory comes before the main directory, while in `projectb` the main directory comes before the test directory:

```bash
# in projecta
(cd projecta; sbt test)
[info] Loading global plugins from ${HOME}/.sbt/0.13/plugins
[info] Set current project to project-a (in build file:/path/to/sbt-scalatest-dependent-project/projecta/)
file:/path/to/sbt-scalatest-dependent-project/projecta/target/scala-2.11/test-classes/
file:/path/to/sbt-scalatest-dependent-project/projecta/target/scala-2.11/classes/
[info] ConfigSpec:
[info] Project A Config
[info] - should be read successfully
...

# in projectb
(cd projectb; sbt test)
[info] Loading global plugins from ${HOME}/.sbt/0.13/plugins
[info] Set current project to project-b (in build file:/path/to/sbt-scalatest-dependent-project/projectb/)
file:/path/to/sbt-scalatest-dependent-project/projecta/target/scala-2.11/classes/
file:/path/to/sbt-scalatest-dependent-project/projecta/target/scala-2.11/test-classes/
[info] ConfigSpec:
[info] Project B Config
[info] - should be read successfully *** FAILED ***
...
```
