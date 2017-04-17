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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.checks.PropertiesDefinitionKeysCheck;
import com.liferay.source.formatter.checks.PropertiesDependenciesFileCheck;
import com.liferay.source.formatter.checks.PropertiesLiferayPluginPackageFileCheck;
import com.liferay.source.formatter.checks.PropertiesLongLinesCheck;
import com.liferay.source.formatter.checks.PropertiesPortalFileCheck;
import com.liferay.source.formatter.checks.PropertiesPortletFileCheck;
import com.liferay.source.formatter.checks.PropertiesSourceFormatterFileCheck;
import com.liferay.source.formatter.checks.PropertiesWhitespaceCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return content;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		if (portalSource) {
			return new String[] {
				"**/lib/*/dependencies.properties",
				"**/liferay-plugin-package.properties", "**/portal.properties",
				"**/portal-ext.properties", "**/portal-legacy-*.properties",
				"**/portlet.properties", "**/source-formatter.properties",
				"**/test.properties"
			};
		}

		return new String[] {
			"**/liferay-plugin-package.properties", "**/portal.properties",
			"**/portal-ext.properties", "**/portlet.properties",
			"**/source-formatter.properties"
		};
	}

	@Override
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected void populateSourceChecks() throws Exception {
		_sourceChecks.add(new PropertiesWhitespaceCheck(true));

		_sourceChecks.add(new PropertiesDefinitionKeysCheck());
		_sourceChecks.add(new PropertiesDependenciesFileCheck());
		_sourceChecks.add(
			new PropertiesLiferayPluginPackageFileCheck(
				getProjectPathPrefix()));
		_sourceChecks.add(
			new PropertiesLongLinesCheck(
				sourceFormatterArgs.getMaxLineLength()));
		_sourceChecks.add(
			new PropertiesPortalFileCheck(
				portalSource, subrepository,
				_getPortalPortalPropertiesContent()));
		_sourceChecks.add(
			new PropertiesPortletFileCheck(portalSource, subrepository));
		_sourceChecks.add(
			new PropertiesSourceFormatterFileCheck(
				portalSource, sourceFormatterArgs.getBaseDirName(),
				getProjectPathPrefix(), _hasPrivateAppsDir()));
	}

	private String _getPortalPortalPropertiesContent() throws Exception {
		String portalPortalPropertiesContent = null;

		if (portalSource) {
			File file = getFile(
				"portal-impl/src/portal.properties", PORTAL_MAX_DIR_LEVEL);

			return FileUtil.read(file);
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			portalPortalPropertiesContent = StringPool.BLANK;
		}

		return portalPortalPropertiesContent;
	}

	private boolean _hasPrivateAppsDir() {
		if (!portalSource) {
			return false;
		}

		File privateAppsDir = getFile(
			"modules/private/apps", PORTAL_MAX_DIR_LEVEL);

		if (privateAppsDir != null) {
			return true;
		}

		return false;
	}

	private final List<SourceCheck> _sourceChecks = new ArrayList<>();

}