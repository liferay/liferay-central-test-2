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
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoDefinitionVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionVersion
 * @generated
 */
@ProviderType
public class KaleoDefinitionVersionCacheModel implements CacheModel<KaleoDefinitionVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoDefinitionVersionCacheModel)) {
			return false;
		}

		KaleoDefinitionVersionCacheModel kaleoDefinitionVersionCacheModel = (KaleoDefinitionVersionCacheModel)obj;

		if (kaleoDefinitionVersionId == kaleoDefinitionVersionCacheModel.kaleoDefinitionVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, kaleoDefinitionVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", content=");
		sb.append(content);
		sb.append(", version=");
		sb.append(version);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoDefinitionVersion toEntityModel() {
		KaleoDefinitionVersionImpl kaleoDefinitionVersionImpl = new KaleoDefinitionVersionImpl();

		kaleoDefinitionVersionImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoDefinitionVersionImpl.setGroupId(groupId);
		kaleoDefinitionVersionImpl.setCompanyId(companyId);
		kaleoDefinitionVersionImpl.setUserId(userId);

		if (userName == null) {
			kaleoDefinitionVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setUserName(userName);
		}

		kaleoDefinitionVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			kaleoDefinitionVersionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			kaleoDefinitionVersionImpl.setStatusDate(null);
		}
		else {
			kaleoDefinitionVersionImpl.setStatusDate(new Date(statusDate));
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoDefinitionVersionImpl.setCreateDate(null);
		}
		else {
			kaleoDefinitionVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoDefinitionVersionImpl.setModifiedDate(null);
		}
		else {
			kaleoDefinitionVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			kaleoDefinitionVersionImpl.setName(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setName(name);
		}

		if (title == null) {
			kaleoDefinitionVersionImpl.setTitle(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setTitle(title);
		}

		if (description == null) {
			kaleoDefinitionVersionImpl.setDescription(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setDescription(description);
		}

		if (content == null) {
			kaleoDefinitionVersionImpl.setContent(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setContent(content);
		}

		if (version == null) {
			kaleoDefinitionVersionImpl.setVersion(StringPool.BLANK);
		}
		else {
			kaleoDefinitionVersionImpl.setVersion(version);
		}

		kaleoDefinitionVersionImpl.setStatus(status);

		kaleoDefinitionVersionImpl.resetOriginalValues();

		return kaleoDefinitionVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		kaleoDefinitionVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		content = objectInput.readUTF();
		version = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (content == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(content);
		}

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeInt(status);
	}

	public long kaleoDefinitionVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String title;
	public String description;
	public String content;
	public String version;
	public int status;
}