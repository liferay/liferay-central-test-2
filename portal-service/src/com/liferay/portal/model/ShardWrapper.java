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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Shard}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Shard
 * @generated
 */
@ProviderType
public class ShardWrapper implements Shard, ModelWrapper<Shard> {
	public ShardWrapper(Shard shard) {
		_shard = shard;
	}

	@Override
	public Class<?> getModelClass() {
		return Shard.class;
	}

	@Override
	public String getModelClassName() {
		return Shard.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("shardId", getShardId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long shardId = (Long)attributes.get("shardId");

		if (shardId != null) {
			setShardId(shardId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ShardWrapper((Shard)_shard.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Shard shard) {
		return _shard.compareTo(shard);
	}

	/**
	* Returns the fully qualified class name of this shard.
	*
	* @return the fully qualified class name of this shard
	*/
	@Override
	public java.lang.String getClassName() {
		return _shard.getClassName();
	}

	/**
	* Returns the class name ID of this shard.
	*
	* @return the class name ID of this shard
	*/
	@Override
	public long getClassNameId() {
		return _shard.getClassNameId();
	}

	/**
	* Returns the class p k of this shard.
	*
	* @return the class p k of this shard
	*/
	@Override
	public long getClassPK() {
		return _shard.getClassPK();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shard.getExpandoBridge();
	}

	/**
	* Returns the mvcc version of this shard.
	*
	* @return the mvcc version of this shard
	*/
	@Override
	public long getMvccVersion() {
		return _shard.getMvccVersion();
	}

	/**
	* Returns the name of this shard.
	*
	* @return the name of this shard
	*/
	@Override
	public java.lang.String getName() {
		return _shard.getName();
	}

	/**
	* Returns the primary key of this shard.
	*
	* @return the primary key of this shard
	*/
	@Override
	public long getPrimaryKey() {
		return _shard.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shard.getPrimaryKeyObj();
	}

	/**
	* Returns the shard ID of this shard.
	*
	* @return the shard ID of this shard
	*/
	@Override
	public long getShardId() {
		return _shard.getShardId();
	}

	@Override
	public int hashCode() {
		return _shard.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _shard.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _shard.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _shard.isNew();
	}

	@Override
	public void persist() {
		_shard.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shard.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_shard.setClassName(className);
	}

	/**
	* Sets the class name ID of this shard.
	*
	* @param classNameId the class name ID of this shard
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_shard.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this shard.
	*
	* @param classPK the class p k of this shard
	*/
	@Override
	public void setClassPK(long classPK) {
		_shard.setClassPK(classPK);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shard.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shard.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shard.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this shard.
	*
	* @param mvccVersion the mvcc version of this shard
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_shard.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this shard.
	*
	* @param name the name of this shard
	*/
	@Override
	public void setName(java.lang.String name) {
		_shard.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_shard.setNew(n);
	}

	/**
	* Sets the primary key of this shard.
	*
	* @param primaryKey the primary key of this shard
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shard.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shard.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the shard ID of this shard.
	*
	* @param shardId the shard ID of this shard
	*/
	@Override
	public void setShardId(long shardId) {
		_shard.setShardId(shardId);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Shard> toCacheModel() {
		return _shard.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Shard toEscapedModel() {
		return new ShardWrapper(_shard.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shard.toString();
	}

	@Override
	public com.liferay.portal.model.Shard toUnescapedModel() {
		return new ShardWrapper(_shard.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _shard.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShardWrapper)) {
			return false;
		}

		ShardWrapper shardWrapper = (ShardWrapper)obj;

		if (Validator.equals(_shard, shardWrapper._shard)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Shard getWrappedShard() {
		return _shard;
	}

	@Override
	public Shard getWrappedModel() {
		return _shard;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _shard.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _shard.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_shard.resetOriginalValues();
	}

	private final Shard _shard;
}