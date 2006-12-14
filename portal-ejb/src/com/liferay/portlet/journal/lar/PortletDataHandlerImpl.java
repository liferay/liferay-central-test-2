/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticlePK;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructurePK;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Provides the default Journal (portlet 15) import/export functionality, which is to "clone"
 * all articles, structures, and templates created/owned by the group 
 * when the group's Layout set is exported.  Upon import, new instances of 
 * the corresponding articles/structures/templates are created (if they don't 
 * already exist for the group). The author/owner of the newly created
 * article is determined by the NewJournalContentCreationStrategy class defined in
 * <i>portal.properties</i>.
 * <p>This <code>PortletDataHandler</code> differs from from 
 * <code>DefaultJournalContentPortletDataHandler</code>
 * n that it exports <i>ALL</i> articles owned by the group whether or not
 * they are actually displayed in a portlet in the layout set.
 *
 * <p><a href="PortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @see com.liferay.portlet.journal.lar.DefaultJournalContentPortletDataHandler
 * @see com.liferay.portlet.journal.lar.NewJournalContentCreationStrategy
 * 
 * @author  Raymond Auge
 * @author Joel Kozikowski
 *
 */
public class PortletDataHandlerImpl implements PortletDataHandler {

	public String exportData(String companyId, String groupId, String portletId, PortletPreferences portletPreferences)
		throws PortletDataException {

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
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(String companyId, String groupId, String portletId, PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		try {
            NewJournalContentCreationStrategy creationStrategy = PortletDataHandlerUtils.getContentCreationStrategy();
            
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Articles

			Element el = root.element("articles").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List articles = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = articles.iterator();

			while (itr.hasNext()) {
				JournalArticle article = (JournalArticle)itr.next();
				article.setGroupId(groupId);
                
                // See if the article already exists...
                JournalArticlePK pk = article.getPrimaryKey();
                boolean exists = (JournalArticleUtil.fetchByPrimaryKey(pk) != null);

                if (!exists) {
                    // Article ID not in this group yet - create it...   
                    article.setNew(true);

                    String authorId = creationStrategy.getAuthorUserId(companyId, groupId, article);
                    if (authorId != null) {
                        article.setUserId(authorId);
                        article.setUserName(creationStrategy.getAuthorUserName(companyId, groupId, article));
                    }
                    
                    String approvedById = creationStrategy.getApprovalUserId(companyId, groupId, article);
                    if (approvedById != null) {
                        article.setApprovedByUserId(approvedById);
                        article.setApprovedByUserName(creationStrategy.getApprovalUserName(companyId, groupId, article));
                        article.setApproved(true);
                    }
                    else {
                        article.setApprovedByUserId(null);
                        article.setApprovedByUserName(null);
                        article.setApproved(false);
                    }
                    
                    article = JournalArticleUtil.update(article, true);
                    JournalArticleLocalServiceUtil.addArticleResources(article, 
                             creationStrategy.addCommunityPermissions(companyId, groupId, article), 
                             creationStrategy.addGuestPermissions(companyId, groupId, article));
                }
			}

			// Structures

			el = root.element("structures").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List structures = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = structures.iterator();

			while (itr.hasNext()) {
				JournalStructure structure = (JournalStructure)itr.next();
				structure.setGroupId(groupId);
                
                JournalStructurePK pk = structure.getPrimaryKey();
                boolean exists = (JournalStructureUtil.fetchByPrimaryKey(pk) != null);

                if (!exists) {
                    structure.setNew(true);
                    
                    String authorId = creationStrategy.getAuthorUserId(companyId, groupId, structure);
                    if (authorId != null) {
                        structure.setUserId(authorId);
                        structure.setUserName(creationStrategy.getAuthorUserName(companyId, groupId, structure));
                    }
                    
                    structure = JournalStructureUtil.update(structure, true);
                    JournalStructureLocalServiceUtil.addStructureResources(structure,
                            creationStrategy.addCommunityPermissions(companyId, groupId, structure), 
                            creationStrategy.addGuestPermissions(companyId, groupId, structure));
                }
			}

			// Templates

			el = root.element("templates").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List templates = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = templates.iterator();

			while (itr.hasNext()) {
				JournalTemplate template = (JournalTemplate)itr.next();
				template.setGroupId(groupId);

                JournalTemplatePK pk = template.getPrimaryKey();
                boolean exists = (JournalTemplateUtil.fetchByPrimaryKey(pk) != null);

                if (!exists) {
                    template.setNew(true);

                    String authorId = creationStrategy.getAuthorUserId(companyId, groupId, template);
                    if (authorId != null) {
                        template.setUserId(authorId);
                        template.setUserName(creationStrategy.getAuthorUserName(companyId, groupId, template));
                    }
                    
                    template = JournalTemplateUtil.update(template, true);
                    JournalTemplateLocalServiceUtil.addTemplateResources(template,
                            creationStrategy.addCommunityPermissions(companyId, groupId, template), 
                            creationStrategy.addGuestPermissions(companyId, groupId, template));
                }
			}
            
            // No special modification to the incomming portlet preferences needed...
            return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

}