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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * <a href="JournalArticleTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleTable {

	public static String TABLE_NAME = "JournalArticle";

	public static Object[][] TABLE_COLUMNS = {
		{"id_", new Integer(Types.BIGINT)},
		{"resourcePrimKey", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"articleId", new Integer(Types.VARCHAR)},
		{"version", new Integer(Types.DOUBLE)},
		{"title", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"content", new Integer(Types.CLOB)},
		{"type_", new Integer(Types.VARCHAR)},
		{"structureId", new Integer(Types.VARCHAR)},
		{"templateId", new Integer(Types.VARCHAR)},
		{"displayDate", new Integer(Types.TIMESTAMP)},
		{"approved", new Integer(Types.BOOLEAN)},
		{"approvedByUserId", new Integer(Types.BIGINT)},
		{"approvedByUserName", new Integer(Types.VARCHAR)},
		{"approvedDate", new Integer(Types.TIMESTAMP)},
		{"expired", new Integer(Types.BOOLEAN)},
		{"expirationDate", new Integer(Types.TIMESTAMP)},
		{"reviewDate", new Integer(Types.TIMESTAMP)}
	};

	public static String TABLE_SQL_CREATE = "create table JournalArticle (id_ LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,articleId VARCHAR(75) null,version DOUBLE,title VARCHAR(100) null,description STRING null,content TEXT null,type_ VARCHAR(75) null,structureId VARCHAR(75) null,templateId VARCHAR(75) null,displayDate DATE null,approved BOOLEAN,approvedByUserId LONG,approvedByUserName VARCHAR(75) null,approvedDate DATE null,expired BOOLEAN,expirationDate DATE null,reviewDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table JournalArticle";

}