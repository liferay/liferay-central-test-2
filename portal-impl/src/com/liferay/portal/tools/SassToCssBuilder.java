/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsConstants;
import com.liferay.portal.scripting.ruby.RubyExecutor;
import com.liferay.portal.servlet.filters.aggregate.AggregateFilter;
import com.liferay.portal.servlet.filters.aggregate.FileAggregateContext;
import com.liferay.portal.servlet.filters.dynamiccss.RTLCSSUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.tools.ant.DirectoryScanner;

import org.jruby.RubyArray;
import org.jruby.RubyException;
import org.jruby.embed.ScriptingContainer;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class SassToCssBuilder {

	public static File getCacheFile(String fileName) {
		return getCacheFile(fileName, StringPool.BLANK);
	}

	public static File getCacheFile(String fileName, String suffix) {
		return new File(getCacheFileName(fileName, suffix));
	}

	public static String getCacheFileName(String fileName, String suffix) {
		String cacheFileName = StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);

		int x = cacheFileName.lastIndexOf(StringPool.SLASH);
		int y = cacheFileName.lastIndexOf(StringPool.PERIOD);

		return cacheFileName.substring(0, x + 1) + ".sass-cache/" +
			cacheFileName.substring(x + 1, y) + suffix +
				cacheFileName.substring(y);
	}

	public static String getContent(String docrootDirName, String fileName)
		throws Exception {

		File file = new File(docrootDirName.concat(fileName));

		String content = FileUtil.read(file);

		content = AggregateFilter.aggregateCss(
			new FileAggregateContext(docrootDirName, fileName), content);

		return parseStaticTokens(content);
	}

	public static String getRtlCustomFileName(String fileName) {
		int pos = fileName.lastIndexOf(StringPool.PERIOD);

		return fileName.substring(0, pos) + "_rtl" + fileName.substring(pos);
	}

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		List<String> dirNames = new ArrayList<String>();

		String dirName = arguments.get("sass.dir");

		if (Validator.isNotNull(dirName)) {
			dirNames.add(dirName);
		}
		else {
			for (int i = 0;; i++ ) {
				dirName = arguments.get("sass.dir." + i);

				if (Validator.isNotNull(dirName)) {
					dirNames.add(dirName);
				}
				else {
					break;
				}
			}
		}

		String docrootDirName = arguments.get("sass.docroot.dir");
		String portalCommonDirName = arguments.get("sass.portal.common.dir");

		try {
			new SassToCssBuilder(dirNames, docrootDirName, portalCommonDirName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String parseStaticTokens(String content) {
		return StringUtil.replace(
			content,
			new String[] {
				"@model_hints_constants_text_display_height@",
				"@model_hints_constants_text_display_width@",
				"@model_hints_constants_textarea_display_height@",
				"@model_hints_constants_textarea_display_width@"
			},
			new String[] {
				ModelHintsConstants.TEXT_DISPLAY_HEIGHT,
				ModelHintsConstants.TEXT_DISPLAY_WIDTH,
				ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT,
				ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH
			});
	}

	public SassToCssBuilder(
			List<String> dirNames, String docrootDirName,
			String portalCommonDirName)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		_initUtil(classLoader);

		RubyExecutor rubyExecutor = new RubyExecutor();

		rubyExecutor.setExecuteInSeparateThread(false);

		ScriptingContainer scriptingContainer =
			rubyExecutor.getScriptingContainer();

		String rubyScript = StringUtil.read(
			classLoader,
			"com/liferay/portal/servlet/filters/dynamiccss" +
				"/dependencies/main.rb");

		Object scriptObject = scriptingContainer.runScriptlet(rubyScript);

		List<String> fileNames = new ArrayList<String>();

		for (String dirName : dirNames) {
			_collectSassFiles(fileNames, dirName, docrootDirName);
		}

		Runtime runtime = Runtime.getRuntime();

		ExecutorService executorService = Executors.newFixedThreadPool(
			runtime.availableProcessors());

		List<Future<String>> futures = new ArrayList<Future<String>>(
			fileNames.size());

		for (String fileName : fileNames) {
			Callable<String> callable = new CacheSassCallable(
				scriptingContainer, scriptObject, docrootDirName,
				portalCommonDirName, fileName);

			futures.add(executorService.submit(callable));
		}

		for (Future<String> future : futures) {
			System.out.println(future.get());
		}

		executorService.shutdownNow();
	}

	private void _collectSassFiles(
			List<String> fileNames, String dirName, String docrootDirName)
		throws Exception {

		DirectoryScanner directoryScanner = new DirectoryScanner();

		String basedir = docrootDirName.concat(dirName);

		directoryScanner.setBasedir(basedir);

		directoryScanner.setExcludes(
			new String[] {
				"**\\_diffs\\**", "**\\.sass-cache*\\**",
				"**\\.sass_cache_*\\**", "**\\_sass_cache_*\\**",
				"**\\_styled\\**", "**\\_unstyled\\**"
			});
		directoryScanner.setIncludes(new String[] {"**\\*.css"});

		directoryScanner.scan();

		String[] fileNamesArray = directoryScanner.getIncludedFiles();

		if (!_isModified(basedir, fileNamesArray)) {
			return;
		}

		for (String fileName : fileNamesArray) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileNames.add(_normalizeFileName(dirName, fileName));
		}
	}

	private String _getCssThemePath(String fileName) {
		int pos = fileName.lastIndexOf("/css/");

		return fileName.substring(0, pos + 4);
	}

	private void _initUtil(ClassLoader classLoader) {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		PortalClassLoaderUtil.setClassLoader(classLoader);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsUtil.setProps(new PropsImpl());

		RTLCSSUtil.init();
	}

	private boolean _isModified(String dirName, String[] fileNames)
		throws Exception {

		for (String fileName : fileNames) {
			fileName = _normalizeFileName(dirName, fileName);

			File file = new File(fileName);
			File cacheFile = getCacheFile(fileName);

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		return StringUtil.replace(
			dirName + StringPool.SLASH + fileName,
			new String[] {
				StringPool.BACK_SLASH, StringPool.DOUBLE_SLASH
			},
			new String[] {
				StringPool.SLASH, StringPool.SLASH
			}
		);
	}

	private String _parseSassFile(
			ScriptingContainer scriptingContainer, Object scriptObject,
			String docrootDirName, String portalCommonDirName, String fileName)
		throws Exception {

		String content = getContent(docrootDirName, fileName);
		String filePath = docrootDirName.concat(fileName);

		Object[] arguments = new Object[] {
			content, portalCommonDirName, filePath, _getCssThemePath(filePath),
			_tempDir, false
		};

		try {
			content = scriptingContainer.callMethod(
				scriptObject, "process", arguments, String.class);
		}
		catch (Exception e) {
			if (e instanceof RaiseException) {
				RaiseException raiseException = (RaiseException)e;

				RubyException rubyException = raiseException.getException();

				System.err.println(
					String.valueOf(rubyException.message.toJava(String.class)));

				IRubyObject iRubyObject = rubyException.getBacktrace();

				RubyArray rubyArray = (RubyArray)iRubyObject.toJava(
					RubyArray.class);

				for (int i = 0; i < rubyArray.size(); i++) {
					Object object = rubyArray.get(i);

					System.err.println(String.valueOf(object));
				}
			}
			else {
				e.printStackTrace();
			}
		}

		return content;
	}

	private String _tempDir = SystemProperties.get(SystemProperties.TMP_DIR);

	private class CacheSassCallable implements Callable<String> {

		public CacheSassCallable(
			ScriptingContainer scriptingContainer, Object scriptObject,
			String docrootDirName, String portalCommonDirName,
			String fileName) {

			_scriptingContainer = scriptingContainer;
			_scriptObject = scriptObject;
			_docrootDirName = docrootDirName;
			_portalCommonDirName = portalCommonDirName;
			_fileName = fileName;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();

			String fileName = _docrootDirName.concat(_fileName);

			File cacheFile = getCacheFile(fileName);

			String parsedContent = _parseSassFile(
				_scriptingContainer, _scriptObject, _docrootDirName,
				_portalCommonDirName, _fileName);

			FileUtil.write(cacheFile, parsedContent);

			File file = new File(fileName);

			long lastModified = file.lastModified();

			cacheFile.setLastModified(lastModified);

			// Generate RTL cache

			File rtlCacheFile = getCacheFile(fileName, "_rtl");

			String rtlCss = RTLCSSUtil.getRtlCss(parsedContent);

			// Append custom CSS for RTL

			String rtlCustomFileName = getRtlCustomFileName(_fileName);

			File rtlCustomFile = new File(_docrootDirName, rtlCustomFileName);

			if (rtlCustomFile.exists()) {
				lastModified = rtlCustomFile.lastModified();

				String rtlCustomCss = _parseSassFile(
					_scriptingContainer, _scriptObject, _docrootDirName,
					_portalCommonDirName, rtlCustomFileName);

				rtlCss += rtlCustomCss;
			}

			FileUtil.write(rtlCacheFile, rtlCss);

			rtlCacheFile.setLastModified(lastModified);

			long end = System.currentTimeMillis();

			return "Parsed " + _docrootDirName + _fileName + " in " +
				(end - start) + " ms";
		}

		private String _docrootDirName;
		private String _fileName;
		private String _portalCommonDirName;
		private ScriptingContainer _scriptingContainer;
		private Object _scriptObject;

	}

}