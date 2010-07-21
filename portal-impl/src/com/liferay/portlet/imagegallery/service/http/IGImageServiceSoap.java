/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.imagegallery.service.IGImageServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.imagegallery.model.IGImageSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.imagegallery.model.IGImage}, that is translated to a
 * {@link com.liferay.portlet.imagegallery.model.IGImageSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageServiceHttp
 * @see       com.liferay.portlet.imagegallery.model.IGImageSoap
 * @see       com.liferay.portlet.imagegallery.service.IGImageServiceUtil
 * @generated
 */
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

	public static void deleteImageByFolderIdAndNameWithExtension(long groupId,
		long folderId, java.lang.String nameWithExtension)
		throws RemoteException {
		try {
			IGImageServiceUtil.deleteImageByFolderIdAndNameWithExtension(groupId,
				folderId, nameWithExtension);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap[] getGroupImages(
		long groupId, long userId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.imagegallery.model.IGImage> returnValue =
				IGImageServiceUtil.getGroupImages(groupId, userId, start, end);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupImagesCount(long groupId, long userId)
		throws RemoteException {
		try {
			int returnValue = IGImageServiceUtil.getGroupImagesCount(groupId,
					userId);

			return returnValue;
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
		long groupId, long folderId, java.lang.String nameWithExtension)
		throws RemoteException {
		try {
			com.liferay.portlet.imagegallery.model.IGImage returnValue = IGImageServiceUtil.getImageByFolderIdAndNameWithExtension(groupId,
					folderId, nameWithExtension);

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
		long groupId, long folderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.imagegallery.model.IGImage> returnValue =
				IGImageServiceUtil.getImages(groupId, folderId);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImageSoap[] getImages(
		long groupId, long folderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.imagegallery.model.IGImage> returnValue =
				IGImageServiceUtil.getImages(groupId, folderId, start, end);

			return com.liferay.portlet.imagegallery.model.IGImageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getImagesCount(long groupId, long folderId)
		throws RemoteException {
		try {
			int returnValue = IGImageServiceUtil.getImagesCount(groupId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(IGImageServiceSoap.class);
}