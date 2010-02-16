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
 * <a href="ResourceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Resource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Resource
 * @generated
 */
public class ResourceWrapper implements Resource {
	public ResourceWrapper(Resource resource) {
		_resource = resource;
	}

	public long getPrimaryKey() {
		return _resource.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_resource.setPrimaryKey(pk);
	}

	public long getResourceId() {
		return _resource.getResourceId();
	}

	public void setResourceId(long resourceId) {
		_resource.setResourceId(resourceId);
	}

	public long getCodeId() {
		return _resource.getCodeId();
	}

	public void setCodeId(long codeId) {
		_resource.setCodeId(codeId);
	}

	public java.lang.String getPrimKey() {
		return _resource.getPrimKey();
	}

	public void setPrimKey(java.lang.String primKey) {
		_resource.setPrimKey(primKey);
	}

	public com.liferay.portal.model.Resource toEscapedModel() {
		return _resource.toEscapedModel();
	}

	public boolean isNew() {
		return _resource.isNew();
	}

	public boolean setNew(boolean n) {
		return _resource.setNew(n);
	}

	public boolean isCachedModel() {
		return _resource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resource.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resource.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resource.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resource.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _resource.clone();
	}

	public int compareTo(com.liferay.portal.model.Resource resource) {
		return _resource.compareTo(resource);
	}

	public int hashCode() {
		return _resource.hashCode();
	}

	public java.lang.String toString() {
		return _resource.toString();
	}

	public java.lang.String toXmlString() {
		return _resource.toXmlString();
	}

	public long getCompanyId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getCompanyId();
	}

	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getName();
	}

	public int getScope()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getScope();
	}

	public void setCompanyId(long companyId) {
		_resource.setCompanyId(companyId);
	}

	public void setName(java.lang.String name) {
		_resource.setName(name);
	}

	public void setScope(int scope) {
		_resource.setScope(scope);
	}

	public Resource getWrappedResource() {
		return _resource;
	}

	private Resource _resource;
}