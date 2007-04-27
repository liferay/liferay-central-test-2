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

import com.liferay.portal.service.persistence.LayoutPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="LayoutSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.LayoutServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.LayoutServiceSoap
 *
 */
public class LayoutSoap implements Serializable {
	public static LayoutSoap toSoapModel(Layout model) {
		LayoutSoap soapModel = new LayoutSoap();
		soapModel.setLayoutId(model.getLayoutId());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setParentLayoutId(model.getParentLayoutId());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setHidden(model.getHidden());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setIconImage(model.getIconImage());
		soapModel.setThemeId(model.getThemeId());
		soapModel.setColorSchemeId(model.getColorSchemeId());
		soapModel.setCss(model.getCss());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static LayoutSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			Layout model = (Layout)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (LayoutSoap[])soapModels.toArray(new LayoutSoap[0]);
	}

	public LayoutSoap() {
	}

	public LayoutPK getPrimaryKey() {
		return new LayoutPK(_layoutId, _ownerId);
	}

	public void setPrimaryKey(LayoutPK pk) {
		setLayoutId(pk.layoutId);
		setOwnerId(pk.ownerId);
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

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getParentLayoutId() {
		return _parentLayoutId;
	}

	public void setParentLayoutId(String parentLayoutId) {
		_parentLayoutId = parentLayoutId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public boolean getHidden() {
		return _hidden;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public boolean getIconImage() {
		return _iconImage;
	}

	public boolean isIconImage() {
		return _iconImage;
	}

	public void setIconImage(boolean iconImage) {
		_iconImage = iconImage;
	}

	public String getThemeId() {
		return _themeId;
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	public String getColorSchemeId() {
		return _colorSchemeId;
	}

	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	public String getCss() {
		return _css;
	}

	public void setCss(String css) {
		_css = css;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private String _layoutId;
	private String _ownerId;
	private long _companyId;
	private String _parentLayoutId;
	private String _name;
	private String _title;
	private String _type;
	private String _typeSettings;
	private boolean _hidden;
	private String _friendlyURL;
	private boolean _iconImage;
	private String _themeId;
	private String _colorSchemeId;
	private String _css;
	private int _priority;
}