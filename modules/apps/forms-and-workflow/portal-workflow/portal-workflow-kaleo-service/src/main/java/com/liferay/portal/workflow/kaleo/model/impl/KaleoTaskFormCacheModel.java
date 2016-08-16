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

package com.liferay.portal.workflow.kaleo.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTaskForm in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskForm
 * @generated
 */
@ProviderType
public class KaleoTaskFormCacheModel implements CacheModel<KaleoTaskForm>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskFormCacheModel)) {
			return false;
		}

		KaleoTaskFormCacheModel kaleoTaskFormCacheModel = (KaleoTaskFormCacheModel)obj;

		if (kaleoTaskFormId == kaleoTaskFormCacheModel.kaleoTaskFormId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, kaleoTaskFormId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{kaleoTaskFormId=");
		sb.append(kaleoTaskFormId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoNodeId=");
		sb.append(kaleoNodeId);
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", kaleoTaskName=");
		sb.append(kaleoTaskName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", formCompanyId=");
		sb.append(formCompanyId);
		sb.append(", formDefinition=");
		sb.append(formDefinition);
		sb.append(", formGroupId=");
		sb.append(formGroupId);
		sb.append(", formId=");
		sb.append(formId);
		sb.append(", formUuid=");
		sb.append(formUuid);
		sb.append(", metadata=");
		sb.append(metadata);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTaskForm toEntityModel() {
		KaleoTaskFormImpl kaleoTaskFormImpl = new KaleoTaskFormImpl();

		kaleoTaskFormImpl.setKaleoTaskFormId(kaleoTaskFormId);
		kaleoTaskFormImpl.setGroupId(groupId);
		kaleoTaskFormImpl.setCompanyId(companyId);
		kaleoTaskFormImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskFormImpl.setUserName(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskFormImpl.setCreateDate(null);
		}
		else {
			kaleoTaskFormImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskFormImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskFormImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTaskFormImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTaskFormImpl.setKaleoNodeId(kaleoNodeId);
		kaleoTaskFormImpl.setKaleoTaskId(kaleoTaskId);

		if (kaleoTaskName == null) {
			kaleoTaskFormImpl.setKaleoTaskName(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setKaleoTaskName(kaleoTaskName);
		}

		if (name == null) {
			kaleoTaskFormImpl.setName(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setName(name);
		}

		if (description == null) {
			kaleoTaskFormImpl.setDescription(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setDescription(description);
		}

		kaleoTaskFormImpl.setFormCompanyId(formCompanyId);

		if (formDefinition == null) {
			kaleoTaskFormImpl.setFormDefinition(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setFormDefinition(formDefinition);
		}

		kaleoTaskFormImpl.setFormGroupId(formGroupId);
		kaleoTaskFormImpl.setFormId(formId);

		if (formUuid == null) {
			kaleoTaskFormImpl.setFormUuid(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setFormUuid(formUuid);
		}

		if (metadata == null) {
			kaleoTaskFormImpl.setMetadata(StringPool.BLANK);
		}
		else {
			kaleoTaskFormImpl.setMetadata(metadata);
		}

		kaleoTaskFormImpl.setPriority(priority);

		kaleoTaskFormImpl.resetOriginalValues();

		return kaleoTaskFormImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		kaleoTaskFormId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoNodeId = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();
		kaleoTaskName = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		formCompanyId = objectInput.readLong();
		formDefinition = objectInput.readUTF();

		formGroupId = objectInput.readLong();

		formId = objectInput.readLong();
		formUuid = objectInput.readUTF();
		metadata = objectInput.readUTF();

		priority = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(kaleoTaskFormId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(kaleoDefinitionId);

		objectOutput.writeLong(kaleoNodeId);

		objectOutput.writeLong(kaleoTaskId);

		if (kaleoTaskName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(kaleoTaskName);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(formCompanyId);

		if (formDefinition == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(formDefinition);
		}

		objectOutput.writeLong(formGroupId);

		objectOutput.writeLong(formId);

		if (formUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(formUuid);
		}

		if (metadata == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(metadata);
		}

		objectOutput.writeInt(priority);
	}

	public long kaleoTaskFormId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoNodeId;
	public long kaleoTaskId;
	public String kaleoTaskName;
	public String name;
	public String description;
	public long formCompanyId;
	public String formDefinition;
	public long formGroupId;
	public long formId;
	public String formUuid;
	public String metadata;
	public int priority;
}