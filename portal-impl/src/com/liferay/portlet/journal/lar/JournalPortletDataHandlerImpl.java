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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="JournalPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated. The author of the newly created
 * objects are determined by the JournalCreationStrategy class defined in
 * <i>portal.properties</i>.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see com.liferay.portlet.journal.lar.JournalCreationStrategy
 *
 */
public class JournalPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_JOURNAL_DATA,
			_enableExport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("journal-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Articles

			List obj = JournalArticleUtil.findByGroupId(context.getGroupId());

			Iterator itr = obj.iterator();

			while (itr.hasNext()) {
				JournalArticle article = (JournalArticle)itr.next();

				String articlePrimaryKey = getPrimaryKey(
					article.getGroupId(), article.getArticleId());

				if (context.addPrimaryKey(
						JournalArticle.class, articlePrimaryKey)) {

					itr.remove();
				}
			}

			String xml = xStream.toXML(obj);

			Element el = root.addElement("articles");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Structures

			obj = JournalStructureUtil.findByGroupId(context.getGroupId());

			itr = obj.iterator();

			while (itr.hasNext()) {
				JournalStructure structure = (JournalStructure)itr.next();

				String structurePrimaryKey = getPrimaryKey(
					structure.getGroupId(), structure.getStructureId());

				if (context.addPrimaryKey(
						JournalStructure.class, structurePrimaryKey)) {

					itr.remove();
				}
			}

			xml = xStream.toXML(obj);

			tempDoc = reader.read(new StringReader(xml));

			el = root.addElement("structures");

			el.content().add(tempDoc.getRootElement().createCopy());

			// Templates

			obj = JournalTemplateUtil.findByGroupId(context.getGroupId());

			itr = obj.iterator();

			while (itr.hasNext()) {
				JournalTemplate template = (JournalTemplate)itr.next();

				String templatePrimaryKey = getPrimaryKey(
					template.getGroupId(), template.getTemplateId());

				if (context.addPrimaryKey(
						JournalTemplate.class, templatePrimaryKey)) {

					itr.remove();
				}
			}

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

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_JOURNAL_DATA,
			_enableImport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		try {
			JournalCreationStrategy creationStrategy =
				JournalCreationStrategyFactory.getInstance();

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

				article.setGroupId(context.getGroupId());

				if (JournalArticleUtil.fetchByPrimaryKey(
						article.getPrimaryKey()) == null) {

					article.setNew(true);

					long authorId = creationStrategy.getAuthorUserId(
						context.getCompanyId(), context.getGroupId(), article);

					if (authorId > 0) {
						article.setUserId(authorId);
						article.setUserName(
							creationStrategy.getAuthorUserName(
								context.getCompanyId(), context.getGroupId(),
								article));
					}

					long approvedById = creationStrategy.getApprovalUserId(
						context.getCompanyId(), context.getGroupId(), article);

					if (approvedById > 0) {
						article.setApprovedByUserId(approvedById);
						article.setApprovedByUserName(
							creationStrategy.getApprovalUserName(
								context.getCompanyId(), context.getGroupId(),
								article));
						article.setApproved(true);
					}
					else {
						article.setApprovedByUserId(0);
						article.setApprovedByUserName(null);
						article.setApproved(false);
					}

					String newContent = creationStrategy.getTransformedContent(
						context.getCompanyId(), context.getGroupId(), article);

					if (newContent != null) {
                        article.setContent(newContent);
                    }

					article = JournalArticleUtil.update(article);

					boolean addCommunityPermissions =
						creationStrategy.addCommunityPermissions(
							context.getCompanyId(), context.getGroupId(),
							article);
					boolean addGuestPermissions =
						creationStrategy.addGuestPermissions(
							context.getCompanyId(), context.getGroupId(),
							article);

					JournalArticleLocalServiceUtil.addArticleResources(
						article, addCommunityPermissions, addGuestPermissions);
				}
				else {
					JournalArticleUtil.update(article, true);
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

				structure.setGroupId(context.getGroupId());

				if (JournalStructureUtil.fetchByPrimaryKey(
						structure.getPrimaryKey()) == null) {

					structure.setNew(true);

					long authorId = creationStrategy.getAuthorUserId(
						context.getCompanyId(), context.getGroupId(),
						structure);

					if (authorId > 0) {
						structure.setUserId(authorId);
						structure.setUserName(
							creationStrategy.getAuthorUserName(
								context.getCompanyId(), context.getGroupId(),
								structure));
					}

					structure = JournalStructureUtil.update(structure);

					boolean addCommunityPermissions =
						creationStrategy.addCommunityPermissions(
							context.getCompanyId(), context.getGroupId(),
							structure);
					boolean addGuestPermissions =
						creationStrategy.addGuestPermissions(
							context.getCompanyId(), context.getGroupId(),
							structure);

					JournalStructureLocalServiceUtil.addStructureResources(
						structure, addCommunityPermissions,
						addGuestPermissions);
				}
				else {
					JournalStructureUtil.update(structure, true);
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

				template.setGroupId(context.getGroupId());

				if (JournalTemplateUtil.fetchByPrimaryKey(
						template.getPrimaryKey()) == null) {

					template.setNew(true);

					long authorId = creationStrategy.getAuthorUserId(
						context.getCompanyId(), context.getGroupId(), template);

					if (authorId > 0) {
						template.setUserId(authorId);
						template.setUserName(
							creationStrategy.getAuthorUserName(
								context.getCompanyId(), context.getGroupId(),
								template));
					}

					template = JournalTemplateUtil.update(template);

					boolean addCommunityPermissions =
						creationStrategy.addCommunityPermissions(
							context.getCompanyId(), context.getGroupId(),
							template);
					boolean addGuestPermissions =
						creationStrategy.addGuestPermissions(
							context.getCompanyId(), context.getGroupId(),
							template);

					JournalTemplateLocalServiceUtil.addTemplateResources(
						template, addCommunityPermissions, addGuestPermissions);
				}
				else {
					JournalTemplateUtil.update(template, true);
				}
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected String getPrimaryKey(long groupId, String key) {
		StringMaker sm = new StringMaker();

		sm.append(groupId);
		sm.append(StringPool.POUND);
		sm.append(key);

		return sm.toString();
	}

	private static final String _EXPORT_JOURNAL_DATA =
		"export-" + PortletKeys.JOURNAL + "-data";

	private static final String _IMPORT_JOURNAL_DATA =
		"import-" + PortletKeys.JOURNAL + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_JOURNAL_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_JOURNAL_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(JournalPortletDataHandlerImpl.class);

}