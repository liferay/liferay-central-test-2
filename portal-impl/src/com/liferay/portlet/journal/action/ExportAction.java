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

package com.liferay.portlet.journal.action;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.util.comparator.LayoutComparator;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

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

	public static final long DEFAULT_SITE_GROUP_ID = 1;

	public static final long  DEFAULT_CMS_GROUP_ID = 1;

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
				long siteGroupId = ParamUtil.getLong(
					req, "siteGroupId", DEFAULT_SITE_GROUP_ID);

				long cmsGroupId = ParamUtil.getLong(
					req, "cmsGroupId", DEFAULT_CMS_GROUP_ID);

				ZipWriter zipWriter = new ZipWriter();

				List journalContentSearches = new ArrayList();

				insertDataCMSLayout(
					siteGroupId, zipWriter, journalContentSearches);
				insertDataCMSContent(
					cmsGroupId, zipWriter, journalContentSearches);
				insertDataImage(zipWriter);

				String fileName = "journal.zip";

				ServletResponseUtil.sendFile(res, fileName, zipWriter.finish());
			}

			return null;
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(Constants.COMMON_ERROR);
		}
	}

	protected void addColumn(StringMaker sm, boolean value) {
		//sm.append("'");

		if (value) {
			sm.append("TRUE");
		}
		else {
			sm.append("FALSE");
		}

		//sm.append("', ");
		sm.append(", ");
	}

	protected void addColumn(StringMaker sm, double value) {
		sm.append(value);
		sm.append(", ");
	}

	protected void addColumn(StringMaker sm, float value) {
		sm.append(value);
		sm.append(", ");
	}

	protected void addColumn(StringMaker sm, int value) {
		sm.append(value);
		sm.append(", ");
	}

	protected void addColumn(StringMaker sm, long value) {
		sm.append(value);
		sm.append(", ");
	}

	protected void addColumn(StringMaker sm, short value) {
		sm.append(value);
		sm.append(", ");
	}

	protected void addColumn(StringMaker sm, Date value) {
		addColumn(sm, value, true);
	}

	protected void addColumn(StringMaker sm, Date value, boolean current) {
		if (current) {
			sm.append("CURRENT_TIMESTAMP, ");
		}
		else {
			sm.append("SPECIFIC_TIMESTAMP_");
			sm.append(Time.getSimpleDate(value, "yyyyMMddHHmmss"));
			sm.append(", ");
		}
	}

	protected void addColumn(StringMaker sm, String value) {
		addColumn(sm, value, true);
	}

	protected void addColumn(StringMaker sm, String value, boolean format) {
		if (format) {
			value = StringUtil.replace(
				value,
				new String[] {"\\", "'", "\"", "\n", "\r"},
				new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"});
		}

		value = GetterUtil.getString(value);

		sm.append("'");
		sm.append(value);
		sm.append("', ");
	}

	protected void insertDataCMSContent(
			long cmsGroupId, ZipWriter zipWriter, List journalContentSearches)
		throws Exception {

		StringMaker sm = new StringMaker();

		List igImages = new ArrayList();

		Iterator itr = IGFolderLocalServiceUtil.getFolders(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			IGFolder folder = (IGFolder)itr.next();

			sm.append("insert into IGFolder (");
			sm.append("folderId, groupId, companyId, userId, createDate, ");
			sm.append("modifiedDate, parentFolderId, name");
			sm.append(") values (");
			addColumn(sm, folder.getFolderId());
			addColumn(sm, folder.getGroupId());
			addColumn(sm, folder.getCompanyId());
			addColumn(sm, folder.getUserId());
			addColumn(sm, folder.getCreateDate());
			addColumn(sm, folder.getModifiedDate());
			addColumn(sm, folder.getParentFolderId());
			addColumn(sm, folder.getName());
			removeTrailingComma(sm);
			sm.append(");\n");

			igImages.addAll(
				IGImageLocalServiceUtil.getImages(folder.getFolderId()));
		}

		//sm.append("\n");

		Collections.sort(igImages);

		itr = igImages.iterator();

		while (itr.hasNext()) {
			IGImage image = (IGImage)itr.next();

			sm.append("insert into IGImage (");
			sm.append("imageId, companyId, userId, createDate, modifiedDate, ");
			sm.append("folderId, description, smallImageId, largeImageId");
			sm.append(") values (");
			addColumn(sm, image.getImageId());
			addColumn(sm, image.getCompanyId());
			addColumn(sm, image.getUserId());
			addColumn(sm, image.getCreateDate());
			addColumn(sm, image.getModifiedDate());
			addColumn(sm, image.getFolderId());
			addColumn(sm, image.getDescription());
			addColumn(sm, image.getSmallImageId());
			addColumn(sm, image.getLargeImageId());
			removeTrailingComma(sm);
			sm.append(");\n");
		}

		//sm.append("\n");

		itr = JournalArticleLocalServiceUtil.getArticles(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			JournalArticle article = (JournalArticle)itr.next();

			if (article.isApproved() &&
				JournalArticleLocalServiceUtil.isLatestVersion(
					article.getGroupId(), article.getArticleId(),
					article.getVersion())) {

				sm.append("insert into JournalArticle (");
				sm.append("id_, groupId, companyId, userId, userName, ");
				sm.append("createDate, modifiedDate, articleId, version, ");
				sm.append("title, description, content, type_, structureId, ");
				sm.append("templateId, displayDate, approved, ");
				sm.append("approvedByUserId, approvedByUserName, expired");
				sm.append(") values (");
				addColumn(sm, article.getId());
				addColumn(sm, article.getGroupId());
				addColumn(sm, article.getCompanyId());
				//addColumn(sm, article.getUserId());
				//addColumn(sm, article.getUserName());
				addColumn(sm, 2);
				addColumn(sm, "Joe Bloggs");
				addColumn(sm, article.getCreateDate());
				addColumn(sm, article.getModifiedDate());
				addColumn(sm, article.getArticleId());
				addColumn(sm, JournalArticleImpl.DEFAULT_VERSION);
				addColumn(sm, article.getTitle());
				addColumn(sm, article.getDescription());
				addColumn(sm, article.getContent());
				addColumn(sm, article.getType());
				addColumn(sm, article.getStructureId());
				addColumn(sm, article.getTemplateId());
				addColumn(sm, article.getDisplayDate(), false);
				addColumn(sm, article.getApproved());
				//addColumn(sm, article.getApprovedByUserId());
				//addColumn(sm, article.getApprovedByUserName());
				addColumn(sm, "liferay.com.1");
				addColumn(sm, "Joe Bloggs");
				addColumn(sm, article.getExpired());
				//addColumn(sm, article.getExpirationDate());
				//addColumn(sm, article.getReviewDate());
				removeTrailingComma(sm);
				sm.append(");\n");
			}
		}

		sm.append("\n");

		itr = journalContentSearches.iterator();

		while (itr.hasNext()) {
			JournalContentSearch contentSearch =
				(JournalContentSearch)itr.next();

			sm.append("insert into JournalContentSearch (");
			sm.append("contentSearchId, groupId, companyId, privateLayout, ");
			sm.append("layoutId, portletId, articleId");
			sm.append(") values (");
			addColumn(sm, contentSearch.getContentSearchId());
			addColumn(sm, contentSearch.getGroupId());
			addColumn(sm, contentSearch.getCompanyId());
			addColumn(sm, contentSearch.isPrivateLayout());
			addColumn(sm, contentSearch.getLayoutId());
			addColumn(sm, contentSearch.getPortletId());
			addColumn(sm, contentSearch.getArticleId());

			removeTrailingComma(sm);
			sm.append(");\n");
		}

		sm.append("\n");

		itr = JournalStructureLocalServiceUtil.getStructures(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			JournalStructure structure = (JournalStructure)itr.next();

			sm.append("insert into JournalStructure (");
			sm.append("id_, groupId, companyId, userId, userName, ");
			sm.append("createDate, modifiedDate, structureId, name, ");
			sm.append("description, xsd");
			sm.append(") values (");
			addColumn(sm, structure.getId());
			addColumn(sm, structure.getGroupId());
			addColumn(sm, structure.getCompanyId());
			addColumn(sm, structure.getUserId());
			addColumn(sm, structure.getUserName());
			addColumn(sm, structure.getCreateDate());
			addColumn(sm, structure.getModifiedDate());
			addColumn(sm, structure.getStructureId());
			addColumn(sm, structure.getName());
			addColumn(sm, structure.getDescription());
			addColumn(sm, structure.getXsd());
			removeTrailingComma(sm);
			sm.append(");\n");
		}

		sm.append("\n");

		itr = JournalTemplateLocalServiceUtil.getTemplates(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			JournalTemplate template = (JournalTemplate)itr.next();

			sm.append("insert into JournalTemplate (");
			sm.append("id_, groupId, companyId, userId, userName, ");
			sm.append("createDate, modifiedDate, templateId, structureId, ");
			sm.append("name, description, xsl, langType, smallImage, ");
			sm.append("smallImageId, smallImageURL");
			sm.append(") values (");
			addColumn(sm, template.getId());
			addColumn(sm, template.getGroupId());
			addColumn(sm, template.getCompanyId());
			addColumn(sm, template.getUserId());
			addColumn(sm, template.getUserName());
			addColumn(sm, template.getCreateDate());
			addColumn(sm, template.getModifiedDate());
			addColumn(sm, template.getTemplateId());
			addColumn(sm, template.getStructureId());
			addColumn(sm, template.getName());
			addColumn(sm, template.getDescription());
			addColumn(sm, template.getXsl());
			addColumn(sm, template.getLangType());
			addColumn(sm, template.getSmallImage());
			addColumn(sm, template.getSmallImageId());
			addColumn(sm, template.getSmallImageURL());
			removeTrailingComma(sm);
			sm.append(");\n");
		}

		removeTrailingNewLine(sm);

		zipWriter.addEntry("portal-data-cms-content.sql", sm);
	}

	protected void insertDataCMSLayout(
			long siteGroupId, ZipWriter zipWriter,
			List journalContentSearches)
		throws Exception {

		StringMaker sm = new StringMaker();

		List layouts = LayoutLocalServiceUtil.getLayouts(siteGroupId, false);

		Collections.sort(layouts, new LayoutComparator(true));

		Iterator itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			sm.append("insert into Layout (");
			sm.append("plid, groupId, companyId, privateLayout, layoutId, ");
			sm.append("parentLayoutId, name, title, type_, typeSettings, ");
			sm.append("hidden_, friendlyURL, iconImage, iconImageId, ");
			sm.append("themeId, colorSchemeId, wapThemeId, wapColorSchemeId, ");
			sm.append("css, priority");
			sm.append(") values (");
			addColumn(sm, layout.getPlid());
			addColumn(sm, layout.getGroupId());
			addColumn(sm, layout.getCompanyId());
			addColumn(sm, layout.isPrivateLayout());
			addColumn(sm, layout.getLayoutId());
			addColumn(sm, layout.getParentLayoutId());
			addColumn(sm, layout.getName());
			addColumn(sm, layout.getTitle());
			addColumn(sm, layout.getType());
			addColumn(sm, layout.getTypeSettings());
			addColumn(sm, layout.getHidden());
			addColumn(sm, layout.getFriendlyURL());
			addColumn(sm, layout.isIconImage());
			addColumn(sm, layout.getIconImageId());
			addColumn(sm, layout.getThemeId());
			addColumn(sm, layout.getColorSchemeId());
			addColumn(sm, layout.getWapThemeId());
			addColumn(sm, layout.getWapColorSchemeId());
			addColumn(sm, layout.getCss());
			addColumn(sm, layout.getPriority());
			removeTrailingComma(sm);
			sm.append(");\n");
		}

		sm.append("\n");

		itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			LayoutTypePortlet layoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			List portletIds = layoutType.getPortletIds();

			Collections.sort(portletIds);

			for (int i = 0; i < portletIds.size(); i++) {
				String portletId = (String)portletIds.get(i);

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

						// Make sure article id is upper case in the preferences
						// XML

						prefs.setValue("article-id", articleId);

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
						journalContentSearch.setArticleId(articleId);

						journalContentSearches.add(journalContentSearch);
					}

					sm.append("insert into PortletPreferences (");
					sm.append("portletPreferencesId, ownerId, ownerType, ");
					sm.append("plid, portletId, preferences");
					sm.append(") values (");
					addColumn(sm, portletPreferences.getPortletPreferencesId());
					addColumn(sm, portletPreferences.getOwnerId());
					addColumn(sm, portletPreferences.getOwnerType());
					addColumn(sm, portletPreferences.getPlid());
					addColumn(sm, portletId);
					addColumn(sm, prefsXml);
					removeTrailingComma(sm);
					sm.append(");\n");
				}
				catch (NoSuchPortletPreferencesException nsppe) {
					_log.warn(nsppe.getMessage());
				}
			}

			sm.append("\n");
		}

		removeTrailingNewLine(sm);
		removeTrailingNewLine(sm);

		zipWriter.addEntry("portal-data-cms-layout.sql", sm);
	}

	protected void insertDataImage(ZipWriter zipWriter) throws Exception {
		StringMaker sm = new StringMaker();

		Iterator itr = ImageLocalServiceUtil.getImages().iterator();

		while (itr.hasNext()) {
			Image image = (Image)itr.next();

			sm.append("insert into Image (");
			sm.append("imageId, modifiedDate, text_, type_, height, width, ");
			sm.append("size_");
			sm.append(") values (");
			addColumn(sm, image.getImageId());
			addColumn(sm, image.getModifiedDate());
			addColumn(sm, image.getText(), false);
			addColumn(sm, image.getType());
			addColumn(sm, image.getHeight());
			addColumn(sm, image.getWidth());
			addColumn(sm, image.getSize());
			removeTrailingComma(sm);
			sm.append(");\n");
		}

		removeTrailingNewLine(sm);

		zipWriter.addEntry("portal-data-image.sql", sm);
	}

	protected void removeTrailingComma(StringMaker sm) {
		sm.delete(sm.length() - 2, sm.length());
	}

	protected void removeTrailingNewLine(StringMaker sm) {
		if (sm.length() > 0) {
			sm.delete(sm.length() - 1, sm.length());
		}
	}

	private static Log _log = LogFactory.getLog(ExportAction.class);

}