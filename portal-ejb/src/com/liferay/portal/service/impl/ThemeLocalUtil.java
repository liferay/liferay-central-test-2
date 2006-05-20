/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.ThemeCompanyId;
import com.liferay.portal.model.ThemeCompanyLimit;
import com.liferay.portal.util.CompanyPropsUtil;
import com.liferay.portal.util.EntityResolver;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.ListUtil;
import com.liferay.util.NullSafeProperties;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="ThemeLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ThemeLocalUtil {

	public static ColorScheme getColorScheme(
		String companyId, String themeId, String colorSchemeId) {

		colorSchemeId = GetterUtil.getString(colorSchemeId);

		Theme theme = getTheme(companyId, themeId);

		Map colorSchemesMap = theme.getColorSchemesMap();

		ColorScheme colorScheme = (ColorScheme)colorSchemesMap.get(
			colorSchemeId);

		if (colorScheme == null) {
			List colorSchemes = theme.getColorSchemes();

			if (colorSchemes.size() > 0) {
				colorScheme = (ColorScheme)colorSchemes.get(0);
			}
		}

		if (colorScheme == null) {
			colorScheme = (ColorScheme)colorSchemesMap.get(
				CompanyPropsUtil.get(
					companyId, PropsUtil.DEFAULT_COLOR_SCHEME_ID));
		}

		if (colorScheme == null) {
			colorScheme = ColorScheme.NULL_COLOR_SCHEME;
		}

		return colorScheme;
	}

	public static Theme getTheme(String companyId, String themeId) {
		themeId = GetterUtil.getString(themeId);

		Theme theme = (Theme)_getThemes(companyId).get(themeId);

		if (theme == null) {
			theme = (Theme)_themes.get(
				CompanyPropsUtil.get(companyId, PropsUtil.DEFAULT_THEME_ID));
		}

		return theme;
	}

	public static List getThemes(String companyId) {
		List themes = ListUtil.fromCollection(_getThemes(companyId).values());

		Collections.sort(themes);

		return themes;
	}

	public static List init(ServletContext ctx, String[] xmls) {
		return init(null, ctx, xmls);
	}

	public static List init(
		String servletContextName, ServletContext ctx, String[] xmls) {

		List themeIds = new ArrayList();

		try {
			for (int i = 0; i < xmls.length; i++) {
				Iterator itr = _readThemes(
					servletContextName, ctx, xmls[i]).iterator();

				while (itr.hasNext()) {
					String themeId = (String)itr.next();

					if (!themeIds.contains(themeId)) {
						themeIds.add(themeId);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		_themesPool.clear();

		return themeIds;
	}

	public static void uninstallThemes(List themeIds) {
		for (int i = 0; i < themeIds.size(); i++) {
			String themeId = (String)themeIds.get(i);

			_themes.remove(themeId);

			LayoutTemplateLocalUtil.uninstallLayoutTemplates(themeId);
		}

		_themesPool.clear();
	}

	private static List _getCompanyLimitExcludes(Element el) {
		List includes = new ArrayList();

		if (el != null) {
			List companyIds = el.elements("company-id");

			for (int i = 0; i < companyIds.size(); i++) {
				Element companyIdEl = (Element)companyIds.get(i);

				String name = companyIdEl.attributeValue("name");
				String pattern = companyIdEl.attributeValue("pattern");

				ThemeCompanyId themeCompanyId = null;

				if (Validator.isNotNull(name)) {
					themeCompanyId = new ThemeCompanyId(name, false);
				}
				else if (Validator.isNotNull(pattern)) {
					themeCompanyId = new ThemeCompanyId(pattern, true);
				}

				if (themeCompanyId != null) {
					includes.add(themeCompanyId);
				}
			}
		}

		return includes;
	}

	private static List _getCompanyLimitIncludes(Element el) {
		return _getCompanyLimitExcludes(el);
	}

	private static Map _getThemes(String companyId) {
		Map themes = (Map)_themesPool.get(companyId);

		if (themes == null) {
			themes = CollectionFactory.getSyncHashMap();

			Iterator itr = _themes.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String themeId = (String)entry.getKey();
				Theme theme = (Theme)entry.getValue();

				boolean available = true;

				ThemeCompanyLimit companyLimit =
					(ThemeCompanyLimit)_themeCompanyLimits.get(themeId);

				_log.debug(
					"Check if theme " + themeId + " is available for " +
						companyId);

				if (companyLimit != null) {
					List includes = companyLimit.getIncludes();
					List excludes = companyLimit.getExcludes();

					if ((includes.size() != 0) && (excludes.size() != 0)) {

						// Since includes and excludes are specified, check to
						// make sure the current company id is included and also
						// not excluded

						_log.debug("Check includes and excludes");

						available = companyLimit.isIncluded(companyId);

						if (available) {
							available = !companyLimit.isExcluded(companyId);
						}
					}
					else if ((includes.size() == 0) && (excludes.size() != 0)) {

						// Since no includes are specified, check to make sure
						// the current company id is not excluded

						_log.debug("Check excludes");

						available = !companyLimit.isExcluded(companyId);
					}
					else if ((includes.size() != 0) && (excludes.size() == 0)) {

						// Since no excludes are specified, check to make sure
						// the current company id is included

						_log.debug("Check includes");

						available = companyLimit.isIncluded(companyId);
					}
					else {

						// Since no includes or excludes are specified, this
						// theme is available for every company

						_log.debug("No includes or excludes set");

						available = true;
					}
				}

				_log.debug(
					"Theme " + themeId + " is " + (!available ? "NOT " : "") +
						"available for " + companyId);

				if (available) {
					themes.put(themeId, theme);
				}
			}

			_themesPool.put(companyId, themes);
		}

		return themes;
	}

	private static void _readColorSchemes(Element theme, Map colorSchemes)
		throws IOException {

		Iterator itr = theme.elements("color-scheme").iterator();

		while (itr.hasNext()) {
			Element colorScheme = (Element)itr.next();

			String id = colorScheme.attributeValue("id");
			String name = colorScheme.attributeValue("name");
			String settings = colorScheme.getText();

			ColorScheme colorSchemeModel =
				(ColorScheme)colorSchemes.get(id);

			if (colorSchemeModel == null) {
				colorSchemeModel = new ColorScheme(id);
			}

			colorSchemeModel.setName(GetterUtil.get(
				name, colorSchemeModel.getName()));

			Properties p = new NullSafeProperties();

			PropertiesUtil.load(p, settings);
			PropertiesUtil.trimKeys(p);

			PropertiesUtil.merge(
				colorSchemeModel.getSettingsProperties(), p);

			colorSchemes.put(id, colorSchemeModel);
		}
	}

	private static Set _readThemes(
			String servletContextName, ServletContext ctx, String xml)
		throws DocumentException, IOException {

		Set themeIds = new HashSet();

		if (xml == null) {
			return themeIds;
		}

		SAXReader reader = new SAXReader(true);

		reader.setEntityResolver(new EntityResolver());

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Set compatibleVersions = new HashSet();

		Element compatibilityEl = root.element("compatibility");

		if (compatibilityEl != null) {
			Iterator itr = compatibilityEl.elements("version").iterator();

			while (itr.hasNext()) {
				Element versionEl = (Element)itr.next();

				String version = versionEl.getTextTrim();

				compatibleVersions.add(version);
			}
		}

		if (!compatibleVersions.contains(ReleaseInfo.getVersion())) {
			_log.error(
				"Themes in this WAR are not compatible with " +
					ReleaseInfo.getServerInfo());

			return themeIds;
		}

		ThemeCompanyLimit companyLimit = null;

		Element companyLimitEl = root.element("company-limit");

		if (companyLimitEl != null) {
			companyLimit = new ThemeCompanyLimit();

			Element companyIncludesEl =
				companyLimitEl.element("company-includes");

			if (companyIncludesEl != null) {
				companyLimit.setIncludes(
					_getCompanyLimitIncludes(companyIncludesEl));
			}

			Element companyExcludesEl =
				companyLimitEl.element("company-excludes");

			if (companyExcludesEl != null) {
				companyLimit.setExcludes(
					_getCompanyLimitExcludes(companyExcludesEl));
			}
		}

		Iterator itr1 = root.elements("theme").iterator();

		while (itr1.hasNext()) {
			Element theme = (Element)itr1.next();

			String themeId = theme.attributeValue("id");
			if (servletContextName != null) {
				themeId =
					themeId + Portlet.WAR_SEPARATOR + servletContextName;
			}

			themeIds.add(themeId);

			Theme themeModel = (Theme)_themes.get(themeId);

			if (themeModel == null) {
				themeModel = new Theme(themeId);

				_themes.put(themeId, themeModel);
			}

			if (companyLimit != null) {
				_themeCompanyLimits.put(themeId, companyLimit);
			}

			if (servletContextName != null) {
				themeModel.setServletContextName(servletContextName);
			}

			themeModel.setName(GetterUtil.get(
				theme.attributeValue("name"),
				themeModel.getName()));
			themeModel.setRootPath(GetterUtil.get(
				theme.elementText("root-path"),
				themeModel.getRootPath()));
			themeModel.setTemplatesPath(GetterUtil.get(
				theme.elementText("templates-path"),
				themeModel.getTemplatesPath()));
			themeModel.setImagesPath(GetterUtil.get(
				theme.elementText("images-path"),
				themeModel.getImagesPath()));
			themeModel.setTemplateExtension(GetterUtil.get(
				theme.elementText("template-extension"),
				themeModel.getTemplateExtension()));
			themeModel.setEnableDragAndDrop(GetterUtil.get(
				theme.elementText("enable-drag-and-drop"),
				themeModel.isEnableDragAndDrop()));

			Element settingsEl = theme.element("settings");

			if (settingsEl != null) {
				Iterator itr2 = settingsEl.elements("setting").iterator();

				while (itr2.hasNext()) {
					Element settingEl = (Element)itr2.next();

					String key = settingEl.attributeValue("key");
					String value = settingEl.attributeValue("value");

					themeModel.setSetting(key, value);
				}
			}

			_readColorSchemes(theme, themeModel.getColorSchemesMap());
			_readColorSchemes(theme, themeModel.getColorSchemesMap());

			Element layoutTemplatesEl = theme.element("layout-templates");

			if (layoutTemplatesEl != null) {
				Element standardEl = layoutTemplatesEl.element("standard");

				if (standardEl != null) {
					LayoutTemplateLocalUtil.readLayoutTemplate(
						null, ctx, null, standardEl, true, themeId);
				}

				Element customEl = layoutTemplatesEl.element("custom");

				if (customEl != null) {
					LayoutTemplateLocalUtil.readLayoutTemplate(
						null, ctx, null, customEl, false, themeId);
				}
			}
		}

		return themeIds;
	}

	private static Log _log = LogFactory.getLog(ThemeLocalUtil.class);

	private static Map _themes = CollectionFactory.getSyncHashMap();
	private static Map _themesPool = CollectionFactory.getSyncHashMap();
	private static Map _themeCompanyLimits = CollectionFactory.getSyncHashMap();

}