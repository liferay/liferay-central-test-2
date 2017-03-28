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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaIllegalImportsCheck extends BaseFileCheck {

	public JavaIllegalImportsCheck(
		List<String> proxyExcludes, List<String> runOutsidePortalExcludes,
		List<String> secureRandomExcludes) {

		_proxyExcludes = proxyExcludes;
		_runOutsidePortalExcludes = runOutsidePortalExcludes;
		_secureRandomExcludes = secureRandomExcludes;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("JavaIllegalImportsCheck.java")) {
			return new Tuple(content, Collections.emptySet());
		}

		content = StringUtil.replace(
			content,
			new String[] {
				"com.liferay.portal.PortalException",
				"com.liferay.portal.SystemException",
				"com.liferay.util.LocalizationUtil"
			},
			new String[] {
				"com.liferay.portal.kernel.exception.PortalException",
				"com.liferay.portal.kernel.exception.SystemException",
				"com.liferay.portal.kernel.util.LocalizationUtil"
			});

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (!isExcludedPath(_runOutsidePortalExcludes, absolutePath) &&
			!isExcludedPath(_proxyExcludes, absolutePath) &&
			content.contains("import java.lang.reflect.Proxy;")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Use ProxyUtil instead of java.lang.reflect.Proxy");
		}

		if (content.contains("import edu.emory.mathcs.backport.java")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Illegal import: edu.emory.mathcs.backport.java");
		}

		if (content.contains("import jodd.util.StringPool")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Illegal import: jodd.util.StringPool");
		}

		// LPS-45027

		if (content.contains(
				"com.liferay.portal.kernel.util.UnmodifiableList")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Use java.util.Collections.unmodifiableList instead of " +
					"com.liferay.portal.kernel.util.UnmodifiableList, see " +
						"LPS-45027");
		}

		// LPS-39508

		if (!isExcludedPath(_runOutsidePortalExcludes, absolutePath) &&
			!isExcludedPath(_secureRandomExcludes, absolutePath) &&
			content.contains("java.security.SecureRandom") &&
			!content.contains("javax.crypto.KeyGenerator")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
					"SecureRandom instead of java.security.SecureRandom, see " +
						"LPS-39058");
		}

		// LPS-55690

		if (content.contains("org.testng.Assert")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Use org.junit.Assert instead of org.testng.Assert, see " +
					"LPS-55690");
		}

		// LPS-60473

		if (content.contains(".supportsBatchUpdates()") &&
			!fileName.endsWith("AutoBatchPreparedStatementUtil.java")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Use AutoBatchPreparedStatementUtil instead of " +
					"DatabaseMetaData.supportsBatchUpdates, see LPS-60473");
		}

		// LPS-64056

		if (content.contains("Configurable.createConfigurable(") &&
			!fileName.endsWith("ConfigurableUtil.java")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Use ConfigurableUtil.createConfigurable instead of " +
					"Configurable.createConfigurable, see LPS-64056");
		}

		// LPS-65229

		if (fileName.endsWith("ResourceCommand.java") &&
			content.contains("ServletResponseUtil.sendFile(")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Use PortletResponseUtil.sendFile instead of " +
					"ServletResponseUtil.sendFile, see LPS-65229");
		}

		// LPS-69494

		if (!fileName.endsWith("AbstractExtender.java") &&
			content.contains(
				"org.apache.felix.utils.extender.AbstractExtender")) {

			StringBundler sb = new StringBundler(4);

			sb.append("Use com.liferay.osgi.felix.util.AbstractExtender ");
			sb.append("instead of ");
			sb.append("org.apache.felix.utils.extender.AbstractExtender, see ");
			sb.append("LPS-69494");

			addMessage(sourceFormatterMessages, fileName, sb.toString());
		}

		// LPS-70963

		if (content.contains("java.util.WeakHashMap")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Do not use java.util.WeakHashMap because it is not " +
					"thread-safe, see LPS-70963");
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private final List<String> _proxyExcludes;
	private final List<String> _runOutsidePortalExcludes;
	private final List<String> _secureRandomExcludes;

}