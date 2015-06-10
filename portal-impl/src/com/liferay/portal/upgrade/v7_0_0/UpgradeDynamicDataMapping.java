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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.upgrade.v7_0_0.util.DDMContentTable;
import com.liferay.portal.upgrade.v7_0_0.util.DDMStructureTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormLayoutJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.DDMFieldsCounter;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormFieldValueTransformer;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesTransformer;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	protected void addStructureLayout(
			String uuid_, long structureLayoutId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long structureVersionId, String definition)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DDMStructureLayout (uuid_, ");
			sb.append("structureLayoutId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, ");
			sb.append("structureVersionId, definition) values (?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, uuid_);
			ps.setLong(2, structureLayoutId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setLong(9, structureVersionId);
			ps.setString(10, definition);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure layout " +
					"with structure version ID " + structureVersionId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addStructureVersion(
			long structureVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long structureId,
			long parentStructureId, String name, String description,
			String definition, String storageType, int type, int status,
			long statusByUserId, String statusByUserName, Timestamp statusDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructureVersion (structureVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("structureId, version, parentStructureId, name, ");
			sb.append("description, definition, storageType, type_, status, ");
			sb.append("statusByUserId, statusByUserName, statusDate) values ");
			sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, structureVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, structureId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setLong(9, parentStructureId);
			ps.setString(10, name);
			ps.setString(11, description);
			ps.setString(12, definition);
			ps.setString(13, storageType);
			ps.setInt(14, type);
			ps.setInt(15, status);
			ps.setLong(16, statusByUserId);
			ps.setString(17, statusByUserName);
			ps.setTimestamp(18, statusDate);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure version " +
					"with structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addTemplateVersion(
			long templateVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long classNameId,
			long classPK, long templateId, String name, String description,
			String language, String script, int status, long statusByUserId,
			String statusByUserName, Timestamp statusDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DDMTemplateVersion (templateVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("classNameId, classPK, templateId, version, name, ");
			sb.append("description, language, script, status, ");
			sb.append("statusByUserId, statusByUserName, statusDate) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, templateVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setLong(9, templateId);
			ps.setString(10, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(11, name);
			ps.setString(12, description);
			ps.setString(13, language);
			ps.setString(14, script);
			ps.setInt(15, status);
			ps.setLong(16, statusByUserId);
			ps.setString(17, statusByUserName);
			ps.setTimestamp(18, statusDate);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template version " +
					"with template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeDDMSchema();

		upgradeStructuresAndAddStructureVersionsAndLayouts();
		upgradeTemplatesAndAddTemplateVersions();
		upgradeXMLStorageAdapter();

		upgradeFileUploadReferences();
	}

	protected DDMForm getDDMForm(long structureId) throws Exception {
		DDMForm ddmForm = _ddmForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select parentStructureId, definition from DDMStructure " +
					"where structureId = ?" );

			ps.setLong(1, structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long parentStructureId = rs.getLong("parentStructureId");
				String definition = rs.getString("definition");

				ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

				if (parentStructureId > 0) {
					DDMForm parentDDMForm = getDDMForm(parentStructureId);

					List<DDMFormField> ddmFormFields =
						ddmForm.getDDMFormFields();

					ddmFormFields.addAll(parentDDMForm.getDDMFormFields());
				}

				_ddmForms.put(structureId, ddmForm);

				return ddmForm;
			}

			throw new UpgradeException(
				"Unable to find dynamic data mapping structure with ID " +
					structureId);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected DDMFormValues getDDMFormValues(DDMForm ddmForm, String xml)
		throws Exception {

		DDMFormValuesXSDDeserializer ddmFormValuesXSDDeserializer =
			new DDMFormValuesXSDDeserializer();

		return ddmFormValuesXSDDeserializer.deserialize(ddmForm, xml);
	}

	protected String getDefaultDDMFormLayoutDefinition(DDMForm ddmForm) {
		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return DDMFormLayoutJSONSerializerUtil.serialize(ddmFormLayout);
	}

	protected String toJSON(DDMForm ddmForm) {
		return DDMFormJSONSerializerUtil.serialize(ddmForm);
	}

	protected String toJSON(DDMFormValues ddmFormValues) {
		return DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues);
	}

	protected void transformFileUploadDDMFormFields(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long entryId, String entryVersion,
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new FileUploadDDMFormFieldValueTransformer(
				groupId, companyId, userId, userName, createDate, entryId,
				entryVersion));

		ddmFormValuesTransformer.transform();
	}

	protected void updateContent(DDMForm ddmForm, long contentId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select data_ from DDMContent where contentId = ?");

			ps.setLong(1, contentId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String xml = rs.getString("data_");

				DDMFormValues ddmFormValues = getDDMFormValues(ddmForm, xml);

				updateContent(contentId, toJSON(ddmFormValues));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateContent(long contentId, String data_)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DDMContent set data_= ? where contentId = ?");

			ps.setString(1, data_);
			ps.setLong(2, contentId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected DDMForm updateDDMFormFields(DDMForm ddmForm) {
		DDMForm copyDDMForm = new DDMForm(ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			copyDDMForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			String dataType = ddmFormField.getDataType();

			if (Validator.equals(dataType, "file-upload")) {
				ddmFormField.setDataType("document-library");
				ddmFormField.setType("ddm-documentlibrary");
			}
			else if (Validator.equals(dataType, "image")) {
				ddmFormField.setFieldNamespace("ddm");
				ddmFormField.setType("ddm-image");
			}
		}

		return copyDDMForm;
	}

	protected void updateStructureStorageType() throws Exception {
		runSQL(
			"update DDMStructure set storageType='json' where " +
				"storageType = 'xml'");
	}

	protected void updateStructureVersionStorageType() throws Exception {
		runSQL(
			"update DDMStructureVersion set storageType='json' where " +
				"storageType = 'xml'");
	}

	protected void upgradeDDLFileUploadReferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("select DDLRecordVersion.*, DDMContent.data_, ");
			sb.append("DDMStructure.structureId from DDLRecordVersion inner ");
			sb.append("join DDLRecordSet on DDLRecordVersion.recordSetId = ");
			sb.append("DDLRecordSet.recordSetId inner join DDMContent on  ");
			sb.append("DDLRecordVersion.DDMStorageId = DDMContent.contentId ");
			sb.append("inner join DDMStructure on DDLRecordSet.");
			sb.append("DDMStructureId = DDMStructure.structureId");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				long entryId = rs.getLong("recordId");
				String entryVersion = rs.getString("version");
				long contentId = rs.getLong("ddmStorageId");
				String data_ = rs.getString("data_");
				long ddmStructureId = rs.getLong("structureId");

				DDMForm ddmForm = getDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues =
					DDMFormValuesJSONDeserializerUtil.deserialize(
						ddmForm, data_);

				transformFileUploadDDMFormFields(
					groupId, companyId, userId, userName, createDate, entryId,
					entryVersion, ddmFormValues);

				updateContent(contentId, toJSON(ddmFormValues));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeDDMSchema() throws Exception {
		try {
			runSQL("alter_column_name DDMContent xml data_ TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMContentTable.TABLE_NAME, DDMContentTable.TABLE_COLUMNS,
				DDMContentTable.TABLE_SQL_CREATE,
				DDMContentTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_name DDMStructure xsd definition TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMStructureTable.TABLE_NAME, DDMStructureTable.TABLE_COLUMNS,
				DDMStructureTable.TABLE_SQL_CREATE,
				DDMStructureTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected void upgradeDLFileUploadReferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(10);

			sb.append("select DLFileVersion.*, DDMContent.contentId, ");
			sb.append("DDMContent.data_, DDMStructure.structureId from ");
			sb.append("DLFileEntryMetadata inner join DDMContent on ");
			sb.append("DLFileEntryMetadata.DDMStorageId = DDMContent.");
			sb.append("contentId inner join DDMStructure on ");
			sb.append("DLFileEntryMetadata.DDMStructureId = DDMStructure.");
			sb.append("structureId inner join DLFileVersion on ");
			sb.append("DLFileEntryMetadata.fileVersionId = DLFileVersion.");
			sb.append("fileVersionId and DLFileEntryMetadata.fileEntryId = ");
			sb.append("DLFileVersion.fileEntryId");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				long entryId = rs.getLong("fileEntryId");
				String entryVersion = rs.getString("version");
				long contentId = rs.getLong("contentId");
				String data_ = rs.getString("data_");
				long ddmStructureId = rs.getLong("structureId");

				DDMForm ddmForm = getDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues =
					DDMFormValuesJSONDeserializerUtil.deserialize(
						ddmForm, data_);

				transformFileUploadDDMFormFields(
					groupId, companyId, userId, userName, createDate, entryId,
					entryVersion, ddmFormValues);

				updateContent(contentId, toJSON(ddmFormValues));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeFileUploadReferences() throws Exception {
		upgradeDDLFileUploadReferences();
		upgradeDLFileUploadReferences();
	}

	protected void upgradeStructureDefinition(
			long structureId, String definition)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DDMStructure set definition = ? where structureId = ?");

			ps.setString(1, definition);
			ps.setLong(2, structureId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure with " +
					"structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeStructuresAndAddStructureVersionsAndLayouts()
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentStructureId = rs.getLong("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String storageType = rs.getString("storageType");
				int type = rs.getInt("type_");

				DDMForm ddmForm = getDDMForm(structureId);

				ddmForm = updateDDMFormFields(ddmForm);

				String definition = toJSON(ddmForm);

				upgradeStructureDefinition(structureId, definition);

				long structureVersionId = increment();

				addStructureVersion(
					structureVersionId, groupId, companyId, userId, userName,
					modifiedDate, structureId, parentStructureId, name,
					description, definition, storageType, type,
					WorkflowConstants.STATUS_APPROVED, userId, userName,
					modifiedDate);

				String ddmFormLayoutDefinition =
					getDefaultDDMFormLayoutDefinition(ddmForm);

				addStructureLayout(
					PortalUUIDUtil.generate(), increment(), groupId, companyId,
					userId, userName, modifiedDate, modifiedDate,
					structureVersionId, ddmFormLayoutDefinition);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeTemplatesAndAddTemplateVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMTemplate");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				long templateId = rs.getLong("templateId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String language = rs.getString("language");
				String script = rs.getString("script");

				if (language.equals("xsd")) {
					DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(
						script);

					ddmForm = updateDDMFormFields(ddmForm);

					script = toJSON(ddmForm);

					upgradeTemplateScript(templateId, script);
				}

				addTemplateVersion(
					increment(), groupId, companyId, userId, userName,
					modifiedDate, classNameId, classPK, templateId, name,
					description, language, script,
					WorkflowConstants.STATUS_APPROVED, userId, userName,
					modifiedDate);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeTemplateScript(long templateId, String script)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DDMTemplate set language = ?, script = ? where " +
					"templateId = ?");

			ps.setString(1, "json");
			ps.setString(2, script);
			ps.setLong(3, templateId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template with " +
					"template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeXMLStorageAdapter() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select DDMStorageLink.classPK, DDMStorageLink.");
			sb.append("structureId from DDMStorageLink inner join ");
			sb.append("DDMStructure on (DDMStorageLink.structureId = ");
			sb.append("DDMStructure.structureId) where DDMStorageLink.");
			sb.append("classNameId = ? and DDMStructure.storageType = ?");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, PortalUtil.getClassNameId(DDMContent.class));
			ps.setString(2, "xml");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long classPK = rs.getLong("classPK");

				DDMForm ddmForm = getDDMForm(structureId);

				updateContent(ddmForm, classPK);
			}

			updateStructureStorageType();
			updateStructureVersionStorageType();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

	private final Map<Long, DDMForm> _ddmForms = new HashMap<>();

	private class DDMFormValuesXSDDeserializer {

		public DDMFormValues deserialize(DDMForm ddmForm, String xml)
			throws PortalException {

			try {
				DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

				Document document = SAXReaderUtil.read(xml);

				Element rootElement = document.getRootElement();

				setDDMFormValuesAvailableLocales(ddmFormValues, rootElement);
				setDDMFormValuesDefaultLocale(ddmFormValues, rootElement);

				DDMFieldsCounter ddmFieldsCounter = new DDMFieldsCounter();

				for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
					String fieldName = ddmFormField.getName();

					int repetitions = countDDMFieldRepetitions(
						rootElement, fieldName, null, -1);

					for (int i = 0; i < repetitions; i++) {
						DDMFormFieldValue ddmFormFieldValue =
							createDDMFormFieldValue(fieldName);

						setDDMFormFieldValueProperties(
							ddmFormFieldValue, ddmFormField, rootElement,
							ddmFieldsCounter);

						ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
					}
				}

				return ddmFormValues;
			}
			catch (DocumentException de) {
				throw new UpgradeException(de);
			}
		}

		protected int countDDMFieldRepetitions(
			Element rootElement, String fieldName, String parentFieldName,
			int parentOffset) {

			String[] ddmFieldsDisplayValues = getDDMFieldsDisplayValues(
				rootElement, true);

			if (ddmFieldsDisplayValues.length != 0) {
				return countDDMFieldRepetitions(
					ddmFieldsDisplayValues, fieldName, parentFieldName,
					parentOffset);
			}

			Element dynamicElementElement = getDynamicElementElementByName(
				rootElement, fieldName);

			if (dynamicElementElement != null) {
				return 1;
			}

			return 0;
		}

		protected int countDDMFieldRepetitions(
			String[] fieldsDisplayValues, String fieldName,
			String parentFieldName, int parentOffset) {

			int offset = -1;

			int repetitions = 0;

			for (int i = 0; i < fieldsDisplayValues.length; i++) {
				String fieldDisplayName = fieldsDisplayValues[i];

				if (offset > parentOffset) {
					break;
				}

				if (fieldDisplayName.equals(parentFieldName)) {
					offset++;
				}

				if (fieldDisplayName.equals(fieldName) &&
					(offset == parentOffset)) {

					repetitions++;
				}
			}

			return repetitions;
		}

		protected DDMFormFieldValue createDDMFormFieldValue(String name) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(name);

			return ddmFormFieldValue;
		}

		protected Set<Locale> getAvailableLocales(
			Element dynamicElementElement) {

			List<Element> dynamicContentElements =
				dynamicElementElement.elements("dynamic-content");

			Set<Locale> availableLocales = new LinkedHashSet<>();

			for (Element dynamicContentElement : dynamicContentElements) {
				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				availableLocales.add(LocaleUtil.fromLanguageId(languageId));
			}

			return availableLocales;
		}

		protected Set<Locale> getAvailableLocales(
			List<Element> dynamicElementElements) {

			Set<Locale> availableLocales = new LinkedHashSet<>();

			for (Element dynamicElementElement : dynamicElementElements) {
				availableLocales.addAll(
					getAvailableLocales(dynamicElementElement));
			}

			return availableLocales;
		}

		protected String getDDMFieldInstanceId(
			Element rootElement, String fieldName, int index) {

			String[] ddmFieldsDisplayValues = getDDMFieldsDisplayValues(
				rootElement, false);

			if (ddmFieldsDisplayValues.length == 0) {
				return StringUtil.randomString();
			}

			String prefix = fieldName.concat(DDMImpl.INSTANCE_SEPARATOR);

			for (String ddmFieldsDisplayValue : ddmFieldsDisplayValues) {
				if (ddmFieldsDisplayValue.startsWith(prefix)) {
					index--;

					if (index < 0) {
						return StringUtil.extractLast(
							ddmFieldsDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
					}
				}
			}

			return null;
		}

		protected String[] getDDMFieldsDisplayValues(
			Element rootElement, boolean extractFieldName) {

			Element fieldsDisplayElement = getDynamicElementElementByName(
				rootElement, "_fieldsDisplay");

			List<String> ddmFieldsDisplayValues = new ArrayList<>();

			if (fieldsDisplayElement != null) {
				Element fieldsDisplayDynamicContent =
					fieldsDisplayElement.element("dynamic-content");

				String fieldsDisplayText =
					fieldsDisplayDynamicContent.getText();

				for (String fieldDisplayValue :
						StringUtil.split(fieldsDisplayText)) {

					if (extractFieldName) {
						fieldDisplayValue = StringUtil.extractFirst(
							fieldDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
					}

					ddmFieldsDisplayValues.add(fieldDisplayValue);
				}
			}

			return ddmFieldsDisplayValues.toArray(
				new String[ddmFieldsDisplayValues.size()]);
		}

		protected DDMFormFieldValue getDDMFormFieldValue(
			Element dynamicElementElement) {

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(
				dynamicElementElement.attributeValue("name"));

			List<Element> dynamicContentElements =
				dynamicElementElement.elements("dynamic-content");

			ddmFormFieldValue.setValue(getValue(dynamicContentElements));

			ddmFormFieldValue.setNestedDDMFormFields(
				getDDMFormFieldValues(
					dynamicElementElement.elements("dynamic-element")));

			return ddmFormFieldValue;
		}

		protected List<DDMFormFieldValue> getDDMFormFieldValues(
			List<Element> dynamicElementElements) {

			if (dynamicElementElements == null) {
				return null;
			}

			List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

			for (Element dynamicElement : dynamicElementElements) {
				ddmFormFieldValues.add(getDDMFormFieldValue(dynamicElement));
			}

			return ddmFormFieldValues;
		}

		protected String getDDMFormFieldValueValueString(
			Element dynamicElementElement, Locale locale, int index) {

			Element dynamicContentElement = getDynamicContentElement(
				dynamicElementElement, locale, index);

			return dynamicContentElement.getTextTrim();
		}

		protected Locale getDefaultLocale(Element dynamicElementElement) {
			String defaultLanguageId = dynamicElementElement.attributeValue(
				"default-language-id");

			return LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		protected Locale getDefaultLocale(
			List<Element> dynamicElementElements) {

			for (Element dynamicElement : dynamicElementElements) {
				String defaultLanguageId = dynamicElement.attributeValue(
					"default-language-id");

				if (defaultLanguageId != null) {
					return LocaleUtil.fromLanguageId(defaultLanguageId);
				}
			}

			return null;
		}

		protected Element getDynamicContentElement(
			Element dynamicElementElement, Locale locale, int index) {

			String languageId = LocaleUtil.toLanguageId(locale);

			XPath dynamicContentXPath = SAXReaderUtil.createXPath(
				"dynamic-content[(@language-id='" + languageId + "')]");

			List<Node> nodes = dynamicContentXPath.selectNodes(
				dynamicElementElement);

			return (Element)nodes.get(index);
		}

		protected Element getDynamicElementElementByName(
			Element rootElement, String fieldName) {

			XPath dynamicElementXPath = SAXReaderUtil.createXPath(
				"//dynamic-element[(@name=\"" + fieldName + "\")]");

			if (dynamicElementXPath.booleanValueOf(rootElement)) {
				return (Element)dynamicElementXPath.evaluate(rootElement);
			}

			return null;
		}

		protected Value getValue(List<Element> dynamicContentElements) {
			Value value = new LocalizedValue();

			for (Element dynamicContentElement : dynamicContentElements) {
				String fieldValue = dynamicContentElement.getText();

				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				Locale locale = LocaleUtil.fromLanguageId(languageId);

				value.addString(locale, fieldValue);
			}

			return value;
		}

		protected void setDDMFormFieldValueInstanceId(
			DDMFormFieldValue ddmFormFieldValue, Element rootElement,
			DDMFieldsCounter ddmFieldsCounter) {

			String name = ddmFormFieldValue.getName();

			String instanceId = getDDMFieldInstanceId(
				rootElement, name, ddmFieldsCounter.get(name));

			ddmFormFieldValue.setInstanceId(instanceId);
		}

		protected void setDDMFormFieldValueLocalizedValue(
			DDMFormFieldValue ddmFormFieldValue, Element dynamicElementElement,
			int index) {

			Value value = new LocalizedValue(
				getDefaultLocale(dynamicElementElement));

			Map<String, Integer> dynamicContentValuesMap = new HashMap<>();

			for (Element dynamicContentElement : dynamicElementElement.elements(
				"dynamic-content")) {

				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				int localizedContentIndex = 0;

				if (dynamicContentValuesMap.containsKey(languageId)) {
					localizedContentIndex = dynamicContentValuesMap.get(
						languageId);
				}

				if (localizedContentIndex == index) {
					Locale locale = LocaleUtil.fromLanguageId(languageId);

					String content = dynamicContentElement.getText();

					value.addString(locale, content);
				}

				dynamicContentValuesMap.put(
					languageId, localizedContentIndex+1);
			}

			ddmFormFieldValue.setValue(value);
		}

		protected void setDDMFormFieldValueProperties(
				DDMFormFieldValue ddmFormFieldValue, DDMFormField ddmFormField,
				Element rootElement, DDMFieldsCounter ddmFieldsCounter)
			throws PortalException {

			setDDMFormFieldValueInstanceId(
				ddmFormFieldValue, rootElement, ddmFieldsCounter);

			setNestedDDMFormFieldValues(
				ddmFormFieldValue, ddmFormField, rootElement, ddmFieldsCounter);

			setDDMFormFieldValueValues(
				ddmFormFieldValue, ddmFormField, rootElement, ddmFieldsCounter);
		}

		protected void setDDMFormFieldValueUnlocalizedValue(
			DDMFormFieldValue ddmFormFieldValue, Element dynamicElement,
			int index) {

			String valueString = getDDMFormFieldValueValueString(
				dynamicElement, getDefaultLocale(dynamicElement), index);

			Value value = new UnlocalizedValue(valueString);

			ddmFormFieldValue.setValue(value);
		}

		protected void setDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue, DDMFormField ddmFormField,
			Element rootElement, DDMFieldsCounter ddmFieldsCounter) {

			String fieldName = ddmFormFieldValue.getName();

			Element dynamicElement = getDynamicElementElementByName(
				rootElement, fieldName);

			if (Validator.isNotNull(ddmFormField.getDataType())) {
				if (ddmFormField.isLocalizable()) {
					setDDMFormFieldValueLocalizedValue(
						ddmFormFieldValue, dynamicElement,
						ddmFieldsCounter.get(fieldName));
				}
				else {
					setDDMFormFieldValueUnlocalizedValue(
						ddmFormFieldValue, dynamicElement,
						ddmFieldsCounter.get(fieldName));
				}
			}

			ddmFieldsCounter.incrementKey(fieldName);
		}

		protected void setDDMFormValuesAvailableLocales(
			DDMFormValues ddmFormValues, Element rootElement) {

			Set<Locale> availableLocales = getAvailableLocales(
				rootElement.elements("dynamic-element"));

			ddmFormValues.setAvailableLocales(availableLocales);
		}

		protected void setDDMFormValuesDefaultLocale(
			DDMFormValues ddmFormValues, Element rootElement) {

			Locale defaultLocale = getDefaultLocale(
				rootElement.elements("dynamic-element"));

			ddmFormValues.setDefaultLocale(defaultLocale);
		}

		protected void setNestedDDMFormFieldValues(
				DDMFormFieldValue ddmFormFieldValue, DDMFormField ddmFormField,
				Element rootElement, DDMFieldsCounter ddmFieldsCounter)
			throws PortalException {

			String fieldName = ddmFormFieldValue.getName();

			int parentOffset = ddmFieldsCounter.get(fieldName);

			Map<String, DDMFormField> nestedDDMFormFieldsMap =
				ddmFormField.getNestedDDMFormFieldsMap();

			String[] ddmFieldsDisplayValues = getDDMFieldsDisplayValues(
				rootElement, true);

			for (Map.Entry<String, DDMFormField> nestedDDMFormFieldEntry :
					nestedDDMFormFieldsMap.entrySet()) {

				String nestedDDMFormFieldName =
					nestedDDMFormFieldEntry.getKey();

				DDMFormField nestedDDMFormField =
					nestedDDMFormFieldEntry.getValue();

				int repetitions = countDDMFieldRepetitions(
					ddmFieldsDisplayValues, nestedDDMFormFieldName, fieldName,
					parentOffset);

				for (int i = 0; i < repetitions; i++) {
					DDMFormFieldValue nestedDDMFormFieldValue =
						createDDMFormFieldValue(nestedDDMFormFieldName);

					setDDMFormFieldValueProperties(
						nestedDDMFormFieldValue, nestedDDMFormField,
						rootElement, ddmFieldsCounter);

					ddmFormFieldValue.addNestedDDMFormFieldValue(
						nestedDDMFormFieldValue);
				}
			}
		}

	}

	private class FileUploadDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public FileUploadDDMFormFieldValueTransformer(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long entryId, String entryVersion) {

			_groupId = groupId;
			_companyId = companyId;
			_userId = userId;
			_userName = userName;
			_createDate = createDate;
			_entryId = entryId;
			_entryVersion = entryVersion;
		}

		@Override
		public String getFieldType() {
			return "ddm-fileupload";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				if (Validator.isNull(valueString)) {
					continue;
				}

				String fileEntryUuid = PortalUUIDUtil.generate();

				upgradeFileUploadReference(
					fileEntryUuid, ddmFormFieldValue.getName(), valueString);

				value.addString(locale, toJSON(_groupId, fileEntryUuid));
			}
		}

		protected void addAssetEntry(
				long entryId, long groupId, long companyId, long userId,
				String userName, Timestamp createDate, Timestamp modifiedDate,
				long classNameId, long classPK, String classUuid,
				long classTypeId, boolean visible, Timestamp startDate,
				Timestamp endDate, Timestamp publishDate,
				Timestamp expirationDate, String mimeType, String title,
				String description, String summary, String url,
				String layoutUuid, int height, int width, double priority,
				int viewCount)
			throws Exception {

			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(9);

				sb.append("insert into AssetEntry (entryId, groupId, ");
				sb.append("companyId, userId, userName, createDate, ");
				sb.append("modifiedDate, classNameId, classPK, classUuid, ");
				sb.append("classTypeId, visible, startDate, endDate, ");
				sb.append("publishDate, expirationDate, mimeType, title, ");
				sb.append("description, summary, url, layoutUuid, height, ");
				sb.append("width, priority, viewCount) values (?, ?, ?, ?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
				sb.append("?, ?, ?, ?, ?, ?)");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setLong(1, entryId);
				ps.setLong(2, groupId);
				ps.setLong(3, companyId);
				ps.setLong(4, userId);
				ps.setString(5, userName);
				ps.setTimestamp(6, createDate);
				ps.setTimestamp(7, modifiedDate);
				ps.setLong(8, classNameId);
				ps.setLong(9, classPK);
				ps.setString(10, classUuid);
				ps.setLong(11, classTypeId);
				ps.setBoolean(12, visible);
				ps.setTimestamp(13, startDate);
				ps.setTimestamp(14, endDate);
				ps.setTimestamp(15, publishDate);
				ps.setTimestamp(16, expirationDate);
				ps.setString(17, mimeType);
				ps.setString(18, title);
				ps.setString(19, description);
				ps.setString(20, summary);
				ps.setString(21, url);
				ps.setString(22, layoutUuid);
				ps.setInt(23, height);
				ps.setInt(24, width);
				ps.setDouble(25, priority);
				ps.setInt(26, viewCount);

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(con, ps);
			}
		}

		protected long addDDMDLFolder() throws Exception {
			long ddmFolderId = getDLFolderId(
				_groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "DDM");

			if (ddmFolderId > 0) {
				return ddmFolderId;
			}

			ddmFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), ddmFolderId, _groupId, _companyId,
				_userId, _userName, _now, _now, _groupId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "DDM",
				StringPool.BLANK, _now);

			return ddmFolderId;
		}

		protected void addDLFileEntry(
				String uuid, long fileEntryId, long groupId, long companyId,
				long userId, String userName, Timestamp createDate,
				Timestamp modifiedDate, long classNameId, long classPK,
				long repositoryId, long folderId, String treePath, String name,
				String fileName, String extension, String mimeType,
				String title, String description, String extraSettings,
				long fileEntryTypeId, String version, long size, int readCount,
				long smallImageId, long largeImageId, long custom1ImageId,
				long custom2ImageId, boolean manualCheckInRequired)
			throws Exception {

			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(9);

				sb.append("insert into DLFileEntry (uuid_, fileEntryId, ");
				sb.append("groupId, companyId, userId, userName, createDate, ");
				sb.append("modifiedDate, classNameId, classPK, repositoryId, ");
				sb.append("folderId, treePath, name, fileName, extension, ");
				sb.append("mimeType, title, description, extraSettings, ");
				sb.append("fileEntryTypeId, version, size_, readCount,  ");
				sb.append("smallImageId, largeImageId, custom1ImageId, ");
				sb.append("custom2ImageId, manualCheckInRequired) values (?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setString(1, uuid);
				ps.setLong(2, fileEntryId);
				ps.setLong(3, groupId);
				ps.setLong(4, companyId);
				ps.setLong(5, userId);
				ps.setString(6, userName);
				ps.setTimestamp(7, createDate);
				ps.setTimestamp(8, modifiedDate);
				ps.setLong(9, classNameId);
				ps.setLong(10, classPK);
				ps.setLong(11, repositoryId);
				ps.setLong(12, folderId);
				ps.setString(13, treePath);
				ps.setString(14, name);
				ps.setString(15, fileName);
				ps.setString(16, extension);
				ps.setString(17, mimeType);
				ps.setString(18, title);
				ps.setString(19, description);
				ps.setString(20, extraSettings);
				ps.setLong(21, fileEntryTypeId);
				ps.setString(22, version);
				ps.setLong(23, size);
				ps.setInt(24, readCount);
				ps.setLong(25, smallImageId);
				ps.setLong(26, largeImageId);
				ps.setLong(27, custom1ImageId);
				ps.setLong(28, custom2ImageId);
				ps.setBoolean(29, manualCheckInRequired);

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(con, ps);
			}
		}

		protected void addDLFileVersion(
				String uuid, long fileVersionId, long groupId, long companyId,
				long userId, String userName, Timestamp createDate,
				Timestamp modifiedDate, long repositoryId, long folderId,
				long fileEntryId, String treePath, String fileName,
				String extension, String mimeType, String title,
				String description, String changeLog, String extraSettings,
				long fileEntryTypeId, String version, long size,
				String checksum, int status, long statusByUserId,
				String statusByUserName, Timestamp statusDate)
			throws Exception {

			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(10);

				sb.append("insert into DLFileVersion (uuid_, fileVersionId, ");
				sb.append("groupId, companyId, userId, userName, createDate, ");
				sb.append("modifiedDate, repositoryId, folderId, ");
				sb.append("fileEntryId, treePath, fileName, extension, ");
				sb.append("mimeType, title, description, changeLog, ");
				sb.append("extraSettings, fileEntryTypeId, version, size_, ");
				sb.append("checksum, status, statusByUserId, ");
				sb.append("statusByUserName, statusDate) values (?, ?, ?, ?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?)");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setString(1, uuid);
				ps.setLong(2, fileVersionId);
				ps.setLong(3, groupId);
				ps.setLong(4, companyId);
				ps.setLong(5, userId);
				ps.setString(6, userName);
				ps.setTimestamp(7, createDate);
				ps.setTimestamp(8, modifiedDate);
				ps.setLong(9, repositoryId);
				ps.setLong(10, folderId);
				ps.setLong(11, fileEntryId);
				ps.setString(12, treePath);
				ps.setString(13, fileName);
				ps.setString(14, extension);
				ps.setString(15, mimeType);
				ps.setString(16, title);
				ps.setString(17, description);
				ps.setString(18, changeLog);
				ps.setString(19, extraSettings);
				ps.setLong(20, fileEntryTypeId);
				ps.setString(21, version);
				ps.setLong(22, size);
				ps.setString(23, checksum);
				ps.setInt(24, status);
				ps.setLong(25, statusByUserId);
				ps.setString(26, statusByUserName);
				ps.setTimestamp(27, statusDate);

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(con, ps);
			}
		}

		protected void addDLFolder(
				String uuid, long folderId, long groupId, long companyId,
				long userId, String userName, Timestamp createDate,
				Timestamp modifiedDate, long repositoryId, long parentFolderId,
				String name, String description, Timestamp lastPostDate)
			throws Exception {

			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(5);

				sb.append("insert into DLFolder (uuid_, folderId, groupId, ");
				sb.append("companyId, userId, userName, createDate, ");
				sb.append("modifiedDate, repositoryId, mountPoint, ");
				sb.append("parentFolderId, name, description, lastPostDate, ");
				sb.append("defaultFileEntryTypeId, hidden_, status, ");
				sb.append("statusByUserId, statusByUserName, restrictionType");
				sb.append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?)");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setString(1, uuid);
				ps.setLong(2, folderId);
				ps.setLong(3, groupId);
				ps.setLong(4, companyId);
				ps.setLong(5, userId);
				ps.setString(6, userName);
				ps.setTimestamp(7, createDate);
				ps.setTimestamp(8, modifiedDate);
				ps.setLong(9, repositoryId);
				ps.setBoolean(10, false);
				ps.setLong(11, parentFolderId);
				ps.setString(12, name);
				ps.setString(13, description);
				ps.setTimestamp(14, lastPostDate);
				ps.setLong(15, 0);
				ps.setBoolean(16, false);
				ps.setInt(17, WorkflowConstants.STATUS_APPROVED);
				ps.setInt(18, 0);
				ps.setString(19, StringPool.BLANK);
				ps.setInt(20, 0);

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(con, ps);
			}
		}

		protected long addDLFolderTree(String ddmFormFieldName)
			throws Exception {

			long ddmFolderId = addDDMDLFolder();

			long entryIdFolderId = addEntryIdDLFolder(ddmFolderId);

			long entryVersionFolderId = addEntryVersionDLFolder(
				entryIdFolderId);

			long fieldNameFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), fieldNameFolderId, _groupId,
				_companyId, _userId, _userName, _now, _now, _groupId,
				entryVersionFolderId, ddmFormFieldName, StringPool.BLANK, _now);

			return fieldNameFolderId;
		}

		protected long addEntryIdDLFolder(long ddmFolderId) throws Exception {
			long entryIdFolderId = getDLFolderId(
				_groupId, ddmFolderId, String.valueOf(_entryId));

			if (entryIdFolderId > 0) {
				return entryIdFolderId;
			}

			entryIdFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), entryIdFolderId, _groupId,
				_companyId, _userId, _userName, _now, _now, _groupId,
				ddmFolderId, String.valueOf(_entryId), StringPool.BLANK, _now);

			return entryIdFolderId;
		}

		protected long addEntryVersionDLFolder(long entryIdFolderId)
			throws Exception {

			long entryVersionFolderId = getDLFolderId(
				_groupId, entryIdFolderId, _entryVersion);

			if (entryVersionFolderId > 0) {
				return entryVersionFolderId;
			}

			entryVersionFolderId = increment();

			addDLFolder(
				PortalUUIDUtil.generate(), entryVersionFolderId, _groupId,
				_companyId, _userId, _userName, _now, _now, _groupId,
				entryIdFolderId, _entryVersion, StringPool.BLANK, _now);

			return entryVersionFolderId;
		}

		protected void addResourcePermissions(
				int mvccVersion, long resourcePermissionId, long companyId,
				String name, long scope, long primKey, long roleId,
				long ownerId, long actionIds)
			throws Exception {

			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(4);

				sb.append("insert into ResourcePermission (mvccVersion, ");
				sb.append("resourcePermissionId, companyId, name, scope, ");
				sb.append("primKey, roleId, ownerId, actionIds) values (?, ");
				sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setLong(1, mvccVersion);
				ps.setLong(2, resourcePermissionId);
				ps.setLong(3, companyId);
				ps.setString(4, name);
				ps.setLong(5, scope);
				ps.setLong(6, primKey);
				ps.setLong(7, roleId);
				ps.setLong(8, ownerId);
				ps.setLong(9, actionIds);

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(con, ps);
			}
		}

		protected long getActionBitwiseValue(String action) throws Exception {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(4);

				sb.append("select bitwiseValue from ResourceAction where ");
				sb.append("name = ? and actionId = ?");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setString(1, DLFileEntry.class.getName());
				ps.setString(2, action);

				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getLong("bitwiseValue");
				}

				return 0;
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}
		}

		protected long getActionIdsLong(String[] actions) throws Exception {
			long actionIdsLong = 0;

			for (String action : actions) {
				actionIdsLong |= getActionBitwiseValue(action);
			}

			return actionIdsLong;
		}

		protected long getDLFolderId(
				long groupId, long parentFolderId, String name)
			throws Exception {

			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				String sql =
					"select folderId from DLFolder where groupId = ? and " +
					"parentFolderId = ? and name = ?";

				ps = con.prepareStatement(sql);

				ps.setLong(1, groupId);
				ps.setLong(2, parentFolderId);
				ps.setString(3, name);

				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getLong("folderId");
				}
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}

			return 0;
		}

		protected String getExtension(String fileName) {
			String extension = StringPool.BLANK;

			int pos = fileName.lastIndexOf(CharPool.PERIOD);

			if (pos > 0) {
				extension = fileName.substring(pos + 1, fileName.length());
			}

			return StringUtil.toLowerCase(extension);
		}

		protected long getRoleId(String roleName) throws Exception {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				StringBundler sb = new StringBundler(4);

				sb.append("select roleId from role_ where companyId = ? and ");
				sb.append("name = ?");

				String sql = sb.toString();

				ps = con.prepareStatement(sql);

				ps.setLong(1, _companyId);
				ps.setString(2, roleName);

				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getLong("roleId");
				}

				return 0;
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}
		}

		protected String toJSON(long groupId, String fileEntryUuid) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", groupId);
			jsonObject.put("uuid", fileEntryUuid);

			return jsonObject.toString();
		}

		protected String upgradeFileUploadReference(
				String fileEntryUuid, String ddmFormFieldName,
				String valueString)
			throws PortalException {

			try {
				long dlFolderId = addDLFolderTree(ddmFormFieldName);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					valueString);

				String name = String.valueOf(
					increment(DLFileEntry.class.getName()));

				String fileName = jsonObject.getString("name");
				String filePath = jsonObject.getString("path");

				// File entry

				long fileEntryId = increment();

				String extension = getExtension(fileName);

				File file = DLStoreUtil.getFile(
					_companyId, CompanyConstants.SYSTEM, filePath);

				addDLFileEntry(
					fileEntryUuid, fileEntryId, _groupId, _companyId, _userId,
					_userName, _createDate, _createDate, 0, 0, _groupId,
					dlFolderId, StringPool.BLANK, name, fileName, extension,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					DLFileEntryConstants.VERSION_DEFAULT, file.length(),
					DLFileEntryConstants.DEFAULT_READ_COUNT, 0, 0, 0, 0, false);

				// Resource permissions

				addResourcePermissions(
					0, increment(), _companyId, DLFileEntry.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, fileEntryId,
					getRoleId(RoleConstants.OWNER), _userId,
					getActionIdsLong(_ownerPermissions));

				if (_groupId > 0) {
					addResourcePermissions(
						0, increment(), _companyId, DLFileEntry.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL, fileEntryId,
						getRoleId(RoleConstants.SITE_MEMBER), 0,
						getActionIdsLong(_groupPermissions));
				}

				addResourcePermissions(
					0, increment(), _companyId, DLFileEntry.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, fileEntryId,
					getRoleId(RoleConstants.GUEST), 0,
					getActionIdsLong(_guestPermissions));

				// File version

				addDLFileVersion(
					fileEntryUuid, increment(), _groupId, _companyId, _userId,
					_userName, _createDate, _createDate, _groupId, dlFolderId,
					fileEntryId, StringPool.BLANK, fileName, extension,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					DLFileEntryConstants.VERSION_DEFAULT, file.length(),
					StringPool.BLANK, WorkflowConstants.STATUS_APPROVED,
					_userId, _userName, _createDate);

				// Asset entry

				addAssetEntry(
					increment(), _groupId, _companyId, _userId, _userName,
					_createDate, _createDate,
					PortalUtil.getClassNameId(DLFileEntry.class), fileEntryId,
					fileEntryUuid,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					false, null, null, null, null,
					MimeTypesUtil.getContentType(fileName), fileName,
					StringPool.BLANK, StringPool.BLANK, null, null, 0, 0, 0, 0);

				// File

				DLStoreUtil.addFile(_companyId, dlFolderId, name, file);

				return fileEntryUuid;
			}
			catch (Exception e) {
				throw new UpgradeException(e);
			}
		}

		private final long _companyId;
		private final Timestamp _createDate;
		private final long _entryId;
		private final String _entryVersion;
		private final long _groupId;
		private final String[] _groupPermissions = {"ADD_DISCUSSION", "VIEW"};
		private final String[] _guestPermissions = {"ADD_DISCUSSION", "VIEW"};
		private final Timestamp _now = new Timestamp(
			System.currentTimeMillis());
		private final String[] _ownerPermissions = {
			"ADD_DISCUSSION", "DELETE", "DELETE_DISCUSSION",
			"OVERRIDE_CHECKOUT", "PERMISSIONS", "UPDATE", "UPDATE_DISCUSSION",
			"VIEW"
		};
		private final long _userId;
		private final String _userName;

	}

}