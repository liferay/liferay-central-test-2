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
 * <a href="ServiceComponentSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ServiceComponent}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponent
 * @generated
 */
public class ServiceComponentWrapper implements ServiceComponent {
	public ServiceComponentWrapper(ServiceComponent serviceComponent) {
		_serviceComponent = serviceComponent;
	}

	public long getPrimaryKey() {
		return _serviceComponent.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_serviceComponent.setPrimaryKey(pk);
	}

	public long getServiceComponentId() {
		return _serviceComponent.getServiceComponentId();
	}

	public void setServiceComponentId(long serviceComponentId) {
		_serviceComponent.setServiceComponentId(serviceComponentId);
	}

	public java.lang.String getBuildNamespace() {
		return _serviceComponent.getBuildNamespace();
	}

	public void setBuildNamespace(java.lang.String buildNamespace) {
		_serviceComponent.setBuildNamespace(buildNamespace);
	}

	public long getBuildNumber() {
		return _serviceComponent.getBuildNumber();
	}

	public void setBuildNumber(long buildNumber) {
		_serviceComponent.setBuildNumber(buildNumber);
	}

	public long getBuildDate() {
		return _serviceComponent.getBuildDate();
	}

	public void setBuildDate(long buildDate) {
		_serviceComponent.setBuildDate(buildDate);
	}

	public java.lang.String getData() {
		return _serviceComponent.getData();
	}

	public void setData(java.lang.String data) {
		_serviceComponent.setData(data);
	}

	public com.liferay.portal.model.ServiceComponent toEscapedModel() {
		return _serviceComponent.toEscapedModel();
	}

	public boolean isNew() {
		return _serviceComponent.isNew();
	}

	public boolean setNew(boolean n) {
		return _serviceComponent.setNew(n);
	}

	public boolean isCachedModel() {
		return _serviceComponent.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_serviceComponent.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _serviceComponent.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_serviceComponent.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _serviceComponent.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _serviceComponent.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_serviceComponent.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _serviceComponent.clone();
	}

	public int compareTo(
		com.liferay.portal.model.ServiceComponent serviceComponent) {
		return _serviceComponent.compareTo(serviceComponent);
	}

	public int hashCode() {
		return _serviceComponent.hashCode();
	}

	public java.lang.String toString() {
		return _serviceComponent.toString();
	}

	public java.lang.String toXmlString() {
		return _serviceComponent.toXmlString();
	}

	public java.lang.String getTablesSQL() {
		return _serviceComponent.getTablesSQL();
	}

	public java.lang.String getSequencesSQL() {
		return _serviceComponent.getSequencesSQL();
	}

	public java.lang.String getIndexesSQL() {
		return _serviceComponent.getIndexesSQL();
	}

	public ServiceComponent getWrappedServiceComponent() {
		return _serviceComponent;
	}

	private ServiceComponent _serviceComponent;
}