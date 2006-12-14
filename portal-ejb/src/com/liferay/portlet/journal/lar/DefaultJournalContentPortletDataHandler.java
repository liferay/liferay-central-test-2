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
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticlePK;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructurePK;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.Validator;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Provides the default Journal Content portlet (#56) import/export functionality, which is to "clone"
 * the article, structure, and/or template referenced in the journal content portlet IF
 * the content was created/owned by the group whos layout is being exported.
 * Upon import, new instances of the corresponding articles/structures/templates
 * are created (if they don't already exist). The author/owner of the newly created
 * article is determined by the NewJournalContentCreationStrategy class defined in
 * <i>portal.properties</i>.
 * <p>This <code>PortletDataHandler</code> differs from from the primary journal's
 * <code>PortletDataHandlerImpl</code> in that it only exports articles referenced in content
 * portlets. Articles NOT displayed in content portlets will not be exported
 * unless PortletDataHandlerImpl is activated.
 * 
 * <p><a href="DefaultJournalContentPortletDataHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @see com.liferay.portlet.journal.lar.NewJournalContentCreationStrategy
 * @see com.liferay.portlet.journal.lar.PortletDataHandlerImpl
 * @see com.liferay.portal.kernel.lar.PortletDataHandler
 *
 * @author  Joel Kozikowski
 * 
 */
public class DefaultJournalContentPortletDataHandler implements PortletDataHandler {

	public String exportData(String companyId, String groupId, String portletId, PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
            // First, retrieve the article this portlet displays.
            String articleId = portletPreferences.getValue("article-id", null);
            if (articleId == null) {
                _log.warn("No article Id found in preferences of portlet id " + portletId);
                return null;
            }
            String articleGroupId = portletPreferences.getValue("group-id", null);
            if (articleGroupId == null) {
                _log.warn("No group Id found in preferences of portlet id " + portletId);
                return null;
            }

            JournalArticle article = null;
            try {
                article = JournalArticleLocalServiceUtil.getLatestArticle(companyId, articleGroupId, articleId);
            }
            catch (NoSuchArticleException e) {
            }
            if (article == null) {
                _log.warn("No article found with an id of " + articleId + " for group id " + articleGroupId + " (referenced by portlet id " + portletId + ")");
                return null;
            }
            
			// Export the article if it is owned by the group we are exporting...
            if (article.getGroupId().equals(groupId)) {
                SAXReader reader = SAXReaderFactory.getInstance();
                XStream xStream = new XStream();

                Document doc = DocumentHelper.createDocument();
                Element root = doc.addElement("journal-content");

                // This is one of this group's articles, so export it in its entirety...
                String xml = xStream.toXML(article);
                Document tempDoc = reader.read(new StringReader(xml));
                root.content().add(tempDoc.getRootElement().createCopy());
                
                // The article's structure?
                String structureId = article.getStructureId();
                if (structureId != null) {
                    JournalStructure structure = JournalStructureUtil.findByPrimaryKey(new JournalStructurePK(companyId, article.getGroupId(), structureId));
                    xml = xStream.toXML(structure);
                    tempDoc = reader.read(new StringReader(xml));
                    root.content().add(tempDoc.getRootElement().createCopy());
                }

                // The article's template?
                String templateId = article.getTemplateId();
                if (templateId != null) {
                    JournalTemplate template = JournalTemplateUtil.findByPrimaryKey(new JournalTemplatePK(companyId, article.getGroupId(), templateId));
                    xml = xStream.toXML(template);
                    tempDoc = reader.read(new StringReader(xml));
                    root.content().add(tempDoc.getRootElement().createCopy());
                }

                return XMLFormatter.toString(doc);
                
            }
            else {
                // The article is an external reference, so there is nothing special to export...
                return null;
            }

		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(String companyId, String groupId, String portletId, PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		try {
            if (Validator.isNull(data)) {
                // Nothing to do...
                return null;
            }
            
            NewJournalContentCreationStrategy creationStrategy = PortletDataHandlerUtils.getContentCreationStrategy();
            
			SAXReader reader = SAXReaderFactory.getInstance();
			XStream xStream = new XStream();

            // Parse the data...
			Document doc = reader.read(new StringReader(data));
			Element root = doc.getRootElement();
            if (!root.getName().equals("journal-content")) {
                _log.warn("Unexpected portlet data while attempting to import portlet id " + portletId + " : expected <journal-content>, but was <" + root.getName() + ">");
                return null;
            }

			// The article...
			Element el = root.element(JournalArticleImpl.class.getName());
			Document tempDoc = DocumentHelper.createDocument();
			tempDoc.content().add(el.createCopy());
			JournalArticle article = (JournalArticle)xStream.fromXML(XMLFormatter.toString(tempDoc));

            article.setGroupId(groupId);
                
            // See if the article already exists...
            JournalArticlePK pk = article.getPrimaryKey();
            boolean exists = (JournalArticleUtil.fetchByPrimaryKey(pk) != null);

            if (!exists) {
                // Article ID not in this group yet - create it...   
                article.setNew(true);
                article.setVersion(1.0);

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

			// Is there a structure needed?
            el = root.element(JournalStructureImpl.class.getName());
            if (el != null) {
                tempDoc = DocumentHelper.createDocument();
                tempDoc.content().add(el.createCopy());
                JournalStructure structure = (JournalStructure)xStream.fromXML(XMLFormatter.toString(tempDoc));
                structure.setGroupId(groupId);
                
                JournalStructurePK structurePk = structure.getPrimaryKey();
                exists = (JournalStructureUtil.fetchByPrimaryKey(structurePk) != null);

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

            // Is there a template needed?
            el = root.element(JournalTemplateImpl.class.getName());
            if (el != null) {
                tempDoc = DocumentHelper.createDocument();
                tempDoc.content().add(el.createCopy());
                JournalTemplate template = (JournalTemplate)xStream.fromXML(XMLFormatter.toString(tempDoc));
                template.setGroupId(groupId);
                
                JournalTemplatePK templatePk = template.getPrimaryKey();
                exists = (JournalTemplateUtil.fetchByPrimaryKey(templatePk) != null);

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
            
            // Update the "group-id" in the display content to reference THIS group, since article
            // is now in this group.
            portletPreferences.setValue("group-id", groupId);
            return portletPreferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

    private static Log _log = LogFactory.getLog(DefaultJournalContentPortletDataHandler.class);
    
}
