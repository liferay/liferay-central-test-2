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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.store.Directory;

/**
 * @author Shuyang Zhou
 */
public class IndexCommitMetaInfo implements Serializable {

	public IndexCommitMetaInfo(IndexCommit indexCommit) throws IOException {
		if (indexCommit == null) {
			_empty = true;
			return;
		}

		_empty = false;

		List<String> fileNameLists = new ArrayList<String>(
			indexCommit.getFileNames());

		_fileInfoList = new ArrayList<FileInfo>(fileNameLists.size());

		Directory directory = indexCommit.getDirectory();

		for (String fileName : fileNameLists) {
			FileInfo fileInfo = new FileInfo(fileName,
				directory.fileLength(fileName));
			_fileInfoList.add(fileInfo);
		}

		_generation = indexCommit.getGeneration();

	}

	public boolean isEmpty() {
		return _empty;
	}

	public List<FileInfo> getFileInfoList() {
		return _fileInfoList;
	}

	public long getGeneration() {
		return _generation;
	}

	public String toString() {
		if (_empty) {
			return "{empty}";
		}

		StringBundler sb = new StringBundler(_fileInfoList.size() * 5 + 4);

		sb.append("{generation=");
		sb.append(_generation);
		sb.append(", fileInfos[");

		for (FileInfo fileInfo : _fileInfoList) {
			sb.append("name=");
			sb.append(fileInfo._fileName);
			sb.append(", fileSize=");
			sb.append(fileInfo._fileSize);
			sb.append(StringPool.COLON);
		}

		sb.append("]}");

		return sb.toString();
	}

	private boolean _empty;
	private List<FileInfo> _fileInfoList;
	private long _generation;

	public static final String SEGMENTS_GEN = "segments.gen";

	public static class FileInfo implements Serializable {

		public FileInfo(String fileName, long fileSize) {
			_fileName = fileName;
			_fileSize = fileSize;
		}

		public String getFileName() {
			return _fileName;
		}

		public long getFileSize() {
			return _fileSize;
		}

		private String _fileName;
		private long _fileSize;

	}

}