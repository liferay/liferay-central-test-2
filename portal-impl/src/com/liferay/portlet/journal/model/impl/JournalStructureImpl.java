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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;

import java.util.Iterator;

/**
 * <a href="JournalStructureImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JournalStructureImpl
	extends JournalStructureModelImpl implements JournalStructure {

	public JournalStructureImpl() {
	}

	public String getMergedXsd() {
		String parentStructureId = getParentStructureId();

		String xsd = getXsd();

		if (Validator.isNull(parentStructureId)) {
			return xsd;
		}

		try {
			JournalStructure parentStructure =
				JournalStructureLocalServiceUtil.getStructure(
					getGroupId(), parentStructureId);

			Document doc = SAXReaderUtil.read(getXsd());

			Element root = doc.getRootElement();

			Document parentDoc = SAXReaderUtil.read(
				parentStructure.getMergedXsd());

			Element parentRoot = parentDoc.getRootElement();

			addParentStructureId(parentRoot, parentStructureId);

			root.content().addAll(0, parentRoot.content());

			xsd = root.asXML();
		}
		catch (Exception e) {
		}

		return xsd;
	}

	protected void addParentStructureId(
		Element parentEl, String parentStructureId) {

		Iterator<Element> itr = parentEl.elements(_DYNAMIC_ELEMENT).iterator();

		while (itr.hasNext()) {
			Element dynamicEl = itr.next();

			dynamicEl.addAttribute(_PARENT_STRUCTURE_ID, parentStructureId);

			addParentStructureId(dynamicEl, parentStructureId);
		}
	}

	private static final String _DYNAMIC_ELEMENT = "dynamic-element";

	private static final String _PARENT_STRUCTURE_ID = "parent-structure-id";

}