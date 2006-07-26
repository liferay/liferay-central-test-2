/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.lucene;

import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.JNDIUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.DataAccess;
import com.liferay.util.lucene.KeywordsUtil;

import java.io.IOException;

import java.sql.Connection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.dialect.Dialect;

/**
 * <a href="LuceneUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Harry Mark
 *
 */
public class LuceneUtil {

	public static final Pattern TERM_END_PATTERN = Pattern.compile("(\\w)\\b");

	public static void addTerm(
			BooleanQuery booleanQuery, String field, String text)
		throws ParseException {

		if (Validator.isNotNull(text)) {
			Matcher matcher = TERM_END_PATTERN.matcher(text);

			// Add wildcard to the end of every word

			text = matcher.replaceAll("$1*");

			// Add fuzzy query to the end of every word

			text = text + " " + matcher.replaceAll("$1~");

			QueryParser queryParser = new QueryParser(
				field, LuceneUtil.getAnalyzer());

			Query query = queryParser.parse(text);

			booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		}
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String text) {

		text = KeywordsUtil.escape(text);

		Term term = new Term(field, text);
		TermQuery termQuery = new TermQuery(term);

		booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
	}

	public static Analyzer getAnalyzer() {
		return _instance._getAnalyzer();
	}

	public static Directory getLuceneDir(String companyId) {
		return _instance._getLuceneDir(companyId);
	}

	public static IndexReader getReader(String companyId) throws IOException {
		return IndexReader.open(getLuceneDir(companyId));
	}

	public static IndexSearcher getSearcher(String companyId)
		throws IOException {

		return new IndexSearcher(getLuceneDir(companyId));
	}

	public static IndexWriter getWriter(String companyId) throws IOException {
		return getWriter(companyId, false);
	}

	public static IndexWriter getWriter(String companyId, boolean create)
		throws IOException {

		return new IndexWriter(
			getLuceneDir(companyId), getAnalyzer(), create);
	}

	public static void write(IndexWriter writer) throws IOException {
		//writer.optimize();
		writer.close();
	}

	public static void delete(String companyId) {
		_instance._delete(companyId);
	}

	private LuceneUtil() {

		// Analyzer

		String analyzerName = PropsUtil.get(PropsUtil.LUCENE_ANALYZER);

		if (Validator.isNotNull(analyzerName)) {
			try {
				_analyzerClass = Class.forName(analyzerName);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		// Dialect

		if (PropsUtil.get(PropsUtil.LUCENE_STORE_TYPE).equals(
				_LUCENE_STORE_TYPE_JDBC)) {

			Connection con = null;

			try {
				con = DataAccess.getConnection(Constants.DATA_SOURCE);

				String url = con.getMetaData().getURL();

				int x = url.indexOf(":");
				int y = url.indexOf(":", x + 1);

				String urlPrefix = url.substring(x + 1, y);

				String dialectClass = PropsUtil.get(
					PropsUtil.LUCENE_STORE_JDBC_DIALECT + urlPrefix);

				if (dialectClass != null) {
					if (_log.isDebugEnabled()) {
						_log.debug("JDBC class implementation " + dialectClass);
					}
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("JDBC class implementation is null");
					}
				}

				if (dialectClass != null) {
					_dialect =
						(Dialect)Class.forName(dialectClass).newInstance();
				}
			}
			catch (Exception e) {
				_log.error(e);
			}
			finally{
				DataAccess.cleanUp(con);
			}

			if (_dialect == null) {
				_log.error("No JDBC dialect found");
			}
		}
	}

	public void _delete(String companyId) {
		if (PropsUtil.get(PropsUtil.LUCENE_STORE_TYPE).equals(
				_LUCENE_STORE_TYPE_JDBC)) {

			try {
				DataSource ds = (DataSource)JNDIUtil.lookup(
					new InitialContext(), Constants.DATA_SOURCE);

				JdbcDirectory directory = new JdbcDirectory(
					ds, _dialect, _getTableName(companyId));

				if (directory.tableExists()) {
					directory.delete();
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (NamingException ne) {
				throw new RuntimeException(ne);
			}
			catch (UnsupportedOperationException uoe) {
				throw new RuntimeException(uoe);
			}
		}
		else {
			FileUtil.deltree(
				PropsUtil.get(PropsUtil.LUCENE_DIR) + companyId +
					StringPool.SLASH);
		}
	}

	private Analyzer _getAnalyzer() {
		try {
			return (Analyzer)_analyzerClass.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Directory _getLuceneDir(String companyId) {
		Directory directory = null;

		if (PropsUtil.get(PropsUtil.LUCENE_STORE_TYPE).equals(
				_LUCENE_STORE_TYPE_JDBC)) {

			try {
				DataSource ds = (DataSource)JNDIUtil.lookup(
					new InitialContext(), Constants.DATA_SOURCE);

				directory = new JdbcDirectory(
					ds, _dialect, _getTableName(companyId));

				JdbcDirectory jdbcDir = (JdbcDirectory) directory;

				if (!jdbcDir.tableExists()) {
					jdbcDir.create();
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (NamingException ne) {
				throw new RuntimeException(ne);
			}
			catch (UnsupportedOperationException uoe) {
				throw new RuntimeException(uoe);
			}
		}
		else {
			String path = PropsUtil.get(PropsUtil.LUCENE_DIR) + companyId +
				StringPool.SLASH;

			try {
				directory = FSDirectory.getDirectory(path, false);
			}
			catch (IOException ioe1) {
				try {
					if (directory != null) {
						directory.close();
					}

					directory = FSDirectory.getDirectory(path, true);
				}
				catch (IOException ioe2) {
					throw new RuntimeException(ioe2);
				}
			}
		}

		return directory;
	}

	private String _getTableName(String companyId) {
		return _LUCENE_TABLE_PREFIX + StringUtil.replace(
			companyId, StringPool.PERIOD, StringPool.UNDERLINE);
	}

	//private static final String _LUCENE_STORE_TYPE_FILE = "file";

	private static final String _LUCENE_STORE_TYPE_JDBC = "jdbc";

	private static final String _LUCENE_TABLE_PREFIX = "LUCENE_";

	private static Log _log = LogFactory.getLog(LuceneUtil.class);

	private static LuceneUtil _instance = new LuceneUtil();

	private Class _analyzerClass = WhitespaceAnalyzer.class;
	private Dialect _dialect;

}