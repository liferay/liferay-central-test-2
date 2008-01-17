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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.journal.NoSuchSyndicatedFeedException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalSyndicatedFeed;
import com.liferay.portlet.journal.model.impl.JournalSyndicatedFeedImpl;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalSyndicatedFeedLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalRSSUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.RSSUtil;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * <a href="RSSAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class RSSAction extends PortletAction {

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception {

		HttpServletRequest httpReq =
			((RenderRequestImpl)req).getHttpServletRequest();

		if (req.getWindowState() == LiferayWindowState.EXCLUSIVE) {
			res.setContentType(ContentTypes.TEXT_XML_UTF8);

			OutputStream out = res.getPortletOutputStream();

			try {
				out.write(getRSS(httpReq));
			}
			finally {
				out.close();
			}
		}

		return mapping.findForward(ActionConstants.COMMON_NULL);
	}

	protected byte[] getRSS(HttpServletRequest req) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long plid = 0;

		long groupId = ParamUtil.getLong(req, "groupId");

		String feedId = ParamUtil.getString(req, "feedId");

		long synFeedId = ParamUtil.getLong(req, "synFeedId");

		String languageId = LanguageUtil.getLanguageId(req);

		String rss = StringPool.BLANK;

		JournalSyndicatedFeed synFeed = null;

		List articles = new ArrayList();

		try {
			if (synFeedId > 0) {
				synFeed =
					JournalSyndicatedFeedLocalServiceUtil.getSyndicatedFeed(
						synFeedId);
			}
			else {
				synFeed =
					JournalSyndicatedFeedLocalServiceUtil.getSyndicatedFeed(
						groupId, feedId);
			}

			plid = PortalUtil.getPlidIdFromFriendlyURL(
				themeDisplay.getCompanyId(),
				synFeed.getTargetLayoutFriendlyUrl());

			if (plid > 0) {
				try {
					layout = LayoutLocalServiceUtil.getLayout(plid);
				}
				catch (Exception e) {
				}
			}

			String feedURL = themeDisplay.getURLPortal() +
				PortalUtil.getLayoutFriendlyURL(layout, themeDisplay) +
					"/journal/rss/" + synFeed.getId();

			articles = JournalRSSUtil.getArticles(synFeed);

			if (_log.isDebugEnabled()) {
				_log.debug("syndicating " + articles.size() + " articles");
			}

			StringMaker xmlRequest = new StringMaker();

			xmlRequest.append("<request><parameters>");
			xmlRequest.append(	"<parameter>");
			xmlRequest.append(		"<name>rss</name>");
			xmlRequest.append(		"<value>true</value>");
			xmlRequest.append(	"</parameter>");
			xmlRequest.append("</parameters></request>");

			rss = exportToRSS(
				synFeed, themeDisplay, layout, languageId, feedURL, articles,
				xmlRequest.toString());
		}
		catch (NoSuchSyndicatedFeedException nssfe) {
			_log.error(nssfe.getMessage(), nssfe);
		}
		catch (Exception e) {
			_log.error("Cannot process SyndicatedFeed for " + synFeedId, e);
		}

		return rss.getBytes(StringPool.UTF8);
	}

	protected String exportToRSS(
			JournalSyndicatedFeed synFeed, ThemeDisplay themeDisplay,
			Layout layout, String languageId, String feedURL, List articles,
			String xmlRequest)
		throws SystemException {

		SyndFeed feed = new SyndFeedImpl();

		String contentField = synFeed.getContentField();

		String portalURL = themeDisplay.getURLPortal();

		feed.setFeedType(
			synFeed.getFeedType() + "_" + synFeed.getFeedVersion());

		feed.setTitle(synFeed.getName());
		feed.setLink(feedURL);
		feed.setDescription(synFeed.getDescription());

		List entries = new ArrayList();

		feed.setEntries(entries);

		Iterator itr = articles.iterator();

		while (itr.hasNext()) {
			JournalArticle article = (JournalArticle)itr.next();

			SyndEntry syndEntry = new SyndEntryImpl();
			syndEntry.setAuthor(article.getUserName());

			SyndContent description = new SyndContentImpl();

			description.setType("text");
            description.setValue(article.getDescription());

			syndEntry.setTitle(article.getTitle());

			syndEntry.setPublishedDate(article.getCreateDate());

			String entryURL = getEntryURL(
				themeDisplay, layout, article,
				synFeed.getTargetLayoutFriendlyUrl(),
				synFeed.getTargetPortletId());

			syndEntry.setLink(entryURL);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType("html");

			String content = article.getDescription();

			if (Validator.isNotNull(contentField)) {
				if (contentField.equals(
					JournalSyndicatedFeedImpl.RENDERED_ARTICLE)) {

					String rendererTemplateId = article.getTemplateId();

					if (Validator.isNotNull(synFeed.getRendererTemplateId())) {
						rendererTemplateId = synFeed.getRendererTemplateId();
					}

					JournalArticleDisplay articleDisplay =
						JournalContentUtil.getDisplay(
							synFeed.getGroupId(), article.getArticleId(),
							rendererTemplateId, languageId, themeDisplay,
							1, xmlRequest);

					if (articleDisplay != null) {
						content = articleDisplay.getContent();
					}
				}
				else if (!contentField.equals(
					JournalSyndicatedFeedImpl.ARTICLE_DESCRIPTION)) {

					try {
						Document doc = PortalUtil.readDocumentFromXML(
							article.getContent());

						XPath xpathSelector = DocumentHelper.createXPath(
							"//dynamic-element[@name='" + contentField + "']");

						List results = xpathSelector.selectNodes(doc);

						if (results.size() > 0) {
							Element el = (Element)results.get(0);

							String elType = el.attributeValue("type");

							if (elType.equals("document_library")) {
								String entryLink =
									el.element("dynamic-content").getTextTrim();

								entryLink = StringUtil.replace(
									entryLink,
									new String[] {
										"@group_id@",
										"@main_path@",
										"@image_path@"
									},
									new String[] {
										String.valueOf(synFeed.getGroupId()),
										themeDisplay.getPathMain(),
										themeDisplay.getPathImage()
									}
								);

								syndEntry.setLinks(
									JournalRSSUtil.getDLLinksFromURL(
										portalURL, entryLink));
								syndEntry.setEnclosures(
									JournalRSSUtil.getDLEnclosuresFromURL(
										portalURL, entryLink));
							}
							else if (elType.equals("image") ||
									elType.equals("image_gallery")) {

								String entryLink =
									el.element("dynamic-content").getTextTrim();

								entryLink = StringUtil.replace(
									entryLink,
									new String[] {
										"@group_id@",
										"@main_path@",
										"@image_path@"
									},
									new String[] {
										String.valueOf(synFeed.getGroupId()),
										themeDisplay.getPathMain(),
										themeDisplay.getPathImage()
									}
								);

								content =
									content + "<br/><br/><img src='" + portalURL +
										entryLink + "' />";

								syndEntry.setLinks(
									JournalRSSUtil.getIGLinksFromURL(
										portalURL, entryLink));
								syndEntry.setEnclosures(
									JournalRSSUtil.getIGEnclosuresFromURL(
										portalURL, entryLink));
							}
							else if (elType.equals("text_box")) {
								syndContent.setType("text");

								content = el.element(
									"dynamic-content").getTextTrim();
							}
							else {
								content = el.element(
									"dynamic-content").getTextTrim();
							}
						}
					}
					catch (DocumentException de) {
					}
				}
			}

			syndContent.setValue(content);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		try {
			return RSSUtil.export(feed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected String getEntryURL(
			ThemeDisplay themeDisplay, Layout layout, JournalArticle article,
			String targetLayoutFriendlyUrl, String targetPortletId) {

		StringMaker entryURL = new StringMaker();

		List hitLayoutIds = new ArrayList();

		try {
			hitLayoutIds = JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(),
				article.getArticleId());

			if (hitLayoutIds.size() > 0) {
				Long hitLayoutId = (Long)hitLayoutIds.get(0);

				Layout hitLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					hitLayoutId.longValue());

				return themeDisplay.getURLPortal() +
					PortalUtil.getLayoutURL(hitLayout, themeDisplay);
			}
			else if (Validator.isNotNull(targetLayoutFriendlyUrl)) {
				entryURL.append(themeDisplay.getURLPortal());
				entryURL.append(targetLayoutFriendlyUrl);
			}
			else {
				entryURL.append(themeDisplay.getURLPortal());
				entryURL.append(
					PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));
			}
		}
		catch (Exception se){
		}

		entryURL.append("/journal_content/");

		if (Validator.isNotNull(targetPortletId)) {
			entryURL.append(targetPortletId);
		}
		else {
			entryURL.append(PortletKeys.JOURNAL_CONTENT);
		}

		entryURL.append("/");
		entryURL.append(article.getGroupId());
		entryURL.append("/");
		entryURL.append(article.getArticleId());

		return entryURL.toString();
	}

	private static Log _log = LogFactory.getLog(RSSAction.class);

}
