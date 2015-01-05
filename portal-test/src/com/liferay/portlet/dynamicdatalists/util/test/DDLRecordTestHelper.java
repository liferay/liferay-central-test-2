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

package com.liferay.portlet.dynamicdatalists.util.test;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordTestHelper {

	public DDLRecordTestHelper(Group group, DDLRecordSet recordSet)
		throws Exception {

		_group = group;
		_recordSet = recordSet;
	}

	public DDLRecord addRecord() throws Exception {
		DDMStructure ddmStructure = _recordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormValues ddmFormValues = DDLRecordTestUtil.createDDMFormValues(
			ddmForm, DDLRecordTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (ddmFormField.isLocalizable()) {
				ddmFormValues.addDDMFormFieldValue(
					DDLRecordTestUtil.createLocalizedTextDDMFormFieldValue(
						ddmFormField.getName(), RandomTestUtil.randomString()));
			}
			else {
				ddmFormValues.addDDMFormFieldValue(
					DDLRecordTestUtil.createUnlocalizedTextDDMFormFieldValue(
						ddmFormField.getName(), RandomTestUtil.randomString()));
			}
		}

		return addRecord(ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
	}

	public DDLRecord addRecord(DDMFormValues ddmFormValues, int workflowAction)
		throws Exception {

		ServiceContext serviceContext = DDLRecordTestUtil.getServiceContext(
			workflowAction);

		return DDLRecordLocalServiceUtil.addRecord(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_recordSet.getRecordSetId(),
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			serviceContext);
	}

	public DDLRecordSet getRecordSet() {
		return _recordSet;
	}

	private final Group _group;
	private final DDLRecordSet _recordSet;

}