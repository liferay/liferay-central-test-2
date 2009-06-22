/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.util.LocalizationUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="PollsQuestionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 *
 */
public class PollsQuestionImpl
	extends PollsQuestionModelImpl implements PollsQuestion {

	public PollsQuestionImpl() {
	}

	public String getDescription(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getDescription(localeLanguageId);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getDescription(localeLanguageId, useDefault);
	}

	public String getDescription(String localeLanguageId) {
		String description = LocalizationUtil.getLocalization(
			getDescription(), localeLanguageId);

		if (Validator.isNull(description)) {
			description = getDescription();
		}

		return description;
	}

	public String getDescription(String localeLanguageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getDescription(), localeLanguageId, useDefault);
	}

	public Map<Locale, String> getDescriptionsMap() {
		return LocalizationUtil.getLocalizedField(getDescription());
	}

	public String getTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getTitle(localeLanguageId);
	}

	public String getTitle(Locale locale, boolean useDefault) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getTitle(localeLanguageId, useDefault);
	}

	public String getTitle(String localeLanguageId) {
		String title = LocalizationUtil.getLocalization(
			getTitle(), localeLanguageId);

		if (Validator.isNull(title)) {
			title = getTitle();
		}

		return title;
	}

	public String getTitle(String localeLanguageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getTitle(), localeLanguageId, useDefault);
	}

	public Map<Locale, String> getTitlesMap() {
		return LocalizationUtil.getLocalizedField(getTitle());
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public boolean isExpired() {
		Date expirationDate = getExpirationDate();

		if ((expirationDate != null) && (expirationDate.before(new Date()))) {
			return true;
		}
		else {
			return false;
		}
	}

	public List<PollsChoice> getChoices() throws SystemException {
		return PollsChoiceLocalServiceUtil.getChoices(getQuestionId());
	}

	public int getVotesCount() throws SystemException {
		return PollsVoteLocalServiceUtil.getQuestionVotesCount(getQuestionId());
	}

	public void setDescription(String description, Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		if (Validator.isNotNull(description)) {
			setDescription(
				LocalizationUtil.updateLocalization(
					getDescription(), "description", description,
					localeLanguageId));
		}
		else {
			setDescription(
				LocalizationUtil.removeLocalization(
					getDescription(), "description", localeLanguageId));
		}
	}

	public void setTitle(String title, Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		if (Validator.isNotNull(title)) {
			setTitle(
				LocalizationUtil.updateLocalization(
					getTitle(), "title", title, localeLanguageId));
		}
		else {
			setTitle(
				LocalizationUtil.removeLocalization(
					getTitle(), "title", localeLanguageId));
		}
	}

	private String _userUuid;

}