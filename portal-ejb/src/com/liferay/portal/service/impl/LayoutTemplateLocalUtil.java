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

package com.liferay.portal.service.impl;

import EDU.oswego.cs.dl.util.concurrent.SyncMap;
import EDU.oswego.cs.dl.util.concurrent.WriterPreferenceReadWriteLock;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.impl.LayoutTemplateImpl;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.layoutconfiguration.util.velocity.InitColumnProcessor;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.ListUtil;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="LayoutTemplateLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 *
*/
public class LayoutTemplateLocalUtil {

	public static String getContent(
		String layoutTemplateId, boolean standard, String themeId) {

		LayoutTemplate layoutTemplate =
			getLayoutTemplate(layoutTemplateId, standard, themeId);

		if (layoutTemplate == null) {
			_log.error(
				"Layout template " + layoutTemplateId + " does not exist");

			return null;
		}
		else {
			return layoutTemplate.getContent();
		}
	}

	public static LayoutTemplate getLayoutTemplate(
		String layoutTemplateId, boolean standard, String themeId) {

		if (Validator.isNull(layoutTemplateId)) {
			return null;
		}

		LayoutTemplate layoutTemplate = null;

		if (themeId != null) {
			if (standard) {
				layoutTemplate = (LayoutTemplate)_getThemesStandard(
					themeId).get(layoutTemplateId);
			}
			else {
				layoutTemplate = (LayoutTemplate)_getThemesCustom(
					themeId).get(layoutTemplateId);
			}

			if (layoutTemplate != null) {
				return layoutTemplate;
			}
		}

		if (standard) {
			layoutTemplate =
				(LayoutTemplate)_warStandard.get(layoutTemplateId);

			if (layoutTemplate == null) {
				layoutTemplate =
					(LayoutTemplate)_portalStandard.get(layoutTemplateId);
			}
		}
		else {
			layoutTemplate =
				(LayoutTemplate)_warCustom.get(layoutTemplateId);

			if (layoutTemplate == null) {
				layoutTemplate =
					(LayoutTemplate)_portalCustom.get(layoutTemplateId);
			}
		}

		return layoutTemplate;
	}

	public static List getLayoutTemplates() {
		List customLayoutTemplates =
			new ArrayList(_portalCustom.size() + _warCustom.size());

		customLayoutTemplates.addAll(
			ListUtil.fromCollection(_portalCustom.values()));

		customLayoutTemplates.addAll(
			ListUtil.fromCollection(_warCustom.values()));

		return customLayoutTemplates;
	}

	public static List init(ServletContext ctx, String[] xmls) {
		return init(null, ctx, xmls);
	}

