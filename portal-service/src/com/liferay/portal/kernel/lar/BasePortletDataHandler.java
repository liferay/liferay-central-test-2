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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePortletDataHandler implements PortletDataHandler {

	@Override
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Deleting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		try {
			return doDeleteData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Deleted portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		try {
			portletDataContext.addDeletionSystemEventClassNames(
				getDeletionSystemEventClassNames());

			for (PortletDataHandlerControl portletDataHandlerControl :
					getExportControls()) {

				addUncheckedModelAdditionCount(
					portletDataContext, portletDataHandlerControl);
			}

			return doExportData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Exported portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public DataLevel getDataLevel() {
		return _dataLevel;
	}

	@Override
	public String[] getDataPortletPreferences() {
		return _dataPortletPreferences;
	}

	@Override
	public String[] getDeletionSystemEventClassNames() {
		return _deletionSystemEventClassNames;
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet,
			boolean privateLayout)
		throws Exception {

		return getExportConfigurationControls(
			companyId, groupId, portlet, -1, privateLayout);
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet, long plid,
			boolean privateLayout)
		throws Exception {

		if (Validator.isNull(portlet.getConfigurationActionClass())) {
			return null;
		}

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<PortletDataHandlerBoolean>();

		// Setup

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, groupId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portlet,
				privateLayout) > 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
				portlet.getRootPortletId()) > 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				portlet.getRootPortletId()) > 0)) {

				configurationControls.add(
					new PortletDataHandlerBoolean(
						null, PortletDataHandlerKeys.PORTLET_SETUP, "setup",
						true, false, null, null, null));
		}

		// Archived setups

		if (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
				portlet.getRootPortletId()) > 0) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"archived-setups", true, false, null, null, null)
			);
		}

		// User preferences

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, groupId, -1, PortletKeys.PREFS_OWNER_TYPE_USER, plid,
				portlet, privateLayout) > 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, groupId, groupId, PortletKeys.PREFS_OWNER_TYPE_USER,
				PortletKeys.PREFS_PLID_SHARED, portlet, privateLayout) > 0)) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return _exportControls;
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return _exportMetadataControls;
	}

	@Override
	public long getExportModelCount(ManifestSummary manifestSummary) {
		return getExportModelCount(manifestSummary, getExportControls());
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary) {

		if (Validator.isNull(portlet.getConfigurationActionClass())) {
			return null;
		}

		String[] configurationOptions =
			manifestSummary.getConfigurationPortletOptions(
				portlet.getRootPortletId());

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<PortletDataHandlerBoolean>();

		// Setup

		if (ArrayUtil.contains(configurationOptions, "setup")) {
			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, null, null, null));
		}

		// Archived setups

		if (ArrayUtil.contains(configurationOptions, "archived-setups")) {
			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"archived-setups", true, false, null, null, null));
		}

		// User preferences

		if (ArrayUtil.contains(configurationOptions, "user-preferences")) {
			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return _importControls;
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return _importMetadataControls;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Importing portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		long sourceGroupId = portletDataContext.getSourceGroupId();

		try {
			if (Validator.isXml(data)) {
				Document document = SAXReaderUtil.read(data);

				Element rootElement = document.getRootElement();

				portletDataContext.setImportDataRootElement(rootElement);

				long portletSourceGroupId = GetterUtil.getLong(
					rootElement.attributeValue("group-id"));

				if (portletSourceGroupId != 0) {
					portletDataContext.setSourceGroupId(portletSourceGroupId);
				}
			}

			return doImportData(
				portletDataContext, portletId, portletPreferences, data);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			portletDataContext.setSourceGroupId(sourceGroupId);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Imported portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public boolean isDataLocalized() {
		return _dataLocalized;
	}

	@Override
	public boolean isDataPortalLevel() {
		return _dataLevel.equals(DataLevel.PORTAL);
	}

	@Override
	public boolean isDataPortletInstanceLevel() {
		return _dataLevel.equals(DataLevel.PORTLET_INSTANCE);
	}

	@Override
	public boolean isDataSiteLevel() {
		return _dataLevel.equals(DataLevel.SITE);
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _publishToLiveByDefault;
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException {

		try {
			doPrepareManifestSummary(portletDataContext);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, Element rootElement)
		throws PortletDataException {

		try {
			return doProcessExportPortletPreferences(
				portletDataContext, portletId, portletPreferences, rootElement);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return doProcessImportPortletPreferences(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected Element addExportDataRootElement(
		PortletDataContext portletDataContext) {

		Document document = SAXReaderUtil.createDocument();

		Class<?> clazz = getClass();

		Element rootElement = document.addElement(clazz.getSimpleName());

		portletDataContext.setExportDataRootElement(rootElement);

		return rootElement;
	}

	protected void addUncheckedModelAdditionCount(
		PortletDataContext portletDataContext,
		PortletDataHandlerControl portletDataHandlerControl) {

		if (!(portletDataHandlerControl instanceof PortletDataHandlerBoolean)) {
			return;
		}

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			(PortletDataHandlerBoolean)portletDataHandlerControl;

		PortletDataHandlerControl[] childPortletDataHandlerControls =
			portletDataHandlerBoolean.getChildren();

		if (childPortletDataHandlerControls != null) {
			for (PortletDataHandlerControl childPortletDataHandlerControl :
					childPortletDataHandlerControls) {

				addUncheckedModelAdditionCount(
					portletDataContext, childPortletDataHandlerControl);
			}
		}

		if (Validator.isNull(portletDataHandlerControl.getClassName())) {
			return;
		}

		boolean checkedControl = GetterUtil.getBoolean(
			portletDataContext.getBooleanParameter(
				portletDataHandlerControl.getNamespace(),
				portletDataHandlerControl.getControlName(), false));

		if (!checkedControl) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			String manifestSummaryKey = ManifestSummary.getManifestSummaryKey(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerBoolean.getReferrerClassName());

			manifestSummary.addModelAdditionCount(manifestSummaryKey, 0);
		}
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return portletPreferences;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		return null;
	}

	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, Element rootElement)
		throws Exception {

		return portletPreferences;
	}

	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return portletPreferences;
	}

	protected String getExportDataRootElementString(Element rootElement) {
		if (rootElement == null) {
			return StringPool.BLANK;
		}

		try {
			Document document = rootElement.getDocument();

			return document.formattedString();
		}
		catch (IOException ioe) {
			return StringPool.BLANK;
		}
	}

	protected long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] exportControls) {

		long totalModelCount = -1;

		for (PortletDataHandlerControl exportControl : exportControls) {
			long modelCount = manifestSummary.getModelAdditionCount(
				exportControl.getClassName(),
				exportControl.getReferrerClassName());

			if (exportControl instanceof PortletDataHandlerBoolean) {
				PortletDataHandlerBoolean portletDataHandlerBoolean =
					(PortletDataHandlerBoolean)exportControl;

				PortletDataHandlerControl[] childPortletDataHandlerControls =
					portletDataHandlerBoolean.getChildren();

				if (childPortletDataHandlerControls != null) {
					long childModelCount = getExportModelCount(
						manifestSummary, childPortletDataHandlerControls);

					if (childModelCount != -1) {
						if (modelCount == -1) {
							modelCount = childModelCount;
						}
						else {
							modelCount += childModelCount;
						}
					}
				}
			}

			if (modelCount == -1) {
				continue;
			}

			if (totalModelCount == -1) {
				totalModelCount = modelCount;
			}
			else {
				totalModelCount += modelCount;
			}
		}

		return totalModelCount;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	protected void setAlwaysExportable(boolean alwaysExportable) {
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	protected void setAlwaysStaged(boolean alwaysStaged) {
	}

	protected void setDataLevel(DataLevel dataLevel) {
		_dataLevel = dataLevel;
	}

	protected void setDataLocalized(boolean dataLocalized) {
		_dataLocalized = dataLocalized;
	}

	protected void setDataPortletPreferences(String... dataPortletPreferences) {
		_dataPortletPreferences = dataPortletPreferences;
	}

	protected void setDeletionSystemEventClassNames(
		String... deletionSystemEventClassNames) {

		_deletionSystemEventClassNames = deletionSystemEventClassNames;
	}

	protected void setExportControls(
		PortletDataHandlerControl... exportControls) {

		_exportControls = exportControls;

		setImportControls(exportControls);
	}

	protected void setExportMetadataControls(
		PortletDataHandlerControl... exportMetadataControls) {

		_exportMetadataControls = exportMetadataControls;

		setImportMetadataControls(exportMetadataControls);
	}

	protected void setImportControls(
		PortletDataHandlerControl... importControls) {

		_importControls = importControls;
	}

	protected void setImportMetadataControls(
		PortletDataHandlerControl... importMetadataControls) {

		_importMetadataControls = importMetadataControls;
	}

	protected void setPublishToLiveByDefault(boolean publishToLiveByDefault) {
		_publishToLiveByDefault = publishToLiveByDefault;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BasePortletDataHandler.class);

	private DataLevel _dataLevel = DataLevel.SITE;
	private boolean _dataLocalized;
	private String[] _dataPortletPreferences = StringPool.EMPTY_ARRAY;
	private String[] _deletionSystemEventClassNames = StringPool.EMPTY_ARRAY;
	private PortletDataHandlerControl[] _exportControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _exportMetadataControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _importControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _importMetadataControls =
		new PortletDataHandlerControl[0];
	private String _portletId;
	private boolean _publishToLiveByDefault;

}