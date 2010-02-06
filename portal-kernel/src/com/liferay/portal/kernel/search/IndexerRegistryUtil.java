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

package com.liferay.portal.kernel.search;

import java.util.List;

/**
 * <a href="IndexerRegistryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class IndexerRegistryUtil {

	public static Indexer getIndexer(Class<?> classObj) {
		return getIndexerRegistry().getIndexer(classObj.getName());
	}

	public static Indexer getIndexer(String className) {
		return getIndexerRegistry().getIndexer(className);
	}

	public static IndexerRegistry getIndexerRegistry() {
		return _indexerRegistry;
	}

	public static List<Indexer> getIndexers() {
		return getIndexerRegistry().getIndexers();
	}

	public static void register(Indexer indexer) {
		for (String className : indexer.getClassNames()) {
			register(className, indexer);
		}

		register(indexer.getClass().getName(), indexer);
	}

	public static void register(
		String className, Indexer indexer) {

		getIndexerRegistry().register(className, indexer);
	}

	public static void unregister(Indexer indexer) {
		for (String className : indexer.getClassNames()) {
			unregister(className);
		}

		unregister(indexer.getClass().getName());
	}

	public static void unregister(String className) {
		getIndexerRegistry().unregister(className);
	}

	public void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	private static IndexerRegistry _indexerRegistry;

}