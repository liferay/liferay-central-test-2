/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.project.templates;

import aQute.bnd.main.bnd;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.internal.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.StringTestUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import net.diibadaaba.zipdiff.DifferenceCalculator;
import net.diibadaaba.zipdiff.Differences;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@ClassRule
	public static final TemporaryFolder testCaseTemporaryFolder =
		new TemporaryFolder();

	@BeforeClass
	public static void setUpClass() throws IOException {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		_gradleDistribution = URI.create(gradleDistribution);

		_projectTemplateVersions = FileTestUtil.readProperties(
			Paths.get("build", "project-template-versions.properties"));
	}

	@Test
	public void testBuildTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			null, "hello-world-portlet");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/hello/world/portlet/portlet/HelloWorldPortlet.java",
			"public class HelloWorldPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "hello-world-portlet", "-DclassName=HelloWorld",
			"-Dpackage=hello.world.portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/hello.world.portlet-1.0.0.jar",
			"target/hello-world-portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"activator", "bar-activator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"activator", "bar-activator", "-DclassName=BarActivator",
			"-Dpackage=bar.activator");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/bar.activator-1.0.0.jar",
			"target/bar-activator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateApi() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("api", "foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/api/Foo.java",
			"public interface Foo");
		_testContains(
			gradleProjectDir, "src/main/resources/foo/api/packageinfo",
			"1.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "foo", "-DclassName=Foo", "-Dpackage=foo");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo-1.0.0.jar",
			"target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateApiContainsCorrectAuthor() throws Exception {
		String author = "Test Author";

		File gradleProjectDir = _buildTemplateWithGradle(
			"api", "author-test", "--author", author);

		_testContains(
			gradleProjectDir, "src/main/java/author/test/api/AuthorTest.java",
			"@author " + author);

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "author-test", "-Dauthor=" + author,
			"-DclassName=AuthorTest", "-Dpackage=author.test");

		_testContains(
			mavenProjectDir, "src/main/java/author/test/api/AuthorTest.java",
			"@author " + author);
	}

	@Test
	public void testBuildTemplateContentTargetingReport() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/report/FooBarReport.java",
			"public class FooBarReport extends BaseJSPReport");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-report", "foo-bar", "-DclassName=FooBar",
			"-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingRule() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/rule/FooBarRule.java",
			"public class FooBarRule extends BaseJSPRule");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-rule", "foo-bar", "-DclassName=FooBar",
			"-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/tracking/action" +
				"/FooBarTrackingAction.java",
			"public class FooBarTrackingAction extends BaseJSPTrackingAction");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-tracking-action", "foo-bar",
			"-DclassName=FooBar", "-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateControlMenuEntry() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/control/menu" +
				"/FooBarProductNavigationControlMenuEntry.java",
			"public class FooBarProductNavigationControlMenuEntry",
			"extends BaseProductNavigationControlMenuEntry",
			"implements ProductNavigationControlMenuEntry");

		File mavenProjectDir = _buildTemplateWithMaven(
			"control-menu-entry", "foo-bar", "-DclassName=FooBar",
			"-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFormField() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {");
		_testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"class FoobarDDMFormFieldType extends BaseDDMFormFieldType");
		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"'foobar-form-field': {");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{template .Foobar autoescape");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"var FoobarField");

		String[] gradleTaskPaths = new String[] {
			_GRADLE_TASK_PATH_CHECK_SOURCE_FORMATTING, _GRADLE_TASK_PATH_BUILD
		};

		_executeGradle(gradleProjectDir, gradleTaskPaths);

		_testExists(gradleProjectDir, "build/libs/foobar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFragment() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"fragment", "loginhook", "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: loginhook",
			"Fragment-Host: com.liferay.login.web;bundle-version=\"1.0.0\"");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"fragment", "loginhook",
			"-DhostBundleSymbolicName=com.liferay.login.web",
			"-DhostBundleVersion=1.0.0", "-Dpackage=loginhook");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/loginhook-1.0.0.jar",
			"target/loginhook-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortlet() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("mvc-portlet", "foo");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "foo", "-DclassName=Foo", "-Dpackage=foo");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo-1.0.0.jar",
			"target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"mvc-portlet", "foo", "--package-name", "com.liferay.test");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "foo", "-DclassName=Foo",
			"-Dpackage=com.liferay.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/com.liferay.test-1.0.0.jar", "target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"mvc-portlet", "portlet");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "portlet", "-DclassName=Portlet",
			"-Dpackage=portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/portlet-1.0.0.jar",
			"target/portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"mvc-portlet", "portlet-portlet");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "portlet-portlet", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/portlet.portlet-1.0.0.jar",
			"target/portlet-portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File destinationDir = temporaryFolder.newFolder("gradle");

		_buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
		_buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
	}

	@Test
	public void testBuildTemplatePanelApp() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		_testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/application/list/FooPanelApp.java",
			"public class FooPanelApp extends BasePanelApp");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "-DclassName=Foo",
			"-Dpackage=gradle.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/gradle.test-1.0.0.jar", "target/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortlet() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;", "javax.portlet.display-name=foo.test",
			"public class FooPortlet extends GenericPortlet {",
			"printWriter.print(\"foo.test Portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "-DclassName=Foo", "-Dpackage=foo.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.test-1.0.0.jar",
			"target/foo.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icontest", "--package-name",
			"blade.test");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/configuration/icon" +
				"/IcontestPortletConfigurationIcon.java",
			"public class IcontestPortletConfigurationIcon",
			"extends BasePortletConfigurationIcon");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-configuration-icon", "icontest", "-DclassName=Icontest",
			"-Dpackage=blade.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/icontest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletProvider() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		_testContains(
			gradleProjectDir,
			"src/main/java/provider/test/constants" +
				"/ProviderTestPortletKeys.java",
			"package provider.test.constants;",
			"public class ProviderTestPortletKeys",
			"public static final String ProviderTest = \"ProviderTest\";");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "-DclassName=ProviderTest",
			"-Dpackage=provider.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/provider.test-1.0.0.jar",
			"target/provider.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "toolbartest", "--package-name",
			"blade.test");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/toolbar/contributor" +
				"/ToolbartestPortletToolbarContributor.java",
			"public class ToolbartestPortletToolbarContributor",
			"implements PortletToolbarContributor");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-toolbar-contributor", "toolbartest",
			"-DclassName=Toolbartest", "-Dpackage=blade.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/toolbartest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletWithPortletName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("portlet", "portlet");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"package portlet.portlet;", "javax.portlet.display-name=portlet",
			"public class PortletPortlet extends GenericPortlet {",
			"printWriter.print(\"portlet Portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "portlet", "-DclassName=Portlet", "-Dpackage=portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/portlet-1.0.0.jar",
			"target/portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateRest() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("rest", "my-rest");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		_testContains(
			gradleProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf",
			"contextPath=/my-rest");
		_testContains(
			gradleProjectDir,
			"src/main/resources/configuration/com.liferay.portal.remote.rest." +
				"extender.configuration.RestExtenderConfiguration-rest",
			"contextPaths=/my-rest",
			"jaxRsServiceFilterStrings=(component.name=" +
				"my.rest.application.MyRestApplication)");

		File mavenProjectDir = _buildTemplateWithMaven(
			"rest", "my-rest", "-DclassName=MyRest", "-Dpackage=my.rest");

		_testContains(
			mavenProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		_testContains(
			mavenProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf",
			"contextPath=/my-rest");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/my.rest-1.0.0.jar",
			"target/my-rest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateService() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_writeServiceClass(gradleProjectDir);

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction");

		_writeServiceClass(mavenProjectDir);

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/servicepreaction-1.0.0.jar",
			"target/servicepreaction-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateServiceBuilder() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName);

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, gradleProjectDir, name, packageName, "");
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = _buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample");

		_testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, workspaceProjectDir, "sample", "com.test.sample",
			":modules:nested:path:sample");
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName);

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, gradleProjectDir, name, packageName, "");
	}

	@Test
	public void testBuildTemplateServiceWrapper() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/serviceoverride/Serviceoverride.java",
			"package serviceoverride;",
			"import com.liferay.portal.kernel.service.UserLocalServiceWrapper;",
			"service = ServiceWrapper.class",
			"public class Serviceoverride extends UserLocalServiceWrapper {",
			"public Serviceoverride() {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-wrapper", "serviceoverride", "-DclassName=Serviceoverride",
			"-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/serviceoverride-1.0.0.jar",
			"target/serviceoverride-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator", "--package-name",
			"test.simulator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/test/simulator/application/list" +
				"/SimulatorSimulationPanelApp.java",
			"public class SimulatorSimulationPanelApp",
			"extends BaseJSPPanelApp");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulation-panel-entry", "simulator", "-DclassName=Simulator",
			"-Dpackage=test.simulator");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/test.simulator-1.0.0.jar",
			"target/simulator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateTemplateContextContributor() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor", "blade-test");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/context/contributor" +
				"/BladeTestTemplateContextContributor.java",
			"public class BladeTestTemplateContextContributor",
			"implements TemplateContextContributor");

		File mavenProjectDir = _buildTemplateWithMaven(
			"template-context-contributor", "blade-test",
			"-DclassName=BladeTest", "-Dpackage=blade.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/blade-test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateTheme() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("theme", "theme-test");

		_testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.gradle.plugins.theme.builder\"",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=theme-test");

		File mavenProjectDir = _buildTemplateWithMaven("theme", "theme-test");

		_testContains(
			mavenProjectDir, "pom.xml",
			"com.liferay.portal.tools.theme.builder");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/theme-test.war",
			"target/theme-test-1.0.0.war");
	}

	@Test
	public void testBuildTemplateThemeContributorCustom() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar");

		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar");
		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Web-ContextPath: /foo-bar-theme-contributor");
		_testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme-contributor", "my-contributor-custom",
			"-DcontributorType=foo-bar", "-Dpackage=my.contributor.custom");

		_testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/my.contributor.custom-1.0.0.jar",
			"target/my-contributor-custom-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateThemeContributorDefaults() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-default");

		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: my-contributor-default");
		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Web-ContextPath: /my-contributor-default-theme-contributor");
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"", "barfoo", "--package-name", "foo.bar");

		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "barfoo", "-DclassName=Barfoo", "-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/barfoo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateWorkspace() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "foows");

		_testExists(workspaceProjectDir, "configs/dev/portal-ext.properties");
		_testExists(workspaceProjectDir, "gradle.properties");
		_testExists(workspaceProjectDir, "modules");
		_testExists(workspaceProjectDir, "themes");
		_testExists(workspaceProjectDir, "wars");

		_testNotExists(workspaceProjectDir, "modules/pom.xml");
		_testNotExists(workspaceProjectDir, "themes/pom.xml");
		_testNotExists(workspaceProjectDir, "wars/pom.xml");

		_testContains(
			workspaceProjectDir, "settings.gradle", "version: \"1.2.0\"");

		File moduleProjectDir = _buildTemplateWithGradle(
			new File(workspaceProjectDir, "modules"), "", "foo-portlet");

		_testNotContains(
			moduleProjectDir, "build.gradle", "buildscript", "repositories");

		_executeGradle(
			workspaceProjectDir,
			":modules:foo-portlet" + _GRADLE_TASK_PATH_BUILD);

		_testExists(moduleProjectDir, "build/libs/foo.portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateWorkspaceExistingFile() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		File file = new File(destinationDir, "foo");

		Assert.assertTrue(file.createNewFile());

		_buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "foo");
	}

	@Test
	public void testBuildTemplateWorkspaceForce() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		File file = new File(destinationDir, "foo");

		Assert.assertTrue(file.createNewFile());

		_buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "forced", "--force");
	}

	@Test
	public void testBuildTemplateWorkspaceWithPortlet() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet");

		File gradleModulesDir = new File(gradleWorkspaceProjectDir, "modules");

		_buildTemplateWithGradle(
			gradleModulesDir, "mvc-portlet", "foo-portlet");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet");

		File mavenModulesDir = new File(mavenWorkspaceProjectDir, "modules");

		_buildTemplateWithMaven(
			mavenModulesDir, "mvc-portlet", "foo-portlet", "-DclassName=Foo",
			"-Dpackage=foo.portlet");

		_buildProjects(
			gradleWorkspaceProjectDir, mavenWorkspaceProjectDir,
			"modules/foo-portlet/build/libs/foo.portlet-1.0.0.jar",
			"modules/foo-portlet/target/foo-portlet-1.0.0.jar",
			":modules:foo-portlet" + _GRADLE_TASK_PATH_BUILD);
	}

	@Test
	public void testListTemplates() throws Exception {
		Set<String> templates = new HashSet<>(
			Arrays.asList(ProjectTemplates.getTemplates()));

		final Set<String> expectedTemplates = new HashSet<>();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				Path fileNamePath = path.getFileName();

				String fileName = fileNamePath.toString();

				String template = fileName.substring(
					FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX.length());

				if (!template.equals(WorkspaceUtil.WORKSPACE)) {
					expectedTemplates.add(template);
				}
			}
		}

		Assert.assertEquals(expectedTemplates, templates);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static void _buildProjects(
			File gradleProjectDir, File mavenProjectDir,
			String gradleBundleFileName, String mavenBundleFileName)
		throws Exception {

		final AtomicBoolean hasJavaFiles = new AtomicBoolean();

		Files.walkFileTree(
			gradleProjectDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					if (path.endsWith(".java")) {
						hasJavaFiles.set(true);

						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		String[] gradleTaskPaths;

		if (hasJavaFiles.get()) {
			gradleTaskPaths = new String[] {
				_GRADLE_TASK_PATH_CHECK_SOURCE_FORMATTING,
				_GRADLE_TASK_PATH_BUILD
			};
		}
		else {
			gradleTaskPaths = new String[] {_GRADLE_TASK_PATH_BUILD};
		}

		_buildProjects(
			gradleProjectDir, mavenProjectDir, gradleBundleFileName,
			mavenBundleFileName, gradleTaskPaths);
	}

	private static void _buildProjects(
			File gradleProjectDir, File mavenProjectDir,
			String gradleBundleFileName, String mavenBundleFileName,
			String... gradleTaskPath)
		throws Exception {

		_executeGradle(gradleProjectDir, gradleTaskPath);

		File gradleBundleFile = _testExists(
			gradleProjectDir, gradleBundleFileName);

		_executeMaven(mavenProjectDir, _MAVEN_GOAL_PACKAGE);

		File mavenBundleFile = _testExists(
			mavenProjectDir, mavenBundleFileName);

		if (gradleBundleFileName.endsWith(".jar")) {
			_testBundlesDiff(gradleBundleFile, mavenBundleFile);
		}
		else if (gradleBundleFileName.endsWith(".war")) {
			_testWarsDiff(gradleBundleFile, mavenBundleFile);
		}
	}

	private static File _buildTemplateWithGradle(
			File destinationDir, String template, String name, String... args)
		throws Exception {

		List<String> completeArgs = new ArrayList<>(args.length + 6);

		completeArgs.add("--destination");
		completeArgs.add(destinationDir.getPath());

		if (Validator.isNotNull(name)) {
			completeArgs.add("--name");
			completeArgs.add(name);
		}

		if (Validator.isNotNull(template)) {
			completeArgs.add("--template");
			completeArgs.add(template);
		}

		for (String arg : args) {
			completeArgs.add(arg);
		}

		ProjectTemplates.main(
			completeArgs.toArray(new String[completeArgs.size()]));

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, ".gitignore");
		_testExists(projectDir, "build.gradle");

		if (WorkspaceUtil.isWorkspace(destinationDir)) {
			for (String fileName : _STANDALONE_ONLY_FILE_NAMES) {
				_testNotExists(projectDir, fileName);
			}
		}
		else {
			for (String fileName : _STANDALONE_ONLY_FILE_NAMES) {
				_testExists(projectDir, fileName);
			}
		}

		_testNotExists(projectDir, "pom.xml");

		return projectDir;
	}

	private static File _buildTemplateWithMaven(
			File destinationDir, String template, String name, String... args)
		throws Exception {

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("--batch-mode");

		if (Validator.isNotNull(template)) {
			completeArgs.add(
				"-DarchetypeArtifactId=com.liferay.project.templates." +
					template.replace('-', '.'));
		}

		String projectTemplateVersion = _projectTemplateVersions.getProperty(
			template);

		Assert.assertTrue(
			"Unable to get project template version",
			Validator.isNotNull(projectTemplateVersion));

		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=" + projectTemplateVersion);
		completeArgs.add("-Dauthor=" + System.getProperty("user.name"));
		completeArgs.add("-DgroupId=com.test");
		completeArgs.add("-DartifactId=" + name);
		completeArgs.add("-Dversion=1.0.0");
		completeArgs.add("-DprojectType=standalone");

		for (String arg : args) {
			completeArgs.add(arg);
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, "pom.xml");
		_testNotExists(projectDir, "gradlew");
		_testNotExists(projectDir, "gradlew.bat");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");

		return projectDir;
	}

	private static void _executeGradle(File projectDir, String... taskPaths)
		throws IOException {

		String repositoryUrl = mavenExecutor.getRepositoryUrl();

		if (Validator.isNotNull(repositoryUrl)) {
			File buildGradleFile = new File(projectDir, "build.gradle");

			Path buildGradlePath = buildGradleFile.toPath();

			String buildGradle = FileUtil.read(buildGradlePath);

			buildGradle = buildGradle.replace(
				"\"" + _REPOSITORY_CDN_URL + "\"", "\"" + repositoryUrl + "\"");

			Files.write(
				buildGradlePath, buildGradle.getBytes(StandardCharsets.UTF_8));
		}

		GradleRunner gradleRunner = GradleRunner.create();

		String httpProxyHost = mavenExecutor.getHttpProxyHost();
		int httpProxyPort = mavenExecutor.getHttpProxyPort();

		if (Validator.isNotNull(httpProxyHost) && (httpProxyPort > 0)) {
			String[] arguments = new String[taskPaths.length + 2];

			arguments[0] = "-Dhttp.proxyHost=" + httpProxyHost;
			arguments[1] = "-Dhttp.proxyPort=" + httpProxyPort;

			System.arraycopy(taskPaths, 0, arguments, 2, taskPaths.length);

			gradleRunner.withArguments(arguments);
		}
		else {
			gradleRunner.withArguments(taskPaths);
		}

		gradleRunner.withGradleDistribution(_gradleDistribution);
		gradleRunner.withProjectDir(projectDir);

		BuildResult buildResult = gradleRunner.build();

		for (String taskPath : taskPaths) {
			BuildTask buildTask = buildResult.task(taskPath);

			Assert.assertNotNull(
				"Build task \"" + taskPath + "\" not found", buildTask);

			Assert.assertEquals(
				"Unexpected outcome for task \"" + buildTask.getPath() + "\"",
				TaskOutcome.SUCCESS, buildTask.getOutcome());
		}
	}

	private static void _executeMaven(File projectDir, String... args)
		throws Exception {

		String[] completeArgs = new String[args.length + 1];

		completeArgs[0] = "--update-snapshots";

		System.arraycopy(args, 0, completeArgs, 1, args.length);

		MavenExecutor.Result result = mavenExecutor.execute(projectDir, args);

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static void _testBundlesDiff(File bundleFile1, File bundleFile2)
		throws Exception {

		PrintStream originalErrorStream = System.err;
		PrintStream originalOutputStream = System.out;

		originalErrorStream.flush();
		originalOutputStream.flush();

		ByteArrayOutputStream newErrorStream = new ByteArrayOutputStream();
		ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();

		System.setErr(new PrintStream(newErrorStream, true));
		System.setOut(new PrintStream(newOutputStream, true));

		try (bnd bnd = new bnd()) {
			String[] args = {
				"diff", "--ignore", _BUNDLES_DIFF_IGNORES,
				bundleFile1.getAbsolutePath(), bundleFile2.getAbsolutePath()
			};

			bnd.start(args);
		}
		finally {
			System.setErr(originalErrorStream);
			System.setOut(originalOutputStream);
		}

		String output = newErrorStream.toString();

		if (Validator.isNull(output)) {
			output = newOutputStream.toString();
		}

		Assert.assertEquals(
			"Bundle " + bundleFile1 + " and " + bundleFile2 + " do not match",
			"", output);
	}

	private static File _testContains(
			File dir, String fileName, String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		for (String s : strings) {
			Assert.assertTrue(
				"Not found in " + fileName + ": " + s, content.contains(s));
		}

		return file;
	}

	private static File _testExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	private static File _testNotContains(
			File dir, String fileName, String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		for (String s : strings) {
			Assert.assertFalse(
				"Found in " + fileName + ": " + s, content.contains(s));
		}

		return file;
	}

	private static File _testNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	private static void _testWarsDiff(File warFile1, File warFile2)
		throws IOException {

		DifferenceCalculator differenceCalculator = new DifferenceCalculator(
			warFile1, warFile2);

		differenceCalculator.setFilenameRegexToIgnore(
			Collections.singleton(".*META-INF.*"));
		differenceCalculator.setIgnoreTimestamps(true);

		Differences differences = differenceCalculator.getDifferences();

		if (!differences.hasDifferences()) {
			return;
		}

		boolean realChange;

		Map<String, ZipArchiveEntry> added = differences.getAdded();
		Map<String, ZipArchiveEntry[]> changed = differences.getChanged();
		Map<String, ZipArchiveEntry> removed = differences.getRemoved();

		if (added.isEmpty() && !changed.isEmpty() && removed.isEmpty()) {
			realChange = false;

			for (String change : changed.keySet()) {
				ZipArchiveEntry[] zipArchiveEntries = changed.get(change);

				ZipArchiveEntry zipArchiveEntry1 = zipArchiveEntries[0];
				ZipArchiveEntry zipArchiveEntry2 = zipArchiveEntries[0];

				if (zipArchiveEntry1.isDirectory() &&
					zipArchiveEntry2.isDirectory() &&
					(zipArchiveEntry1.getSize() ==
						zipArchiveEntry2.getSize()) &&
					(zipArchiveEntry1.getCompressedSize() <= 2) &&
					(zipArchiveEntry2.getCompressedSize() <= 2)) {

					// Skip zipdiff bug

					continue;
				}

				realChange = true;

				break;
			}
		}
		else {
			realChange = true;
		}

		Assert.assertFalse(
			"WAR " + warFile1 + " and " + warFile2 + " do not match:\n" +
				differences,
			realChange);
	}

	private static void _writeServiceClass(File projectDir) throws IOException {
		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
		String classLine =
			"public class FooAction implements LifecycleAction {";

		File actionJavaFile = _testContains(
			projectDir, "src/main/java/servicepreaction/FooAction.java",
			"package servicepreaction;", importLine,
			"service = LifecycleAction.class", classLine);

		Path actionJavaPath = actionJavaFile.toPath();

		List<String> lines = Files.readAllLines(
			actionJavaPath, StandardCharsets.UTF_8);

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				actionJavaPath, StandardCharsets.UTF_8)) {

			for (String line : lines) {
				FileTestUtil.write(bufferedWriter, line);

				if (line.equals(classLine)) {
					FileTestUtil.write(
						bufferedWriter, "@Override",
						"public void processLifecycleEvent(",
						"LifecycleEvent lifecycleEvent)",
						"throws ActionException {", "System.out.println(",
						"\"login.event.pre=\" + lifecycleEvent);", "}");
				}
				else if (line.equals(importLine)) {
					FileTestUtil.write(
						bufferedWriter,
						"import com.liferay.portal.kernel.events." +
							"LifecycleEvent;",
						"import com.liferay.portal.kernel.events." +
							"ActionException;");
				}
			}
		}
	}

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return _buildTemplateWithGradle(destinationDir, template, name, args);
	}

	private File _buildTemplateWithMaven(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("maven");

		return _buildTemplateWithMaven(destinationDir, template, name, args);
	}

	private void _testBuildTemplateServiceBuilder(
			File gradleProjectDir, File rootProject, String name,
			String packageName, String projectPath)
		throws Exception {

		String apiProjectName = name + "-api";
		String serviceProjectName = name + "-service";

		boolean workspace = WorkspaceUtil.isWorkspace(gradleProjectDir);

		if (!workspace) {
			_testContains(
				gradleProjectDir, "settings.gradle",
				"include \"" + apiProjectName + "\", \"" + serviceProjectName +
					"\"");
		}

		_testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		_testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		if (!workspace) {
			_testContains(
				gradleProjectDir, serviceProjectName + "/build.gradle",
				"compileOnly project(\":" + apiProjectName + "\")");
		}

		_executeGradle(
			rootProject,
			projectPath + ":" + serviceProjectName +
				_GRADLE_TASK_PATH_BUILD_SERVICE);

		_executeGradle(
			rootProject,
			projectPath + ":" + serviceProjectName + _GRADLE_TASK_PATH_BUILD);

		File gradleApiBundleFile = _testExists(
			gradleProjectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");

		File gradleServiceBundleFile = _testExists(
			gradleProjectDir,
			serviceProjectName + "/build/libs/" + packageName +
				".service-1.0.0.jar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "-Dpackage=" + packageName);

		_executeMaven(
			new File(mavenProjectDir, serviceProjectName),
			_MAVEN_GOAL_BUILD_SERVICE);

		File gradleServicePropertiesFile = new File(
			gradleProjectDir,
			serviceProjectName + "/src/main/resources/service.properties");

		File mavenServicePropertiesFile = new File(
			mavenProjectDir,
			serviceProjectName + "/src/main/resources/service.properties");

		Files.copy(
			gradleServicePropertiesFile.toPath(),
			mavenServicePropertiesFile.toPath(),
			StandardCopyOption.REPLACE_EXISTING);

		_executeMaven(mavenProjectDir, _MAVEN_GOAL_PACKAGE);

		File mavenApiBundleFile = _testExists(
			mavenProjectDir,
			apiProjectName + "/target/" + name + "-api-1.0.0.jar");
		File mavenServiceBundleFile = _testExists(
			mavenProjectDir,
			serviceProjectName + "/target/" + name + "-service-1.0.0.jar");

		_testBundlesDiff(gradleApiBundleFile, mavenApiBundleFile);
		_testBundlesDiff(gradleServiceBundleFile, mavenServiceBundleFile);
	}

	private static final String _BUNDLES_DIFF_IGNORES = StringTestUtil.merge(
		"*pom.properties", "*pom.xml", "Archiver-Version", "Build-Jdk",
		"Built-By", "Javac-Debug", "Javac-Deprecation", "Javac-Encoding");

	private static final String _GRADLE_TASK_PATH_BUILD = ":build";

	private static final String _GRADLE_TASK_PATH_BUILD_SERVICE =
		":buildService";

	private static final String _GRADLE_TASK_PATH_CHECK_SOURCE_FORMATTING =
		":checkSourceFormatting";

	private static final String _MAVEN_GOAL_BUILD_SERVICE =
		"liferay:build-service";

	private static final String _MAVEN_GOAL_PACKAGE = "package";

	private static final String _REPOSITORY_CDN_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups" +
			"/public";

	private static final String[] _STANDALONE_ONLY_FILE_NAMES = {
		"gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
		"gradle/wrapper/gradle-wrapper.properties"
	};

	private static URI _gradleDistribution;
	private static Properties _projectTemplateVersions;

}