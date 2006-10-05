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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.lar.PortletDataHandler;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

import java.io.StringReader;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="JournalPortletDataHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Raymond Auge
 *
 */
public class JournalPortletDataHandler implements PortletDataHandler {

	public String exportData(String companyId, String groupId)
		throws PortalException, SystemException {

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("journal-data");

			root.addAttribute("group-id", groupId);

			// Articles

			Object obj = JournalArticleUtil.findByGroupId(groupId);

			String xml = xStream.toXML(obj);

			Element el = root.addElement("articles");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Structures

			obj = JournalStructureUtil.findByGroupId(groupId);

			xml = xStream.toXML(obj);

			tempDoc = reader.read(new StringReader(xml));

			el = root.addElement("structures");

			el.content().add(tempDoc.getRootElement().createCopy());

			// Templates

			obj = JournalTemplateUtil.findByGroupId(groupId);

			xml = xStream.toXML(obj);

			el = root.addElement("templates");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void importData(String companyId, String groupId, String data)
		throws PortalException, SystemException {

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Articles

			Element el = root.element("articles").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			XStream xStream = new XStream(new Dom4JDriver());

			List articles = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = articles.iterator();

			while (itr.hasNext()) {
				JournalArticle article = (JournalArticle)itr.next();

				article.setGroupId(groupId);

				JournalArticleUtil.update(article, true);
			}

			// Structures

			el = root.element("structures").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			xStream = new XStream(new Dom4JDriver());

			List structures = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = structures.iterator();

			while (itr.hasNext()) {
				JournalStructure structure = (JournalStructure)itr.next();

				structure.setGroupId(groupId);

				JournalStructureUtil.update(structure, true);
			}

			// Templates

			el = root.element("templates").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			xStream = new XStream(new Dom4JDriver());

			List templates = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = templates.iterator();

			while (itr.hasNext()) {
				JournalTemplate template = (JournalTemplate)itr.next();

				template.setGroupId(groupId);

				JournalTemplateUtil.update(template, true);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}