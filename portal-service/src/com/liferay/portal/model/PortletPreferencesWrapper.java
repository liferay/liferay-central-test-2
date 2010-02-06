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
 * <a href="PortletPreferencesSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PortletPreferences}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferences
 * @generated
 */
public class PortletPreferencesWrapper implements PortletPreferences {
	public PortletPreferencesWrapper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	public long getPrimaryKey() {
		return _portletPreferences.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_portletPreferences.setPrimaryKey(pk);
	}

	public long getPortletPreferencesId() {
		return _portletPreferences.getPortletPreferencesId();
	}

	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferences.setPortletPreferencesId(portletPreferencesId);
	}

	public long getOwnerId() {
		return _portletPreferences.getOwnerId();
	}

	public void setOwnerId(long ownerId) {
		_portletPreferences.setOwnerId(ownerId);
	}

	public int getOwnerType() {
		return _portletPreferences.getOwnerType();
	}

	public void setOwnerType(int ownerType) {
		_portletPreferences.setOwnerType(ownerType);
	}

	public long getPlid() {
		return _portletPreferences.getPlid();
	}

	public void setPlid(long plid) {
		_portletPreferences.setPlid(plid);
	}

	public java.lang.String getPortletId() {
		return _portletPreferences.getPortletId();
	}

	public void setPortletId(java.lang.String portletId) {
		_portletPreferences.setPortletId(portletId);
	}

	public java.lang.String getPreferences() {
		return _portletPreferences.getPreferences();
	}

	public void setPreferences(java.lang.String preferences) {
		_portletPreferences.setPreferences(preferences);
	}

	public com.liferay.portal.model.PortletPreferences toEscapedModel() {
		return _portletPreferences.toEscapedModel();
	}

	public boolean isNew() {
		return _portletPreferences.isNew();
	}

	public boolean setNew(boolean n) {
		return _portletPreferences.setNew(n);
	}

	public boolean isCachedModel() {
		return _portletPreferences.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portletPreferences.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portletPreferences.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_portletPreferences.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portletPreferences.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portletPreferences.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portletPreferences.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _portletPreferences.clone();
	}

	public int compareTo(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferences.compareTo(portletPreferences);
	}

	public int hashCode() {
		return _portletPreferences.hashCode();
	}

	public java.lang.String toString() {
		return _portletPreferences.toString();
	}

	public java.lang.String toXmlString() {
		return _portletPreferences.toXmlString();
	}

	public PortletPreferences getWrappedPortletPreferences() {
		return _portletPreferences;
	}

	private PortletPreferences _portletPreferences;
}