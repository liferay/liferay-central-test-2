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
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.MapUtil;

import com.thoughtworks.xstream.XStream;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="JournalContentPortletDataHandlerImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Joel Kozikowski
 * @author Raymond Augé
 * @author Bruno Farache
 *
 * @see com.liferay.portlet.journal.lar.JournalCreationStrategy
 * @see com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl
 * @see com.liferay.portal.kernel.lar.PortletDataHandler
 *
 */
public class JournalContentPortletDataHandlerImpl
	implements PortletDataHandler {

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
			parameterMap, _EXPORT_JOURNAL_CONTENT_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData && !staging) {
			return null;
		}

		try {
			String articleId = prefs.getValue("article-id", null);

			if (articleId == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No article id found in preferences of portlet " +
							portletId);
				}

				return StringPool.BLANK;
			}

			long articleGroupId = GetterUtil.getLong(
				prefs.getValue("group-id", StringPool.BLANK));

			if (articleGroupId <= 0) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No group id found in preferences of portlet " +
							portletId);
				}

				return StringPool.BLANK;
			}

			JournalArticle article = null;

			try {
				article = JournalArticleLocalServiceUtil.getLatestArticle(
					articleGroupId, articleId);
			}
			catch (NoSuchArticleException nsae) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No article found with company id " +
							context.getCompanyId() + ", group id " +
								articleGroupId + " and article id " +
									articleId);
				}
			}

			if (article == null) {
				return StringPool.BLANK;
			}

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("journal-content");

			List content = root.content();

			if (!context.addPrimaryKey(
					JournalArticle.class, article.getPrimaryKeyObj())) {

				JournalPortletDataHandlerImpl.exportArticle(context, article);

				String xml = xStream.toXML(article);

				Document tempDoc = PortalUtil.readDocumentFromXML(xml);

				content.add(tempDoc.getRootElement().createCopy());
			}

			String structureId = article.getStructureId();

			if (Validator.isNotNull(structureId)) {
				JournalStructure structure = JournalStructureUtil.findByG_S(
					article.getGroupId(), structureId);

				if (!context.addPrimaryKey(
						JournalStructure.class, article.getPrimaryKeyObj())) {

					JournalPortletDataHandlerImpl.exportStructure(structure);

					String xml = xStream.toXML(structure);

					Document tempDoc = PortalUtil.readDocumentFromXML(xml);

					content.add(tempDoc.getRootElement().createCopy());
				}
			}

			String templateId = article.getTemplateId();

			if (Validator.isNotNull(templateId)) {
				JournalTemplate template = JournalTemplateUtil.findByG_T(
					article.getGroupId(), templateId);

				if (!context.addPrimaryKey(
						JournalTemplate.class, template.getPrimaryKeyObj())) {

					JournalPortletDataHandlerImpl.exportTemplate(
						context, template);

					String xml = xStream.toXML(template);

					Document tempDoc = PortalUtil.readDocumentFromXML(xml);

					content.add(tempDoc.getRootElement().createCopy());
				}
			}

			return doc.asXML();

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
			parameterMap, _IMPORT_JOURNAL_CONTENT_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData && !staging) {
			return null;
		}

		boolean mergeData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.MERGE_DATA);

		if (!mergeData){
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Merge data option wasn't selected, this can cause " +
						"duplication of entities because there's no way to " +
							"check if they already exist.");
			}
		}

		try {
			if (Validator.isNull(data)) {
				return null;
			}

			XStream xStream = new XStream();

			Document doc = PortalUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			Element el = root.element(JournalStructureImpl.class.getName());

			Document tempDoc = DocumentHelper.createDocument();

			Map structurePKs = CollectionFactory.getHashMap();

			if (el != null) {
				tempDoc.content().add(el.createCopy());

				JournalStructure structure = (JournalStructure)xStream.fromXML(
					tempDoc.asXML());

				JournalPortletDataHandlerImpl.importStructure(
					context, mergeData, structurePKs, structure);
			}

			el = root.element(JournalTemplateImpl.class.getName());

			Map templatePKs = CollectionFactory.getHashMap();

			if (el != null) {
				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				JournalTemplate template = (JournalTemplate)xStream.fromXML(
					tempDoc.asXML());

				JournalPortletDataHandlerImpl.importTemplate(
					context, mergeData, structurePKs, templatePKs, template);
			}

			el = root.element(JournalArticleImpl.class.getName());

			if (el != null) {
				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				JournalArticle article = (JournalArticle)xStream.fromXML(
					tempDoc.asXML());

				article = JournalPortletDataHandlerImpl.importArticle(
					context, mergeData, structurePKs, templatePKs, article);

				prefs.setValue(
					"group-id", String.valueOf(context.getGroupId()));
				prefs.setValue("article-id", article.getArticleId());
			}

			return prefs;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	private static final String _EXPORT_JOURNAL_CONTENT_DATA =
		"export-" + PortletKeys.JOURNAL_CONTENT + "-data";

	private static final String _IMPORT_JOURNAL_CONTENT_DATA =
		"import-" + PortletKeys.JOURNAL_CONTENT + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_JOURNAL_CONTENT_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_JOURNAL_CONTENT_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(JournalContentPortletDataHandlerImpl.class);

}