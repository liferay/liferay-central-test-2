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

package com.liferay.portlet.imagegallery.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

import java.rmi.RemoteException;

public class IGImageServiceSoap {
	public static void deleteImage(long imageId) throws RemoteException {
		try {
			IGImageServiceUtil.deleteImage(imageId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteImageByFolderIdAndNameWithExtension(
		long folderId, java.lang.String nameWithExtension)
		throws RemoteException {
		try {
			IGImageServiceUtil.deleteImageByFolderIdAndNameWithExtension(folderId,
				nameWithExtension);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap getImage(
		long imageId) throws RemoteException {
		try {
			com.liferay.portlet.imagegallery.model.IGImage returnValue = IGImageServiceUtil.getImage(imageId);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap getImageByFolderIdAndNameWithExtension(
		long folderId, java.lang.String nameWithExtension)
		throws RemoteException {
		try {
			com.liferay.portlet.imagegallery.model.IGImage returnValue = IGImageServiceUtil.getImageByFolderIdAndNameWithExtension(folderId,
					nameWithExtension);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap getImageByLargeImageId(
		long largeImageId) throws RemoteException {
		try {
			com.liferay.portlet.imagegallery.model.IGImage returnValue = IGImageServiceUtil.getImageByLargeImageId(largeImageId);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap getImageBySmallImageId(
		long smallImageId) throws RemoteException {
		try {
			com.liferay.portlet.imagegallery.model.IGImage returnValue = IGImageServiceUtil.getImageBySmallImageId(smallImageId);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap[] getImages(
		long folderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.imagegallery.model.IGImage> returnValue =
				IGImageServiceUtil.getImages(folderId);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(IGImageServiceSoap.class);
}