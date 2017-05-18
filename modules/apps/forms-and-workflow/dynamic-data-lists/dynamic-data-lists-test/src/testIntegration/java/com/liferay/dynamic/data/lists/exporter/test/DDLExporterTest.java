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

package com.liferay.dynamic.data.lists.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.exporter.DDLExporterFactory;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Renato Rego
 */
@RunWith(Arquillian.class)
@Sync
public class DDLExporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_availableLocales = DDMFormTestUtil.createAvailableLocales(Locale.US);
		_defaultLocale = Locale.US;
		_group = GroupTestUtil.addGroup();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		setUpDDLExporterFactory();
		setUpDDMFormFieldDataTypes();
		setUpDDMFormFieldValues();
		setUpPermissionChecker();
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testCSVExport() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		createDDMFormFields(ddmForm);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		createDDMFormFieldValues(ddmFormValues);

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		DDLExporter ddlExporter = _ddlExporterFactory.getDDLExporter("csv");

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(bytes);
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(byteArrayInputStream))) {

			String header = bufferedReader.readLine();

			Assert.assertEquals(
				"Field0,Field1,Field2,Field3,Field4,Field5,Field6,Field7," +
					"Field8,Field9,Field10,Field11,Field12,Status," +
						"Modified Date,Author",
				header);

			StringBundler sb = new StringBundler(31);

			sb.append("No");
			sb.append(CharPool.COMMA);

			sb.append("1/1/70");
			sb.append(CharPool.COMMA);

			sb.append("1");
			sb.append(CharPool.COMMA);

			sb.append("file.txt");
			sb.append(CharPool.COMMA);

			sb.append("\"Latitude: -8.035, Longitude: -34.918\"");
			sb.append(CharPool.COMMA);

			sb.append("2");
			sb.append(CharPool.COMMA);

			sb.append("Link to Page content");
			sb.append(CharPool.COMMA);

			sb.append("3");
			sb.append(CharPool.COMMA);

			sb.append("Option 1");
			sb.append(CharPool.COMMA);

			sb.append("Option 1");
			sb.append(CharPool.COMMA);

			sb.append("Text content");
			sb.append(CharPool.COMMA);

			sb.append("Text Area content");
			sb.append(CharPool.COMMA);

			sb.append("Text HTML content");
			sb.append(CharPool.COMMA);

			sb.append("Approved");
			sb.append(CharPool.COMMA);

			sb.append(formatDate(recordVersion.getStatusDate()));
			sb.append(CharPool.COMMA);

			sb.append(recordVersion.getUserName());

			String data = bufferedReader.readLine();

			Assert.assertEquals(sb.toString(), data);
		}
	}

	@Test
	public void testExportRecordsWithDistinctFields() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field0", false, false, false));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0", new UnlocalizedValue("text0")));

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDLRecord record0 = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecordVersion recordVersion0 = record0.getRecordVersion();

		ddmForm.getDDMFormFields().clear();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field1", false, false, false));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field2", false, false, false));

		ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1", new UnlocalizedValue("text1")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2", new UnlocalizedValue("text2")));

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), _group);

		ddmStructure = ddmStructureTestHelper.updateStructure(
			ddmStructure.getStructureId(), ddmForm);

		recordSet = recordSetTestHelper.updateRecordSet(
			recordSet.getRecordSetId(), ddmStructure);

		recordTestHelper = new DDLRecordTestHelper(_group, recordSet);

		DDLRecord record1 = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecordVersion recordVersion1 = record1.getRecordVersion();

		DDLExporter ddlExporter = _ddlExporterFactory.getDDLExporter("csv");

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(bytes);
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(byteArrayInputStream))) {

			String header = bufferedReader.readLine();

			Assert.assertEquals(
				"field0,field1,field2,Status,Modified Date,Author", header);

			String row2 = bufferedReader.readLine();

			StringBundler sb = new StringBundler(10);

			sb.append(CharPool.COMMA);

			sb.append("text1");
			sb.append(CharPool.COMMA);

			sb.append("text2");
			sb.append(CharPool.COMMA);

			sb.append("Approved");
			sb.append(CharPool.COMMA);

			sb.append(formatDate(recordVersion1.getStatusDate()));
			sb.append(CharPool.COMMA);

			sb.append(recordVersion1.getUserName());

			Assert.assertEquals(sb.toString(), row2);

			String row1 = bufferedReader.readLine();

			sb = new StringBundler(9);

			sb.append("text0");
			sb.append(CharPool.COMMA);

			sb.append(CharPool.COMMA);
			sb.append(CharPool.COMMA);

			sb.append("Approved");
			sb.append(CharPool.COMMA);

			sb.append(formatDate(recordVersion0.getStatusDate()));
			sb.append(CharPool.COMMA);

			sb.append(recordVersion0.getUserName());

			Assert.assertEquals(sb.toString(), row1);
		}
	}

	@Test
	public void testExportWithSpecialCharacters() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field0", false, false, false));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0", new UnlocalizedValue("I'm \"good\"")));

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDLRecord record0 = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecordVersion recordVersion0 = record0.getRecordVersion();

		DDLExporter ddlExporter = _ddlExporterFactory.getDDLExporter("csv");

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(bytes);
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(byteArrayInputStream))) {

			String header = bufferedReader.readLine();

			Assert.assertEquals("field0,Status,Modified Date,Author", header);

			String row0 = bufferedReader.readLine();

			StringBundler sb = new StringBundler(10);

			sb.append("\"I'm \"\"good\"\"\"");
			sb.append(CharPool.COMMA);

			sb.append("Approved");
			sb.append(CharPool.COMMA);

			sb.append(formatDate(recordVersion0.getStatusDate()));
			sb.append(CharPool.COMMA);

			sb.append(recordVersion0.getUserName());

			Assert.assertEquals(sb.toString(), row0);
		}
	}

	@Test
	public void testXLSExport() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		createDDMFormFields(ddmForm);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		createDDMFormFieldValues(ddmFormValues);

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		DDLExporter ddlExporter = _ddlExporterFactory.getDDLExporter("xls");

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(bytes);
			HSSFWorkbook workbook = new HSSFWorkbook(byteArrayInputStream)) {

			Sheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(0);

			Cell cell = null;

			for (int i = 0; i < 13; i++) {
				cell = row.getCell(i);

				Assert.assertEquals("Field" + i, cell.getStringCellValue());
			}

			row = sheet.getRow(1);

			cell = row.getCell(0);

			Assert.assertEquals("No", cell.getStringCellValue());

			cell = row.getCell(1);

			Assert.assertEquals("1/1/70", cell.getStringCellValue());

			cell = row.getCell(2);

			Assert.assertEquals("1", cell.getStringCellValue());

			cell = row.getCell(3);

			Assert.assertEquals("file.txt", cell.getStringCellValue());

			cell = row.getCell(4);

			Assert.assertEquals(
				"Latitude: -8.035, Longitude: -34.918",
				cell.getStringCellValue());

			cell = row.getCell(5);

			Assert.assertEquals("2", cell.getStringCellValue());

			cell = row.getCell(6);

			Assert.assertEquals(
				"Link to Page content", cell.getStringCellValue());

			cell = row.getCell(7);

			Assert.assertEquals("3", cell.getStringCellValue());

			cell = row.getCell(8);

			Assert.assertEquals("Option 1", cell.getStringCellValue());

			cell = row.getCell(9);

			Assert.assertEquals("Option 1", cell.getStringCellValue());

			cell = row.getCell(10);

			Assert.assertEquals("Text content", cell.getStringCellValue());

			cell = row.getCell(11);

			Assert.assertEquals("Text Area content", cell.getStringCellValue());

			cell = row.getCell(12);

			Assert.assertEquals("Text HTML content", cell.getStringCellValue());

			cell = row.getCell(13);

			Assert.assertEquals("Approved", cell.getStringCellValue());

			cell = row.getCell(14);

			Assert.assertEquals(
				formatDate(recordVersion.getStatusDate()),
				cell.getStringCellValue());

			cell = row.getCell(15);

			Assert.assertEquals(
				recordVersion.getUserName(), cell.getStringCellValue());
		}
	}

	@Test
	public void testXMLExport() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		createDDMFormFields(ddmForm);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		createDDMFormFieldValues(ddmFormValues);

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		DDLExporter ddlExporter = _ddlExporterFactory.getDDLExporter("xml");

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Element fieldsElement = rootElement.addElement("fields");

		addFieldElement(fieldsElement, "Field0", "No");
		addFieldElement(fieldsElement, "Field1", "1/1/70");
		addFieldElement(fieldsElement, "Field2", "1");
		addFieldElement(fieldsElement, "Field3", "file.txt");
		addFieldElement(
			fieldsElement, "Field4", "Latitude: -8.035, Longitude: -34.918");
		addFieldElement(fieldsElement, "Field5", "2");
		addFieldElement(fieldsElement, "Field6", "Link to Page content");
		addFieldElement(fieldsElement, "Field7", "3");
		addFieldElement(fieldsElement, "Field8", "Option 1");
		addFieldElement(fieldsElement, "Field9", "Option 1");
		addFieldElement(fieldsElement, "Field10", "Text content");
		addFieldElement(fieldsElement, "Field11", "Text Area content");
		addFieldElement(fieldsElement, "Field12", "Text HTML content");
		addFieldElement(fieldsElement, "Status", "Approved");
		addFieldElement(
			fieldsElement, "Modified Date",
			formatDate(recordVersion.getStatusDate()));
		addFieldElement(fieldsElement, "Author", recordVersion.getUserName());

		String xml = document.asXML();

		Assert.assertEquals(new String(bytes), xml);
	}

	protected void addFieldElement(
		Element fieldsElement, String label, Serializable value) {

		Element fieldElement = fieldsElement.addElement("field");

		Element labelElement = fieldElement.addElement("label");

		labelElement.addText(label);

		Element valueElement = fieldElement.addElement("value");

		valueElement.addText(String.valueOf(value));
	}

	protected DDMFormField createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			name, name, type, dataType, true, false, false);

		if (type.equals("radio") || type.equals("select")) {
			setDDMFormFieldOptions(ddmFormField, 3);
		}

		return ddmFormField;
	}

	protected void createDDMFormFields(DDMForm ddmForm) {
		for (DDMFormFieldType ddmFormFieldType : DDMFormFieldType.values()) {
			String fieldName = "Field" + ddmFormFieldType.ordinal();

			ddmForm.addDDMFormField(
				createDDMFormField(
					fieldName, ddmFormFieldType.getValue(),
					_ddmFormFieldDataTypes.get(ddmFormFieldType)));
		}
	}

	protected Value createDDMFormFieldValue(String valueString) {
		Value value = new LocalizedValue(_defaultLocale);

		value.addString(_defaultLocale, valueString);

		return value;
	}

	protected void createDDMFormFieldValues(DDMFormValues ddmFormValues) {
		for (DDMFormFieldType type : DDMFormFieldType.values()) {
			String fieldName = "Field" + type.ordinal();

			Value fieldValue = createDDMFormFieldValue(_fieldValues.get(type));

			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					fieldName, fieldValue));
		}
	}

	protected String createDocumentLibraryDDMFormFieldValue() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "file.txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("name", fileEntry.getTitle());
		jsonObject.put("tempFile", "false");
		jsonObject.put("title", fileEntry.getTitle());
		jsonObject.put("uuid", fileEntry.getUuid());

		return jsonObject.toString();
	}

	protected String createGeolocationDDMFormFieldValue() throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("latitude", "-8.035");
		jsonObject.put("longitude", "-34.918");

		return jsonObject.toString();
	}

	protected String createLinkToPageDDMFormFieldValue() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Link to Page content", false);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", layout.getGroupId());
		jsonObject.put("layoutId", layout.getLayoutId());
		jsonObject.put("privateLayout", layout.getPrivateLayout());

		return jsonObject.toString();
	}

	protected String createListDDMFormFieldValue() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put("Value 1");

		return jsonArray.toString();
	}

	protected String formatDate(Date date) {
		DateTimeFormatter dateTimeFormatter =
			DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

		dateTimeFormatter = dateTimeFormatter.withLocale(_defaultLocale);

		LocalDateTime localDateTime = LocalDateTime.ofInstant(
			date.toInstant(), ZoneId.systemDefault());

		return dateTimeFormatter.format(localDateTime);
	}

	protected void setDDMFormFieldOptions(
		DDMFormField ddmFormField, int availableOptions) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 1; i <= availableOptions; i++) {
			ddmFormFieldOptions.addOptionLabel(
				"Value " + i, LocaleUtil.US, "Option " + i);
		}

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

	protected void setUpDDLExporterFactory() {
		Registry registry = RegistryUtil.getRegistry();

		_ddlExporterFactory = registry.getService(DDLExporterFactory.class);
	}

	protected Map<DDMFormFieldType, String> setUpDDMFormFieldDataTypes() {
		_ddmFormFieldDataTypes = new HashMap<>();

		_ddmFormFieldDataTypes.put(DDMFormFieldType.CHECKBOX, "boolean");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.DATE, "date");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.DECIMAL, "double");
		_ddmFormFieldDataTypes.put(
			DDMFormFieldType.DOCUMENT_LIBRARY, "document-library");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.GEOLOCATION, "geolocation");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.INTEGER, "integer");
		_ddmFormFieldDataTypes.put(
			DDMFormFieldType.LINK_TO_PAGE, "link-to-page");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.NUMBER, "number");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.RADIO, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.SELECT, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.TEXT, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.TEXT_AREA, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.TEXT_HTML, "html");

		return _ddmFormFieldDataTypes;
	}

	protected Map<DDMFormFieldType, String> setUpDDMFormFieldValues()
		throws Exception {

		_fieldValues = new HashMap<>();

		_fieldValues.put(DDMFormFieldType.CHECKBOX, "false");
		_fieldValues.put(DDMFormFieldType.DATE, "1970-01-01");
		_fieldValues.put(DDMFormFieldType.DECIMAL, "1.0");
		_fieldValues.put(
			DDMFormFieldType.DOCUMENT_LIBRARY,
			createDocumentLibraryDDMFormFieldValue());
		_fieldValues.put(
			DDMFormFieldType.GEOLOCATION, createGeolocationDDMFormFieldValue());
		_fieldValues.put(DDMFormFieldType.INTEGER, "2");
		_fieldValues.put(
			DDMFormFieldType.LINK_TO_PAGE, createLinkToPageDDMFormFieldValue());
		_fieldValues.put(DDMFormFieldType.NUMBER, "3");
		_fieldValues.put(DDMFormFieldType.RADIO, createListDDMFormFieldValue());
		_fieldValues.put(
			DDMFormFieldType.SELECT, createListDDMFormFieldValue());
		_fieldValues.put(DDMFormFieldType.TEXT, "Text content");
		_fieldValues.put(DDMFormFieldType.TEXT_AREA, "Text Area content");
		_fieldValues.put(DDMFormFieldType.TEXT_HTML, "Text HTML content");

		return _fieldValues;
	}

	protected void setUpPermissionChecker() throws Exception {
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

			});
	}

	private Set<Locale> _availableLocales;
	private DDLExporterFactory _ddlExporterFactory;
	private Map<DDMFormFieldType, String> _ddmFormFieldDataTypes;
	private Locale _defaultLocale;
	private Map<DDMFormFieldType, String> _fieldValues;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	private enum DDMFormFieldType {

		CHECKBOX("checkbox"), DATE("ddm-date"), DECIMAL("ddm-decimal"),
		DOCUMENT_LIBRARY("ddm-documentlibrary"), GEOLOCATION("ddm-geolocation"),
		INTEGER("ddm-integer"), LINK_TO_PAGE("ddm-link-to-page"),
		NUMBER("ddm-number"), RADIO("radio"), SELECT("select"), TEXT("text"),
		TEXT_AREA("textarea"), TEXT_HTML("ddm-text-html");

		public String getValue() {
			return _value;
		}

		private DDMFormFieldType(String value) {
			_value = value;
		}

		private final String _value;

	}

}