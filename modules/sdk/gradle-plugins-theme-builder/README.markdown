# Theme Builder Gradle Plugin

The Theme Builder Gradle plugin allows you to run the [Liferay Theme Builder](https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-theme-builder)
tool in order to build the Liferay theme files in your project.

## Usage

To use the plugin, include it in your build script:

```gradle
buildscript {
	dependencies {
		classpath group: "com.liferay", name: "com.liferay.gradle.plugins.theme.builder", version: "1.0.0"
	}

	repositories {
		maven {
			url "https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public"
		}
	}
}

apply plugin: "com.liferay.portal.tools.theme.builder"
```

The Theme Builder plugin automatically applies the [`war`](https://docs.gradle.org/current/userguide/war_plugin.html)
plugin. It also applies the [`com.liferay.css.builder`](https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-css-builder)
plugin in order to compile the [Sass](http://sass-lang.com/) files in the theme.

Since the plugin automatically resolves the Liferay Theme Builder library as a
dependency, you have to configure a repository that hosts the library and its
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
`buildTheme` | \- | [`BuildThemeTask`](#buildthemetask) | Builds the theme files.

The plugin also adds the following dependencies to tasks defined by the
`com.liferay.css.builder` and `war` plugins:

Name | Depends On
---- | ----------
[`buildCSS`](https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-css-builder#tasks) | `buildTheme`
[`war`](https://docs.gradle.org/current/userguide/war_plugin.html#sec:war_default_settings) | `buildTheme`

Moreover, the `war` task is configured to exclude the directory specified in the
[`buildTheme.diffsDir`](#diffsdir) property from the WAR file.

The `buildTheme` task is automatically configured with sensible defaults:

Property Name | Default Value
------------- | -------------
[`diffsDir`](#diffsdir) | `"${project.webAppDir}/_diffs"`
[`outputDir`](#outputdir) | `project.webAppDir`
[`parentDir`](#parentdir) | The first JAR file in the [`parentThemes`](#parent-theme-dependencies) configuration that contains a `META-INF/resources/${buildTheme.parentName}` directory
[`parentName`](#parentname) | `"_styled"`
[`templateExtension`](#templateextension) | `"ftl"`
[`themeName`](#themename) | `project.name`
[`unstyledDir`](#unstyleddir) | The first JAR file in the [`parentThemes`](#parent-theme-dependencies) configuration that contains a `META-INF/resources/_unstyled` directory

### BuildThemeTask

Tasks of type `BuildThemeTask` extend [`JavaExec`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html),
so all its properties and methods, such as [`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args(java.css.Iterable))
and [`maxHeapSize`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize),
are available. They also have the following properties set by default:

Property Name | Default Value
------------- | -------------
[`args`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:args) | Theme Builder command line arguments
[`classpath`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:classpath) | [`project.configurations.themeBuilder`](#liferay-theme-builder-dependency)
[`main`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:main) | `"com.liferay.portal.tools.theme.builder.ThemeBuilder"`

#### Task Properties

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`diffsDir` | `File` | `null` | The directory that contains the files to copy over the parent theme. It sets the `--diffs-dir` argument.
`outputDir` | `File` | `null` | The directory where to build the theme. It sets the `--output-dir` argument.
`parentDir` | `File` | `null` | The directory or the JAR file of the parent theme. It sets the `--parent-path` argument.
`parentName` | `String` | `null` | The name of the parent theme. It sets the `--parent-name` argument.
`templateExtension` | `String` | `null` | The extension of the template files, usually `"ftl"` or `"vm"`. It sets the `--template-extension` argument.
`themeName` | `String` | `null` | The name of the new theme. It sets the `--name` argument.
`unstyledDir` | `File` | `null` | The directory or the JAR file of [Liferay Frontend Theme Unstyled](https://github.com/liferay/liferay-portal/tree/master/modules/apps/foundation/frontend-theme/frontend-theme-unstyled). It sets the `--unstyled-dir` argument.

The properties of type `File` support any type that can be resolved by [`project.file`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:file(java.css.Object)).
Moreover, it is possible to use Closures and Callables as values for the
`String` properties, to defer evaluation until task execution.

## Additional Configuration

There are additional configurations that can help you use the CSS Builder.

### Liferay Theme Builder Dependency

By default, the plugin creates a configuration called `themeBuilder` and adds a
dependency to the latest released version of the Liferay Theme Builder. It is
possible to override this setting and use a specific version of the tool by
manually adding a dependency to the `themeBuilder` configuration:

```gradle
dependencies {
	themeBuilder group: "com.liferay", name: "com.liferay.portal.tools.theme.builder", version: "1.0.0"
}
```

### Parent Theme Dependencies

By default, the plugin creates a configuration called `parentThemes` and adds
dependencies to the latest released versions of the
[Liferay Frontend Theme Styled](https://github.com/liferay/liferay-portal/tree/master/modules/apps/foundation/frontend-theme/frontend-theme-styled)
and [Liferay Frontend Theme Unstyled](https://github.com/liferay/liferay-portal/tree/master/modules/apps/foundation/frontend-theme/frontend-theme-unstyled)
artifacts. It is possible to override this setting and use a specific version of
the artifacts by manually adding dependencies to the `parentThemes`
configuration:

```gradle
dependencies {
	parentThemes group: "com.liferay", name: "com.liferay.frontend.theme.styled", version: "2.0.13"
	parentThemes group: "com.liferay", name: "com.liferay.frontend.theme.unstyled", version: "2.0.13"
}
```