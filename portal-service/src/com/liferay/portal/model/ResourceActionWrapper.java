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
 * <a href="ResourceActionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceAction}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceAction
 * @generated
 */
public class ResourceActionWrapper implements ResourceAction {
	public ResourceActionWrapper(ResourceAction resourceAction) {
		_resourceAction = resourceAction;
	}

	public long getPrimaryKey() {
		return _resourceAction.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_resourceAction.setPrimaryKey(pk);
	}

	public long getResourceActionId() {
		return _resourceAction.getResourceActionId();
	}

	public void setResourceActionId(long resourceActionId) {
		_resourceAction.setResourceActionId(resourceActionId);
	}

	public java.lang.String getName() {
		return _resourceAction.getName();
	}

	public void setName(java.lang.String name) {
		_resourceAction.setName(name);
	}

	public java.lang.String getActionId() {
		return _resourceAction.getActionId();
	}

	public void setActionId(java.lang.String actionId) {
		_resourceAction.setActionId(actionId);
	}

	public long getBitwiseValue() {
		return _resourceAction.getBitwiseValue();
	}

	public void setBitwiseValue(long bitwiseValue) {
		_resourceAction.setBitwiseValue(bitwiseValue);
	}

	public com.liferay.portal.model.ResourceAction toEscapedModel() {
		return _resourceAction.toEscapedModel();
	}

	public boolean isNew() {
		return _resourceAction.isNew();
	}

	public boolean setNew(boolean n) {
		return _resourceAction.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourceAction.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourceAction.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourceAction.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resourceAction.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceAction.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceAction.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceAction.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _resourceAction.clone();
	}

	public int compareTo(com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceAction.compareTo(resourceAction);
	}

	public int hashCode() {
		return _resourceAction.hashCode();
	}

	public java.lang.String toString() {
		return _resourceAction.toString();
	}

	public java.lang.String toXmlString() {
		return _resourceAction.toXmlString();
	}

	public ResourceAction getWrappedResourceAction() {
		return _resourceAction;
	}

	private ResourceAction _resourceAction;
}