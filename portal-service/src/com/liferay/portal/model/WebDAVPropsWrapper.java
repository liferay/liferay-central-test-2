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
 * <a href="WebDAVPropsSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WebDAVProps}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVProps
 * @generated
 */
public class WebDAVPropsWrapper implements WebDAVProps {
	public WebDAVPropsWrapper(WebDAVProps webDAVProps) {
		_webDAVProps = webDAVProps;
	}

	public long getPrimaryKey() {
		return _webDAVProps.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_webDAVProps.setPrimaryKey(pk);
	}

	public long getWebDavPropsId() {
		return _webDAVProps.getWebDavPropsId();
	}

	public void setWebDavPropsId(long webDavPropsId) {
		_webDAVProps.setWebDavPropsId(webDavPropsId);
	}

	public long getCompanyId() {
		return _webDAVProps.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_webDAVProps.setCompanyId(companyId);
	}

	public java.util.Date getCreateDate() {
		return _webDAVProps.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_webDAVProps.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _webDAVProps.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_webDAVProps.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _webDAVProps.getClassName();
	}

	public long getClassNameId() {
		return _webDAVProps.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_webDAVProps.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _webDAVProps.getClassPK();
	}

	public void setClassPK(long classPK) {
		_webDAVProps.setClassPK(classPK);
	}

	public java.lang.String getProps() {
		return _webDAVProps.getProps();
	}

	public void setProps(java.lang.String props) {
		_webDAVProps.setProps(props);
	}

	public com.liferay.portal.model.WebDAVProps toEscapedModel() {
		return _webDAVProps.toEscapedModel();
	}

	public boolean isNew() {
		return _webDAVProps.isNew();
	}

	public boolean setNew(boolean n) {
		return _webDAVProps.setNew(n);
	}

	public boolean isCachedModel() {
		return _webDAVProps.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_webDAVProps.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _webDAVProps.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_webDAVProps.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _webDAVProps.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _webDAVProps.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_webDAVProps.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _webDAVProps.clone();
	}

	public int compareTo(com.liferay.portal.model.WebDAVProps webDAVProps) {
		return _webDAVProps.compareTo(webDAVProps);
	}

	public int hashCode() {
		return _webDAVProps.hashCode();
	}

	public java.lang.String toString() {
		return _webDAVProps.toString();
	}

	public java.lang.String toXmlString() {
		return _webDAVProps.toXmlString();
	}

	public java.util.Set<com.liferay.portal.kernel.util.Tuple> getPropsSet()
		throws java.lang.Exception {
		return _webDAVProps.getPropsSet();
	}

	public java.lang.String getText(java.lang.String name,
		java.lang.String prefix, java.lang.String uri)
		throws java.lang.Exception {
		return _webDAVProps.getText(name, prefix, uri);
	}

	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception {
		_webDAVProps.addProp(name, prefix, uri);
	}

	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri, java.lang.String text) throws java.lang.Exception {
		_webDAVProps.addProp(name, prefix, uri, text);
	}

	public void removeProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception {
		_webDAVProps.removeProp(name, prefix, uri);
	}

	public void store() throws java.lang.Exception {
		_webDAVProps.store();
	}

	public WebDAVProps getWrappedWebDAVProps() {
		return _webDAVProps;
	}

	private WebDAVProps _webDAVProps;
}