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
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.JdbcStoreException;
import org.apache.lucene.store.jdbc.dialect.Dialect;
import org.apache.lucene.store.jdbc.lock.JdbcLock;
import org.apache.lucene.store.jdbc.support.JdbcTemplate;

/**
 * <a href="IndexAccessorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class IndexAccessorImpl implements IndexAccessor {

	public IndexAccessorImpl(long companyId) {
		_companyId = companyId;

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

	public void addDocument(Document document) throws IOException {
		write(null, document);
	}

	public void checkLuceneDir() throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			if (_indexWriter == null) {
				Directory directory = getLuceneDir();

				if (IndexWriter.isLocked(directory)) {
					IndexWriter.unlock(directory);
				}

				initIndexWriter();
				cleanUp();
			}
		}
	}

	public void delete() {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
			}

			if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
				_deleteFile();
			}
			else if (PropsValues.LUCENE_STORE_TYPE.equals(
						_LUCENE_STORE_TYPE_JDBC)) {

				_deleteJdbc();
			}
			else if (PropsValues.LUCENE_STORE_TYPE.equals(
						_LUCENE_STORE_TYPE_RAM)) {

				_deleteRam();
			}
			else {
				throw new RuntimeException(
					"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
			}
		}
	}

	public void deleteDocuments(Term term) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			initIndexWriter();

			try {
				_indexWriter.deleteDocuments(term);
			}
			finally {
				cleanUp();
			}
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Directory getLuceneDir() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			return _getLuceneDirFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			return _getLuceneDirJdbc();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			return _getLuceneDirRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	public void updateDocument(Term term, Document document)
		throws IOException {

		write(term, document);
	}

	protected void cleanUp() throws IOException {
		synchronized (this) {
			if (_indexWriter != null) {
				_indexWriter.close();
			}

			_indexWriter = null;
		}
	}

	protected void initIndexWriter() throws IOException {
		if (_indexWriter == null) {
			_indexWriter = new IndexWriter(
				getLuceneDir(), LuceneHelperUtil.getAnalyzer(),
				IndexWriter.MaxFieldLength.LIMITED);

			_indexWriter.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
		}
	}

	protected void write(Term term, Document document) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		synchronized (this) {
			initIndexWriter();

			try {
				if (term != null) {
					_indexWriter.updateDocument(term, document);
				}
				else {
					_indexWriter.addDocument(document);
				}

				_optimizeCount++;

				if ((PropsValues.LUCENE_OPTIMIZE_INTERVAL == 0) ||
					(_optimizeCount >=
						PropsValues.LUCENE_OPTIMIZE_INTERVAL)) {

					_indexWriter.optimize();

					_optimizeCount = 0;
				}
			}
			finally {
				cleanUp();
			}
		}
	}

	private void _deleteFile() {
		String path = _getPath();

		try {
			Directory directory = _getDirectory(path);

			directory.close();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not close directory " + path);
			}
		}

		FileUtil.deltree(path);
	}

	private void _deleteJdbc() {
		String tableName = _getTableName();

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

	private void _deleteRam() {
	}

	private FSDirectory _getDirectory(String path) throws IOException {
		return FSDirectory.open(new File(path));
	}

	private Directory _getLuceneDirFile() {
		Directory directory = null;

		String path = _getPath();

		try {
			directory = _getDirectory(path);
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

	private Directory _getLuceneDirJdbc() {
		JdbcDirectory directory = null;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			String tableName = _getTableName();

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

	private Directory _getLuceneDirRam() {
		String path = _getPath();

		Directory directory = _ramDirectories.get(path);

		if (directory == null) {
			directory = new RAMDirectory();

			_ramDirectories.put(path, directory);
		}

		return directory;
	}

	private String _getPath() {
		StringBuilder sb = new StringBuilder();

		sb.append(PropsValues.LUCENE_DIR);
		sb.append(_companyId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	private String _getTableName() {
		return _LUCENE_TABLE_PREFIX + _companyId;
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

	private static Log _log = LogFactoryUtil.getLog(IndexAccessorImpl.class);

	private long _companyId;
	private Dialect _dialect;
	private IndexWriter _indexWriter;
	private int _optimizeCount;
	private Map<String, Directory> _jdbcDirectories =
		new ConcurrentHashMap<String, Directory>();
	private Map<String, Directory> _ramDirectories =
		new ConcurrentHashMap<String, Directory>();

}