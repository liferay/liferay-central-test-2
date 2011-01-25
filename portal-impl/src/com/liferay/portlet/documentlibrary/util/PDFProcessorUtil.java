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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;
import com.liferay.util.SystemProperties;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

/**
 * @author Alexander Chow
 */
public class PDFProcessorUtil {

	public static final String THUMBNAIL_TYPE = ImageProcessor.TYPE_JPEG;

	public static void generateThumbnails() {
		_instance._generateThumbnails();
	}

	public static File getThumbnailFile(String id) {
		return _instance._getThumbnailFile(id);
	}

	public static boolean hasThumbnail(FileEntry fileEntry) {
		return _instance._hasThumbnail(fileEntry);
	}

	private PDFProcessorUtil() {
		if (!PropsValues.IMAGEMAGICK_ENABLED) {
			return;
		}

		String filterName = null;

		if (OSDetector.isApple()) {
			filterName = "apple";
		}
		else if (OSDetector.isWindows()) {
			filterName = "windows";
		}
		else {
			filterName = "unix";
		}

		String globalSearchPath = PropsUtil.get(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH,
			new Filter(filterName));

		ProcessStarter.setGlobalSearchPath(globalSearchPath);

		_convertCmd = new ConvertCmd();
	}

	private void _generateThumbnail(FileEntry fileEntry, File file)
		throws IOException, PortalException, SystemException {

		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		File thumbnailFile = _getThumbnailFile(id);

		if (thumbnailFile.exists()) {
			return;
		}

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			IMOperation imOperation = new IMOperation();

			imOperation.addImage(file.getPath() + "[0]");

			imOperation.scale(_DIMENSION, _DIMENSION);

			imOperation.addImage(_getThumbnailFilePath(id));

			try {
				_convertCmd.run(imOperation);
			}
			catch (IOException ioe) {
				throw ioe;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}
		else {
			_generateThumbnail(fileEntry, new FileInputStream(file));
		}
	}

	private void _generateThumbnail(
			FileEntry fileEntry, InputStream inputStream)
		throws IOException, PortalException, SystemException {

		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		File thumbnailFile = _getThumbnailFile(id);

		if (thumbnailFile.exists()) {
			return;
		}

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			if (fileEntry.getExtension().equals("pdf")) {
				InputStream tempInputStream =
					DLRepositoryLocalServiceUtil.getFileAsStream(
						fileEntry.getUserId(), fileEntry.getFileEntryId(),
						fileEntry.getVersion());

				String filePath = DocumentConversionUtil.getFilePath(id, "pdf");

				File file = new File(filePath);

				try {
					FileUtil.write(file, tempInputStream);

					_generateThumbnail(fileEntry, file);
				}
				finally {
					file.delete();
				}
			}
			else {
				File file = DocumentConversionUtil.convert(
					id, inputStream, fileEntry.getExtension(), "pdf");

				_generateThumbnail(fileEntry, file);
			}
		}
		else {
			PDDocument pdDocument = null;

			try {
				pdDocument = PDDocument.load(inputStream);

				PDDocumentCatalog pdDocumentCatalog =
					pdDocument.getDocumentCatalog();

				List pdPages = pdDocumentCatalog.getAllPages();

				PDPage pdPage = (PDPage)pdPages.get(0);

				RenderedImage renderedImage = pdPage.convertToImage(
					BufferedImage.TYPE_USHORT_565_RGB, 72);

				RenderedImage thumbnailImage = ImageProcessorUtil.scale(
					renderedImage, _DIMENSION, _DIMENSION);

				thumbnailFile.createNewFile();

				ImageIO.write(
					thumbnailImage, THUMBNAIL_TYPE,
					new FileOutputStream(thumbnailFile));
			}
			finally {
				if (pdDocument != null) {
					pdDocument.close();
				}
			}
		}
	}

	private void _generateThumbnails() {

		// At most, occupy thread for one minute at a time

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, 1);

		Date expiration = cal.getTime();

		while (!_fileEntries.isEmpty()) {
			Date now = new Date();

			if (now.after(expiration)) {
				break;
			}

			FileEntry fileEntry = _fileEntries.remove(0);

			try {
				String extension = fileEntry.getExtension();

				if (extension.equals("pdf")) {
					InputStream inputStream =
						DLRepositoryLocalServiceUtil.getFileAsStream(
							fileEntry.getUserId(), fileEntry.getFileEntryId(),
							fileEntry.getVersion());

					_generateThumbnail(fileEntry, inputStream);
				}
				else if (PrefsPropsUtil.getBoolean(
							PropsKeys.OPENOFFICE_SERVER_ENABLED,
							PropsValues.OPENOFFICE_SERVER_ENABLED)) {

					InputStream inputStream =
						DLRepositoryLocalServiceUtil.getFileAsStream(
							fileEntry.getUserId(), fileEntry.getFileEntryId(),
							fileEntry.getVersion());

					String id = DLUtil.getTempFileId(
						fileEntry.getFileEntryId(), fileEntry.getVersion());

					File file = DocumentConversionUtil.convert(
						id, inputStream, fileEntry.getExtension(), "pdf");

					_generateThumbnail(fileEntry, file);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private File _getThumbnailFile(String id) {
		String filePath = _getThumbnailFilePath(id);

		File file = new File(filePath);

		if (file.getParent() != null) {
			FileUtil.mkdirs(file.getParent());
		}

		return file;
	}

	private String _getThumbnailFilePath(String id) {
		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append("/liferay/document_thumbnail/");
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(THUMBNAIL_TYPE);

		return sb.toString();
	}

	private boolean _hasThumbnail(FileEntry fileEntry) {
		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		String fileName = _getThumbnailFilePath(id);

		File file = new File(fileName);

		boolean hasThumbnail = file.exists();

		if (PropsValues.DL_GENERATE_THUMBNAILS && !hasThumbnail &&
			!_fileEntries.contains(fileEntry)) {

			String extension = fileEntry.getExtension();

			if (extension.equals("pdf")) {
				_fileEntries.add(fileEntry);
			}
			else {
				String[] conversions = DocumentConversionUtil.getConversions(
					fileEntry.getExtension());

				for (String conversion : conversions) {
					if (conversion.equals("pdf")) {
						_fileEntries.add(fileEntry);

						break;
					}
				}
			}
		}

		return hasThumbnail;
	}

	private static final int _DIMENSION = 128;

	private static Log _log = LogFactoryUtil.getLog(PDFProcessorUtil.class);

	private static PDFProcessorUtil _instance = new PDFProcessorUtil();

	private ConvertCmd _convertCmd;
	private List<FileEntry> _fileEntries = new Vector<FileEntry>();

}