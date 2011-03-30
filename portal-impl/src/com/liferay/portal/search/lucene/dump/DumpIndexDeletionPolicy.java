/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.dump;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;

/**
 * @author Shuyang Zhou
 */
public class DumpIndexDeletionPolicy implements IndexDeletionPolicy {

	public void onInit(List<? extends IndexCommit> list) throws IOException {
		onCommit(list);
	}

	public void onCommit(List<? extends IndexCommit> list) throws IOException {
		_lastIndexCommit = list.get(list.size() - 1);

		for (int i = 0; i < list.size() - 1; i++) {
			IndexCommit indexCommit = list.get(i);
			if (!_dumpingSegmensFiletNames.contains(
				indexCommit.getSegmentsFileName())) {
				indexCommit.delete();
			}
		}
	}

	public void dump(
			OutputStream outputStream,
			IndexWriter indexWriter, Lock commitLock)
		throws IOException {

		IndexCommit indexCommit = null;
		String segmentsFileName = null;

		// Lock up to stop external commits while recording dump IndexCommit
		commitLock.lock();
		try {
			// Commit all pending changes to ensure dumped index is integrated
			indexWriter.commit();
			// Record IndexCommit, then release the lock to other dumpers and
			// committers to move on
			indexCommit = _lastIndexCommit;
			segmentsFileName = indexCommit.getSegmentsFileName();
			_dumpingSegmensFiletNames.add(segmentsFileName);
		}
		finally {
			commitLock.unlock();
		}

		try {
			IndexCommitSerializationUtil.serializeIndex(
				indexCommit, outputStream);
		}
		finally {
			// Clear the dumping segments file name, so the old index can be
			// removed.
			_dumpingSegmensFiletNames.remove(segmentsFileName);
		}
	}

	private IndexCommit _lastIndexCommit;
	private List<String> _dumpingSegmensFiletNames =
		new CopyOnWriteArrayList<String>();

}