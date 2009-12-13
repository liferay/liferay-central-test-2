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

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

/**
 * <a href="ExtRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExtRegistry {

	public static Map<String, Set<String>> getConflicts(
			ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		Iterator<Map.Entry<String, Set<String>>> itr =
			_extMap.entrySet().iterator();

		Map<String, Set<String>> conflicts = new HashMap<String, Set<String>>();

		while (itr.hasNext()) {
			Map.Entry<String, Set<String>> entry = itr.next();

			String curServletContextName = entry.getKey();
			Set<String> curFiles = entry.getValue();

			for (String file : files) {
				if (!curFiles.contains(file)) {
					continue;
				}

				Set<String> conflictFiles = conflicts.get(
					curServletContextName);

				if (conflictFiles == null) {
					conflictFiles = new TreeSet<String>();

					conflicts.put(curServletContextName, conflictFiles);
				}

				conflictFiles.add(file);
			}
		}

		return conflicts;
	}

	public static Set<String> getServletContextNames() {
		return Collections.unmodifiableSet(_extMap.keySet());
	}

	public static boolean isRegistered(String servletContextName) {
		if (_extMap.containsKey(servletContextName)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void registerExt(ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		_extMap.put(servletContextName, files);
	}

	public static void registerPortal(ServletContext servletContext)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			"/WEB-INF");

		for (String resourcePath : resourcePaths) {
			if (resourcePath.startsWith("/WEB-INF/ext-") &&
				resourcePath.endsWith("-ext.xml")) {

				String servletContextName = resourcePath.substring(
					13, resourcePath.length() - 4);

				Set<String> files = _readExtFiles(
					servletContext, resourcePath);

				_extMap.put(servletContextName, files);
			}
		}
	}

	private static Set<String> _readExtFiles(
			ServletContext servletContext, String resourcePath)
		throws Exception {

		Set<String> files = new TreeSet<String>();

		Document document = SAXReaderUtil.read(
			servletContext.getResourceAsStream(resourcePath));

		Element rootElement = document.getRootElement();

		Element filesElement = rootElement.element("files");

		List<Element> fileElements = filesElement.elements("file");

		for (Element fileElement : fileElements) {
			files.add(fileElement.getText());
		}

		return files;
	}

	private static Map<String, Set<String>> _extMap =
		new HashMap<String, Set<String>>();

}