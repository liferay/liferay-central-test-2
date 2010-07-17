/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link Shard}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Shard
 * @generated
 */
public class ShardWrapper implements Shard {
	public ShardWrapper(Shard shard) {
		_shard = shard;
	}

	public long getPrimaryKey() {
		return _shard.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shard.setPrimaryKey(pk);
	}

	public long getShardId() {
		return _shard.getShardId();
	}

	public void setShardId(long shardId) {
		_shard.setShardId(shardId);
	}

	public java.lang.String getClassName() {
		return _shard.getClassName();
	}

	public long getClassNameId() {
		return _shard.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_shard.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _shard.getClassPK();
	}

	public void setClassPK(long classPK) {
		_shard.setClassPK(classPK);
	}

	public java.lang.String getName() {
		return _shard.getName();
	}

	public void setName(java.lang.String name) {
		_shard.setName(name);
	}

	public com.liferay.portal.model.Shard toEscapedModel() {
		return _shard.toEscapedModel();
	}

	public boolean isNew() {
		return _shard.isNew();
	}

	public void setNew(boolean n) {
		_shard.setNew(n);
	}

	public boolean isCachedModel() {
		return _shard.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shard.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shard.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shard.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shard.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shard.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shard.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _shard.clone();
	}

	public int compareTo(com.liferay.portal.model.Shard shard) {
		return _shard.compareTo(shard);
	}

	public int hashCode() {
		return _shard.hashCode();
	}

	public java.lang.String toString() {
		return _shard.toString();
	}

	public java.lang.String toXmlString() {
		return _shard.toXmlString();
	}

	public Shard getWrappedShard() {
		return _shard;
	}

	private Shard _shard;
}