/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.sass;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.dynamiccss.RTLCSSUtil;
import com.liferay.portal.tools.SassToCssBuilder;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class SassString implements SassFragment {

	public SassString(String fileName, String sassContent) throws Exception {
		String cssContent = SassExecutorUtil.parse(
			fileName, SassToCssBuilder.parseStaticTokens(sassContent));

		if (fileName.contains("_rtl")) {
			_ltrContent = StringPool.BLANK;
			_rtlContent = cssContent;
		}
		else {
			_ltrContent = cssContent;

			if (!RTLCSSUtil.isExcludedPath(fileName)) {
				_rtlContent = RTLCSSUtil.getRtlCss(cssContent);
			}
			else {
				_rtlContent = null;
			}
		}
	}

	@Override
	public String getLtrContent() {
		return _ltrContent;
	}

	@Override
	public String getRtlContent() {
		return _rtlContent;
	}

	private final String _ltrContent;
	private final String _rtlContent;

}