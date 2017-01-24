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

package com.liferay.adaptive.media.image.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AdaptiveMediaImage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImage
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageCacheModel implements CacheModel<AdaptiveMediaImage>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AdaptiveMediaImageCacheModel)) {
			return false;
		}

		AdaptiveMediaImageCacheModel adaptiveMediaImageCacheModel = (AdaptiveMediaImageCacheModel)obj;

		if (adaptiveMediaImageId == adaptiveMediaImageCacheModel.adaptiveMediaImageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, adaptiveMediaImageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", adaptiveMediaImageId=");
		sb.append(adaptiveMediaImageId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", configurationUuid=");
		sb.append(configurationUuid);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AdaptiveMediaImage toEntityModel() {
		AdaptiveMediaImageImpl adaptiveMediaImageImpl = new AdaptiveMediaImageImpl();

		if (uuid == null) {
			adaptiveMediaImageImpl.setUuid(StringPool.BLANK);
		}
		else {
			adaptiveMediaImageImpl.setUuid(uuid);
		}

		adaptiveMediaImageImpl.setAdaptiveMediaImageId(adaptiveMediaImageId);
		adaptiveMediaImageImpl.setGroupId(groupId);
		adaptiveMediaImageImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			adaptiveMediaImageImpl.setCreateDate(null);
		}
		else {
			adaptiveMediaImageImpl.setCreateDate(new Date(createDate));
		}

		if (configurationUuid == null) {
			adaptiveMediaImageImpl.setConfigurationUuid(StringPool.BLANK);
		}
		else {
			adaptiveMediaImageImpl.setConfigurationUuid(configurationUuid);
		}

		adaptiveMediaImageImpl.setFileVersionId(fileVersionId);
		adaptiveMediaImageImpl.setHeight(height);
		adaptiveMediaImageImpl.setWidth(width);
		adaptiveMediaImageImpl.setSize(size);

		adaptiveMediaImageImpl.resetOriginalValues();

		return adaptiveMediaImageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		adaptiveMediaImageId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		configurationUuid = objectInput.readUTF();

		fileVersionId = objectInput.readLong();

		height = objectInput.readInt();

		width = objectInput.readInt();

		size = objectInput.readLong();
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

		objectOutput.writeLong(adaptiveMediaImageId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		if (configurationUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(configurationUuid);
		}

		objectOutput.writeLong(fileVersionId);

		objectOutput.writeInt(height);

		objectOutput.writeInt(width);

		objectOutput.writeLong(size);
	}

	public String uuid;
	public long adaptiveMediaImageId;
	public long groupId;
	public long companyId;
	public long createDate;
	public String configurationUuid;
	public long fileVersionId;
	public int height;
	public int width;
	public long size;
}