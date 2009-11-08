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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.lucene.KeywordsUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.QueryTermExtractor;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.WeightedTerm;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.JdbcStoreException;
import org.apache.lucene.store.jdbc.dialect.Dialect;
import org.apache.lucene.store.jdbc.lock.JdbcLock;
import org.apache.lucene.store.jdbc.support.JdbcTemplate;

/**
 * <a href="LuceneHelperImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 */
public class LuceneHelperImpl implements LuceneHelper {

	public void addDocument(long companyId, Document document)
		throws IOException {

		_indexAccessor.addDocument(companyId, document);
	}

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		//text = KeywordsUtil.escape(value);

		Query query = new TermQuery(new Term(field, value));

		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
	}

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		if (like) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.STAR);

			value = value.toLowerCase();

			WildcardQuery wildcardQuery = new WildcardQuery(
				new Term(field, value));

			booleanQuery.add(wildcardQuery, BooleanClause.Occur.MUST);
		}
		else {
			//text = KeywordsUtil.escape(value);

			Term term = new Term(field, value);
			TermQuery termQuery = new TermQuery(term);

			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
		}
	}

	public void addTerm(
			BooleanQuery booleanQuery, String field, String value, boolean like)
		throws ParseException {

		if (Validator.isNull(value)) {
			return;
		}

		if (like) {
			value = value.toLowerCase();

			StringBuilder sb = new StringBuilder();

			sb.append(StringPool.STAR);
			sb.append(value);
			sb.append(StringPool.STAR);

			WildcardQuery wildcardQuery = new WildcardQuery(
				new Term(field, sb.toString()));

			booleanQuery.add(wildcardQuery, BooleanClause.Occur.SHOULD);
		}
		else {
			QueryParser queryParser = new QueryParser(field, getAnalyzer());

			try {
				Query query = queryParser.parse(value);

				booleanQuery.add(query, BooleanClause.Occur.SHOULD);
			}
			catch (ParseException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"ParseException thrown, reverting to literal search",
						pe);
				}

				value = KeywordsUtil.escape(value);

				Query query = queryParser.parse(value);

				booleanQuery.add(query, BooleanClause.Occur.SHOULD);
			}
		}
	}

	public void checkLuceneDir(long companyId) {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		Directory luceneDir = getLuceneDir(companyId);

		try {

			// LEP-6078

			if (luceneDir.fileExists("write.lock")) {
				luceneDir.deleteFile("write.lock");
			}
		}
		catch (IOException ioe) {
			_log.error("Unable to clear write lock", ioe);
		}

		// Lucene does not properly release its lock on the index when
		// IndexWriter throws an exception

		try {
			addDocument(companyId, null);
		}
		catch (IOException ioe) {
			_log.error("Check Lucene directory failed for " + companyId, ioe);
		}
	}

	public void delete(long companyId) {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			_deleteFile(companyId);
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			_deleteJdbc(companyId);
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			_deleteRam(companyId);
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	public void deleteDocuments(long companyId, Term term) throws IOException {
		_indexAccessor.deleteDocuments(companyId, term);
	}

	public Analyzer getAnalyzer() {
		try {
			return (Analyzer)_analyzerClass.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public FSDirectory getDirectory(String path) throws IOException {
		return FSDirectory.open(new File(path));
	}

	public Directory getLuceneDir(long companyId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			return _getLuceneDirFile(companyId);
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			return _getLuceneDirJdbc(companyId);
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			return _getLuceneDirRam(companyId);
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	public String[] getQueryTerms(Query query) {
		String[] fieldNames = new String[] {
			Field.CONTENT, Field.DESCRIPTION, Field.PROPERTIES, Field.TITLE,
			Field.USER_NAME
		};

		WeightedTerm[] weightedTerms = null;

		for (String fieldName : fieldNames) {
			weightedTerms = QueryTermExtractor.getTerms(
				query, false, fieldName);

			if (weightedTerms.length > 0) {
				break;
			}
		}

		Set<String> queryTerms = new HashSet<String>();

		for (WeightedTerm weightedTerm : weightedTerms) {
			queryTerms.add(weightedTerm.getTerm());
		}

		return queryTerms.toArray(new String[queryTerms.size()]);
	}

	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		return new IndexSearcher(getLuceneDir(companyId), readOnly);
	}

	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, String preTag,
			String postTag)
		throws IOException {

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
			preTag, postTag);

		QueryScorer queryScorer = new QueryScorer(query, field);

		Highlighter highlighter = new Highlighter(
			simpleHTMLFormatter, queryScorer);

		highlighter.setTextFragmenter(new SimpleFragmenter(fragmentLength));

		TokenStream tokenStream = getAnalyzer().tokenStream(
			field, new StringReader(s));

		try {
			String snippet = highlighter.getBestFragments(
				tokenStream, s, maxNumFragments, fragmentSuffix);

			if (Validator.isNotNull(snippet) &&
				!StringUtil.endsWith(snippet, fragmentSuffix)) {

				snippet = snippet + fragmentSuffix;
			}

			return snippet;
		}
		catch (InvalidTokenOffsetsException itoe) {
			throw new IOException(itoe.getMessage());
		}
	}

	public void updateDocument(long companyId, Term term, Document document)
		throws IOException {

		_indexAccessor.updateDocument(companyId, term, document);
	}

	private LuceneHelperImpl() {
		String analyzerName = PropsUtil.get(PropsKeys.LUCENE_ANALYZER);

		if (Validator.isNotNull(analyzerName)) {
			try {
				_analyzerClass = Class.forName(analyzerName);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		// Dialect

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_JDBC)) {
			Connection con = null;

			try {
				con = DataAccess.getConnection();

				String url = con.getMetaData().getURL();

				int x = url.indexOf(":");
				int y = url.indexOf(":", x + 1);

				String urlPrefix = url.substring(x + 1, y);

				String dialectClass = PropsUtil.get(
					PropsKeys.LUCENE_STORE_JDBC_DIALECT + urlPrefix);

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

	private void _deleteFile(long companyId) {
		String path = _getPath(companyId);

		try {
			Directory directory = getDirectory(path);

			directory.close();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not close directory " + path);
			}
		}

		FileUtil.deltree(path);
	}

	private void _deleteJdbc(long companyId) {
		String tableName = _getTableName(companyId);

		try {
			Directory directory = _jdbcDirectories.remove(tableName);

			if (directory != null) {
				directory.close();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not close directory " + tableName);
			}
		}

		Connection con = null;
		Statement s = null;

		try {
			con = DataAccess.getConnection();

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

	private void _deleteRam(long companyId) {
	}

	private Directory _getLuceneDirFile(long companyId) {
		Directory directory = null;

		String path = _getPath(companyId);

		try {
			directory = getDirectory(path);
		}
		catch (IOException ioe1) {
			if (directory != null) {
				try {
					directory.close();
				}
				catch (Exception e) {
				}
			}
		}

		return directory;
	}

	private Directory _getLuceneDirJdbc(long companyId) {
		JdbcDirectory directory = null;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			String tableName = _getTableName(companyId);

			directory = (JdbcDirectory)_jdbcDirectories.get(tableName);

			if (directory != null) {
				return directory;
			}

			try {
				DataSource dataSource = InfrastructureUtil.getDataSource();

				directory = new JdbcDirectory(dataSource, _dialect, tableName);

				_jdbcDirectories.put(tableName, directory);

				if (!directory.tableExists()) {
					directory.create();
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

				_manuallyCreateJdbcDirectory(directory, tableName);
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return directory;
	}

	private Directory _getLuceneDirRam(long companyId) {
		String path = _getPath(companyId);

		Directory directory = _ramDirectories.get(path);

		if (directory == null) {
			directory = new RAMDirectory();

			_ramDirectories.put(path, directory);
		}

		return directory;
	}

	private String _getPath(long companyId) {
		StringBuilder sb = new StringBuilder();

		sb.append(PropsValues.LUCENE_DIR);
		sb.append(companyId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	private String _getTableName(long companyId) {
		return _LUCENE_TABLE_PREFIX + companyId;
	}

	private void _manuallyCreateJdbcDirectory(
		JdbcDirectory directory, String tableName) {

		// LEP-2181

		Connection con = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			// Check if table exists

			DatabaseMetaData metaData = con.getMetaData();

			rs = metaData.getTables(null, null, tableName, null);

			if (!rs.next()) {
				JdbcTemplate jdbcTemplate = directory.getJdbcTemplate();

				jdbcTemplate.executeUpdate(directory.getTable().sqlCreate());

				Class<?> lockClass = directory.getSettings().getLockClass();

				JdbcLock jdbcLock = null;

				try {
					jdbcLock = (JdbcLock)lockClass.newInstance();
				}
				catch (Exception e) {
					throw new JdbcStoreException(
						"Failed to create lock class " + lockClass);
				}

				jdbcLock.initializeDatabase(directory);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not create " + tableName);
			}
		}
		finally {
			DataAccess.cleanUp(con, null, rs);
		}
	}

	private static final String _LUCENE_STORE_TYPE_FILE = "file";

	private static final String _LUCENE_STORE_TYPE_JDBC = "jdbc";

	private static final String _LUCENE_STORE_TYPE_RAM = "ram";

	private static final String _LUCENE_TABLE_PREFIX = "LUCENE_";

	private static Log _log = LogFactoryUtil.getLog(LuceneHelperImpl.class);

	private Class<?> _analyzerClass = WhitespaceAnalyzer.class;
	private Dialect _dialect;
	private IndexAccessor _indexAccessor = new IndexAccessorImpl();
	private Map<String, Directory> _jdbcDirectories =
		new ConcurrentHashMap<String, Directory>();
	private Map<String, Directory> _ramDirectories =
		new ConcurrentHashMap<String, Directory>();

}