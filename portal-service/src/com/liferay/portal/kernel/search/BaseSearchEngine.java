/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author Bruno Farache
 */
public class BaseSearchEngine implements SearchEngine {

	public BooleanClauseFactory getBooleanClauseFactory() {
		if (_booleanClauseFactory == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			String className =
				"com.liferay.portal.search.generic.BooleanClauseFactoryImpl";

			try {
				_booleanClauseFactory =
					(BooleanClauseFactory)InstanceFactory.newInstance(
						classLoader, className);
			}
			catch (Exception e) {
				_log.fatal(
					"Unable to locate appropriate BooleanClauseFactory", e);
			}
		}

		return _booleanClauseFactory;
	}

	public BooleanQueryFactory getBooleanQueryFactory() {
		if (_booleanQueryFactory == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			String className =
				"com.liferay.portal.search.lucene.BooleanQueryFactoryImpl";

			if (!isLuceneBased()) {
				className =
					"com.liferay.portal.search.generic.BooleanQueryFactoryImpl";
			}

			try {
				_booleanQueryFactory =
					(BooleanQueryFactory)InstanceFactory.newInstance(
						classLoader, className);
			}
			catch (Exception e) {
				_log.fatal(
					"Unable to locate appropriate BooleanQueryFactory", e);
			}
		}

		return _booleanQueryFactory;
	}

	public Priority getClusteredWritePriority() {
		return _clusteredWritePriority;
	}

	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	public TermQueryFactory getTermQueryFactory() {
		if (_termQueryFactory == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			String className =
				"com.liferay.portal.search.lucene.TermQueryFactoryImpl";

			if (!isLuceneBased()) {
				className =
					"com.liferay.portal.search.generic.TermQueryFactoryImpl";
			}

			try {
				_termQueryFactory =
					(TermQueryFactory)InstanceFactory.newInstance(
						classLoader, className);
			}
			catch (Exception e) {
				_log.fatal(
					"Unable to locate appropriate BooleanQueryFactory", e);
			}
		}

		return _termQueryFactory;
	}

	public TermRangeQueryFactory getTermRangeQueryFactory() {
		if (_termRangeQueryFactory == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			String className =
				"com.liferay.portal.search.lucene.TermRangeQueryFactoryImpl";

			if (!isLuceneBased()) {
				className =
					"com.liferay.portal.search.generic.TermRangeQueryFactoryImpl";
			}

			try {
				_termRangeQueryFactory =
					(TermRangeQueryFactory)InstanceFactory.newInstance(
						classLoader, className);
			}
			catch (Exception e) {
				_log.fatal(
					"Unable to locate appropriate BooleanQueryFactory", e);
			}
		}

		return _termRangeQueryFactory;
	}

	public String getVendor() {
		return _vendor;
	}

	public boolean isClusteredWrite() {
		return _clusteredWrite;
	}

	public boolean isLuceneBased() {
		return _luceneBased;
	}

	public void setBooleanClauseFactory(
		BooleanClauseFactory booleanClauseFactory) {

		_booleanClauseFactory = booleanClauseFactory;
	}

	public void setBooleanQueryFactory(
		BooleanQueryFactory booleanQueryFactory) {

		_booleanQueryFactory = booleanQueryFactory;
	}

	public void setClusteredWrite(boolean clusteredWrite) {
		_clusteredWrite = clusteredWrite;
	}

	public void setClusteredWritePriority(Priority clusteredWritePriority) {
		_clusteredWritePriority = clusteredWritePriority;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	public void setLuceneBased(boolean luceneBased) {
		_luceneBased = luceneBased;
	}

	public void setTermQueryFactory(TermQueryFactory termQueryFactory) {
		_termQueryFactory = termQueryFactory;
	}

	public void setTermRangeQueryFactory(
		TermRangeQueryFactory termRangeQueryFactory) {

		_termRangeQueryFactory = termRangeQueryFactory;
	}

	public void setVendor(String vendor) {
		_vendor = vendor;
	}

	private static Log _log = LogFactoryUtil.getLog(BaseSearchEngine.class);

	private BooleanClauseFactory _booleanClauseFactory;
	private BooleanQueryFactory _booleanQueryFactory;
	private boolean _clusteredWrite;
	private Priority _clusteredWritePriority;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private boolean _luceneBased;
	private TermRangeQueryFactory _termRangeQueryFactory;
	private TermQueryFactory _termQueryFactory;
	private String _vendor;

}