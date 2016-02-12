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

package com.liferay.css.builder;

import com.liferay.portal.kernel.regex.PatternFactory;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.rtl.css.RTLCSSConverter;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.SassCompilerException;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 * @author David Truong
 */
public class CSSBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		List<String> dirNames = new ArrayList<>();

		String dirName = GetterUtil.getString(
			arguments.get("sass.dir"), CSSBuilderArgs.DIR_NAME);

		if (Validator.isNotNull(dirName)) {
			dirNames.add(dirName);
		}
		else {
			for (int i = 0;; i++) {
				dirName = arguments.get("sass.dir." + i);

				if (Validator.isNotNull(dirName)) {
					dirNames.add(dirName);
				}
				else {
					break;
				}
			}
		}

		String docrootDirName = GetterUtil.getString(
			arguments.get("sass.docroot.dir"), CSSBuilderArgs.DOCROOT_DIR_NAME);
		boolean generateSourceMap = GetterUtil.getBoolean(
			arguments.get("sass.generate.source.map"));
		String portalCommonDirName = arguments.get("sass.portal.common.dir");
		int precision = GetterUtil.getInteger(
			arguments.get("sass.precision"), CSSBuilderArgs.PRECISION);
		String[] rtlExcludedPathRegexps = StringUtil.split(
			arguments.get("sass.rtl.excluded.path.regexps"));
		String sassCompilerClassName = arguments.get(
			"sass.compiler.class.name");

		try {
			CSSBuilder cssBuilder = new CSSBuilder(
				docrootDirName, generateSourceMap, portalCommonDirName,
				precision, rtlExcludedPathRegexps, sassCompilerClassName);

			cssBuilder.execute(dirNames);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public CSSBuilder(
			String docrootDirName, boolean generateSourceMap,
			String portalCommonDirName, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception {

		_docrootDirName = docrootDirName;
		_generateSourceMap = generateSourceMap;
		_portalCommonDirName = portalCommonDirName;
		_precision = precision;
		_rtlExcludedPathPatterns = PatternFactory.compile(
			rtlExcludedPathRegexps);

		_initSassCompiler(sassCompilerClassName);
	}

	public void execute(List<String> dirNames) throws Exception {
		List<String> fileNames = new ArrayList<>();

		for (String dirName : dirNames) {
			_collectSassFiles(fileNames, dirName, _docrootDirName);
		}

		for (String fileName : fileNames) {
			long start = System.currentTimeMillis();

			_parseSassFile(fileName);

			long elapsedTime = System.currentTimeMillis() - start;

			System.out.println(
				"Parsed " + fileName + " in " + elapsedTime + "ms");
		}
	}

	public boolean isRtlExcludedPath(String filePath) {
		for (Pattern pattern : _rtlExcludedPathPatterns) {
			Matcher matcher = pattern.matcher(filePath);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private void _collectSassFiles(
			List<String> fileNames, String dirName, String docrootDirName)
		throws Exception {

		DirectoryScanner directoryScanner = new DirectoryScanner();

		String basedir = docrootDirName.concat(dirName);

		directoryScanner.setBasedir(basedir);

		directoryScanner.setExcludes(
			new String[] {
				"**\\_*.scss", "**\\_diffs\\**", "**\\.sass-cache*\\**",
				"**\\.sass_cache_*\\**", "**\\_sass_cache_*\\**",
				"**\\_styled\\**", "**\\_unstyled\\**", "**\\css\\aui\\**",
				"**\\tmp\\**"
			});
		directoryScanner.setIncludes(new String[] {"**\\*.scss"});

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

	private String _fixRelativePath(String fileName) {
		String[] paths = StringUtil.split(fileName, CharPool.SLASH);

		StringBundler sb = new StringBundler(paths.length * 2);

		for (String path : paths) {
			if (path.isEmpty() || path.equals(StringPool.PERIOD)) {
				continue;
			}

			if (path.equals(StringPool.DOUBLE_PERIOD) && (sb.length() >= 2)) {
				sb.setIndex(sb.index() - 2);

				continue;
			}

			sb.append(StringPool.SLASH);
			sb.append(path);
		}

		return sb.toString();
	}

	private String _getRtlCss(String fileName, String css) throws Exception {
		String rtlCss = css;

		try {
			if (_rtlCSSConverter == null) {
				_rtlCSSConverter = new RTLCSSConverter();
			}

			rtlCss = _rtlCSSConverter.process(rtlCss);
		}
		catch (Exception e) {
			System.out.println(
				"Unable to generate RTL version for " + fileName +
					StringPool.COMMA_AND_SPACE + e.getMessage());
		}

		return rtlCss;
	}

	private void _initSassCompiler(String sassCompilerClassName)
		throws Exception {

		if (Validator.isNull(sassCompilerClassName) ||
			sassCompilerClassName.equals("jni")) {

			try {
				System.setProperty("jna.nosys", Boolean.TRUE.toString());

				_sassCompiler = new JniSassCompiler(_precision);

				System.out.println("Using native Sass compiler");
			}
			catch (Throwable t) {
				System.out.println(
					"Unable to load native compiler, falling back to Ruby");

				_sassCompiler = new RubySassCompiler(_precision);
			}
		}
		else {
			try {
				_sassCompiler = new RubySassCompiler(_precision);

				System.out.println("Using Ruby Sass compiler");
			}
			catch (Exception e) {
				System.out.println(
					"Unable to load Ruby compiler, falling back to native");

				System.setProperty("jna.nosys", Boolean.TRUE.toString());

				_sassCompiler = new JniSassCompiler(_precision);
			}
		}
	}

	private boolean _isModified(String dirName, String[] fileNames)
		throws Exception {

		for (String fileName : fileNames) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileName = _normalizeFileName(dirName, fileName);

			File file = new File(fileName);
			File cacheFile = CSSBuilderUtil.getCacheFile(fileName);

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		return _fixRelativePath(
			StringUtil.replace(
				dirName + StringPool.SLASH + fileName,
				new String[] {StringPool.BACK_SLASH, StringPool.DOUBLE_SLASH},
				new String[] {StringPool.SLASH, StringPool.SLASH}
			));
	}

	private String _parseSass(String fileName) throws SassCompilerException {
		String filePath = _docrootDirName.concat(fileName);

		String cssBasePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssBasePath = filePath.substring(0, pos + 4);
		}
		else {
			pos = filePath.lastIndexOf("/resources/");

			if (pos >= 0) {
				cssBasePath = filePath.substring(0, pos + 10);
			}
		}

		return CSSBuilderUtil.parseStaticTokens(
			_sassCompiler.compileFile(
				filePath,
				_portalCommonDirName + File.pathSeparator + cssBasePath,
				_generateSourceMap, filePath + ".map"
			));
	}

	private void _parseSassFile(String fileName) throws Exception {
		File file = new File(_docrootDirName, fileName);

		if (!file.exists()) {
			return;
		}

		String ltrContent = _parseSass(fileName);

		_writeCacheFile(fileName, ltrContent, false);

		if (isRtlExcludedPath(fileName)) {
			return;
		}

		StringBundler rtlContent = new StringBundler();

		rtlContent.append(_getRtlCss(fileName, ltrContent));

		String rtlCustomFileName = CSSBuilderUtil.getRtlCustomFileName(
			fileName);

		File rtlCustomFile = new File(_docrootDirName, rtlCustomFileName);

		if (rtlCustomFile.exists()) {
			rtlContent.append(_parseSass(rtlCustomFileName));
		}

		_writeCacheFile(fileName, rtlContent.toString(), true);
	}

	private void _write(File file, String content) throws Exception {
		File parentFile = file.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		Path path = Paths.get(file.toURI());

		Files.write(path, content.getBytes(StringPool.UTF8));
	}

	private void _writeCacheFile(String fileName, String content, boolean rtl)
		throws Exception {

		String cacheFileName;

		if (rtl) {
			String rtlFileName = CSSBuilderUtil.getRtlCustomFileName(fileName);

			cacheFileName = CSSBuilderUtil.getCacheFileName(
				rtlFileName, StringPool.BLANK);
		}
		else {
			cacheFileName = CSSBuilderUtil.getCacheFileName(
				fileName, StringPool.BLANK);
		}

		File cacheFile = new File(_docrootDirName, cacheFileName);

		_write(cacheFile, content);

		File file = new File(_docrootDirName, fileName);

		cacheFile.setLastModified(file.lastModified());
	}

	private static RTLCSSConverter _rtlCSSConverter;

	private final String _docrootDirName;
	private final boolean _generateSourceMap;
	private final String _portalCommonDirName;
	private final int _precision;
	private final Pattern[] _rtlExcludedPathPatterns;
	private SassCompiler _sassCompiler;

}