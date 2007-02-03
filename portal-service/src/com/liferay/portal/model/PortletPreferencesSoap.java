/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.PortletPreferencesPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PortletPreferencesSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPreferencesSoap implements Serializable {
	public static PortletPreferencesSoap toSoapModel(PortletPreferences model) {
		PortletPreferencesSoap soapModel = new PortletPreferencesSoap();
		soapModel.setPortletId(model.getPortletId());
		soapModel.setLayoutId(model.getLayoutId());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setPreferences(model.getPreferences());

		return soapModel;
	}

	public static PortletPreferencesSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			PortletPreferences model = (PortletPreferences)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (PortletPreferencesSoap[])soapModels.toArray(new PortletPreferencesSoap[0]);
	}

	public PortletPreferencesSoap() {
	}

	public PortletPreferencesPK getPrimaryKey() {
		return new PortletPreferencesPK(_portletId, _layoutId, _ownerId);
	}

	public void setPrimaryKey(PortletPreferencesPK pk) {
		setPortletId(pk.portletId);
		setLayoutId(pk.layoutId);
		setOwnerId(pk.ownerId);
	}

	public String getPortletId() {
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public String getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(String layoutId) {
		_layoutId = layoutId;
	}

	public String getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(String ownerId) {
		_ownerId = ownerId;
	}

	public String getPreferences() {
		return _preferences;
	}

	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	private String _portletId;
	private String _layoutId;
	private String _ownerId;
	private String _preferences;
}