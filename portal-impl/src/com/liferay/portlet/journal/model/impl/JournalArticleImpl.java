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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.LocaleTransformerListener;
import com.liferay.util.LocalizationUtil;

/**
 * <a href="JournalArticleImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class JournalArticleImpl
	extends JournalArticleModelImpl implements JournalArticle {

	public static String getContentByLocale(
		String content, boolean templateDriven, String languageId) {

		LocaleTransformerListener listener = new LocaleTransformerListener();

		listener.setTemplateDriven(templateDriven);
		listener.setLanguageId(languageId);

		return listener.onXml(content);
	}

	public JournalArticleImpl() {
	}

	public String[] getAvailableLocales() {
		return LocalizationUtil.getAvailableLocales(getContent());
	}

	public String getContentByLocale(String languageId) {
		return getContentByLocale(getContent(), isTemplateDriven(), languageId);
	}

	public String getDefaultLocale() {
		String xml = getContent();

		if (xml == null) {
			return StringPool.BLANK;
		}

		String defaultLanguageId = LocalizationUtil.getDefaultLocale(xml);

		if (isTemplateDriven() && Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());
		}

		return defaultLanguageId;
	}

	public String getSmallImageType() throws PortalException, SystemException {
		if (_smallImageType == null && isSmallImage()) {
			Image smallImage =  ImageLocalServiceUtil.getImage(
				getSmallImageId());

			_smallImageType = smallImage.getType();
		}

		return _smallImageType;
	}

	public boolean isApproved() {
		if (getStatus() == StatusConstants.APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isExpired() {
		if (getStatus() == StatusConstants.EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isTemplateDriven() {
		if (Validator.isNull(getStructureId())) {
			return false;
		}
		else {
			return true;
		}
	}

	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	private String _smallImageType;

}