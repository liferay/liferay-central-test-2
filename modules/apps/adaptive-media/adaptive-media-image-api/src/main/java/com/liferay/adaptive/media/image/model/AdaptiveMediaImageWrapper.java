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

package com.liferay.adaptive.media.image.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AdaptiveMediaImage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImage
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageWrapper implements AdaptiveMediaImage,
	ModelWrapper<AdaptiveMediaImage> {
	public AdaptiveMediaImageWrapper(AdaptiveMediaImage adaptiveMediaImage) {
		_adaptiveMediaImage = adaptiveMediaImage;
	}

	@Override
	public Class<?> getModelClass() {
		return AdaptiveMediaImage.class;
	}

	@Override
	public String getModelClassName() {
		return AdaptiveMediaImage.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("adaptiveMediaImageId", getAdaptiveMediaImageId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("configurationUuid", getConfigurationUuid());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("mimeType", getMimeType());
		attributes.put("height", getHeight());
		attributes.put("width", getWidth());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long adaptiveMediaImageId = (Long)attributes.get("adaptiveMediaImageId");

		if (adaptiveMediaImageId != null) {
			setAdaptiveMediaImageId(adaptiveMediaImageId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String configurationUuid = (String)attributes.get("configurationUuid");

		if (configurationUuid != null) {
			setConfigurationUuid(configurationUuid);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		Integer height = (Integer)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Integer width = (Integer)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	@Override
	public AdaptiveMediaImage toEscapedModel() {
		return new AdaptiveMediaImageWrapper(_adaptiveMediaImage.toEscapedModel());
	}

	@Override
	public AdaptiveMediaImage toUnescapedModel() {
		return new AdaptiveMediaImageWrapper(_adaptiveMediaImage.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _adaptiveMediaImage.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _adaptiveMediaImage.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _adaptiveMediaImage.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _adaptiveMediaImage.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AdaptiveMediaImage> toCacheModel() {
		return _adaptiveMediaImage.toCacheModel();
	}

	@Override
	public int compareTo(AdaptiveMediaImage adaptiveMediaImage) {
		return _adaptiveMediaImage.compareTo(adaptiveMediaImage);
	}

	/**
	* Returns the height of this adaptive media image.
	*
	* @return the height of this adaptive media image
	*/
	@Override
	public int getHeight() {
		return _adaptiveMediaImage.getHeight();
	}

	/**
	* Returns the width of this adaptive media image.
	*
	* @return the width of this adaptive media image
	*/
	@Override
	public int getWidth() {
		return _adaptiveMediaImage.getWidth();
	}

	@Override
	public int hashCode() {
		return _adaptiveMediaImage.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _adaptiveMediaImage.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new AdaptiveMediaImageWrapper((AdaptiveMediaImage)_adaptiveMediaImage.clone());
	}

	/**
	* Returns the configuration uuid of this adaptive media image.
	*
	* @return the configuration uuid of this adaptive media image
	*/
	@Override
	public java.lang.String getConfigurationUuid() {
		return _adaptiveMediaImage.getConfigurationUuid();
	}

	/**
	* Returns the mime type of this adaptive media image.
	*
	* @return the mime type of this adaptive media image
	*/
	@Override
	public java.lang.String getMimeType() {
		return _adaptiveMediaImage.getMimeType();
	}

	/**
	* Returns the uuid of this adaptive media image.
	*
	* @return the uuid of this adaptive media image
	*/
	@Override
	public java.lang.String getUuid() {
		return _adaptiveMediaImage.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _adaptiveMediaImage.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _adaptiveMediaImage.toXmlString();
	}

	/**
	* Returns the create date of this adaptive media image.
	*
	* @return the create date of this adaptive media image
	*/
	@Override
	public Date getCreateDate() {
		return _adaptiveMediaImage.getCreateDate();
	}

	/**
	* Returns the adaptive media image ID of this adaptive media image.
	*
	* @return the adaptive media image ID of this adaptive media image
	*/
	@Override
	public long getAdaptiveMediaImageId() {
		return _adaptiveMediaImage.getAdaptiveMediaImageId();
	}

	/**
	* Returns the company ID of this adaptive media image.
	*
	* @return the company ID of this adaptive media image
	*/
	@Override
	public long getCompanyId() {
		return _adaptiveMediaImage.getCompanyId();
	}

	/**
	* Returns the file version ID of this adaptive media image.
	*
	* @return the file version ID of this adaptive media image
	*/
	@Override
	public long getFileVersionId() {
		return _adaptiveMediaImage.getFileVersionId();
	}

	/**
	* Returns the group ID of this adaptive media image.
	*
	* @return the group ID of this adaptive media image
	*/
	@Override
	public long getGroupId() {
		return _adaptiveMediaImage.getGroupId();
	}

	/**
	* Returns the primary key of this adaptive media image.
	*
	* @return the primary key of this adaptive media image
	*/
	@Override
	public long getPrimaryKey() {
		return _adaptiveMediaImage.getPrimaryKey();
	}

	/**
	* Returns the size of this adaptive media image.
	*
	* @return the size of this adaptive media image
	*/
	@Override
	public long getSize() {
		return _adaptiveMediaImage.getSize();
	}

	@Override
	public void persist() {
		_adaptiveMediaImage.persist();
	}

	/**
	* Sets the adaptive media image ID of this adaptive media image.
	*
	* @param adaptiveMediaImageId the adaptive media image ID of this adaptive media image
	*/
	@Override
	public void setAdaptiveMediaImageId(long adaptiveMediaImageId) {
		_adaptiveMediaImage.setAdaptiveMediaImageId(adaptiveMediaImageId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_adaptiveMediaImage.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this adaptive media image.
	*
	* @param companyId the company ID of this adaptive media image
	*/
	@Override
	public void setCompanyId(long companyId) {
		_adaptiveMediaImage.setCompanyId(companyId);
	}

	/**
	* Sets the configuration uuid of this adaptive media image.
	*
	* @param configurationUuid the configuration uuid of this adaptive media image
	*/
	@Override
	public void setConfigurationUuid(java.lang.String configurationUuid) {
		_adaptiveMediaImage.setConfigurationUuid(configurationUuid);
	}

	/**
	* Sets the create date of this adaptive media image.
	*
	* @param createDate the create date of this adaptive media image
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_adaptiveMediaImage.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_adaptiveMediaImage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_adaptiveMediaImage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_adaptiveMediaImage.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file version ID of this adaptive media image.
	*
	* @param fileVersionId the file version ID of this adaptive media image
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_adaptiveMediaImage.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the group ID of this adaptive media image.
	*
	* @param groupId the group ID of this adaptive media image
	*/
	@Override
	public void setGroupId(long groupId) {
		_adaptiveMediaImage.setGroupId(groupId);
	}

	/**
	* Sets the height of this adaptive media image.
	*
	* @param height the height of this adaptive media image
	*/
	@Override
	public void setHeight(int height) {
		_adaptiveMediaImage.setHeight(height);
	}

	/**
	* Sets the mime type of this adaptive media image.
	*
	* @param mimeType the mime type of this adaptive media image
	*/
	@Override
	public void setMimeType(java.lang.String mimeType) {
		_adaptiveMediaImage.setMimeType(mimeType);
	}

	@Override
	public void setNew(boolean n) {
		_adaptiveMediaImage.setNew(n);
	}

	/**
	* Sets the primary key of this adaptive media image.
	*
	* @param primaryKey the primary key of this adaptive media image
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_adaptiveMediaImage.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_adaptiveMediaImage.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the size of this adaptive media image.
	*
	* @param size the size of this adaptive media image
	*/
	@Override
	public void setSize(long size) {
		_adaptiveMediaImage.setSize(size);
	}

	/**
	* Sets the uuid of this adaptive media image.
	*
	* @param uuid the uuid of this adaptive media image
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_adaptiveMediaImage.setUuid(uuid);
	}

	/**
	* Sets the width of this adaptive media image.
	*
	* @param width the width of this adaptive media image
	*/
	@Override
	public void setWidth(int width) {
		_adaptiveMediaImage.setWidth(width);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AdaptiveMediaImageWrapper)) {
			return false;
		}

		AdaptiveMediaImageWrapper adaptiveMediaImageWrapper = (AdaptiveMediaImageWrapper)obj;

		if (Objects.equals(_adaptiveMediaImage,
					adaptiveMediaImageWrapper._adaptiveMediaImage)) {
			return true;
		}

		return false;
	}

	@Override
	public AdaptiveMediaImage getWrappedModel() {
		return _adaptiveMediaImage;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _adaptiveMediaImage.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _adaptiveMediaImage.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_adaptiveMediaImage.resetOriginalValues();
	}

	private final AdaptiveMediaImage _adaptiveMediaImage;
}