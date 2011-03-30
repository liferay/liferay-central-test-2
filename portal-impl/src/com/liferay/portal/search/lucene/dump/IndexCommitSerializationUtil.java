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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.lucene.dump.IndexCommitMetaInfo.FileInfo;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;

/**
 * @author Shuyang Zhou
 */
public class IndexCommitSerializationUtil {

	public static void deserializeIndex(InputStream is, Directory directory)
		throws IOException {

		if (PropsValues.INDEX_DUMP_GZIP_ENABLE) {
			is = new GZIPInputStream(is);
		}

		ObjectInputStream objectInputStream = null;

		try {
			objectInputStream = new ObjectInputStream(is);

			IndexCommitMetaInfo indexCommitMetaInfo = null;
			try {
				indexCommitMetaInfo =
					(IndexCommitMetaInfo)objectInputStream.readObject();
			}
			catch (ClassNotFoundException cnfe) {
				throw new IOException(
					"Failed to retrieve indexCommitMetaInfo : "
						+ cnfe.getCause());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Deserializing IndexCommitMetaInfo : " +
					indexCommitMetaInfo);
			}

			if (indexCommitMetaInfo.isEmpty()) {
				return;
			}

			List<FileInfo> fileInfoList = indexCommitMetaInfo.getFileInfoList();

			for (FileInfo fileInfo : fileInfoList) {
				if (_log.isDebugEnabled()) {
					_log.debug("Deserializing segment file name : " +
						fileInfo.getFileName() + ", size : " +
						fileInfo.getFileSize());
				}
				deserializeSegmentFile(objectInputStream,
					fileInfo.getFileSize(),
					directory.createOutput(fileInfo.getFileName()));
			}

			createSegmentsGenFile(directory,
				indexCommitMetaInfo.getGeneration());
		}
		finally {
			if (objectInputStream != null) {
				objectInputStream.close();
			}
		}
	}

	public static void serializeIndex(
			IndexCommit indexCommit, OutputStream outputStream)
		throws IOException {

		if (PropsValues.INDEX_DUMP_GZIP_ENABLE) {
			outputStream = new GZIPOutputStream(outputStream);
		}

		ObjectOutputStream objectOputStream = new ObjectOutputStream(
			outputStream);

		IndexCommitMetaInfo indexCommitMetaInfo =
			new IndexCommitMetaInfo(indexCommit);

		if (_log.isDebugEnabled()) {
			_log.debug("Serializing IndexCommitMetaInfo : " +
				indexCommitMetaInfo);
		}

		objectOputStream.writeObject(indexCommitMetaInfo);

		List<FileInfo> fileInfoList =
			indexCommitMetaInfo.getFileInfoList();

		Directory directory = indexCommit.getDirectory();

		for (FileInfo fileInfo : fileInfoList) {
			if (_log.isDebugEnabled()) {
				_log.debug("Serializing segment file name : " +
					fileInfo.getFileName() + ", size : " +
					fileInfo.getFileSize());
			}
			serializeSegmentFile(directory.openInput(fileInfo.getFileName()),
				fileInfo.getFileSize(), objectOputStream);
		}

		objectOputStream.flush();

		if (PropsValues.INDEX_DUMP_GZIP_ENABLE) {
			((GZIPOutputStream)outputStream).finish();
		}
	}

	private static void createSegmentsGenFile(
			Directory directory, long generation)
		throws IOException {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating file " + _SEGMENTS_GEN_NAME +
				" with generation number : " + generation);
		}
		IndexOutput indexOutput = directory.createOutput(_SEGMENTS_GEN_NAME);
		try {
			indexOutput.writeInt(SegmentInfos.FORMAT_LOCKLESS);
			indexOutput.writeLong(generation);
			indexOutput.writeLong(generation);
		}
		finally {
			indexOutput.close();
		}
	}

	private static void deserializeSegmentFile(
			InputStream is, long length, IndexOutput indexOutput)
		throws IOException {

		try {
			indexOutput.setLength(length);

			byte[] buffer = new byte[_BUFFER_SIZE];

			long received = 0;

			while (received < length) {
				int trySize = _BUFFER_SIZE;

				if (received + _BUFFER_SIZE > length) {
					trySize = (int)(length - received);
				}

				int actualSize = is.read(buffer, 0, trySize);

				indexOutput.writeBytes(buffer, actualSize);

				received += actualSize;
			}
		}
		finally {
			indexOutput.close();
		}
	}

	private static void serializeSegmentFile(
			IndexInput indexInput, long length, OutputStream outputStream)
		throws IOException {
		byte[] buffer = new byte[_BUFFER_SIZE];

		int count = (int)(length / _BUFFER_SIZE);
		int tail = (int)(length - count * _BUFFER_SIZE);

		try {
			for (int i = 0; i < count; i++) {
				indexInput.readBytes(buffer, 0, _BUFFER_SIZE);
				outputStream.write(buffer);
			}

			indexInput.readBytes(buffer, 0, tail);
			outputStream.write(buffer, 0, tail);
		}
		finally {
			indexInput.close();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		IndexCommitSerializationUtil.class);

	private static final int _BUFFER_SIZE = 8192;
	private static final String _SEGMENTS_GEN_NAME = "segments.gen";

}