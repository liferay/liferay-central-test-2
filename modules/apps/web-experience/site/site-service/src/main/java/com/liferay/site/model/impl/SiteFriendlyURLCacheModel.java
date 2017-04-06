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

package com.liferay.site.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.site.model.SiteFriendlyURL;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SiteFriendlyURL in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURL
 * @generated
 */
@ProviderType
public class SiteFriendlyURLCacheModel implements CacheModel<SiteFriendlyURL>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SiteFriendlyURLCacheModel)) {
			return false;
		}

		SiteFriendlyURLCacheModel siteFriendlyURLCacheModel = (SiteFriendlyURLCacheModel)obj;

		if (siteFriendlyURLId == siteFriendlyURLCacheModel.siteFriendlyURLId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, siteFriendlyURLId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", siteFriendlyURLId=");
		sb.append(siteFriendlyURLId);
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
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SiteFriendlyURL toEntityModel() {
		SiteFriendlyURLImpl siteFriendlyURLImpl = new SiteFriendlyURLImpl();

		if (uuid == null) {
			siteFriendlyURLImpl.setUuid(StringPool.BLANK);
		}
		else {
			siteFriendlyURLImpl.setUuid(uuid);
		}

		siteFriendlyURLImpl.setSiteFriendlyURLId(siteFriendlyURLId);
		siteFriendlyURLImpl.setCompanyId(companyId);
		siteFriendlyURLImpl.setUserId(userId);

		if (userName == null) {
			siteFriendlyURLImpl.setUserName(StringPool.BLANK);
		}
		else {
			siteFriendlyURLImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			siteFriendlyURLImpl.setCreateDate(null);
		}
		else {
			siteFriendlyURLImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			siteFriendlyURLImpl.setModifiedDate(null);
		}
		else {
			siteFriendlyURLImpl.setModifiedDate(new Date(modifiedDate));
		}

		siteFriendlyURLImpl.setGroupId(groupId);

		if (friendlyURL == null) {
			siteFriendlyURLImpl.setFriendlyURL(StringPool.BLANK);
		}
		else {
			siteFriendlyURLImpl.setFriendlyURL(friendlyURL);
		}

		if (languageId == null) {
			siteFriendlyURLImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			siteFriendlyURLImpl.setLanguageId(languageId);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			siteFriendlyURLImpl.setLastPublishDate(null);
		}
		else {
			siteFriendlyURLImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		siteFriendlyURLImpl.resetOriginalValues();

		return siteFriendlyURLImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		siteFriendlyURLId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		groupId = objectInput.readLong();
		friendlyURL = objectInput.readUTF();
		languageId = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(siteFriendlyURLId);

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

		objectOutput.writeLong(groupId);

		if (friendlyURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long siteFriendlyURLId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long groupId;
	public String friendlyURL;
	public String languageId;
	public long lastPublishDate;
}