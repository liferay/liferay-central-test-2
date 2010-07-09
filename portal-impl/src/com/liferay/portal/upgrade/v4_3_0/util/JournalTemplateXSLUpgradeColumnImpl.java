/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalTemplateXSLUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public JournalTemplateXSLUpgradeColumnImpl(UpgradeColumn templateIdColumn) {
		super("xsl");

		_templateIdColumn = templateIdColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String xsl = (String)oldValue;

		return formatXSL(xsl);
	}

	protected String formatXSL(String xsl) throws Exception {
		/*if (xsl.indexOf("\\n") != -1) {
			xsl = StringUtil.replace(
				xsl, new String[] {"\\n", "\\r"}, new String[] {"\n", "\r"});
		}*/

		String templateId = (String)_templateIdColumn.getOldValue();

		if (templateId.equals("BASIC-BANNER")) {

			// 4.3 defaults to XHTML 1.0 Transitional and requires stricter CSS

			xsl = StringUtil.replace(
				xsl,
				"background-repeat: no-repeat; width: 520; height: 175;",
				"background-repeat: no-repeat; width: 520px; height: 175px;");
		}

		return xsl;
	}

	private UpgradeColumn _templateIdColumn;

}