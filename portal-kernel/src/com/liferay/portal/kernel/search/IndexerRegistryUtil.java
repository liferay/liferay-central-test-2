/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 *
 */
public class IndexerRegistryUtil {

	public static IndexerRegistry getIndexerRegistry() {
		return _indexerRegistry;
	}

	public static List<Indexer> getRegisteredIndexers() {
		return getIndexerRegistry().getRegisteredIndexers();
	}

	public static Indexer getRegisteredIndexer(String className) {
		return getIndexerRegistry().getRegisteredIndexer(className);
	}

	public static void registerIndexer(
		String className, Indexer indexerInstance) {

		getIndexerRegistry().registerIndexer(className, indexerInstance);
	}

	public void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	public static void unRegisterIndexer(String className) {
		getIndexerRegistry().unRegisterIndexer(className);
	}

	private static IndexerRegistry _indexerRegistry;

}