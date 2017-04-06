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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaStaticBlock;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.comparator.JavaTermComparator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaTermOrderCheck extends BaseJavaTermCheck {

	public JavaTermOrderCheck(
		List<String> excludes, boolean portalSource, boolean subrepository,
		String portalCustomSQLContent) {

		_excludes = excludes;
		_portalSource = portalSource;
		_subrepository = subrepository;
		_portalCustomSQLContent = portalCustomSQLContent;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		String javaTermContent = javaTerm.getContent();

		if (javaTermContent.contains("@Meta.OCD")) {
			return javaTermContent;
		}

		String className = javaTerm.getName();

		String customSQLContent = null;

		if (absolutePath.contains("/persistence/") &&
			className.endsWith("FinderImpl")) {

			customSQLContent = _getCustomSQLContent(fileName, absolutePath);
		}

		return _sortJavaTerms(
			absolutePath, (JavaClass)javaTerm, customSQLContent);
	}

	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _getCustomSQLContent(String fileName, String absolutePath)
		throws Exception {

		if (_portalSource && !isModulesFile(absolutePath, _subrepository)) {
			return _portalCustomSQLContent;
		}

		int i = fileName.lastIndexOf("/src/");

		if (i == -1) {
			return null;
		}

		File customSQLFile = new File(
			fileName.substring(0, i) + "/src/custom-sql/default.xml");

		if (!customSQLFile.exists()) {
			customSQLFile = new File(
				fileName.substring(0, i) +
					"/src/main/resources/META-INF/custom-sql/default.xml");
		}

		if (!customSQLFile.exists()) {
			customSQLFile = new File(
				fileName.substring(0, i) +
					"/src/main/resources/custom-sql/default.xml");
		}

		if (!customSQLFile.exists()) {
			return null;
		}

		return FileUtil.read(customSQLFile);
	}

	private String _sortJavaTerms(
		String absolutePath, JavaClass javaClass, String customSQLContent) {

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		if (childJavaTerms.size() < 2) {
			return javaClass.getContent();
		}

		JavaTermComparator javaTermComparator = new JavaTermComparator(
			customSQLContent);

		JavaTerm previousJavaTerm = null;

		for (JavaTerm javaTerm : childJavaTerms) {
			if (javaTerm instanceof JavaStaticBlock) {
				continue;
			}

			if (previousJavaTerm == null) {
				previousJavaTerm = javaTerm;

				continue;
			}

			if (!isExcludedPath(
					_excludes, absolutePath, previousJavaTerm.getName()) &&
				!isExcludedPath(_excludes, absolutePath, javaTerm.getName()) &&
				(javaTermComparator.compare(previousJavaTerm, javaTerm) > 0)) {

				String classContent = javaClass.getContent();

				String newClassContent = StringUtil.replaceFirst(
					classContent, "\n" + previousJavaTerm.getContent(),
					"\n" + javaTerm.getContent());

				newClassContent = StringUtil.replaceLast(
					newClassContent, "\n" + javaTerm.getContent(),
					"\n" + previousJavaTerm.getContent());

				return newClassContent;
			}

			previousJavaTerm = javaTerm;
		}

		return javaClass.getContent();
	}

	private final List<String> _excludes;
	private final String _portalCustomSQLContent;
	private final boolean _portalSource;
	private final boolean _subrepository;

}