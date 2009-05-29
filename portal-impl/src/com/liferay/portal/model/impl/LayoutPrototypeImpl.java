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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.util.LocalizationUtil;

import java.util.List;
import java.util.Locale;

/**
 * <a href="LayoutPrototypeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class LayoutPrototypeImpl
	extends LayoutPrototypeModelImpl implements LayoutPrototype {

	public LayoutPrototypeImpl() {
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getLayoutPrototypeGroup(
				getCompanyId(), getLayoutPrototypeId());
		}
		catch (Exception e) {
		}

		return group;
	}

	public Layout getLayout() {
		Layout layout = null;
		Group group = getGroup();

		try {
			if ((group != null) && (group.getPrivateLayoutsPageCount() > 0)) {
				List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
					group.getGroupId(), true);

				layout = layouts.get(0);
			}
		}
		catch (Exception e) {
		}

		return layout;
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
			title = getName();
		}

		return title;
	}

	public String getTitle(String localeLanguageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getTitle(), localeLanguageId, useDefault);
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

	public void test() {

	}

}