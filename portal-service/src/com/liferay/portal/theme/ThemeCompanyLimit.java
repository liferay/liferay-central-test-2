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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="ThemeCompanyLimit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ThemeCompanyLimit implements Serializable {

	public ThemeCompanyLimit() {
		_includes = new ArrayList<ThemeCompanyId>();
		_excludes = new ArrayList<ThemeCompanyId>();
	}

	public List<ThemeCompanyId> getIncludes() {
		return _includes;
	}

	public void setIncludes(List<? extends ThemeCompanyId> includes) {
		_includes = (List<ThemeCompanyId>)includes;
	}

	public boolean isIncluded(long companyId) {
		return _matches(_includes, companyId);
	}

	public List<ThemeCompanyId> getExcludes() {
		return _excludes;
	}

	public void setExcludes(List<? extends ThemeCompanyId> excludes) {
		_excludes = (List<ThemeCompanyId>)excludes;
	}

	public boolean isExcluded(long companyId) {
		return _matches(_excludes, companyId);
	}

	private boolean _matches(
		List<ThemeCompanyId> themeCompanyIds, long companyId) {

		for (int i = 0; i < themeCompanyIds.size(); i++) {
			ThemeCompanyId themeCompanyId = themeCompanyIds.get(i);

			String themeCompanyIdValue = themeCompanyId.getValue();

			if (themeCompanyId.isPattern()) {
				Pattern pattern = Pattern.compile(themeCompanyIdValue);
				Matcher matcher = pattern.matcher(String.valueOf(companyId));

				if (matcher.matches()) {
					return true;
				}
			}
			else {
				long themeCompanyIdValueLong = GetterUtil.getLong(
					themeCompanyIdValue);

				if (themeCompanyIdValueLong == companyId) {
					return true;
				}
			}
		}

		return false;
	}

	private List<ThemeCompanyId> _includes;
	private List<ThemeCompanyId> _excludes;

}