	public static List init(
		String servletContextName, ServletContext ctx, String[] xmls) {

		List layoutTemplateIds = new ArrayList();

		try {
			for (int i = 0; i < xmls.length; i++) {
				Iterator itr = _readLayoutTemplates(
					servletContextName, ctx, xmls[i]).iterator();

				while (itr.hasNext()) {
					ObjectValuePair ovp = (ObjectValuePair)itr.next();

					if (!layoutTemplateIds.contains(ovp)) {
						layoutTemplateIds.add(ovp);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return layoutTemplateIds;
	}

	public static void readLayoutTemplate(
			String servletContextName, ServletContext ctx,
			Set layoutTemplateIds, Element el, boolean standard, String themeId)
		throws IOException {

		Map layoutTemplates = null;

		if (themeId != null) {
			if (standard) {
				layoutTemplates = _getThemesStandard(themeId);
			}
			else {
				layoutTemplates = _getThemesCustom(themeId);
			}
		}
		else if (servletContextName != null) {
			if (standard) {
				layoutTemplates = _warStandard;
			}
			else {
				layoutTemplates = _warCustom;
			}
		}
		else {
			if (standard) {
				layoutTemplates = _portalStandard;
			}
			else {
				layoutTemplates = _portalCustom;
			}
		}

		Iterator itr = el.elements("layout-template").iterator();

		while (itr.hasNext()) {
			Element layoutTemplate = (Element)itr.next();

			String layoutTemplateId = layoutTemplate.attributeValue("id");

			if (layoutTemplateIds != null) {
				layoutTemplateIds.add(new ObjectValuePair(
					layoutTemplateId, new Boolean(standard)));
			}

			LayoutTemplate layoutTemplateModel =
				(LayoutTemplate)layoutTemplates.get(layoutTemplateId);

			if (layoutTemplateModel == null) {
				layoutTemplateModel = new LayoutTemplateImpl(layoutTemplateId);

				layoutTemplates.put(layoutTemplateId, layoutTemplateModel);
			}

			if (servletContextName != null) {
				layoutTemplateModel.setServletContextName(servletContextName);
			}

			layoutTemplateModel.setStandard(standard);
			layoutTemplateModel.setName(GetterUtil.getString(
				layoutTemplate.attributeValue("name"),
				layoutTemplateModel.getName()));
			layoutTemplateModel.setTemplatePath(GetterUtil.getString(
				layoutTemplate.elementText("template-path"),
				layoutTemplateModel.getTemplatePath()));
			layoutTemplateModel.setThumbnailPath(GetterUtil.getString(
				layoutTemplate.elementText("thumbnail-path"),
				layoutTemplateModel.getThumbnailPath()));

			String content = Http.URLtoString(ctx.getResource(
				layoutTemplateModel.getTemplatePath()));

			if ((content == null) || (content.length() == 0)) {
				_log.error(
					"No content found at " +
						layoutTemplateModel.getTemplatePath());
			}
			else {
				layoutTemplateModel.setContent(content);
				layoutTemplateModel.setColumns(_getColumns(content));
			}
		}
	}

	public static void uninstallLayoutTemplate(
		String layoutTemplateId, boolean standard) {

		if (standard) {
			_warStandard.remove(layoutTemplateId);
		}
		else {
			_warCustom.remove(layoutTemplateId);
		}
	}

	public static void uninstallLayoutTemplates(String themeId) {
		_getThemesStandard(themeId).clear();
		_getThemesCustom(themeId).clear();
	}

	private static List _getColumns(String content) {
		try {
			InitColumnProcessor processor = new InitColumnProcessor();

			VelocityContext context = new VelocityContext();

			context.put("processor", processor);

			Velocity.evaluate(
				context, new PrintWriter(new StringWriter()),
				LayoutTemplateLocalUtil.class.getName(), content);

			List columns = processor.getColumns();

			Collections.sort(columns);

			return columns;
		}
		catch (Exception e) {
			_log.error(e);

			return new ArrayList();
		}
	}

	private static Set _readLayoutTemplates(
			String servletContextName, ServletContext ctx, String xml)
		throws DocumentException, IOException {

		Set layoutTemplateIds = new HashSet();

		if (xml == null) {
			return layoutTemplateIds;
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Element standardEl = root.element("standard");

		if (standardEl != null) {
			readLayoutTemplate(
				servletContextName, ctx, layoutTemplateIds, standardEl, true,
				null);
		}

		Element customEl = root.element("custom");

		if (customEl != null) {
			readLayoutTemplate(
				servletContextName, ctx, layoutTemplateIds, customEl, false,
				null);
		}

		return layoutTemplateIds;
	}

	private static Map _getThemesCustom(String themeId) {
		String key = themeId + _CUSTOM_SEPARATOR;

		Map layoutTemplates = (Map)_themes.get(key);

		if (layoutTemplates == null) {
			layoutTemplates = CollectionFactory.getSyncHashMap();

			_themes.put(key, layoutTemplates);
		}

		return layoutTemplates;
	}

	private static Map _getThemesStandard(String themeId) {
		String key = themeId + _STANDARD_SEPARATOR;

		Map layoutTemplates = (Map)_themes.get(key);

		if (layoutTemplates == null) {
			layoutTemplates = CollectionFactory.getSyncHashMap();

			_themes.put(key, layoutTemplates);
		}

		return layoutTemplates;
	}

	private static final String _STANDARD_SEPARATOR = "_STANDARD_";

	private static final String _CUSTOM_SEPARATOR = "_CUSTOM_";

	private static Log _log = LogFactory.getLog(LayoutTemplateLocalUtil.class);

	private static Map _portalStandard = CollectionFactory.getSyncHashMap();
	private static Map _portalCustom = new SyncMap(
		new LinkedHashMap(), new WriterPreferenceReadWriteLock());

	private static Map _warStandard = CollectionFactory.getSyncHashMap();
	private static Map _warCustom = new SyncMap(
		new LinkedHashMap(), new WriterPreferenceReadWriteLock());

	private static Map _themes = CollectionFactory.getSyncHashMap();

}