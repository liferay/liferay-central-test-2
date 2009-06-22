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
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.util.LocalizationUtil;

import java.util.Locale;

/**
 * <a href="PollsChoiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 *
 */
public class PollsChoiceImpl
	extends PollsChoiceModelImpl implements PollsChoice {

	public PollsChoiceImpl() {
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

	public int getVotesCount() throws SystemException {
		return PollsVoteLocalServiceUtil.getChoiceVotesCount(getChoiceId());
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

}