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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;

/**
 * <a href="LayoutSetImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class LayoutSetImpl extends LayoutSetModelImpl implements LayoutSet {

	public LayoutSetImpl() {
	}

	public Theme getTheme() throws SystemException {
		return ThemeLocalServiceUtil.getTheme(
			getCompanyId(), getThemeId(), false);
	}

	public ColorScheme getColorScheme() throws SystemException {
		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getTheme().getThemeId(), getColorSchemeId(), false);
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(getGroupId());
		}
		catch (Exception e) {
			group = new GroupImpl();

			_log.error(e, e);
		}

		return group;
	}

	public Theme getWapTheme() throws SystemException {
		return ThemeLocalServiceUtil.getTheme(
			getCompanyId(), getWapThemeId(), true);
	}

	public ColorScheme getWapColorScheme() throws SystemException {
		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getWapTheme().getThemeId(), getWapColorSchemeId(),
			true);
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSetImpl.class);

}