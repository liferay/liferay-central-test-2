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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * <a href="OrgLaborTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OrgLaborTable {

	public static String TABLE_NAME = "OrgLabor";

	public static Object[][] TABLE_COLUMNS = {
		{"orgLaborId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.BIGINT)},
		{"typeId", new Integer(Types.INTEGER)},
		{"sunOpen", new Integer(Types.INTEGER)},
		{"sunClose", new Integer(Types.INTEGER)},
		{"monOpen", new Integer(Types.INTEGER)},
		{"monClose", new Integer(Types.INTEGER)},
		{"tueOpen", new Integer(Types.INTEGER)},
		{"tueClose", new Integer(Types.INTEGER)},
		{"wedOpen", new Integer(Types.INTEGER)},
		{"wedClose", new Integer(Types.INTEGER)},
		{"thuOpen", new Integer(Types.INTEGER)},
		{"thuClose", new Integer(Types.INTEGER)},
		{"friOpen", new Integer(Types.INTEGER)},
		{"friClose", new Integer(Types.INTEGER)},
		{"satOpen", new Integer(Types.INTEGER)},
		{"satClose", new Integer(Types.INTEGER)}
	};

	public static String TABLE_SQL_CREATE = "create table OrgLabor (orgLaborId LONG not null primary key,organizationId LONG,typeId INTEGER,sunOpen INTEGER,sunClose INTEGER,monOpen INTEGER,monClose INTEGER,tueOpen INTEGER,tueClose INTEGER,wedOpen INTEGER,wedClose INTEGER,thuOpen INTEGER,thuClose INTEGER,friOpen INTEGER,friClose INTEGER,satOpen INTEGER,satClose INTEGER)";

	public static String TABLE_SQL_DROP = "drop table OrgLabor";

}