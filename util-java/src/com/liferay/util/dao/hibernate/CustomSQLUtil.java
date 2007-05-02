/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util.dao.hibernate;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.CollectionFactory;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="CustomSQLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class CustomSQLUtil {

	public CustomSQLUtil(String functionIsNull, String functionIsNotNull) {
		_functionIsNull = functionIsNull;
		_functionIsNotNull = functionIsNotNull;
		_sqlPool = CollectionFactory.getHashMap();

		try {
			ClassLoader classLoader = getClass().getClassLoader();

			String[] configs = getConfigs();

			for (int i = 0; i < configs.length; i++) {
				read(classLoader, configs[i]);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public String get(String id) {
		return (String)_sqlPool.get(id);
	}

	public String replaceAndOperator(String sql, boolean andOperator) {
		String andOrConnector = "OR";
		String andOrNullCheck = "AND ? IS NOT NULL";

		if (andOperator) {
			andOrConnector = "AND";
			andOrNullCheck = "OR ? IS NULL";
		}

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$AND_OR_CONNECTOR$]", "[$AND_OR_NULL_CHECK$]"
			},
			new String[] {
				andOrConnector, andOrNullCheck
			});

		sql = replaceIsNull(sql);

		return sql;
	}

	public String replaceIsNull(String sql) {
		if (Validator.isNotNull(_functionIsNull)) {
			sql = StringUtil.replace(
				sql,
				new String[] {
					"? IS NULL", "? IS NOT NULL"
				},
				new String[] {
					_functionIsNull,
					_functionIsNotNull
				});
		}

		return sql;
	}

	public String removeOrderBy(String sql) {
		int pos = sql.indexOf(" ORDER BY ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	public String replaceOrderBy(String sql, OrderByComparator obc) {
		if (obc == null) {
			return sql;
		}

		sql = removeOrderBy(sql);
		sql += " ORDER BY " + obc.getOrderBy();

		return sql;
	}

	protected abstract String[] getConfigs();

	protected void read(ClassLoader classLoader, String source)
		throws Exception {

		String xml = null;

		try {
			xml = StringUtil.read(classLoader, source);
		}
		catch (Exception e) {
			_log.warn("Cannot load " + source);
		}

		if (xml == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + source);
		}

		SAXReader reader = new SAXReader();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Iterator itr = root.elements("sql").iterator();

		while (itr.hasNext()) {
			Element sql = (Element)itr.next();

			String file = sql.attributeValue("file");

			if (Validator.isNotNull(file)) {
				read(classLoader, file);
			}
			else {
				String id = sql.attributeValue("id");
				String content = transform(sql.getText());

				content = replaceIsNull(content);

				_sqlPool.put(id, content);
			}
		}
	}

	protected String transform(String sql) {
		StringMaker sm = new StringMaker();

		try {
			BufferedReader br = new BufferedReader(new StringReader(sql));

			String line = null;

			while ((line = br.readLine()) != null) {
				sm.append(line.trim());
				sm.append(StringPool.SPACE);
			}

			br.close();
		}
		catch (IOException ioe) {
			return sql;
		}

		return sm.toString();
	}

	private static Log _log = LogFactory.getLog(CustomSQLUtil.class);

	private String _functionIsNull;
	private String _functionIsNotNull;
	private Map _sqlPool;

}