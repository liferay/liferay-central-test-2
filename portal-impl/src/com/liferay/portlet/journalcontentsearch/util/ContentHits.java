/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontentsearch.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ContentHits.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ContentHits {

	public void recordHits(
			Hits hits, long groupId, boolean privateLayout, int start, int end)
		throws Exception {

		// This can later be optimized according to LEP-915.

		List<Document> docs = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		for (int i = 0; i < hits.getLength(); i++) {
			Document doc = hits.doc(i);

			String articleId = doc.get(Field.ENTRY_CLASS_PK);
			long articleGroupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));

			if (JournalContentSearchLocalServiceUtil.getLayoutIdsCount(
					groupId, privateLayout, articleId) > 0) {

				docs.add(hits.doc(i));
				scores.add(hits.score(i));
			}
			else if (!isShowListed() && (articleGroupId == groupId)) {
				docs.add(hits.doc(i));
				scores.add(hits.score(i));
			}
		}

		int length = docs.size();

		hits.setLength(length);

		if (end > length) {
			end = length;
		}

		docs = docs.subList(start, end);

		hits.setDocs(docs.toArray(new Document[docs.size()]));
		hits.setScores(scores.toArray(new Float[docs.size()]));

		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);
	}

	public boolean isShowListed() {
		return _showListed;
	}

	public void setShowListed(boolean showListed) {
		_showListed = showListed;
	}

	private boolean _showListed = true;

}