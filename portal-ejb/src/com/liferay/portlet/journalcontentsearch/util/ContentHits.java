/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.Time;
import com.liferay.util.lucene.HitsImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ContentHits.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class ContentHits extends HitsImpl {

	public ContentHits() {
		super();
	}

	public void recordHits(Hits hits, String ownerId) throws SystemException {

		// This can later be optimized according to LEP-915.

		List docs = new ArrayList(hits.getLength());
		List scores = new ArrayList(hits.getLength());

		for (int i = 0; i < hits.getLength(); i++) {
			Document doc = hits.doc(i);

			long groupId = LayoutImpl.getGroupId(ownerId);
			String articleId = doc.get("articleId");

			if (JournalContentSearchLocalServiceUtil.getLayoutIdsCount(
					ownerId, groupId, articleId) > 0) {

				docs.add(hits.doc(i));
				scores.add(new Float(hits.score(i)));
			}
		}

		setLength(docs.size());
		setDocs((Document[])docs.toArray(new Document[0]));
		setScores((Float[])scores.toArray(new Float[0]));

		setSearchTime(
			(float)(System.currentTimeMillis() - getStart()) / Time.SECOND);
	}

}