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

package com.liferay.portlet.journal.util;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.util.MimeTypesUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalSyndicatedFeed;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.util.HttpUtil;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <a href="JournalRSSUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class JournalRSSUtil {

	public static DLFileEntry getDLFileEntryFromURL(String url) {
		String queryString = url.substring(url.indexOf('?') + 1);

		DLFileEntry entry = null;

		Map parameters = HttpUtil.parameterMapFromString(queryString);

		if (parameters.containsKey("folderId") &&
			parameters.containsKey("name")) {

			try {
				long folderId =
					Long.parseLong(((String[])parameters.get("folderId"))[0]);
				String name = ((String[])parameters.get("name"))[0];

				entry =
					DLFileEntryLocalServiceUtil.getFileEntry(folderId, name);
			}
			catch (Exception e) {
			}
		}
		else if (parameters.containsKey("groupId") &&
			parameters.containsKey("uuid")) {

			try {
				long groupId =
					Long.parseLong(((String[])parameters.get("groupId"))[0]);
				String uuid = ((String[])parameters.get("uuid"))[0];

				entry =
					DLFileEntryLocalServiceUtil.getFileEntryByUuidAndGroupId(
						uuid, groupId);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return entry;
	}

	public static Image getImageFromURL(String url) {
		String queryString = url.substring(url.indexOf('?') + 1);

		Image entry = null;

		Map parameters = HttpUtil.parameterMapFromString(queryString);

		if (parameters.containsKey("image_id") ||
			parameters.containsKey("img_id") ||
			parameters.containsKey("i_id")) {

			try {
				long imageId = 0;

				if (parameters.get("image_id") != null) {
					imageId =
						Long.parseLong(
							((String[])parameters.get("image_id"))[0]);
				}
				else if (parameters.get("img_id") != null) {
					imageId =
						Long.parseLong(
							((String[])parameters.get("img_id"))[0]);
				}
				else if (parameters.get("i_id") != null) {
					imageId =
						Long.parseLong(
							((String[])parameters.get("i_id"))[0]);
				}

				entry = ImageLocalUtil.getImage(imageId);
			}
			catch (Exception e) {
			}
		}
		else if (parameters.containsKey("groupId") &&
			parameters.containsKey("uuid")) {

			try {
				long groupId =
					Long.parseLong(((String[])parameters.get("groupId"))[0]);
				String uuid = ((String[])parameters.get("uuid"))[0];

				IGImage igImage =
					IGImageLocalServiceUtil.getImageByUuidAndGroupId(
						uuid, groupId);

				entry = ImageLocalUtil.getImage(igImage.getLargeImageId());
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return entry;
	}

	public static List getDLEnclosuresFromURL(String portalURL, String url) {
		List enclosures = new ArrayList();

		DLFileEntry entry = getDLFileEntryFromURL(url);

		if (entry != null) {
			SyndEnclosure enclosure = new SyndEnclosureImpl();

			enclosure.setLength(entry.getSize());

			enclosure.setType(
				MimeTypesUtil.getContentType(entry.getTitleWithExtension()));

			enclosure.setUrl(portalURL + url);

			enclosures.add(enclosure);
		}

		return enclosures;
	}

	public static List getIGEnclosuresFromURL(String portalURL, String url) {
		List enclosures = new ArrayList();

		Image image = getImageFromURL(url);

		if (image != null) {
			SyndEnclosure enclosure = new SyndEnclosureImpl();

			enclosure.setLength(image.getSize());

			enclosure.setType(
				MimeTypesUtil.getContentType("*." + image.getType()));

			enclosure.setUrl(portalURL + url);

			enclosures.add(enclosure);
		}

		return enclosures;
	}

	public static List getDLLinksFromURL(String portalURL, String url) {
		List links = new ArrayList();

		DLFileEntry entry = getDLFileEntryFromURL(url);

		if (entry != null) {
			SyndLink link = new SyndLinkImpl();

			link.setHref(portalURL + url);

			link.setLength(entry.getSize());

			link.setRel("enclosure");

			link.setType(
				MimeTypesUtil.getContentType(entry.getTitleWithExtension()));

			links.add(link);
		}

		return links;
	}

	public static List getIGLinksFromURL(String portalURL, String url) {
		List links = new ArrayList();

		Image entry = getImageFromURL(url);

		if (entry != null) {
			SyndLink link = new SyndLinkImpl();

			link.setHref(portalURL + url);

			link.setLength(entry.getSize());

			link.setRel("enclosure");

			link.setType(
				MimeTypesUtil.getContentType("*." + entry.getType()));

			links.add(link);
		}

		return links;
	}

	public static List getArticles(JournalSyndicatedFeed synFeed)
		throws SystemException {

		long companyId = synFeed.getCompanyId();
		long groupId = synFeed.getGroupId();
		String articleId = null;
		Double version = null;
		String title = null;
		String description = null;
		String content = null;
		String type = synFeed.getType();
		String structureId = synFeed.getStructureId();
		String templateId = synFeed.getTemplateId();

		if (Validator.isNull(type)) {
			type = null;
		}

		if (Validator.isNull(structureId)) {
			structureId = null;
		}

		if (Validator.isNull(templateId)) {
			templateId = null;
		}

		Date displayDateGT = null;
		Date displayDateLT = new Date();

		Boolean approved = Boolean.TRUE;
		Boolean expired = Boolean.FALSE;
		Date reviewDate = null;
		boolean andOperator = true;
		int begin = 0;
		int end = synFeed.getDelta();

		String orderByCol = synFeed.getOrderByCol();
		String orderByType = synFeed.getOrderByType();
		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator obc = new ArticleModifiedDateComparator(orderByAsc);

		if (orderByCol.equals("display-date")) {
			obc = new ArticleDisplayDateComparator(orderByAsc);
		}

		return JournalArticleLocalServiceUtil.search(
			companyId, groupId, articleId, version, title, description, content,
			type, structureId, templateId, displayDateGT, displayDateLT,
			approved, expired, reviewDate, andOperator, begin, end, obc);
	}

}
