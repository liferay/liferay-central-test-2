# Deployment Helper Gradle Plugin

The Deployment Helper Gradle plugin lets you run the [Liferay Deployment Helper](https://github.com/liferay/liferay-portal/tree/master/modules/util/deployment-helper)
tool in order to create a cluster deployable WAR from your OSGi artifacts.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.deployment.helper", version: "1.0.3"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.deployment.helper"
```

Since the plugin automatically resolves the Liferay Deployment Helper library as
a dependency, you have to configure a repository that hosts the library and its
transitive dependencies. The Liferay CDN repository hosts them all:

```gradle
repositories {
	maven {
		url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
	}
}
```

## Tasks

The plugin adds one task to your project:

Name | Depends On | Type | Description
---- | ---------- | ---- | -----------
`buildDeploymentHelper` | \- | [`BuildDeploymentHelperTask`](#buildeploymenthelper) | Builds a WAR which contains one or more files that will be copied once the WAR is deployed.

### BuildDeploymentHelperTask

Tasks of type `BuildDeploymentHelperTask` extend [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html),
so all its properties and methods, such as [`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args(java.css.Iterable))
and [`maxHeapSize`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize),
are available. They also have the following properties set by default:

Property Name | Default Value
------------- | -------------
[`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args) | Deployment Helper command line arguments
[`classpath`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:classpath) | [`project.configurations.deploymentHelper`](#liferay-deployment-helper-dependency)
[`deploymentFiles`](#deploymentfiles) | The output files of the [`jar`](https://docs.gradle.org/current/userguide/java_plugin.html#sec:jar) tasks of this project and all its sub-projects.
[`main`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:main) | `"com.liferay.deployment.helper.DeploymentHelper"`
[`outputFile`](#outputfile) | `"${project.buildDir}/${project.name}.war"`

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
<a name="deploymentfiles"></a>`deploymentFiles` | `FileCollection` | `[]` | The files or directories to include in the WAR and copy once the WAR is deployed. If a directory is added to this collection, all the JAR files contained in the directory will be included in the WAR.
`deploymentPath` | `File` | `null` | The directory where the included files will be copied to.
<a name="outputfile"></a>`outputFile` | `File` | `null` | The WAR file to build.

The properties of type `File` support any type that can be resolved by [`project.file`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:file(java.css.Object)).

#### Task Methods

Method | Description
------ | -----------
`BuildDeploymentHelperTask deploymentFiles(Iterable<?> deploymentFiles)` | Adds files or directories to include in the WAR and copy once the WAR is deployed. The values are evaluated as per [`project.files`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:files(java.lang.Object[])).
`BuildDeploymentHelperTask deploymentFiles(Object... deploymentFiles)` | Adds files or directories to include in the WAR and copy once the WAR is deployed. The values are evaluated as per [`project.files`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:files(java.lang.Object[])).

## Additional Configuration

There are additional configurations that can help you use the Deployment Helper.

### Liferay Deployment Helper Dependency

By default, the plugin creates a configuration called `deploymentHelper` and
adds a dependency to the latest released version of the Liferay Deployment
Helper. It is possible to override this setting and use a specific version of
the tool by manually adding a dependency to the `deploymentHelper`
configuration:

```gradle
dependencies {
	deploymentHelper group: "com.liferay", name: "com.liferay.deployment.helper", version: "1.0.3"
}
```