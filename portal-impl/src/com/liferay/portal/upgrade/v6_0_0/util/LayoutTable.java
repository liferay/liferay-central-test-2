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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * <a href="LayoutTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutTable {

	public static final String TABLE_NAME = "Layout";

	public static final Object[][] TABLE_COLUMNS = {
		{"plid", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"privateLayout", new Integer(Types.BOOLEAN)},
		{"layoutId", new Integer(Types.BIGINT)},
		{"parentLayoutId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"title", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.VARCHAR)},
		{"typeSettings", new Integer(Types.CLOB)},
		{"hidden_", new Integer(Types.BOOLEAN)},
		{"friendlyURL", new Integer(Types.VARCHAR)},
		{"iconImage", new Integer(Types.BOOLEAN)},
		{"iconImageId", new Integer(Types.BIGINT)},
		{"themeId", new Integer(Types.VARCHAR)},
		{"colorSchemeId", new Integer(Types.VARCHAR)},
		{"wapThemeId", new Integer(Types.VARCHAR)},
		{"wapColorSchemeId", new Integer(Types.VARCHAR)},
		{"css", new Integer(Types.VARCHAR)},
		{"priority", new Integer(Types.INTEGER)},
		{"layoutPrototypeId", new Integer(Types.BIGINT)},
		{"dlFolderId", new Integer(Types.BIGINT)}
	};

	public static final String TABLE_SQL_CREATE = "create table Layout (plid LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,name STRING null,title STRING null,description STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,friendlyURL VARCHAR(255) null,iconImage BOOLEAN,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,priority INTEGER,layoutPrototypeId LONG,dlFolderId LONG)";

	public static final String TABLE_SQL_DROP = "drop table Layout";

}