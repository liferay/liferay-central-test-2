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

package com.liferay.portlet.journal.action;

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.ImageLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.comparator.LayoutComparator;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.spring.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.spring.IGImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.spring.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.spring.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.spring.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.spring.ShoppingCategoryLocalServiceUtil;
import com.liferay.portlet.shopping.service.spring.ShoppingItemLocalServiceUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.zip.ZipWriter;

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
 * @author  Brian Wing Shun Chan
 *
 */
public class ExportAction extends Action {

	public static final String COMPANY_ID = "liferay.com";

	public static final String DEFAULT_SITE_GROUP_ID = "1";

	public static final String DEFAULT_CMS_GROUP_ID = "2";

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			if (OmniadminUtil.isOmniadmin(req.getRemoteUser())) {
				String siteGroupId = ParamUtil.getString(
					req, "siteGroupId", DEFAULT_SITE_GROUP_ID);

				String cmsGroupId = ParamUtil.getString(
					req, "cmsGroupId", DEFAULT_CMS_GROUP_ID);

				ZipWriter zipWriter = new ZipWriter();

				List journalContentSearches = new ArrayList();

				insertDataCMSLayout(
					siteGroupId, zipWriter, journalContentSearches);
				insertDataCMSContent(
					cmsGroupId, zipWriter, journalContentSearches);
				insertDataImage(zipWriter);
				//insertDataShopping(zipWriter);

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

	protected void addColumn(StringBuffer sb, boolean value) {
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

	protected void addColumn(StringBuffer sb, double value) {
		sb.append(value);
		sb.append(", ");
	}

	protected void addColumn(StringBuffer sb, Date value) {
		addColumn(sb, value, true);
	}

	protected void addColumn(StringBuffer sb, Date value, boolean current) {
		if (current) {
			sb.append("CURRENT_TIMESTAMP, ");
		}
		else {
			sb.append("SPECIFIC_TIMESTAMP_");
			sb.append(Time.getSimpleDate(value, "yyyyMMddkkmmss"));
			sb.append(", ");
		}
	}

	protected void addColumn(StringBuffer sb, String value) {
		addColumn(sb, value, true);
	}

	protected void addColumn(StringBuffer sb, String value, boolean format) {
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

	protected void insertDataCMSContent(
			String cmsGroupId, ZipWriter zipWriter, List journalContentSearches)
		throws Exception {

		StringBuffer sb = new StringBuffer();

		List igImages = new ArrayList();

		Iterator itr = IGFolderLocalServiceUtil.getFolders(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			IGFolder folder = (IGFolder)itr.next();

			sb.append("insert into IGFolder (");
			sb.append("folderId, groupId, companyId, userId, createDate, ");
			sb.append("modifiedDate, parentFolderId, name");
			sb.append(") values (");
			addColumn(sb, folder.getFolderId());
			addColumn(sb, folder.getGroupId());
			addColumn(sb, folder.getCompanyId());
			addColumn(sb, folder.getUserId());
			addColumn(sb, folder.getCreateDate());
			addColumn(sb, folder.getModifiedDate());
			addColumn(sb, folder.getParentFolderId());
			addColumn(sb, folder.getName());
			removeTrailingComma(sb);
			sb.append(");\n");

			igImages.addAll(
				IGImageLocalServiceUtil.getImages(folder.getFolderId()));
		}

		//sb.append("\n");

		Collections.sort(igImages);

		itr = igImages.iterator();

		while (itr.hasNext()) {
			IGImage image = (IGImage)itr.next();

			sb.append("insert into IGImage (");
			sb.append("imageId, companyId, userId, createDate, modifiedDate, ");
			sb.append("folderId, description, height, width, size_");
			sb.append(") values (");
			addColumn(sb, image.getImageId());
			addColumn(sb, image.getCompanyId());
			addColumn(sb, image.getUserId());
			addColumn(sb, image.getCreateDate());
			addColumn(sb, image.getModifiedDate());
			addColumn(sb, image.getFolderId());
			addColumn(sb, image.getDescription());
			addColumn(sb, image.getHeight());
			addColumn(sb, image.getWidth());
			addColumn(sb, image.getSize());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		//sb.append("\n");

		itr = JournalArticleLocalServiceUtil.getArticles(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			JournalArticle article = (JournalArticle)itr.next();

			if (article.isApproved()) {
				sb.append("insert into JournalArticle (");
				sb.append("companyId, articleId, version, groupId, userId, ");
				sb.append("userName, createDate, modifiedDate, title, ");
				sb.append("content, type_, structureId, templateId, ");
				sb.append("displayDate, approved, approvedByUserId, ");
				sb.append("approvedByUserName, expired");
				sb.append(") values (");
				addColumn(sb, article.getCompanyId());
				addColumn(sb, article.getArticleId());
				addColumn(sb, JournalArticle.DEFAULT_VERSION);
				addColumn(sb, article.getGroupId());
				addColumn(sb, article.getUserId());
				addColumn(sb, article.getUserName());
				addColumn(sb, article.getCreateDate());
				addColumn(sb, article.getModifiedDate());
				addColumn(sb, article.getTitle());
				addColumn(sb, article.getContent());
				addColumn(sb, article.getType());
				addColumn(sb, article.getStructureId());
				addColumn(sb, article.getTemplateId());
				addColumn(sb, article.getDisplayDate(), false);
				addColumn(sb, article.getApproved());
				addColumn(sb, article.getApprovedByUserId());
				addColumn(sb, article.getApprovedByUserName());
				addColumn(sb, article.getExpired());
				//addColumn(sb, article.getExpirationDate());
				//addColumn(sb, article.getReviewDate());
				removeTrailingComma(sb);
				sb.append(");\n");
			}
		}

		sb.append("\n");

		itr = journalContentSearches.iterator();

		while (itr.hasNext()) {
			JournalContentSearch contentSearch =
				(JournalContentSearch)itr.next();

			sb.append("insert into JournalContentSearch (");
			sb.append("portletId, layoutId, ownerId, companyId, articleId");
			sb.append(") values (");
			addColumn(sb, contentSearch.getPortletId());
			addColumn(sb, contentSearch.getLayoutId());
			addColumn(sb, contentSearch.getOwnerId());
			addColumn(sb, contentSearch.getCompanyId());
			addColumn(sb, contentSearch.getArticleId());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		itr = JournalStructureLocalServiceUtil.getStructures(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			JournalStructure structure = (JournalStructure)itr.next();

			sb.append("insert into JournalStructure (");
			sb.append("companyId, structureId, groupId, userId, userName, ");
			sb.append("createDate, modifiedDate, name, description, xsd");
			sb.append(") values (");
			addColumn(sb, structure.getCompanyId());
			addColumn(sb, structure.getStructureId());
			addColumn(sb, structure.getGroupId());
			addColumn(sb, structure.getUserId());
			addColumn(sb, structure.getUserName());
			addColumn(sb, structure.getCreateDate());
			addColumn(sb, structure.getModifiedDate());
			addColumn(sb, structure.getName());
			addColumn(sb, structure.getDescription());
			addColumn(sb, structure.getXsd());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		itr = JournalTemplateLocalServiceUtil.getTemplates(
			cmsGroupId).iterator();

		while (itr.hasNext()) {
			JournalTemplate template = (JournalTemplate)itr.next();

			sb.append("insert into JournalTemplate (");
			sb.append("companyId, templateId, groupId, userId, userName, ");
			sb.append("createDate, modifiedDate, structureId, name, ");
			sb.append("description, xsl, langType, smallImage, smallImageURL");
			sb.append(") values (");
			addColumn(sb, template.getCompanyId());
			addColumn(sb, template.getTemplateId());
			addColumn(sb, template.getGroupId());
			addColumn(sb, template.getUserId());
			addColumn(sb, template.getUserName());
			addColumn(sb, template.getCreateDate());
			addColumn(sb, template.getModifiedDate());
			addColumn(sb, template.getStructureId());
			addColumn(sb, template.getName());
			addColumn(sb, template.getDescription());
			addColumn(sb, template.getXsl());
			addColumn(sb, template.getLangType());
			addColumn(sb, template.getSmallImage());
			addColumn(sb, template.getSmallImageURL());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		removeTrailingNewLine(sb);

		zipWriter.addEntry("portal-data-cms-content.sql", sb);
	}

	protected void insertDataCMSLayout(
			String siteGroupId, ZipWriter zipWriter, List journalContentSearches)
		throws Exception {

		StringBuffer sb = new StringBuffer();

		List layouts = LayoutLocalServiceUtil.getLayouts(
			Layout.PUBLIC + siteGroupId);

		Collections.sort(layouts, new LayoutComparator(true));

		Iterator itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			sb.append("insert into Layout (");
			sb.append("layoutId, ownerId, companyId, parentLayoutId, name, ");
			sb.append("type_, typeSettings, hidden_, friendlyURL, themeId, ");
			sb.append("colorSchemeId, priority");
			sb.append(") values (");
			addColumn(sb, layout.getLayoutId());
			addColumn(sb, layout.getOwnerId());
			addColumn(sb, layout.getCompanyId());
			addColumn(sb, layout.getParentLayoutId());
			addColumn(sb, layout.getName());
			addColumn(sb, layout.getType());
			addColumn(sb, layout.getTypeSettings());
			addColumn(sb, layout.getHidden());
			addColumn(sb, layout.getFriendlyURL());
			addColumn(sb, layout.getThemeId());
			addColumn(sb, layout.getColorSchemeId());
			addColumn(sb, layout.getPriority());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			LayoutTypePortlet layoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			List portletIds = layoutType.getPortletIds();

			Collections.sort(portletIds);

			for (int i = 0; i < portletIds.size(); i++) {
				String portletId = (String)portletIds.get(i);

				PortletPreferencesPK pk = new PortletPreferencesPK(
					portletId, layout.getLayoutId(), layout.getOwnerId());

				try {
					PortletPreferences portletPreferences =
						PortletPreferencesLocalServiceUtil.
							getPortletPreferences(pk);

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

						JournalContentSearch journalContentSearch =
							new JournalContentSearch();

						journalContentSearch.setPortletId(portletId);
						journalContentSearch.setLayoutId(layout.getLayoutId());
						journalContentSearch.setOwnerId(layout.getOwnerId());
						journalContentSearch.setCompanyId(layout.getCompanyId());
						journalContentSearch.setArticleId(articleId);

						journalContentSearches.add(journalContentSearch);
					}

					sb.append("insert into PortletPreferences (");
					sb.append("portletId, layoutId, ownerId, preferences");
					sb.append(") values (");
					addColumn(sb, portletId);
					addColumn(sb, portletPreferences.getLayoutId());
					addColumn(sb, portletPreferences.getOwnerId());
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

	protected void insertDataImage(ZipWriter zipWriter) throws Exception {
		StringBuffer sb = new StringBuffer();

		Iterator itr = ImageLocalServiceUtil.search(
			COMPANY_ID + "%").iterator();

		while (itr.hasNext()) {
			Image image = (Image)itr.next();

			String imageId = image.getImageId();

			boolean insert = true;

			if ((imageId.indexOf(".shopping.item.") != -1) /*&&
					imageId.endsWith(".large")*/) {

				insert = false;
			}

			if (insert) {
				sb.append("insert into Image (");
				sb.append("imageId, modifiedDate, text_");
				sb.append(") values (");
				addColumn(sb, image.getImageId());
				addColumn(sb, image.getModifiedDate());
				addColumn(sb, image.getText(), false);
				removeTrailingComma(sb);
				sb.append(");\n");
			}
		}

		removeTrailingNewLine(sb);

		zipWriter.addEntry("portal-data-image.sql", sb);
	}

	protected void insertDataShopping(ZipWriter zipWriter) throws Exception {
		StringBuffer sb = new StringBuffer();

		Iterator itr = ShoppingCategoryLocalServiceUtil.getCategories(
			COMPANY_ID).iterator();

		while (itr.hasNext()) {
			ShoppingCategory category = (ShoppingCategory)itr.next();

			sb.append("insert into ShoppingCategory (");
			sb.append("categoryId, companyId, createDate, modifiedDate, ");
			sb.append("parentCategoryId, name");
			sb.append(") values (");
			addColumn(sb, category.getCategoryId());
			addColumn(sb, category.getCompanyId());
			addColumn(sb, category.getCreateDate());
			addColumn(sb, category.getModifiedDate());
			addColumn(sb, category.getParentCategoryId());
			addColumn(sb, category.getName());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		sb.append("\n");

		itr = ShoppingItemLocalServiceUtil.getItems(COMPANY_ID).iterator();

		while (itr.hasNext()) {
			ShoppingItem item = (ShoppingItem)itr.next();

			sb.append("insert into ShoppingItem (");
			sb.append("itemId, companyId, createDate, modifiedDate, ");
			sb.append("categoryId, sku, name, description, properties, ");
			sb.append("fields_, fieldsQuantities, minQuantity, maxQuantity, ");
			sb.append("price, discount, taxable, shipping, ");
			sb.append("useShippingFormula, requiresShipping, stockQuantity, ");
			sb.append("featured_, sale_, smallImage, smallImageURL, ");
			sb.append("mediumImage, mediumImageURL, largeImage, largeImageURL");
			sb.append(") values (");
			addColumn(sb, item.getItemId());
			addColumn(sb, item.getCompanyId());
			addColumn(sb, item.getCreateDate());
			addColumn(sb, item.getModifiedDate());
			addColumn(sb, item.getCategoryId());
			addColumn(sb, item.getSku());
			addColumn(sb, item.getName());
			addColumn(sb, item.getDescription());
			addColumn(sb, item.getProperties());
			addColumn(sb, item.getFields());
			addColumn(sb, item.getFieldsQuantities());
			addColumn(sb, item.getMinQuantity());
			addColumn(sb, item.getMaxQuantity());
			addColumn(sb, item.getPrice());
			addColumn(sb, item.getDiscount());
			addColumn(sb, item.getTaxable());
			addColumn(sb, item.getShipping());
			addColumn(sb, item.getUseShippingFormula());
			addColumn(sb, item.getRequiresShipping());
			addColumn(sb, item.getStockQuantity());
			addColumn(sb, item.getFeatured());
			addColumn(sb, item.getSale());
			addColumn(sb, item.getSmallImage());
			addColumn(sb, item.getSmallImageURL());
			addColumn(sb, item.getMediumImage());
			addColumn(sb, item.getMediumImageURL());
			//addColumn(sb, item.getLargeImage());
			addColumn(sb, false);
			addColumn(sb, item.getLargeImageURL());
			removeTrailingComma(sb);
			sb.append(");\n");
		}

		removeTrailingNewLine(sb);

		zipWriter.addEntry("portal-data-shopping.sql", sb);
	}

	protected void removeTrailingComma(StringBuffer sb) {
		sb.delete(sb.length() - 2, sb.length());
	}

	protected void removeTrailingNewLine(StringBuffer sb) {
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
	}

	private static Log _log = LogFactory.getLog(ExportAction.class);

}