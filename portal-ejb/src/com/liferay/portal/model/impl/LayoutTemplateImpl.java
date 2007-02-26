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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="LayoutTemplateImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class LayoutTemplateImpl implements LayoutTemplate {

	public static final String PLUGIN_TYPE = "layout-template";

	public LayoutTemplateImpl() {
	}

	public LayoutTemplateImpl(String layoutTemplateId) {
		_layoutTemplateId = layoutTemplateId;
	}

	public LayoutTemplateImpl(String layoutTemplateId, String name) {
		_layoutTemplateId = layoutTemplateId;
		_name = name;
	}

	public String getLayoutTemplateId() {
		return _layoutTemplateId;
	}

	public String getPluginId() {
		return getLayoutTemplateId();
	}

	public String getPluginType() {
		return PLUGIN_TYPE;
	}

	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	public PluginSetting getDefaultPluginSetting() {
		return _pluginSetting;
	}

	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_pluginSetting = pluginSetting;
	}

	public boolean getStandard() {
		return _standard;
	}

	public boolean isStandard() {
		return _standard;
	}

	public void setStandard(boolean standard) {
		_standard = standard;
	}

	public String getName() {
		if (Validator.isNull(_name)) {
			return _layoutTemplateId;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTemplatePath() {
		return _templatePath;
	}

	public void setTemplatePath(String templatePath) {
		_templatePath = templatePath;
	}

	public String getThumbnailPath() {
		return _thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		_thumbnailPath = thumbnailPath;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_setContent = true;

		_content = content;
	}

	public boolean hasSetContent() {
		return _setContent;
	}

	public List getColumns() {
		return _columns;
	}

	public void setColumns(List columns) {
		_columns = columns;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;

		if (Validator.isNotNull(_servletContextName)) {
			_warFile = true;
		}
		else {
			_warFile = false;
		}
	}

	public boolean getWARFile() {
		return _warFile;
	}

	public boolean isWARFile() {
		return _warFile;
	}

	public String getContextPath() {
		if (isWARFile()) {
			return StringPool.SLASH + getServletContextName();
		}
		else {
			String contextPath = PropsUtil.get(PropsUtil.PORTAL_CTX);

			if (contextPath.equals(StringPool.SLASH)) {
				contextPath = StringPool.BLANK;
			}

			return contextPath;
		}
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		LayoutTemplate layoutTemplate = (LayoutTemplate) obj;

		return getName().compareTo(layoutTemplate.getName());
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		LayoutTemplate layoutTemplate = null;

		try {
			layoutTemplate = (LayoutTemplate)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String layoutTemplateId = layoutTemplate.getLayoutTemplateId();

		if (getLayoutTemplateId().equals(layoutTemplateId)) {
			return true;
		}
		else {
			return false;
		}
	}

	private String _layoutTemplateId;
	private PluginPackage _pluginPackage;
	private PluginSetting _pluginSetting;
	private boolean _standard;
	private String _name;
	private String _templatePath;
	private String _thumbnailPath;
	private String _content;
	private boolean _setContent;
	private List _columns = new ArrayList();
	private String _servletContextName;
	private boolean _warFile;

}