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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.journal.TransformException;

import java.util.List;
import java.util.Map;

/**
 * <a href="JournalVmUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author	   Alexander Chow
 * @author	   Brian Wing Shun Chan
 * @author	   Raymond Augé
 * @deprecated
 */
public class JournalVmUtil {

	public static List<TemplateNode> extractDynamicContents(Element parent)
		throws TransformException {

		return _instance._velocityTemplateParser.extractDynamicContents(parent);
	}

	public static String transform(
			Map<String, String> tokens, String viewMode, String languageId,
			String xml, String script)
		throws TransformException {

		return _instance._velocityTemplateParser.transform(
			tokens, viewMode, languageId, xml, script);
	}

	private JournalVmUtil() {
		_velocityTemplateParser = new VelocityTemplateParser();
	}

	private static JournalVmUtil _instance = new JournalVmUtil();

	private VelocityTemplateParser _velocityTemplateParser;

}