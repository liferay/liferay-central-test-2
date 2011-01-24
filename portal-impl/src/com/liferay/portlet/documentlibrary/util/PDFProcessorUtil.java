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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

/**
 * @author Alexander Chow
 */
public class PDFProcessorUtil {

	public static final String THUMBNAIL_TYPE = ImageProcessor.TYPE_JPEG;

	public static void generateThumbnail(FileEntry fileEntry, File file)
		throws IOException, PortalException, SystemException {

		_instance._generateThumbnail(fileEntry, file);
	}

	public static void generateThumbnail(FileEntry fileEntry, InputStream is)
		throws IOException, PortalException, SystemException {

		_instance._generateThumbnail(fileEntry, is);
	}

	public static void generateThumbnails() {
		_instance._generateThumbnails();
	}

	public static File getFile(String id) {
		String fileName = getFilePath(id);

		File thumbnail = new File(fileName);

		if (thumbnail.getParent() != null) {
			FileUtil.mkdirs(thumbnail.getParent());
		}

		return thumbnail;
	}

	public static String getFilePath(String id) {
		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append("/liferay/document_thumbnail/");
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(THUMBNAIL_TYPE);

		return sb.toString();
	}

	public static InputStream getThumbnail(String id) throws IOException {
		return _instance._getThumbnail(id);
	}

	public static boolean hasThumbnail(FileEntry fileEntry) {
		return _instance._hasThumbnail(fileEntry);
	}

	private PDFProcessorUtil() {
		if (PropsValues.IMAGEMAGICK_ENABLED) {
			String os = System.getProperty("os.name").toLowerCase();

			String filterName;

			if (os.indexOf("mac") >= 0) {
				filterName = "apple";
			}
			else if (os.indexOf("win") >= 0) {
				filterName = "windows";
			}
			else {
				filterName = "unix";
			}

			String path = PropsUtil.get(
				PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH,
				new Filter(filterName));

			ProcessStarter.setGlobalSearchPath(path);

			_convertCommand = new ConvertCmd();
		}
	}

	private void _generateThumbnail(FileEntry fileEntry, File file)
		throws IOException, PortalException, SystemException {

		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		if (getFile(id).exists()) {
			return;
		}

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			IMOperation op = new IMOperation();

			op.addImage(file.getPath() + "[0]");
			op.scale(_DIMENSION, _DIMENSION);
			op.addImage(getFilePath(id));

			try {
				_convertCommand.run(op);
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

	private void _generateThumbnail(FileEntry fileEntry, InputStream is)
		throws IOException, PortalException, SystemException {

		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		File thumbnail = getFile(id);

		if (thumbnail.exists()) {
			return;
		}

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			if (fileEntry.getExtension().equals("pdf")) {
				InputStream tempIs =
					DLRepositoryLocalServiceUtil.getFileAsStream(
						fileEntry.getUserId(), fileEntry.getFileEntryId(),
						fileEntry.getVersion());

				String filePath = DocumentConversionUtil.getFilePath(id, "pdf");

				File file = new File(filePath);

				try {
					FileUtil.write(file, tempIs);

					_generateThumbnail(fileEntry, file);
				}
				finally {
					file.delete();
				}
			}
			else {
				File file = DocumentConversionUtil.convertToFile(
					id, is, fileEntry.getExtension(), "pdf");

				_generateThumbnail(fileEntry, file);
			}
		}
		else {
			PDDocument document = null;

			try {
				document = PDDocument.load(is);

				List pages = document.getDocumentCatalog().getAllPages();

				PDPage page = (PDPage) pages.get(0);

				RenderedImage renderedImage = page.convertToImage(
					BufferedImage.TYPE_USHORT_565_RGB, 72);

				RenderedImage thumbnailImage = ImageProcessorUtil.scale(
					renderedImage, _DIMENSION, _DIMENSION);

				thumbnail.createNewFile();

				ImageIO.write(
					thumbnailImage, THUMBNAIL_TYPE,
					new FileOutputStream(thumbnail));
			}
			finally {
				if (document != null) {
					document.close();
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
				if (fileEntry.getExtension().equals("pdf")) {
					InputStream is =
						DLRepositoryLocalServiceUtil.getFileAsStream(
							fileEntry.getUserId(),
							fileEntry.getFileEntryId(),
							fileEntry.getVersion());

					_generateThumbnail(fileEntry, is);
				}
				else if (PrefsPropsUtil.getBoolean(
						PropsKeys.OPENOFFICE_SERVER_ENABLED,
						PropsValues.OPENOFFICE_SERVER_ENABLED)) {

					InputStream is =
						DLRepositoryLocalServiceUtil.getFileAsStream(
							fileEntry.getUserId(),
							fileEntry.getFileEntryId(),
							fileEntry.getVersion());

					String id = DLUtil.getTempFileId(
						fileEntry.getFileEntryId(), fileEntry.getVersion());

					File file = DocumentConversionUtil.convertToFile(
						id, is, fileEntry.getExtension(), "pdf");

					_generateThumbnail(fileEntry, file);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private InputStream _getThumbnail(String id) throws IOException {
		String fileName = getFilePath(id);

		return new FileInputStream(new File(fileName));
	}

	private boolean _hasThumbnail(FileEntry fileEntry) {
		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		String fileName = getFilePath(id);

		File file = new File(fileName);

		boolean hasThumbnail = file.exists();

		if (PropsValues.DL_GENERATE_THUMBNAILS && !hasThumbnail &&
			!_fileEntries.contains(fileEntry)) {

			if (fileEntry.getExtension().equals("pdf")) {
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

	private static ConvertCmd _convertCommand;

	private static List<FileEntry> _fileEntries =
		Collections.synchronizedList(new ArrayList<FileEntry>());

	private static Log _log = LogFactoryUtil.getLog(PDFProcessorUtil.class);

	private static PDFProcessorUtil _instance = new PDFProcessorUtil();

}