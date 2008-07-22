/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

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
 * <p>
 * Provides the Journal Content portlet export and import functionality, which
 * is to clone the article, structure, and template referenced in the
 * Journal Content portlet if the article is associated with the layout's group.
 * Upon import, a new instance of the corresponding article, structure, and
 * template will be created or updated. The author of the newly created
 * objects are determined by the JournalCreationStrategy class defined in
 * <i>portal.properties</i>.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from from
 * <code>JournalPortletDataHandlerImpl</code> in that it only exports articles
 * referenced in Journal Content portlets. Articles not displayed in Journal
 * Content portlets will not be exported unless
 * <code>JournalPortletDataHandlerImpl</code> is activated.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Raymond Augé
 * @author Bruno Farache
 *
 * @see com.liferay.portal.kernel.lar.PortletDataHandler
 * @see com.liferay.portlet.journal.lar.JournalCreationStrategy
 * @see com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl
 *
 */
public class JournalContentPortletDataHandlerImpl
	implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			prefs.setValue("group-id", StringPool.BLANK);
			prefs.setValue("article-id", StringPool.BLANK);

			return prefs;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

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
					_log.warn(nsae);
				}
			}

			if (article == null) {
				return StringPool.BLANK;
			}

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("journal-content-data");

			Element dlFoldersEl = root.addElement("dl-folders");
			Element dlFilesEl = root.addElement("dl-file-entries");
			Element dlFileRanksEl = root.addElement("dl-file-ranks");
			Element igFoldersEl = root.addElement("ig-folders");
			Element igImagesEl = root.addElement("ig-images");

			JournalPortletDataHandlerImpl.exportArticle(
				context, root, dlFoldersEl, dlFilesEl, dlFileRanksEl,
				igFoldersEl, igImagesEl, article);

			String structureId = article.getStructureId();

			if (Validator.isNotNull(structureId)) {
				JournalStructure structure = JournalStructureUtil.findByG_S(
					article.getGroupId(), structureId);

				JournalPortletDataHandlerImpl.exportStructure(
					context, root, structure);
			}

			String templateId = article.getTemplateId();

			if (Validator.isNotNull(templateId)) {
				JournalTemplate template = JournalTemplateUtil.findByG_T(
					article.getGroupId(), templateId);

				JournalPortletDataHandlerImpl.exportTemplate(
					context, root, template);
			}

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_selectedArticles, _embeddedAssets, _images, _comments, _ratings,
			_tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_selectedArticles, _images, _comments, _ratings, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			if (Validator.isNull(data)) {
				return null;
			}

			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			Element structureEl = root.element("structure");

			Map<String, String> structureIds = context.getNewPrimaryKeysMap(
				JournalStructure.class);

			if (structureEl != null) {
				JournalPortletDataHandlerImpl.importStructure(
					context, structureIds, structureEl);
			}

			Element templateEl = root.element("template");

			Map<String, String> templateIds = context.getNewPrimaryKeysMap(
				JournalTemplate.class);

			if (templateEl != null) {
				JournalPortletDataHandlerImpl.importTemplate(
					context, structureIds, templateIds, templateEl);
			}

			Element articleEl = root.element("article");

			Map<String, String> articleIds = context.getNewPrimaryKeysMap(
				JournalArticle.class);

			if (articleEl != null) {
				JournalPortletDataHandlerImpl.importArticle(
					context, structureIds, templateIds, articleIds, articleEl);
			}

			String articleId = prefs.getValue("article-id", StringPool.BLANK);

			if (Validator.isNotNull(articleId)) {
				articleId = MapUtil.getString(articleIds, articleId, articleId);

				prefs.setValue(
					"group-id", String.valueOf(context.getGroupId()));
				prefs.setValue("article-id", articleId);
			}

			List<Element> dlFolderEls = root.element("dl-folders").elements(
				"folder");

			Map<Long, Long> dlFolderPKs = context.getNewPrimaryKeysMap(
				DLFolder.class);

			for (Element folderEl : dlFolderEls) {
				String path = folderEl.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					DLFolder folder = (DLFolder)context.getZipEntryAsObject(
						path);

					DLPortletDataHandlerImpl.importFolder(
						context, dlFolderPKs, folder);
				}
			}

			List<Element> fileEntryEls = root.element(
				"dl-file-entries").elements("file-entry");

			Map<String, String> fileEntryNames = context.getNewPrimaryKeysMap(
				DLFileEntry.class);

			for (Element fileEntryEl : fileEntryEls) {
				String path = fileEntryEl.attributeValue("path");
				String binPath = fileEntryEl.attributeValue("bin-path");

				if (context.isPathNotProcessed(path)) {
					DLFileEntry fileEntry =
						(DLFileEntry)context.getZipEntryAsObject(path);

					DLPortletDataHandlerImpl.importFileEntry(
						context, dlFolderPKs, fileEntryNames, fileEntry,
						binPath);
				}
			}

			List<Element> fileRankEls = root.element("dl-file-ranks").elements(
				"file-rank");

			for (Element fileRankEl : fileRankEls) {
				String path = fileRankEl.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					DLFileRank fileRank =
						(DLFileRank)context.getZipEntryAsObject(path);

					DLPortletDataHandlerImpl.importFileRank(
						context, dlFolderPKs, fileEntryNames, fileRank);
				}
			}

			List<Element> folderEls = root.element("ig-folders").elements(
				"folder");

			Map<Long, Long> folderPKs = context.getNewPrimaryKeysMap(
				IGFolder.class);

			for (Element folderEl : folderEls) {
				String path = folderEl.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					IGFolder folder = (IGFolder)context.getZipEntryAsObject(
						path);

					IGPortletDataHandlerImpl.importFolder(
						context, folderPKs, folder);
				}
			}

			List<Element> imageEls = root.element("ig-images").elements(
				"image");

			for (Element imageEl : imageEls) {
				String path = imageEl.attributeValue("path");
				String binPath = imageEl.attributeValue("bin-path");

				if (context.isPathNotProcessed(path)) {
					IGImage image = (IGImage)context.getZipEntryAsObject(path);

					IGPortletDataHandlerImpl.importImage(
						context, folderPKs, image, binPath);
				}
			}

			return prefs;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return true;
	}

	private static final String _NAMESPACE = "journal";

	private static final PortletDataHandlerBoolean _selectedArticles =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "selected-articles", true, true);

	private static final PortletDataHandlerBoolean _embeddedAssets =
		new PortletDataHandlerBoolean(_NAMESPACE, "embedded-assets");

	private static final PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log =
		LogFactory.getLog(JournalContentPortletDataHandlerImpl.class);

}