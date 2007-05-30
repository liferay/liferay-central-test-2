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

package com.liferay.portal.lucene;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.DataAccess;
import com.liferay.util.lucene.KeywordsUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Statement;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 *
 */
public class LuceneUtil {

	public static final Pattern TERM_END_PATTERN =
		Pattern.compile("(\\w{4,}?)\\b");

	public static void acquireLock(long companyId) {
		try {
			_instance._sharedWriter.acquireLock(companyId, true);
		}
		catch (InterruptedException ie) {
			_log.error(ie);
		}
	}

	public static void addExactTerm(
			BooleanQuery booleanQuery, String field, long number)
		throws ParseException {

		addExactTerm(booleanQuery, field, String.valueOf(number));
	}

	public static void addExactTerm(
			BooleanQuery booleanQuery, String field, String text)
		throws ParseException {

		text = KeywordsUtil.escape(text);

		Query query = new TermQuery(new Term(field, text));

		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
	}

	public static void addTerm(
			BooleanQuery booleanQuery, String field, long number)
		throws ParseException {

		addTerm(booleanQuery, field, String.valueOf(number));
	}

	public static void addTerm(
			BooleanQuery booleanQuery, String field, String text)
		throws ParseException {

		if (Validator.isNotNull(text)) {
			Matcher matcher = TERM_END_PATTERN.matcher(text);

			// Add wildcard to the end of every word 4 chars or longer

			text = matcher.replaceAll("$1*");

			// Add fuzzy query to the end of every word 4 chars or longer

			text = text + " " + matcher.replaceAll("$1~");

			// Remove invalid hints

			text = StringUtil.replace(text, "**", "*");
			text = StringUtil.replace(text, "*~", "*");
			text = StringUtil.replace(text, "~~", "~");
			text = StringUtil.replace(text, "~*", "~");

			QueryParser queryParser = new QueryParser(
				field, LuceneUtil.getAnalyzer());

			try {
				Query query = queryParser.parse(text);

				booleanQuery.add(query, BooleanClause.Occur.SHOULD);
			}
			catch(ParseException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"ParseException thrown, reverting to literal search",
						pe);
				}

				text = KeywordsUtil.escape(text);

				Query query = queryParser.parse(text);

				booleanQuery.add(query, BooleanClause.Occur.SHOULD);
			}
		}
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, long number) {

		addRequiredTerm(booleanQuery, field, String.valueOf(number));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String text) {

		text = KeywordsUtil.escape(text);

		Term term = new Term(field, text);
		TermQuery termQuery = new TermQuery(term);

		booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
	}

	public static void delete(long companyId) {
		_instance._delete(companyId);
	}

	public static void deleteDocuments(long companyId, Term term)
		throws IOException {

		try {
			_instance._sharedWriter.deleteDocuments(companyId, term);
		}
		catch (InterruptedException ie) {
			_log.error(ie);
		}
	}

	public static Analyzer getAnalyzer() {
		return _instance._getAnalyzer();
	}

	public static Directory getLuceneDir(long companyId) {
		return _instance._getLuceneDir(companyId);
	}

	public static IndexReader getReader(long companyId) throws IOException {
		return IndexReader.open(getLuceneDir(companyId));
	}

	public static IndexSearcher getSearcher(long companyId) throws IOException {
		return _instance._getSearcher(companyId);
	}

	public static IndexWriter getWriter(long companyId) throws IOException {
		return getWriter(companyId, false);
	}

	public static IndexWriter getWriter(long companyId, boolean create)
		throws IOException {

		return _instance._sharedWriter.getWriter(companyId, create);
	}

	public static void releaseLock(long companyId) {
		_instance._sharedWriter.releaseLock(companyId);
	}

	public static void write(long companyId) throws IOException {
		_instance._sharedWriter.write(companyId);
	}

	public static void write(IndexWriter writer) throws IOException {
		_instance._sharedWriter.write(writer);
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
				con = HibernateUtil.getConnection();

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

	public void _delete(long companyId) {
		if (PropsUtil.get(PropsUtil.LUCENE_STORE_TYPE).equals(
				_LUCENE_STORE_TYPE_JDBC)) {

			String tableName = _getTableName(companyId);

			try {
				DataSource ds = HibernateUtil.getDataSource();

				JdbcDirectory directory = new JdbcDirectory(
					ds, _dialect, tableName);

				if (directory.tableExists()) {
					directory.delete();
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (UnsupportedOperationException uoe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Database doesn't support the ability to check " +
							"whether a table exists");
				}

				Connection con = null;
				Statement s = null;

				try {
					con = HibernateUtil.getConnection();

					s = con.createStatement();

					s.executeUpdate("DELETE FROM " + tableName);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Could not truncate " + tableName);
					}
				}
				finally {
					DataAccess.cleanUp(con, s);
				}
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

	private Directory _getLuceneDir(long companyId) {
		Directory directory = null;

		if (PropsUtil.get(PropsUtil.LUCENE_STORE_TYPE).equals(
				_LUCENE_STORE_TYPE_JDBC)) {

			String tableName = _getTableName(companyId);

			JdbcDirectory jdbcDir = null;

			try {
				DataSource ds = HibernateUtil.getDataSource();

				directory = new JdbcDirectory(ds, _dialect, tableName);

				jdbcDir = (JdbcDirectory)directory;

				if (!jdbcDir.tableExists()) {
					jdbcDir.create();
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (UnsupportedOperationException uoe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Database doesn't support the ability to check " +
							"whether a table exists");
				}

				try {
					jdbcDir.create();
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Could not create " + tableName);
					}
				}
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

	private IndexSearcher _getSearcher(long companyId) throws IOException {
		Long companyIdObj = new Long(companyId);

		IndexSearcher indexSearcher =
			(IndexSearcher)_sharedSearchers.get(companyIdObj);

		if (indexSearcher == null) {
			indexSearcher = new IndexSearcher(_getLuceneDir(companyId));

			_instance._sharedSearchers.put(companyIdObj, indexSearcher);
		}

		return indexSearcher;
	}

	private String _getTableName(long companyId) {
		return _LUCENE_TABLE_PREFIX + companyId;
	}

	//private static final String _LUCENE_STORE_TYPE_FILE = "file";

	private static final String _LUCENE_STORE_TYPE_JDBC = "jdbc";

	private static final String _LUCENE_TABLE_PREFIX = "LUCENE_";

	private static Log _log = LogFactory.getLog(LuceneUtil.class);

	private static LuceneUtil _instance = new LuceneUtil();

	private IndexWriterFactory _sharedWriter = new IndexWriterFactory();
	private Map _sharedSearchers = CollectionFactory.getSyncHashMap();
	private Class _analyzerClass = WhitespaceAnalyzer.class;
	private Dialect _dialect;

}