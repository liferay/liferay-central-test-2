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

package com.liferay.gradle.plugins.soy;

import com.liferay.gradle.plugins.soy.tasks.ReplaceSoyTranslationTask;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;

/**
 * @author Andrea Di Giorgi
 */
public class SoyTranslationPlugin implements Plugin<Project> {

	public static final String REPLACE_SOY_TRANSLATION_TASK_NAME =
		"replaceSoyTranslation";

	@Override
	public void apply(Project project) {
		_addTaskReplaceSoyTranslation(project);
	}

	@SuppressWarnings("rawtypes")
	private ReplaceSoyTranslationTask _addTaskReplaceSoyTranslation(
		Project project) {

		final ReplaceSoyTranslationTask replaceSoyTranslationTask =
			GradleUtil.addTask(
				project, REPLACE_SOY_TRANSLATION_TASK_NAME,
				ReplaceSoyTranslationTask.class);

		replaceSoyTranslationTask.setDescription(
			"Replaces 'goog.getMsg' definitions.");
		replaceSoyTranslationTask.setGroup(BasePlugin.BUILD_GROUP);
		replaceSoyTranslationTask.setIncludes(
			Collections.singleton("**/*.soy.js"));
		replaceSoyTranslationTask.setReplacementClosure(
			new LiferayReplacementClosure(replaceSoyTranslationTask));

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withId(
			_JS_MODULE_CONFIG_GENERATOR_PLUGIN_ID,
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					replaceSoyTranslationTask.dependsOn(
						_CONFIG_JS_MODULES_TASK_NAME);
				}

			});

		pluginContainer.withId(
			_JS_TRANSPILER_PLUGIN_ID,
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					replaceSoyTranslationTask.dependsOn(
						_TRANSPILE_JS_TASK_NAME);
				}

			});

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskReplaceSoyTranslationForJavaPlugin(
						replaceSoyTranslationTask);
				}

			});

		return replaceSoyTranslationTask;
	}

	private void _configureTaskReplaceSoyTranslationForJavaPlugin(
		final ReplaceSoyTranslationTask replaceSoyTranslationTask) {

		replaceSoyTranslationTask.dependsOn(
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		replaceSoyTranslationTask.setSource(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						replaceSoyTranslationTask.getProject(),
						SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getResourcesDir();
				}

			});

		Task classesTask = GradleUtil.getTask(
			replaceSoyTranslationTask.getProject(),
			JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(replaceSoyTranslationTask);
	}

	private static final String _CONFIG_JS_MODULES_TASK_NAME =
		"configJSModules";

	private static final String _JS_MODULE_CONFIG_GENERATOR_PLUGIN_ID =
		"com.liferay.js.module.config.generator";

	private static final String _JS_TRANSPILER_PLUGIN_ID =
		"com.liferay.js.transpiler";

	private static final String _TRANSPILE_JS_TASK_NAME = "transpileJS";

	private static class LiferayReplacementClosure extends Closure<String> {

		public LiferayReplacementClosure(Object owner) {
			super(owner);
		}

		@SuppressWarnings("unused")
		public String doCall(
			String variableName, String languageKey, String argumentsObject) {

			StringBuilder sb = new StringBuilder();

			sb.append("var ");
			sb.append(variableName);

			// Split string to avoid SF error

			sb.append(" = Liferay.Language");
			sb.append(".get('");

			sb.append(_fixLanguageKey(languageKey));
			sb.append("');");

			if (_hasArguments(argumentsObject)) {
				_appendArgumentReplaces(sb, argumentsObject, variableName);
			}
			else {
				_appendArgumentMarkerReplace(sb, variableName);
			}

			return sb.toString();
		}

		private void _appendArgumentMarkerReplace(
			StringBuilder sb, String variableName) {

			sb.append(System.lineSeparator());
			sb.append(variableName);
			sb.append(" = ");
			sb.append(variableName);
			sb.append(".replace(/{(\\d+)}/g, '\\x01$1\\x01')");
		}

		private void _appendArgumentReplaces(
			StringBuilder sb, String argumentsObject, String variableName) {

			int i = 0;

			Matcher matcher = _argumentsObjectPattern.matcher(argumentsObject);

			while (matcher.find()) {
				sb.append(System.lineSeparator());

				sb.append(variableName);
				sb.append(" = ");
				sb.append(variableName);
				sb.append(".replace('{");
				sb.append(i);
				sb.append("}', ");
				sb.append(matcher.group(1));
				sb.append(");");

				i++;
			}
		}

		private String _fixLanguageKey(String languageKey) {
			Matcher matcher = _languageKeyPlaceholderPattern.matcher(
				languageKey);

			return matcher.replaceAll("x");
		}

		private boolean _hasArguments(String argumentsObject) {
			if (Validator.isNotNull(argumentsObject)) {
				Matcher matcher = _argumentsObjectPattern.matcher(
					argumentsObject);

				return matcher.find();
			}

			return false;
		}

		private static final Pattern _argumentsObjectPattern = Pattern.compile(
			"'.+'\\s*:\\s*([\\d\\w\\._]+)+");
		private static final Pattern _languageKeyPlaceholderPattern =
			Pattern.compile("\\{\\$\\w+\\}");

	}

}