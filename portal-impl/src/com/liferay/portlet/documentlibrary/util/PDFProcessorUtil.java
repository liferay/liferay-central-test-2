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
import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;
import com.liferay.util.SystemProperties;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	public static final String PREVIEW_TYPE = ImageProcessor.TYPE_PNG;

	public static final String THUMBNAIL_TYPE = ImageProcessor.TYPE_JPEG;

	public static void generateImages() {
		_instance._generateImages();
	}

	public static File getPreviewFile(String id, int index) {
		return _instance._getPreviewFile(id, index);
	}

	public static int getPreviewFileCount(FileEntry fileEntry) {
		return _instance._getPreviewFileCount(fileEntry);
	}

	public static File getThumbnailFile(String id) {
		return _instance._getThumbnailFile(id);
	}

	public static boolean hasImages(FileEntry fileEntry) {
		return _instance._hasImages(fileEntry);
	}

	private PDFProcessorUtil() {
		FileUtil.mkdirs(_PREVIEW_PATH);
		FileUtil.mkdirs(_THUMBNAIL_PATH);

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

	private void _generateImages() {

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
							fileEntry.getVersion(), false);

					_generateImages(fileEntry, inputStream);
				}
				else if (DocumentConversionUtil.isEnabled()) {
					InputStream inputStream =
						DLRepositoryLocalServiceUtil.getFileAsStream(
							fileEntry.getUserId(), fileEntry.getFileEntryId(),
							fileEntry.getVersion(), false);

					String id = DLUtil.getTempFileId(
						fileEntry.getFileEntryId(), fileEntry.getVersion());

					File file = DocumentConversionUtil.convert(
						id, inputStream, fileEntry.getExtension(), "pdf");

					_generateImages(fileEntry, file);
				}
			}
			catch (NoSuchFileEntryException nsfee) {
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private void _generateImages(FileEntry fileEntry, File file)
		throws IOException, PortalException, SystemException {

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			String id = DLUtil.getTempFileId(
				fileEntry.getFileEntryId(), fileEntry.getVersion());

			if (_isGeneratePreview(id)) {
				_generateImagesIM(
					file, id, PropsValues.DL_FILE_ENTRY_PREVIEW_DEPTH,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DPI,
					PropsValues.DL_FILE_ENTRY_PREVIEW_HEIGHT,
					PropsValues.DL_FILE_ENTRY_PREVIEW_WIDTH, false);

				// ImageMagick converts single page PDFs without appending an
				// index. Rename file for consistency.

				File singlePagePreviewFile = _getPreviewFile(id, -1);

				if (singlePagePreviewFile.exists()) {
					singlePagePreviewFile.renameTo(_getPreviewFile(id, 1));
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"ImageMagick generated " +
							_getPreviewFileCount(fileEntry) +
								" preview pages for " + id);
				}
			}

			if (_isGenerateThumbnail(id)) {
				_generateImagesIM(
					file, id, PropsValues.DL_FILE_ENTRY_THUMBNAIL_DEPTH,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH, true);

				if (_log.isInfoEnabled()) {
					_log.info("ImageMagick generated a thumbnail for " + id);
				}
			}
		}
		else {
			_generateImages(fileEntry, new FileInputStream(file));
		}
	}

	private void _generateImages(
			FileEntry fileEntry, InputStream inputStream)
		throws IOException, PortalException, SystemException {

		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			if (fileEntry.getExtension().equals("pdf")) {
				String filePath = DocumentConversionUtil.getFilePath(id, "pdf");

				File file = new File(filePath);

				try {
					FileUtil.write(file, inputStream);

					_generateImages(fileEntry, file);
				}
				finally {
					file.delete();
				}
			}
			else {
				File file = DocumentConversionUtil.convert(
					id, inputStream, fileEntry.getExtension(), "pdf");

				_generateImages(fileEntry, file);
			}
		}
		else {
			boolean generatePreview = _isGeneratePreview(id);
			boolean generateThumbnail = _isGenerateThumbnail(id);

			PDDocument pdDocument = null;

			try {
				pdDocument = PDDocument.load(inputStream);

				PDDocumentCatalog pdDocumentCatalog =
					pdDocument.getDocumentCatalog();

				List<PDPage> pdPages = pdDocumentCatalog.getAllPages();

				for (int i = 0; i < pdPages.size(); i++) {
					PDPage pdPage = pdPages.get(i);

					if (generateThumbnail && (i == 0)) {
						_generateImagesPB(
							pdPage, id, PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH, true, 0);

						if (_log.isInfoEnabled()) {
							_log.info("PDFBox generated a thumbnail for " + id);
						}
					}

					if (!generatePreview) {
						break;
					}

					_generateImagesPB(
						pdPage, id, PropsValues.DL_FILE_ENTRY_PREVIEW_DPI,
						PropsValues.DL_FILE_ENTRY_PREVIEW_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_WIDTH, false, i + 1);
				}

				if (_log.isInfoEnabled() && generatePreview) {
					_log.info(
						"PDFBox generated " + _getPreviewFileCount(fileEntry) +
							" preview pages for " + id);
				}
			}
			finally {
				if (pdDocument != null) {
					pdDocument.close();
				}
			}
		}
	}

	private void _generateImagesIM(
			File file, String id, int depth, int dpi, int height, int width,
			boolean thumbnail)
		throws IOException, SystemException {

		IMOperation imOperation = new IMOperation();

		imOperation.density(dpi, dpi);

		if (height != 0) {
			imOperation.adaptiveResize(width, height);
		}
		else {
			imOperation.adaptiveResize(width);
		}

		imOperation.depth(depth);

		if (thumbnail) {
			imOperation.addImage(file.getPath() + "[0]");
			imOperation.addImage(_getThumbnailFilePath(id));
		}
		else {
			imOperation.addImage(file.getPath());
			imOperation.addImage(_getPreviewFilePath(id, -1));
		}

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

	private void _generateImagesPB(
			PDPage pdPage, String id, int dpi, int height, int width,
			boolean thumbnail, int index)
		throws IOException, FileNotFoundException {

		RenderedImage renderedImage = pdPage.convertToImage(
			BufferedImage.TYPE_INT_RGB,
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI);

		if (height != 0) {
			renderedImage = ImageProcessorUtil.scale(
				renderedImage, width, height);
		}
		else {
			renderedImage = ImageProcessorUtil.scale(renderedImage, width);
		}

		if (thumbnail) {
			File file = _getThumbnailFile(id);

			file.createNewFile();

			ImageIO.write(
				renderedImage, THUMBNAIL_TYPE, new FileOutputStream(file));
		}
		else {
			File file = _getPreviewFile(id, index);

			file.createNewFile();

			ImageIO.write(
				renderedImage, PREVIEW_TYPE, new FileOutputStream(file));
		}
	}

	private File _getPreviewFile(String id, int index) {
		String filePath = _getPreviewFilePath(id, index);

		return new File(filePath);
	}

	private String _getPreviewFilePath(String id, int index) {
		StringBundler sb;

		if (index <= 0 ) {
			sb = new StringBundler(4);
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_PREVIEW_PATH);
		sb.append(id);

		if (index > 0) {
			sb.append(StringPool.DASH);
			sb.append(index - 1);
		}

		sb.append(StringPool.PERIOD);
		sb.append(PREVIEW_TYPE);

		return sb.toString();
	}

	private int _getPreviewFileCount(FileEntry fileEntry) {
		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		StringBundler sb = new StringBundler(5);

		sb.append(id);
		sb.append(StringPool.DASH);
		sb.append("(.*)");
		sb.append(StringPool.PERIOD);
		sb.append(PREVIEW_TYPE);

		File dir = new File(_PREVIEW_PATH);

		File[] files = dir.listFiles(new FileFilter(sb.toString()));

		if (_log.isDebugEnabled()) {
			for (File file : files) {
				_log.debug("Preview page for " + id + " " + file);
			}
		}

		return files.length;
	}

	private File _getThumbnailFile(String id) {
		String filePath = _getThumbnailFilePath(id);

		return new File(filePath);
	}

	private String _getThumbnailFilePath(String id) {
		StringBundler sb = new StringBundler(4);

		sb.append(_THUMBNAIL_PATH);
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(THUMBNAIL_TYPE);

		return sb.toString();
	}

	private boolean _hasImages(FileEntry fileEntry) {
		String id = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), fileEntry.getVersion());

		File previewFile = _getPreviewFile(id, 1);

		File thumbnailFile = _getThumbnailFile(id);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			if (previewFile.exists() && thumbnailFile.exists()) {
				return true;
			}
		}
		else  if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
				  previewFile.exists()) {

			return true;
		}
		else if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				 thumbnailFile.exists()) {

			return true;
		}

		if (!_fileEntries.contains(fileEntry)) {
			String extension = fileEntry.getExtension();

			if (extension.equals("pdf")) {
				_fileEntries.add(fileEntry);
			}
			else if (DocumentConversionUtil.isEnabled()){
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

		return false;
	}

	private boolean _isGeneratePreview(String id) {
		File previewFile = _getPreviewFile(id, 1);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!previewFile.exists()) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(String id) {
		File thumbnailFile = _getThumbnailFile(id);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!thumbnailFile.exists()) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final String _PREVIEW_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/document_preview/";

	private static final String _THUMBNAIL_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/document_thumbnail/";

	private static Log _log = LogFactoryUtil.getLog(PDFProcessorUtil.class);

	private static PDFProcessorUtil _instance = new PDFProcessorUtil();

	private ConvertCmd _convertCmd;
	private List<FileEntry> _fileEntries = new Vector<FileEntry>();

}