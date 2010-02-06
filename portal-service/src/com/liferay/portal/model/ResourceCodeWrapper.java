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
 * <a href="ResourceCodeSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceCode}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCode
 * @generated
 */
public class ResourceCodeWrapper implements ResourceCode {
	public ResourceCodeWrapper(ResourceCode resourceCode) {
		_resourceCode = resourceCode;
	}

	public long getPrimaryKey() {
		return _resourceCode.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_resourceCode.setPrimaryKey(pk);
	}

	public long getCodeId() {
		return _resourceCode.getCodeId();
	}

	public void setCodeId(long codeId) {
		_resourceCode.setCodeId(codeId);
	}

	public long getCompanyId() {
		return _resourceCode.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_resourceCode.setCompanyId(companyId);
	}

	public java.lang.String getName() {
		return _resourceCode.getName();
	}

	public void setName(java.lang.String name) {
		_resourceCode.setName(name);
	}

	public int getScope() {
		return _resourceCode.getScope();
	}

	public void setScope(int scope) {
		_resourceCode.setScope(scope);
	}

	public com.liferay.portal.model.ResourceCode toEscapedModel() {
		return _resourceCode.toEscapedModel();
	}

	public boolean isNew() {
		return _resourceCode.isNew();
	}

	public boolean setNew(boolean n) {
		return _resourceCode.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourceCode.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourceCode.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourceCode.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resourceCode.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceCode.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceCode.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceCode.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _resourceCode.clone();
	}

	public int compareTo(com.liferay.portal.model.ResourceCode resourceCode) {
		return _resourceCode.compareTo(resourceCode);
	}

	public int hashCode() {
		return _resourceCode.hashCode();
	}

	public java.lang.String toString() {
		return _resourceCode.toString();
	}

	public java.lang.String toXmlString() {
		return _resourceCode.toXmlString();
	}

	public ResourceCode getWrappedResourceCode() {
		return _resourceCode;
	}

	private ResourceCode _resourceCode;
}