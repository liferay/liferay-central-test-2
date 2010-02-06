/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;


/**
 * <a href="ShardSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public boolean setNew(boolean n) {
		return _shard.setNew(n);
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