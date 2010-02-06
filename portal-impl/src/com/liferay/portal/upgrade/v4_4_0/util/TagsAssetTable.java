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

package com.liferay.portal.upgrade.v4_4_0.util;

import java.sql.Types;

/**
 * <a href="TagsAssetTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class TagsAssetTable {

	public static final String TABLE_NAME = "TagsAsset";

	public static final Object[][] TABLE_COLUMNS = {
		{"assetId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"classNameId", new Integer(Types.BIGINT)},
		{"classPK", new Integer(Types.BIGINT)},
		{"startDate", new Integer(Types.TIMESTAMP)},
		{"endDate", new Integer(Types.TIMESTAMP)},
		{"publishDate", new Integer(Types.TIMESTAMP)},
		{"expirationDate", new Integer(Types.TIMESTAMP)},
		{"mimeType", new Integer(Types.VARCHAR)},
		{"title", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"summary", new Integer(Types.VARCHAR)},
		{"url", new Integer(Types.VARCHAR)},
		{"height", new Integer(Types.INTEGER)},
		{"width", new Integer(Types.INTEGER)},
		{"priority", new Integer(Types.DOUBLE)},
		{"viewCount", new Integer(Types.INTEGER)}
	};

	public static final String TABLE_SQL_CREATE = "create table TagsAsset (assetId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,startDate DATE null,endDate DATE null,publishDate DATE null,expirationDate DATE null,mimeType VARCHAR(75) null,title VARCHAR(300) null,description STRING null,summary STRING null,url STRING null,height INTEGER,width INTEGER,priority DOUBLE,viewCount INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table TagsAsset";

}