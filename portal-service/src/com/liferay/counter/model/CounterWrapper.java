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

package com.liferay.counter.model;

/**
 * <p>
 * This class is a wrapper for {@link Counter}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Counter
 * @generated
 */
public class CounterWrapper implements Counter {
	public CounterWrapper(Counter counter) {
		_counter = counter;
	}

	public java.lang.String getPrimaryKey() {
		return _counter.getPrimaryKey();
	}

	public void setPrimaryKey(java.lang.String pk) {
		_counter.setPrimaryKey(pk);
	}

	public java.lang.String getName() {
		return _counter.getName();
	}

	public void setName(java.lang.String name) {
		_counter.setName(name);
	}

	public long getCurrentId() {
		return _counter.getCurrentId();
	}

	public void setCurrentId(long currentId) {
		_counter.setCurrentId(currentId);
	}

	public com.liferay.counter.model.Counter toEscapedModel() {
		return _counter.toEscapedModel();
	}

	public boolean isNew() {
		return _counter.isNew();
	}

	public void setNew(boolean n) {
		_counter.setNew(n);
	}

	public boolean isCachedModel() {
		return _counter.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_counter.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _counter.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_counter.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _counter.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _counter.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_counter.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _counter.clone();
	}

	public int compareTo(com.liferay.counter.model.Counter counter) {
		return _counter.compareTo(counter);
	}

	public int hashCode() {
		return _counter.hashCode();
	}

	public java.lang.String toString() {
		return _counter.toString();
	}

	public java.lang.String toXmlString() {
		return _counter.toXmlString();
	}

	public Counter getWrappedCounter() {
		return _counter;
	}

	private Counter _counter;
}