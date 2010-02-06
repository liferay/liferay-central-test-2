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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <a href="WSDDMerger.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WSDDMerger {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		new WSDDMerger(args[0], args[1]);
	}

	public static void merge(String source, String destination)
		throws DocumentException, IOException {

		// Source

		File sourceFile = new File(source);

		Document doc = SAXReaderUtil.read(sourceFile);

		Element root = doc.getRootElement();

		List<Element> sourceServices = root.elements("service");

		if (sourceServices.size() == 0) {
			return;
		}

		// Destination

		File destinationFile = new File(destination);

		doc = SAXReaderUtil.read(destinationFile);

		root = doc.getRootElement();

		Map<String, Element> servicesMap = new TreeMap<String, Element>();

		Iterator<Element> itr = root.elements("service").iterator();

		while (itr.hasNext()) {
			Element service = itr.next();

			String name = service.attributeValue("name");

			servicesMap.put(name, service);

			service.detach();
		}

		itr = sourceServices.iterator();

		while (itr.hasNext()) {
			Element service = itr.next();

			String name = service.attributeValue("name");

			servicesMap.put(name, service);

			service.detach();
		}

		for (Map.Entry<String, Element> entry : servicesMap.entrySet()) {
			Element service = entry.getValue();

			root.add(service);
		}

		FileUtil.write(destination, doc.formattedString(), true);
	}

	public WSDDMerger(String source, String destination) {
		try {
			merge(source, destination);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}