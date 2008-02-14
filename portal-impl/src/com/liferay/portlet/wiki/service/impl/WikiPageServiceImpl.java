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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.velocity.VelocityUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.base.WikiPageServiceBaseImpl;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.util.Html;
import com.liferay.util.RSSUtil;
import com.liferay.util.diff.DiffUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="WikiPageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WikiPageServiceImpl extends WikiPageServiceBaseImpl {

	public WikiPage addPage(
			long nodeId, String title, PortletPreferences prefs,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, prefs, themeDisplay);
	}

	public void addPageAttachments(long nodeId, String title, List files)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.addPageAttachments(nodeId, title, files);
	}

	public void deletePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePage(nodeId, title);
	}

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	public List<WikiPage> getNodePages(long nodeId, int max)
		throws PortalException, SystemException {

		List<WikiPage> pages = new ArrayList<WikiPage>();

		Iterator<WikiPage> itr = wikiPageLocalService.getPages(nodeId, true, 0,
			_MAX_END).iterator();

		while (itr.hasNext() && (pages.size() < max)) {
			WikiPage page = itr.next();

			if (WikiPagePermission.contains(getPermissionChecker(), page,
					ActionKeys.VIEW)) {

				pages.add(page);
			}
		}

		return pages;
	}

	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		long companyId = 0;
		String name = node.getName();
		String description = node.getDescription();

		List<WikiPage> pages = getNodePages(nodeId, max);

		Locale locale = null;

		return exportToRSS(
			companyId, name, description, type, version, displayStyle,
			feedURL, entryURL, pages, false, locale);
	}

	public WikiPage getPage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title);
	}

	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title, version);
	}

	public String getPagesRSS(
			long companyId, long nodeId, String title, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, Locale locale)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		List<WikiPage> pages = wikiPageLocalService.getPages(
			nodeId, title, 0, _MAX_END, new PageCreateDateComparator(true));

		return exportToRSS(
			companyId, title, null, type, version, displayStyle, feedURL,
			entryURL, pages, true, locale);
	}

	public void movePage(
			long nodeId, String title, String newTitle,
			PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		wikiPageLocalService.movePage(
			getUserId(), nodeId, title, newTitle, prefs, themeDisplay);
	}

	public WikiPage revertPage(
			long nodeId, String title, double version, PortletPreferences prefs,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.revertPage(
			getUserId(), nodeId, title, version, prefs, themeDisplay);
	}

	public void subscribePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.subscribePage(getUserId(), nodeId, title);
	}

	public void unsubscribePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.unsubscribePage(getUserId(), nodeId, title);
	}

	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String format, String redirectTo, String[] tagsEntries,
			PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.updatePage(
			getUserId(), nodeId, title, version, content, format, redirectTo,
			tagsEntries, prefs, themeDisplay);
	}

	protected String exportToRSS(
			long companyId, String name, String description, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, List<WikiPage> wikiPages, boolean diff,
			Locale locale)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(type + "_" + version);
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		WikiPage lastPageVersion = null;

		for (WikiPage page : wikiPages) {
			String author = PortalUtil.getUserName(
				page.getUserId(), page.getUserName());

			String value = null;
			String link = entryURL;

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(page.getTitle());
			syndEntry.setPublishedDate(page.getCreateDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType("html");

			if (diff) {
				if (lastPageVersion != null) {
					link = entryURL + "?_" + PortletKeys.WIKI +
						"_version=" + page.getVersion();

					try {
						value = getPageDiff(
							companyId, lastPageVersion, page, locale);

						syndContent.setValue(value);
						syndEntry.setDescription(syndContent);
						entries.add(syndEntry);
					}
					catch (Exception e) {
						throw new SystemException(e);
					}
				}
			}
			else {
				if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
					value = StringUtil.shorten(
						Html.stripHtml(page.getContent()), _RSS_ABSTRACT_LENGTH,
						StringPool.BLANK);
				}
				else {
					value = page.getContent();
				}

				syndContent.setValue(value);
				syndEntry.setDescription(syndContent);
				entries.add(syndEntry);
			}

			syndEntry.setLink(link);
			lastPageVersion = page;
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected String getPageDiff(
			long companyId, WikiPage lastPageVersion, WikiPage page,
			Locale locale)
		throws Exception {

		String sourceContent = WikiUtil.processContent(
			lastPageVersion.getContent());
		String targetContent = WikiUtil.processContent(
			page.getContent());

		sourceContent = Html.escape(sourceContent);
		targetContent = Html.escape(targetContent);

		List[] diffResults = DiffUtil.diff(
			new StringReader(sourceContent), new StringReader(targetContent));

		ClassLoader classLoader = getClass().getClassLoader();

		InputStream is = classLoader.getResourceAsStream(
			"com/liferay/portlet/wiki/dependencies/rss.vm");

		String template = StringUtil.read(is);

		is.close();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("languageUtil", LanguageUtil.getLanguage());
		variables.put("locale", locale);
		variables.put("contextLine", DiffUtil.CONTEXT_LINE);
		variables.put("diffUtil", new DiffUtil());
		variables.put("companyId", companyId);
		variables.put("sourceResults", diffResults[0]);
		variables.put("targetResults", diffResults[1]);

		return VelocityUtil.evaluate(template, variables);
	}

	private static final int _MAX_END = 200;

	private static final int _RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.WIKI_RSS_ABSTRACT_LENGTH));

}