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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTaskFormInstance in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstance
 * @generated
 */
@ProviderType
public class KaleoTaskFormInstanceCacheModel implements CacheModel<KaleoTaskFormInstance>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskFormInstanceCacheModel)) {
			return false;
		}

		KaleoTaskFormInstanceCacheModel kaleoTaskFormInstanceCacheModel = (KaleoTaskFormInstanceCacheModel)obj;

		if (kaleoTaskFormInstanceId == kaleoTaskFormInstanceCacheModel.kaleoTaskFormInstanceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, kaleoTaskFormInstanceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{kaleoTaskFormInstanceId=");
		sb.append(kaleoTaskFormInstanceId);
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
		sb.append(", kaleoInstanceId=");
		sb.append(kaleoInstanceId);
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);
		sb.append(", kaleoTaskFormId=");
		sb.append(kaleoTaskFormId);
		sb.append(", formValues=");
		sb.append(formValues);
		sb.append(", formValueEntryGroupId=");
		sb.append(formValueEntryGroupId);
		sb.append(", formValueEntryId=");
		sb.append(formValueEntryId);
		sb.append(", formValueEntryUuid=");
		sb.append(formValueEntryUuid);
		sb.append(", metadata=");
		sb.append(metadata);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTaskFormInstance toEntityModel() {
		KaleoTaskFormInstanceImpl kaleoTaskFormInstanceImpl = new KaleoTaskFormInstanceImpl();

		kaleoTaskFormInstanceImpl.setKaleoTaskFormInstanceId(kaleoTaskFormInstanceId);
		kaleoTaskFormInstanceImpl.setGroupId(groupId);
		kaleoTaskFormInstanceImpl.setCompanyId(companyId);
		kaleoTaskFormInstanceImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskFormInstanceImpl.setUserName(StringPool.BLANK);
		}
		else {
			kaleoTaskFormInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskFormInstanceImpl.setCreateDate(null);
		}
		else {
			kaleoTaskFormInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskFormInstanceImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskFormInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTaskFormInstanceImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTaskFormInstanceImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoTaskFormInstanceImpl.setKaleoTaskId(kaleoTaskId);
		kaleoTaskFormInstanceImpl.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
		kaleoTaskFormInstanceImpl.setKaleoTaskFormId(kaleoTaskFormId);

		if (formValues == null) {
			kaleoTaskFormInstanceImpl.setFormValues(StringPool.BLANK);
		}
		else {
			kaleoTaskFormInstanceImpl.setFormValues(formValues);
		}

		kaleoTaskFormInstanceImpl.setFormValueEntryGroupId(formValueEntryGroupId);
		kaleoTaskFormInstanceImpl.setFormValueEntryId(formValueEntryId);

		if (formValueEntryUuid == null) {
			kaleoTaskFormInstanceImpl.setFormValueEntryUuid(StringPool.BLANK);
		}
		else {
			kaleoTaskFormInstanceImpl.setFormValueEntryUuid(formValueEntryUuid);
		}

		if (metadata == null) {
			kaleoTaskFormInstanceImpl.setMetadata(StringPool.BLANK);
		}
		else {
			kaleoTaskFormInstanceImpl.setMetadata(metadata);
		}

		kaleoTaskFormInstanceImpl.resetOriginalValues();

		return kaleoTaskFormInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		kaleoTaskFormInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();

		kaleoTaskInstanceTokenId = objectInput.readLong();

		kaleoTaskFormId = objectInput.readLong();
		formValues = objectInput.readUTF();

		formValueEntryGroupId = objectInput.readLong();

		formValueEntryId = objectInput.readLong();
		formValueEntryUuid = objectInput.readUTF();
		metadata = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(kaleoTaskFormInstanceId);

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

		objectOutput.writeLong(kaleoInstanceId);

		objectOutput.writeLong(kaleoTaskId);

		objectOutput.writeLong(kaleoTaskInstanceTokenId);

		objectOutput.writeLong(kaleoTaskFormId);

		if (formValues == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(formValues);
		}

		objectOutput.writeLong(formValueEntryGroupId);

		objectOutput.writeLong(formValueEntryId);

		if (formValueEntryUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(formValueEntryUuid);
		}

		if (metadata == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(metadata);
		}
	}

	public long kaleoTaskFormInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoInstanceId;
	public long kaleoTaskId;
	public long kaleoTaskInstanceTokenId;
	public long kaleoTaskFormId;
	public String formValues;
	public long formValueEntryGroupId;
	public long formValueEntryId;
	public String formValueEntryUuid;
	public String metadata;
}