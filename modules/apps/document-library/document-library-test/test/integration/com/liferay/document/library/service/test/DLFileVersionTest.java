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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.events.AddDefaultDocumentLibraryStructuresAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.BaseStore;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class DLFileVersionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();

		_group = GroupTestUtil.addGroup();

		setUpParentFolder();
		setUpResourcePermission();

		Registry registry = RegistryUtil.getRegistry();

		SimpleAction simpleAction = registry.getService(
			AddDefaultDocumentLibraryStructuresAction.class);

		String companyIdString = String.valueOf(TestPropsValues.getCompanyId());

		simpleAction.run(new String[] {companyIdString});

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					_group.getGroupId()));

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			String fileEntryTypeKey = dlFileEntryType.getFileEntryTypeKey();

			if (fileEntryTypeKey.equals(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT)) {

				_contractDLFileEntryTypeId =
					dlFileEntryType.getFileEntryTypeId();
			}
		}

		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), _EXPANDO_ATTRIBUTE_NAME,
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		_serviceContext = getServiceContext();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(), _SOURCE_FILE_NAME,
			ContentTypes.APPLICATION_OCTET_STREAM, _TITLE, StringPool.BLANK,
			StringPool.BLANK, _DATA_VERSION_1, _serviceContext);

		_fileVersion = DLFileVersionLocalServiceUtil.getFileVersion(
			fileEntry.getFileEntryId(), DLFileEntryConstants.VERSION_DEFAULT);

		_captureAppender = Log4JLoggerTestUtil.configureLog4JLogger(
			BaseStore.class.getName(), Level.WARN);
	}

	@After
	public void tearDown() throws Exception {
		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.getDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoTableLocalServiceUtil.deleteTable(expandoTable);

		tearDownPermissionThreadLocal();
		tearDownPrincipalThreadLocal();
		tearDownResourcePermission();

		List<LoggingEvent> loggingEvents = _captureAppender.getLoggingEvents();

		Assert.assertTrue(loggingEvents.isEmpty());

		_captureAppender.close();
	}

	@Test
	public void testRevertVersion() throws Exception {
		DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _UPDATE_VALUE,
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		DLAppServiceUtil.revertFileEntry(
			fileEntry.getFileEntryId(), DLFileEntryConstants.VERSION_DEFAULT,
			_serviceContext);

		fileEntry = DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId());

		Assert.assertEquals("2.0", fileEntry.getVersion());

		checkLogForFileDeletion(4);
	}

	@Test
	public void testUpdateChecksum() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_2, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateDescription() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(), _UPDATE_VALUE,
			_fileVersion.getChangeLog(), false, _DATA_VERSION_1,
			_serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateExpando() throws Exception {
		updateServiceContext(
			_UPDATE_VALUE, _contractDLFileEntryTypeId, StringPool.BLANK);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateFileEntryType() throws Exception {
		updateServiceContext(
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			StringPool.BLANK);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateMetadata() throws Exception {
		updateServiceContext(
			StringPool.BLANK, _contractDLFileEntryTypeId, _UPDATE_VALUE);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateNothing() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateSize() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_3, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	@Test
	public void testUpdateTitle() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _UPDATE_VALUE,
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());

		checkLogForFileDeletion(1);
	}

	protected void checkLogForFileDeletion(int size) {
		List<LoggingEvent> loggingEvents = _captureAppender.getLoggingEvents();

		Assert.assertEquals(size, loggingEvents.size());

		for (LoggingEvent loggingEvent : loggingEvents) {
			String message = (String)loggingEvent.getMessage();

			Assert.assertTrue(
				message.startsWith(
					"Unable to delete file {companyId=" +
						_fileVersion.getCompanyId()));
			Assert.assertTrue(
				message.endsWith(
					"versionLabel=PWC} because it does not exist"));
		}

		loggingEvents.clear();
	}

	protected Field createField(DDMStructure ddmStructure, String name) {
		Field field = new Field(
			ddmStructure.getStructureId(), name, StringPool.BLANK);

		field.setDefaultLocale(LocaleUtil.US);

		return field;
	}

	protected Field createFieldsDisplayField(
		DDMStructure ddmStructure, Set<String> fieldNames) {

		List<String> fieldsDisplayValues = new ArrayList<>();

		for (String fieldName : fieldNames) {
			fieldsDisplayValues.add(
				fieldName + DDMImpl.INSTANCE_SEPARATOR +
				StringUtil.randomString());
		}

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
			StringUtil.merge(fieldsDisplayValues));

		fieldsDisplayField.setDefaultLocale(LocaleUtil.US);

		return fieldsDisplayField;
	}

	protected ServiceContext getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _contractDLFileEntryTypeId);

		Map<String, Serializable> expandoBridgeAttributes =
			serviceContext.getExpandoBridgeAttributes();

		expandoBridgeAttributes.put(_EXPANDO_ATTRIBUTE_NAME, StringPool.BLANK);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				_contractDLFileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = new Fields();

			Set<String> fieldNames = ddmStructure.getFieldNames();

			for (String fieldName : fieldNames) {
				Field field = createField(ddmStructure, fieldName);

				fields.put(field);
			}

			Field fieldsDisplayField = createFieldsDisplayField(
				ddmStructure, fieldNames);

			fields.put(fieldsDisplayField);

			DDMFormValues ddmFormValues =
				FieldsToDDMFormValuesConverterUtil.convert(
					DDMStructureLocalServiceUtil.getDDMStructure(
						ddmStructure.getStructureId()),
					fields);

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + ddmStructure.getStructureId(),
				ddmFormValues);
		}

		return serviceContext;
	}

	protected void setUpParentFolder() throws Exception {
		try {
			DLAppServiceUtil.deleteFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"Test Folder");
		}
		catch (NoSuchFolderException nsfe) {
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		parentFolder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder", RandomTestUtil.randomString(), serviceContext);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {

				{
					init(TestPropsValues.getUser());
				}

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

				@Override
				public boolean hasPermission(
					long groupId, String name, String primKey,
					String actionId) {

					return true;
				}

			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	protected void setUpResourcePermission() throws Exception {
		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, "com.liferay.portlet.documentlibrary",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	protected void tearDownPermissionThreadLocal() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	protected void tearDownPrincipalThreadLocal() {
		PrincipalThreadLocal.setName(_originalName);
	}

	protected void tearDownResourcePermission() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, "com.liferay.portlet.documentlibrary",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	protected void updateServiceContext(
			String expando, long fileEntryTypeId, String metadata)
		throws PortalException {

		Map<String, Serializable> expandoBridgeAttributes =
			_serviceContext.getExpandoBridgeAttributes();

		expandoBridgeAttributes.put(_EXPANDO_ATTRIBUTE_NAME, expando);

		_serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		_serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

		if (fileEntryTypeId <= 0) {
			return;
		}

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DDMFormValues ddmFormValues =
				(DDMFormValues)_serviceContext.getAttribute(
					DDMFormValues.class.getName() +
					ddmStructure.getStructureId());

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = DDMStructureLocalServiceUtil.getDDMStructure(
					ddmStructure.getStructureId());

			Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
				structure, ddmFormValues);

			for (Field field : fields) {
				String type = field.getType();

				if (!field.isPrivate() && type.equals("text")) {
					field.setValue(metadata);
				}
			}

			ddmFormValues = FieldsToDDMFormValuesConverterUtil.convert(
				structure, fields);

			_serviceContext.setAttribute(
				DDMFormValues.class.getName() + ddmStructure.getStructureId(),
				ddmFormValues);
		}
	}

	private static final int _DATA_SIZE_1 = 512;

	private static final int _DATA_SIZE_2 = 1024;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE_1];

	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE_1];

	private static final byte[] _DATA_VERSION_3 = new byte[_DATA_SIZE_2];

	private static final String _EXPANDO_ATTRIBUTE_NAME = "Expando";

	private static final String _SOURCE_FILE_NAME = "SourceFileName.txt";

	private static final String _TITLE = "Title";

	private static final String _UPDATE_VALUE = "Update Value";

	static {
		for (int i = 0; i < _DATA_SIZE_1; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}

		for (int i = 0; i < _DATA_SIZE_2; i++) {
			_DATA_VERSION_3[i] = (byte)i;
		}
	}

	private CaptureAppender _captureAppender;
	private long _contractDLFileEntryTypeId;
	private DLFileVersion _fileVersion;

	@DeleteAfterTestRun
	private Group _group;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private ServiceContext _serviceContext;
	private Folder parentFolder;

}