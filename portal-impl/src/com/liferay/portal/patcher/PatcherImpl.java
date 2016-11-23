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

package com.liferay.portal.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.PatchInconsistencyException;
import com.liferay.portal.kernel.patcher.Patcher;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@DoPrivileged
public class PatcherImpl implements Patcher {

	public PatcherImpl() {
		_properties = _getProperties(PATCHER_PROPERTIES);

		_fixedIssueKeys = StringUtil.split(
			_properties.getProperty(PROPERTY_FIXED_ISSUES));

		_installedPatchNames = StringUtil.split(
			_properties.getProperty(PROPERTY_INSTALLED_PATCHES));

		_patchLevels = StringUtil.split(
			_properties.getProperty(PROPERTY_PATCH_LEVELS));

		_patchingToolVersion = GetterUtil.getInteger(
			_properties.get(PROPERTY_PATCHING_TOOL_VERSION));

		_patchingToolVersionDisplayName = getPatchingToolVersionDisplayName();

		_separated = GetterUtil.getBoolean(
			_properties.getProperty(PROPERTY_SEPARATED));

		_separationId = _properties.getProperty(PROPERTY_SEPARATION_ID);
	}

	@Override
	public boolean applyPatch(File patchFile) {
		File patchDirectory = getPatchDirectory();

		if (patchDirectory == null) {
			return false;
		}

		try {
			FileUtil.copyFile(
				patchFile,
				new File(
					patchDirectory + StringPool.SLASH + patchFile.getName()));

			return true;
		}
		catch (Exception e) {
			_log.error(
				"Unable to copy " + patchFile.getAbsolutePath() + " to " +
					patchDirectory.getAbsolutePath());

			return false;
		}
	}

	@Override
	public String[] getFixedIssues() {
		return _fixedIssueKeys;
	}

	@Override
	public String[] getInstalledPatches() {
		return _installedPatchNames;
	}

	@Override
	public File getPatchDirectory() {
		String patchDirectoryName = _properties.getProperty(
			PROPERTY_PATCH_DIRECTORY);

		File patchDirectory = null;

		if (Validator.isNotNull(patchDirectoryName)) {
			patchDirectory = new File(patchDirectoryName);

			if (!patchDirectory.exists()) {
				_log.error("The patch directory does not exist");

				_configured = false;
			}
			else {
				_configured = true;
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("The patch directory is not specified");

				_configured = false;
			}
		}

		return patchDirectory;
	}

	@Override
	public int getPatchingToolVersion() {
		return _patchingToolVersion;
	}

	@Override
	public String getPatchingToolVersionDisplayName() {
		if (_patchingToolVersionDisplayName != null) {
			return _patchingToolVersionDisplayName;
		}

		String patchingToolVersionDisplayName =
			"1.0." + getPatchingToolVersion();

		if (_properties.containsKey(
				PROPERTY_PATCHING_TOOL_VERSION_DISPLAY_NAME)) {

			patchingToolVersionDisplayName = _properties.getProperty(
				PROPERTY_PATCHING_TOOL_VERSION_DISPLAY_NAME);
		}

		return patchingToolVersionDisplayName;
	}

	@Override
	public String[] getPatchLevels() {
		return _patchLevels;
	}

	@Override
	public Properties getProperties() {
		return _properties;
	}

	@Override
	public String getSeparationId() {
		return _separationId;
	}

	@Override
	public boolean hasInconsistentPatchLevels() {
		return _inconsistentPatchLevels;
	}

	@Override
	public boolean isConfigured() {
		getPatchDirectory();

		return _configured;
	}

	@Override
	public boolean isSeparated() {
		return _separated;
	}

	@Override
	public void verifyPatchLevels() throws PatchInconsistencyException {
		Properties portalKernelJARProperties = _getProperties(
			PATCHER_SERVICE_PROPERTIES);

		String[] kernelJARPatches = _getInstalledPatches(
			portalKernelJARProperties);

		Arrays.sort(kernelJARPatches);

		Properties portalImplJARProperties = _getProperties(PATCHER_PROPERTIES);

		String[] portalImplJARPatches = _getInstalledPatches(
			portalImplJARProperties);

		Arrays.sort(portalImplJARPatches);

		if (!Arrays.equals(portalImplJARPatches, kernelJARPatches)) {
			_log.error("Inconsistent patch level detected");

			if (_log.isWarnEnabled()) {
				if (ArrayUtil.isEmpty(portalImplJARPatches)) {
					_log.warn(
						"There are no patches installed on portal-impl.jar");
				}
				else {
					_log.warn(
						"Patch level on portal-impl.jar: " +
							Arrays.toString(portalImplJARPatches));
				}

				if (ArrayUtil.isEmpty(kernelJARPatches)) {
					_log.warn(
						"There are no patches installed on portal-kernel.jar");
				}
				else {
					_log.warn(
						"Patch level on portal-kernel.jar: " +
							Arrays.toString(kernelJARPatches));
				}
			}

			_inconsistentPatchLevels = true;

			throw new PatchInconsistencyException();
		}
	}

	private String[] _getInstalledPatches(Properties properties) {
		if (properties == null) {
			properties = getProperties();
		}

		String[] installedPatchNames = StringUtil.split(
			properties.getProperty(PROPERTY_INSTALLED_PATCHES));

		return installedPatchNames;
	}

	private Properties _getProperties(String fileName) {
		if (Validator.isNull(fileName)) {
			fileName = PATCHER_PROPERTIES;
		}

		Properties properties = new Properties();

		Class<?> clazz = getClass();

		if (Objects.equals(fileName, PATCHER_SERVICE_PROPERTIES)) {
			clazz = clazz.getInterfaces()[0];
		}

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		if (inputStream == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to load " + fileName);
			}
		}
		else {
			try {
				properties.load(inputStream);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
			finally {
				StreamUtil.cleanUp(inputStream);
			}
		}

		return properties;
	}

	private static final Log _log = LogFactoryUtil.getLog(PatcherImpl.class);

	private boolean _configured;
	private final String[] _fixedIssueKeys;
	private boolean _inconsistentPatchLevels;
	private final String[] _installedPatchNames;
	private final int _patchingToolVersion;
	private final String _patchingToolVersionDisplayName;
	private final String[] _patchLevels;
	private final Properties _properties;
	private final boolean _separated;
	private final String _separationId;

}