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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;

import org.dom4j.Element;

/**
 * <a href="HitsOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 *
 */
public abstract class HitsOpenSearchImpl extends BaseOpenSearchImpl {

	public abstract Hits getHits(
			long companyId, String keywords, int start, int end)
		throws Exception;

	public abstract String getSearchPath();

	public abstract String getTitle(String keywords);

	public String search(
			HttpServletRequest req, String keywords, int startPage,
			int itemsPerPage)
		throws SearchException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			int start = (startPage * itemsPerPage) - itemsPerPage;
			int end = startPage * itemsPerPage;

			Hits results = getHits(
				themeDisplay.getCompanyId(), keywords, start, end);

			int total = results.getLength();

			Object[] values = addSearchResults(
				keywords, startPage, itemsPerPage, total, start,
				getTitle(keywords), getSearchPath(), themeDisplay);

			org.dom4j.Document doc = (org.dom4j.Document)values[0];
			Element root = (Element)values[1];

			for (int i = 0; i < results.getDocs().length; i++) {
				Document result = results.doc(i);

				String portletId = result.get(Field.PORTLET_ID);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);

				//String portletTitle = PortalUtil.getPortletTitle(
				//	portletId, themeDisplay.getUser());

				long groupId = GetterUtil.getLong(result.get(Field.GROUP_ID));

				PortletURL portletURL = getPortletURL(req, portletId, groupId);

				Indexer indexer = (Indexer)InstancePool.get(
					portlet.getIndexerClass());

				DocumentSummary docSummary = indexer.getDocumentSummary(
					result, portletURL);

				String title = docSummary.getTitle();
				String url = getURL(themeDisplay, groupId, result, portletURL);
				Date modifedDate = DateTools.stringToDate(
					result.get(Field.MODIFIED));
				String content = docSummary.getContent();

				String[] tags = new String[0];

				Field tagsEntriesField = (Field)result.getFields().get(
					Field.TAGS_ENTRIES);

				if (tagsEntriesField != null) {
					tags = tagsEntriesField.getValues();
				}

				double ratings = 0.0;

				String entryClassName = result.get(Field.ENTRY_CLASS_NAME);
				long entryClassPK = GetterUtil.getLong(
					result.get(Field.ENTRY_CLASS_PK));

				if ((Validator.isNotNull(entryClassName)) &&
					(entryClassPK > 0)) {

					RatingsStats stats = RatingsStatsLocalServiceUtil.getStats(
						entryClassName, entryClassPK);

					ratings = stats.getTotalScore();
				}

				double score = results.score(i);

				addSearchResult(
					root, title, url, modifedDate, content, tags, ratings,
					score);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Return\n" + doc.asXML());
			}

			return doc.asXML();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	protected String getURL(
			ThemeDisplay themeDisplay, long groupId, Document result,
			PortletURL portletURL)
		throws Exception {

		return portletURL.toString();
	}

	private static Log _log = LogFactory.getLog(HitsOpenSearchImpl.class);

}