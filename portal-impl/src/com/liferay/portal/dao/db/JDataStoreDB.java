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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * <a href="JDataStoreDB.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class JDataStoreDB extends FirebirdDB {

	public static DB getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(
			template,
			new String[] {"\\'", "\\\"", "\\\\",  "\\n", "\\r"},
			new String[] {"''", "\"", "\\", "\n", "\r"});

		return template;
	}

	protected JDataStoreDB() {
		super(TYPE_JDATASTORE);
	}

	protected String getServerName() {
		return "jdatastore";
	}

	protected String[] getTemplate() {
		return _JDATASTORE;
	}

	private static String[] _JDATASTORE = {
		"--", "TRUE", "FALSE",
		"'1970-01-01'", "current_timestamp",
		" binary", " boolean", " date",
		" double", " integer", " bigint",
		" long varchar", " long varchar", " varchar",
		"", "commit"
	};

	private static JDataStoreDB _instance = new JDataStoreDB();

}