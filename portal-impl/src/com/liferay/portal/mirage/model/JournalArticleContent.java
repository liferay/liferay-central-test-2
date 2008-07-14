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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.model;

import com.liferay.portlet.journal.model.JournalArticle;

import com.sun.portal.cms.mirage.model.custom.Content;

import java.io.File;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="JournalArticleContent.java.html"><b><i>View Source</i></b></a>
 *
 * @author Joshna
 *
 */
public class JournalArticleContent extends Content {

	public class CreationAttributes {

		public Boolean getAddCommunityPermissions() {

			return _addCommunityPermissions;
		}

		public void setAddCommunityPermissions(
			Boolean addCommunityPermissions) {

			_addCommunityPermissions = addCommunityPermissions;
		}

		public Boolean getAddGuestPermissions() {

			return _addGuestPermissions;
		}

		public void setAddGuestPermissions(Boolean addGuestPermissions) {

			_addGuestPermissions = addGuestPermissions;
		}

		public String getArticleURL() {

			return _articleURL;
		}

		public void setArticleURL(String articleURL) {

			_articleURL = articleURL;
		}

		public PortletPreferences getPrefs() {

			return _prefs;
		}

		public void setPrefs(PortletPreferences prefs) {

			_prefs = prefs;
		}

		public String[] getTagsEntries() {

			return _tagsEntries;
		}

		public void setTagsEntries(String[] tagsEntries) {

			_tagsEntries = tagsEntries;
		}

		public boolean isAutoArticleId() {

			return _autoArticleId;
		}

		public void setAutoArticleId(boolean autoArticleId) {

			_autoArticleId = autoArticleId;
		}

		public int getDisplayDateMonth() {

			return _displayDateMonth;
		}

		public void setDisplayDateMonth(int displayDateMonth) {

			_displayDateMonth = displayDateMonth;
		}

		public int getDisplayDateYear() {

			return _displayDateYear;
		}

		public void setDisplayDateYear(int displayDateYear) {

			_displayDateYear = displayDateYear;
		}

		public int getDisplayDateDay() {

			return _displayDateDay;
		}

		public void setDisplayDateDay(int displayDateDay) {

			_displayDateDay = displayDateDay;
		}

		public int getDisplayDateHour() {

			return _displayDateHour;
		}

		public void setDisplayDateHour(int displayDateHour) {

			_displayDateHour = displayDateHour;
		}

		public int getDisplayDateMinute() {

			return _displayDateMinute;
		}

		public void setDisplayDateMinute(int displayDateMinute) {

			_displayDateMinute = displayDateMinute;
		}

		public int getExpirationDateMonth() {

			return _expirationDateMonth;
		}

		public void setExpirationDateMonth(int expirationDateMonth) {

			_expirationDateMonth = expirationDateMonth;
		}

		public int getExpirationDateYear() {

			return _expirationDateYear;
		}

		public void setExpirationDateYear(int expirationDateYear) {

			_expirationDateYear = expirationDateYear;
		}

		public int getExpirationDateDay() {

			return _expirationDateDay;
		}

		public void setExpirationDateDay(int expirationDateDay) {

			_expirationDateDay = expirationDateDay;
		}

		public int getExpirationDateHour() {

			return _expirationDateHour;
		}

		public void setExpirationDateHour(int expirationDateHour) {

			_expirationDateHour = expirationDateHour;
		}

		public int getExpirationDateMinute() {

			return _expirationDateMinute;
		}

		public void setExpirationDateMinute(int expirationDateMinute) {

			_expirationDateMinute = expirationDateMinute;
		}

		public boolean isNeverExpire() {

			return _neverExpire;
		}

		public void setNeverExpire(boolean neverExpire) {

			_neverExpire = neverExpire;
		}

		public int getReviewDateYear() {

			return _reviewDateYear;
		}

		public void setReviewDateYear(int reviewDateYear) {

			_reviewDateYear = reviewDateYear;
		}

		public int getReviewDateMonth() {

			return _reviewDateMonth;
		}

		public void setReviewDateMonth(int reviewDateMonth) {

			_reviewDateMonth = reviewDateMonth;
		}

		public int getReviewDateDay() {

			return _reviewDateDay;
		}

		public void setReviewDateDay(int reviewDateDay) {

			_reviewDateDay = reviewDateDay;
		}

		public int getReviewDateHour() {

			return _reviewDateHour;
		}

		public void setReviewDateHour(int reviewDateHour) {

			_reviewDateHour = reviewDateHour;
		}

		public int getReviewDateMinute() {

			return _reviewDateMinute;
		}

		public void setReviewDateMinute(int reviewDateMinute) {

			_reviewDateMinute = reviewDateMinute;
		}

		public boolean isNeverReview() {

			return _neverReview;
		}

		public void setNeverReview(boolean neverReview) {

			_neverReview = neverReview;
		}

		public boolean isIncrementVersion() {

			return _incrementVersion;
		}

		public void setIncrementVersion(boolean incrementVersion) {

			_incrementVersion = incrementVersion;
		}

		public File getSmallFile() {

			return _smallFile;
		}

		public void setSmallFile(File smallFile) {

			_smallFile = smallFile;
		}

		public Map<String, byte[]> getImages() {

			return _images;
		}

		public void setImages(Map<String, byte[]> images) {

			_images = images;
		}

		private boolean _autoArticleId;
		private Boolean _addCommunityPermissions;
		private Boolean _addGuestPermissions;
		private String _articleURL;
		private PortletPreferences _prefs;
		private String[] _tagsEntries;
		private int _displayDateMonth;
		private int _displayDateDay;
		private int _displayDateYear;
		private int _displayDateHour;
		private int _displayDateMinute;
		private int _expirationDateMonth;
		private int _expirationDateDay;
		private int _expirationDateYear;
		private int _expirationDateHour;
		private int _expirationDateMinute;
		private boolean _neverExpire;
		private int _reviewDateMonth;
		private int _reviewDateDay;
		private int _reviewDateYear;
		private int _reviewDateHour;
		private int _reviewDateMinute;
		private boolean _neverReview;
		private boolean _incrementVersion;
		private File _smallFile;
		private Map<String, byte[]> _images;
	}

	public JournalArticleContent() {

	}

	public JournalArticleContent(JournalArticle article) {

		_article = article;
	}

	public JournalArticleContent(
		JournalArticle article, String[] communityPermissions,
		String[] guestPermissions) {

		_article = article;
		_communityPermissions = communityPermissions;
		_guestPermissions = guestPermissions;
	}

	public JournalArticle getArticle() {

		return _article;
	}

	public void setArticle(JournalArticle article) {

		_article = article;
	}

	public List<JournalArticle> getArticles() {

		return _articleList;
	}

	public void setArticles(List<JournalArticle> articleList) {

		_articleList = articleList;
	}

	public String[] getCommunityPermissions() {

		return _communityPermissions;
	}

	public void setCommunityPermissions(String[] communityPermissions) {

		_communityPermissions = communityPermissions;
	}

	public String[] getGuestPermissions() {

		return _guestPermissions;
	}

	public void setGuestPermissions(String[] guestPermissions) {

		_guestPermissions = guestPermissions;
	}

	public JournalArticleContent.CreationAttributes getCreationAttributes() {

		return _creationAttributes;
	}

	public void setCreationAttributes(
		JournalArticleContent.CreationAttributes creationAttributes) {

		_creationAttributes = creationAttributes;
	}

	private JournalArticle _article;
	private List<JournalArticle> _articleList;
	private String[] _communityPermissions;
	private String[] _guestPermissions;
	private CreationAttributes _creationAttributes;

}