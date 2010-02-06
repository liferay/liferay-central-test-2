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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="TLDFormatter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TLDFormatter {

	public static void main(String[] args) {
		try {
			InitUtil.initWithSpring();

			_formatTLD();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _formatTLD() throws Exception {
		String basedir = "./util-taglib/src/META-INF/";

		if (!FileUtil.exists(basedir)) {
			return;
		}

		List<String> list = new ArrayList<String>();

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(new String[] {"**\\liferay-portlet-ext.tld"});
		ds.setIncludes(new String[] {"**\\*.tld"});

		ds.scan();

		list.addAll(ListUtil.fromArray(ds.getIncludedFiles()));

		String[] files = list.toArray(new String[list.size()]);

		for (int i = 0; i < files.length; i++) {
			File file = new File(basedir + files[i]);

			String content = FileUtil.read(file);

			Document document = SAXReaderUtil.read(
				new UnsyncStringReader(
				StringUtil.replace(
					content, "xml/ns/j2ee/web-jsptaglibrary_2_0.xsd",
					"dtd/web-jsptaglibrary_1_2.dtd")));

			Element root = document.getRootElement();

			_sortElements(root, "tag", "name");

			List<Element> tagEls = root.elements("tag");

			for (Element tagEl : tagEls) {
				_sortElements(tagEl, "attribute", "name");

				Element dynamicAttributesEl = tagEl.element(
					"dynamic-attributes");

				if (dynamicAttributesEl != null) {
					dynamicAttributesEl.detach();

					tagEl.add(dynamicAttributesEl);
				}
			}

			String newContent = document.formattedString();

			int x = newContent.indexOf("<tlib-version");
			int y = newContent.indexOf("</taglib>");

			newContent = newContent.substring(x, y);

			x = content.indexOf("<tlib-version");
			y = content.indexOf("</taglib>");

			newContent =
				content.substring(0, x) + newContent + content.substring(y);

			if (!content.equals(newContent)) {
				FileUtil.write(file, newContent);

				System.out.println(file);
			}
		}
	}

	private static void _sortElements(
		Element parentElement, String name, String sortBy) {

		Map<String, Element> map = new TreeMap<String, Element>();

		List<Element> elements = parentElement.elements(name);

		for (Element element : elements) {
			map.put(element.elementText(sortBy), element);

			element.detach();
		}

		for (Map.Entry<String, Element> entry : map.entrySet()) {
			Element element = entry.getValue();

			parentElement.add(element);
		}
	}

}