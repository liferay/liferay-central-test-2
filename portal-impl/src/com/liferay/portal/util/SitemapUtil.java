/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

/**
 * <a href="SitemapUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class SitemapUtil {

	public static String getSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Document doc = SAXReaderUtil.createDocument();

		doc.setXMLEncoding(StringPool.UTF8);

		Element root = doc.addElement(
			"urlset", "http://www.google.com/schemas/sitemap/0.84");

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		_visitLayouts(root, layouts, themeDisplay);

		return doc.asXML();
	}

	public static String encodeXML(String input) {
		return StringUtil.replace(
			input,
			new String[] {"&", "<", ">", "'", "\""},
			new String[] {"&amp;", "&lt;", "&gt;", "&apos;", "&quot;"});
	}

	private static void _visitLayouts(
			Element element, List<Layout> layouts, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		for (Layout layout : layouts) {
			UnicodeProperties props = layout.getTypeSettingsProperties();

			if (PortalUtil.isLayoutSitemapable(layout) && !layout.isHidden() &&
				GetterUtil.getBoolean(
					props.getProperty("sitemap-include"), true)) {

				Element url = element.addElement("url");

				String layoutFullURL = PortalUtil.getLayoutFullURL(
					layout, themeDisplay);

				url.addElement("loc").addText(encodeXML(layoutFullURL));

				String changefreq = props.getProperty("sitemap-changefreq");

				if (Validator.isNotNull(changefreq)) {
					url.addElement("changefreq").addText(changefreq);
				}

				String priority = props.getProperty("sitemap-priority");

				if (Validator.isNotNull(priority)) {
					url.addElement("priority").addText(priority);
				}

				List<Layout> children = layout.getChildren();

				_visitLayouts(element, children, themeDisplay);
			}
		}
	}

}