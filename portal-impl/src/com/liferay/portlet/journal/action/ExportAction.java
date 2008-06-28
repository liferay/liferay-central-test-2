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

package com.liferay.portlet.journal.action;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upgrade.util.IdReplacer;
import com.liferay.portal.upgrade.util.MemoryValueMapper;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.util.comparator.LayoutComparator;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchUtil;
import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExportAction extends Action {

	public static final String COMPANY_ID = "liferay.com";

	public static final long DEFAULT_GROUP_ID = 14;

	public static final long DEFAULT_USER_ID = 2;

	public static final String DEFAULT_USER_NAME = "Joe Bloggs";

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (permissionChecker.isOmniadmin()) {
				long groupId = ParamUtil.getLong(
					req, "groupId", DEFAULT_GROUP_ID);

				_primaryKeys.clear();
				_primaryKeyCount = 1500;

				ZipWriter zipWriter = new ZipWriter();

				List<JournalContentSearch> journalContentSearches =
					new ArrayList<JournalContentSearch>();

				insertDataImage(groupId, zipWriter);
				insertDataCMSLayout(groupId, zipWriter, journalContentSearches);
				insertDataCMSContent(
					groupId, zipWriter, journalContentSearches);

				String fileName = "journal.zip";

				ServletResponseUtil.sendFile(res, fileName, zipWriter.finish());
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, req, res);

			return null;
		}
	}

	protected void addColumn(StringBuilder sb, boolean value) {
		//sb.append("'");

		if (value) {
			sb.append("TRUE");
		}
		else {
			sb.append("FALSE");
		}

		//sb.append("', ");
		sb.append(", ");
	}

	protected void addColumn(StringBuilder sb, double value) {
		sb.append(value);
		sb.append(", ");
	}

	protected void addColumn(StringBuilder sb, float value) {
		sb.append(value);
		sb.append(", ");
	}

	protected void addColumn(StringBuilder sb, int value) {
		sb.append(value);
		sb.append(", ");
	}

	protected void addColumn(StringBuilder sb, long value) {
		sb.append(value);
		sb.append(", ");
	}

	protected void addColumn(StringBuilder sb, short value) {
		sb.append(value);
		sb.append(", ");
	}

	protected void addColumn(StringBuilder sb, Date value) {
		addColumn(sb, value, true);
	}

	protected void addColumn(StringBuilder sb, Date value, boolean current) {
		if (current) {
			sb.append("CURRENT_TIMESTAMP, ");
		}
		else {
			sb.append("SPECIFIC_TIMESTAMP_");
			sb.append(Time.getSimpleDate(value, "yyyyMMddHHmmss"));
			sb.append(", ");
		}
	}

	protected void addColumn(StringBuilder sb, String value) {
		addColumn(sb, value, true);
	}

	protected void addColumn(StringBuilder sb, String value, boolean format) {
		if (format) {
			value = StringUtil.replace(
				value,
				new String[] {"\\", "'", "\"", "\n", "\r"},
				new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"});
		}

		value = GetterUtil.getString(value);

		sb.append("'");
		sb.append(value);
		sb.append("', ");
	}

	protected void addPKColumn(StringBuilder sb, long value) {
		sb.append(getNewPrimaryKey(value));
		sb.append(", ");
	}

	protected void addPKColumn(StringBuilder sb, String value) {
		sb.append("'");
		sb.append(getNewPrimaryKey(value));
		sb.append("', ");
	}

	protected String getNewPrimaryKey(String pk) {
		if (Validator.isNumber(pk)) {
			long pkLong = GetterUtil.getLong(pk);

			return String.valueOf(getNewPrimaryKey(pkLong));
		}
		else {
			return pk;
		}
	}

	protected long getNewPrimaryKey(long pk) {
		Long newPk = _primaryKeys.get(pk);

		if (newPk == null) {
			newPk = new Long(_primaryKeyCount);

			_primaryKeyCount++;

			_primaryKeys.put(pk, newPk);
		}

		return newPk.longValue();
	}

	protected void insertDataCMSContent(
			long groupId, ZipWriter zipWriter,
			List<JournalContentSearch> journalContentSearches)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		List<IGImage> igImages = new ArrayList<IGImage>();

		List<IGFolder> igFolders = IGFolderLocalServiceUtil.getFolders(
			groupId);

		for (IGFolder folder : igFolders) {
			sb.append("insert into IGFolder (");
			sb.append("folderId, groupId, companyId, userId, createDate, ");
			sb.append("modifiedDate, parentFolderId, name");
			sb.append(") values (");
			addPKColumn(sb, folder.getFolderId());
			addColumn(sb, folder.getGroupId());
			addColumn(sb, folder.getCompanyId());
			//addColumn(sb, folder.getUserId());
			addColumn(sb, DEFAULT_USER_ID);
			addColumn(sb, folder.getCreateDate());
			addColumn(sb, folder.getModifiedDate());
			addColumn(sb, folder.getParentFolderId());
			addColumn(sb, folder.getName());
			removeTrailingComma(sb);
			sb.append(");\n");

			igImages.addAll(
				IGImageLocalServiceUtil.getImages(folder.getFolderId()));
		}

		sb.append("\n");

		Collections.sort(igImages);

		for (IGImage image : igImages) {
			sb.append("insert into IGImage (");
			sb.append("imageId, companyId, userId, createDate, modifiedDate, ");
			sb.append("folderId, description, smallImageId, largeImageId");
			sb.append(") values (");
			addPKColumn(sb, image.getImageId());
			addColumn(sb, image.getCompanyId());
			//addColumn(sb, image.getUserId());
			addColumn(sb, DEFAULT_USER_ID);
			addColumn(sb, image.getCreateDate());
			addColumn(sb, image.getModifiedDate());
			addPKColumn(sb, image.getFolderId());
			addColumn(sb, image.getDescription());
			addPKColumn(sb, image.getSmallImageId());
			addPKColumn(sb, image.getLargeImageId());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(groupId);

		for (JournalArticle article : articles) {
			if (article.isApproved() &&
				JournalArticleLocalServiceUtil.isLatestVersion(
					article.getGroupId(), article.getArticleId(),
					article.getVersion())) {

				sb.append("insert into JournalArticle (");
				sb.append("id_, resourcePrimKey, groupId, companyId, userId, ");
				sb.append("userName, createDate, modifiedDate, articleId, ");
				sb.append("version, title, description, content, type_, ");
				sb.append("structureId, templateId, displayDate, approved, ");
				sb.append("approvedByUserId, approvedByUserName, expired, ");
				sb.append("indexable");
				sb.append(") values (");
				addPKColumn(sb, article.getId());
				addPKColumn(sb, article.getResourcePrimKey());
				addColumn(sb, article.getGroupId());
				addColumn(sb, article.getCompanyId());
				//addColumn(sb, article.getUserId());
				//addColumn(sb, article.getUserName());
				addColumn(sb, DEFAULT_USER_ID);
				addColumn(sb, DEFAULT_USER_NAME);
				addColumn(sb, article.getCreateDate());
				addColumn(sb, article.getModifiedDate());
				addPKColumn(sb, article.getArticleId());
				addColumn(sb, JournalArticleImpl.DEFAULT_VERSION);
				addColumn(sb, article.getTitle());
				addColumn(sb, article.getDescription());
				addColumn(sb, replaceIds(article.getContent()));
				addColumn(sb, article.getType());
				addPKColumn(sb, article.getStructureId());
				addPKColumn(sb, article.getTemplateId());
				addColumn(sb, article.getDisplayDate(), false);
				addColumn(sb, article.getApproved());
				//addColumn(sb, article.getApprovedByUserId());
				//addColumn(sb, article.getApprovedByUserName());
				addColumn(sb, DEFAULT_USER_ID);
				addColumn(sb, DEFAULT_USER_NAME);
				//addColumn(sb, article.getApprovedDate(), false);
				addColumn(sb, article.getExpired());
				//addColumn(sb, article.getExpirationDate(), false);
				//addColumn(sb, article.getReviewDate(), false);
				addColumn(sb, article.getIndexable());
				removeTrailingComma(sb);
				sb.append(");\n");
			}
		}

		sb.append("\n");

		List<JournalArticleImage> articleImages =
			JournalArticleImageLocalServiceUtil.getArticleImages(groupId);

		for (JournalArticleImage articleImage : articleImages) {
			sb.append("insert into JournalArticleImage (");
			sb.append("articleImageId, groupId, articleId, version, elName, ");
			sb.append("languageId, tempImage");
			sb.append(") values (");
			addPKColumn(sb, articleImage.getArticleImageId());
			addColumn(sb, articleImage.getGroupId());
			addPKColumn(sb, articleImage.getArticleId());
			addColumn(sb, articleImage.getVersion());
			addColumn(sb, articleImage.getElName());
			addColumn(sb, articleImage.getLanguageId());
			addColumn(sb, articleImage.getTempImage());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		List<JournalArticleResource> articleResources =
			JournalArticleResourceLocalServiceUtil.getArticleResources(groupId);

		for (JournalArticleResource articleResource : articleResources) {
			sb.append("insert into JournalArticleResource (");
			sb.append("resourcePrimKey, groupId, articleId");
			sb.append(") values (");
			addPKColumn(sb, articleResource.getResourcePrimKey());
			addColumn(sb, articleResource.getGroupId());
			addPKColumn(sb, articleResource.getArticleId());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		for (JournalContentSearch contentSearch : journalContentSearches) {
			sb.append("insert into JournalContentSearch (");
			sb.append("contentSearchId, groupId, companyId, privateLayout, ");
			sb.append("layoutId, portletId, articleId");
			sb.append(") values (");
			addPKColumn(sb, contentSearch.getContentSearchId());
			addColumn(sb, contentSearch.getGroupId());
			addColumn(sb, contentSearch.getCompanyId());
			addColumn(sb, contentSearch.isPrivateLayout());
			addColumn(sb, contentSearch.getLayoutId());
			addColumn(sb, contentSearch.getPortletId());
			addPKColumn(sb, contentSearch.getArticleId());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		List<JournalFeed> feeds = JournalFeedLocalServiceUtil.getFeeds(groupId);

		for (JournalFeed feed : feeds) {
			sb.append("insert into JournalFeed (");
			sb.append("id_, groupId, companyId, userId, userName, ");
			sb.append("createDate, modifiedDate, feedId, name, description, ");
			sb.append("type_, structureId, templateId, rendererTemplateId, ");
			sb.append("delta, orderByCol, orderByType, ");
			sb.append("targetLayoutFriendlyUrl, targetPortletId, ");
			sb.append("contentField, feedType, feedVersion");
			sb.append(") values (");
			addPKColumn(sb, feed.getId());
			addColumn(sb, feed.getGroupId());
			addColumn(sb, feed.getCompanyId());
			//addColumn(sb, feed.getUserId());
			//addColumn(sb, feed.getUserName());
			addColumn(sb, DEFAULT_USER_ID);
			addColumn(sb, DEFAULT_USER_NAME);
			addColumn(sb, feed.getCreateDate());
			addColumn(sb, feed.getModifiedDate());
			addPKColumn(sb, feed.getFeedId());
			addColumn(sb, feed.getName());
			addColumn(sb, feed.getDescription());
			addColumn(sb, feed.getType());
			addPKColumn(sb, feed.getStructureId());
			addPKColumn(sb, feed.getTemplateId());
			addPKColumn(sb, feed.getRendererTemplateId());
			addColumn(sb, feed.getDelta());
			addColumn(sb, feed.getOrderByCol());
			addColumn(sb, feed.getOrderByType());
			addColumn(sb, feed.getTargetLayoutFriendlyUrl());
			addColumn(sb, feed.getTargetPortletId());
			addColumn(sb, feed.getContentField());
			addColumn(sb, feed.getFeedType());
			addColumn(sb, feed.getFeedVersion());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		List<JournalStructure> structures =
			JournalStructureLocalServiceUtil.getStructures(groupId);

		for (JournalStructure structure : structures) {
			sb.append("insert into JournalStructure (");
			sb.append("id_, groupId, companyId, userId, userName, ");
			sb.append("createDate, modifiedDate, structureId, name, ");
			sb.append("description, xsd");
			sb.append(") values (");
			addPKColumn(sb, structure.getId());
			addColumn(sb, structure.getGroupId());
			addColumn(sb, structure.getCompanyId());
			//addColumn(sb, structure.getUserId());
			//addColumn(sb, structure.getUserName());
			addColumn(sb, DEFAULT_USER_ID);
			addColumn(sb, DEFAULT_USER_NAME);
			addColumn(sb, structure.getCreateDate());
			addColumn(sb, structure.getModifiedDate());
			addPKColumn(sb, structure.getStructureId());
			addColumn(sb, structure.getName());
			addColumn(sb, structure.getDescription());
			addColumn(sb, replaceIds(structure.getXsd()));
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates(groupId);

		for (JournalTemplate template : templates) {
			sb.append("insert into JournalTemplate (");
			sb.append("id_, groupId, companyId, userId, userName, ");
			sb.append("createDate, modifiedDate, templateId, structureId, ");
			sb.append("name, description, xsl, langType, smallImage, ");
			sb.append("smallImageId, smallImageURL");
			sb.append(") values (");
			addPKColumn(sb, template.getId());
			addColumn(sb, template.getGroupId());
			addColumn(sb, template.getCompanyId());
			//addColumn(sb, template.getUserId());
			//addColumn(sb, template.getUserName());
			addColumn(sb, DEFAULT_USER_ID);
			addColumn(sb, DEFAULT_USER_NAME);
			addColumn(sb, template.getCreateDate());
			addColumn(sb, template.getModifiedDate());
			addPKColumn(sb, template.getTemplateId());
			addPKColumn(sb, template.getStructureId());
			addColumn(sb, template.getName());
			addColumn(sb, template.getDescription());
			addColumn(sb, replaceIds(template.getXsl()));
			addColumn(sb, template.getLangType());
			addColumn(sb, template.getSmallImage());
			addPKColumn(sb, template.getSmallImageId());
			addColumn(sb, template.getSmallImageURL());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		removeTrailingNewLine(sb);

		zipWriter.addEntry("portal-data-cms-content.sql", sb);
	}

	protected void insertDataCMSLayout(
			long groupId, ZipWriter zipWriter,
			List<JournalContentSearch> journalContentSearches)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, false);

		sb.append("update LayoutSet ");
		sb.append("set themeId = 'liferayjedi_WAR_liferayjeditheme', ");
		sb.append("pageCount = ");
		sb.append(layouts.size());
		sb.append(" where groupId = ");
		sb.append(groupId);
		sb.append(" and privateLayout = FALSE;\n\n");

		Collections.sort(layouts, new LayoutComparator(true));

		for (Layout layout : layouts) {
			getNewPrimaryKey(layout.getPlid());
		}

		for (Layout layout : layouts) {
			Properties props = layout.getTypeSettingsProperties();

			long linkToPlid = GetterUtil.getLong(
				props.getProperty("linkToPlid"));

			if (linkToPlid > 0) {
				long newLinkToPlid = getNewPrimaryKey(linkToPlid);

				props.setProperty("linkToPlid", String.valueOf(newLinkToPlid));
			}

			sb.append("insert into Layout (");
			sb.append("plid, groupId, companyId, privateLayout, layoutId, ");
			sb.append("parentLayoutId, name, title, type_, typeSettings, ");
			sb.append("hidden_, friendlyURL, iconImage, iconImageId, ");
			sb.append("css, priority");
			sb.append(") values (");
			addPKColumn(sb, layout.getPlid());
			addColumn(sb, layout.getGroupId());
			addColumn(sb, layout.getCompanyId());
			addColumn(sb, layout.isPrivateLayout());
			addColumn(sb, layout.getLayoutId());
			addColumn(sb, layout.getParentLayoutId());
			addColumn(sb, layout.getName());
			addColumn(sb, layout.getTitle());
			addColumn(sb, layout.getType());
			addColumn(sb, layout.getTypeSettings());
			addColumn(sb, layout.isHidden());
			addColumn(sb, layout.getFriendlyURL());
			addColumn(sb, layout.isIconImage());
			addColumn(sb, layout.getIconImageId());
			addColumn(sb, layout.getCss());
			addColumn(sb, layout.getPriority());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			List<String> portletIds = layoutType.getPortletIds();

			Collections.sort(portletIds);

			for (int i = 0; i < portletIds.size(); i++) {
				String portletId = portletIds.get(i);

				try {
					PortletPreferences portletPreferences =
						PortletPreferencesLocalServiceUtil.
							getPortletPreferences(
								PortletKeys.PREFS_OWNER_ID_DEFAULT,
								PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
								layout.getPlid(), portletId);

					String prefsXml = portletPreferences.getPreferences();

					PortletPreferencesImpl prefs = (PortletPreferencesImpl)
						PortletPreferencesSerializer.fromDefaultXML(
							portletPreferences.getPreferences());

					String articleId =
						prefs.getValue("article-id", StringPool.BLANK);

					articleId = articleId.toUpperCase();

					if (Validator.isNotNull(articleId)) {
						if (!JournalArticleLocalServiceUtil.hasArticle(
								layout.getGroupId(), articleId)) {

							continue;
						}

						// Make sure article id is upper case in the preferences
						// XML

						prefs.setValue(
							"article-id", getNewPrimaryKey(articleId));

						prefsXml = PortletPreferencesSerializer.toXML(prefs);

						// Add to the journal content search list

						long contentSearchId =
							CounterLocalServiceUtil.increment();

						JournalContentSearch journalContentSearch =
							JournalContentSearchUtil.create(contentSearchId);

						journalContentSearch.setContentSearchId(
							contentSearchId);
						journalContentSearch.setCompanyId(
							layout.getCompanyId());
						journalContentSearch.setGroupId(layout.getGroupId());
						journalContentSearch.setPrivateLayout(
							layout.isPrivateLayout());
						journalContentSearch.setPortletId(portletId);
						journalContentSearch.setLayoutId(layout.getLayoutId());
						journalContentSearch.setPortletId(portletId);
						journalContentSearch.setArticleId(
							getNewPrimaryKey(articleId));

						journalContentSearches.add(journalContentSearch);
					}

					sb.append("insert into PortletPreferences (");
					sb.append("portletPreferencesId, ownerId, ownerType, ");
					sb.append("plid, portletId, preferences");
					sb.append(") values (");
					addPKColumn(
						sb, portletPreferences.getPortletPreferencesId());
					addColumn(sb, portletPreferences.getOwnerId());
					addColumn(sb, portletPreferences.getOwnerType());
					addPKColumn(sb, portletPreferences.getPlid());
					addColumn(sb, portletId);
					addColumn(sb, prefsXml);
					removeTrailingComma(sb);
					sb.append(");\n");
				}
				catch (NoSuchPortletPreferencesException nsppe) {
					_log.warn(nsppe.getMessage());
				}
			}

			sb.append("\n");
		}

		removeTrailingNewLine(sb);
		removeTrailingNewLine(sb);

		zipWriter.addEntry("portal-data-cms-layout.sql", sb);
	}

	protected void insertDataImage(long groupId, ZipWriter zipWriter)
		throws Exception {

		StringBuilder sm1 = new StringBuilder();
		StringBuilder sm2 = new StringBuilder();

		List<Image> images = ImageLocalServiceUtil.getImagesBySize(70000);

		for (Image image : images) {
			long imageId = image.getImageId();

			try {
				UserLocalServiceUtil.getUserByPortraitId(imageId);

				continue;
			}
			catch (NoSuchUserException nsue) {
			}

			try {
				IGImage igImage =
					IGImageLocalServiceUtil.getImageBySmallImageId(imageId);

				IGFolder igFolder = igImage.getFolder();

				if (igFolder.getGroupId() != groupId) {
					continue;
				}
			}
			catch (NoSuchImageException nsie) {
			}

			try {
				IGImage igImage =
					IGImageLocalServiceUtil.getImageByLargeImageId(imageId);

				IGFolder igFolder = igImage.getFolder();

				if (igFolder.getGroupId() != groupId) {
					continue;
				}
			}
			catch (NoSuchImageException nsie) {
			}

			try {
				JournalArticleImage journalArticleImage =
					JournalArticleImageLocalServiceUtil.getArticleImage(
						imageId);

				if (journalArticleImage.getGroupId() != groupId) {
					continue;
				}
			}
			catch (NoSuchArticleImageException nsaie) {
			}

			try {
				SCProductScreenshotLocalServiceUtil.
					getProductScreenshotByFullImageId(imageId);

				continue;
			}
			catch (NoSuchProductScreenshotException nspse) {
			}

			try {
				SCProductScreenshotLocalServiceUtil.
					getProductScreenshotByThumbnailId(imageId);

				continue;
			}
			catch (NoSuchProductScreenshotException nspse) {
			}

			sm1.append("insert into Image (");
			sm1.append("imageId, modifiedDate, text_, type_, height, width, ");
			sm1.append("size_");
			sm1.append(") values (");
			addPKColumn(sm1, imageId);
			addColumn(sm1, image.getModifiedDate());
			addColumn(sm1, image.getText(), false);
			addColumn(sm1, image.getType());
			addColumn(sm1, image.getHeight());
			addColumn(sm1, image.getWidth());
			addColumn(sm1, image.getSize());
			removeTrailingComma(sm1);
			sm1.append(");\n");

			sm2.append("<img alt=\"");
			sm2.append(imageId);
			sm2.append("\" src=\"http://localhost:8080/image?img_id=");
			sm2.append(imageId);
			sm2.append("\" /><br />\n");
		}

		removeTrailingNewLine(sm1);

		zipWriter.addEntry("portal-data-image.sql", sm1);
		zipWriter.addEntry("portal-data-image.html", sm2);
	}

	protected void removeTrailingComma(StringBuilder sb) {
		sb.delete(sb.length() - 2, sb.length());
	}

	protected void removeTrailingNewLine(StringBuilder sb) {
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
	}

	protected String replaceIds(String content) throws Exception {
		content = IdReplacer.replaceLongIds(content, "?img_id=", _valueMapper);

		return content;
	}

	private static Log _log = LogFactory.getLog(ExportAction.class);

	private MemoryValueMapper _valueMapper = new MemoryValueMapper();
	private Map<Long, Long> _primaryKeys = _valueMapper.getMap();
	private int _primaryKeyCount;

}