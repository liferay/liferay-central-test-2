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

package com.liferay.portlet.imagegallery.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.imagegallery.service.IGImageServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.imagegallery.service.IGImageServiceUtil
 * @generated
 */
public class IGImageServiceHttp {
	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"addImage", _addImageParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, name, description, file, contentType,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.imagegallery.model.IGImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteImage(HttpPrincipal httpPrincipal, long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"deleteImage", _deleteImageParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, imageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteImageByFolderIdAndNameWithExtension(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String nameWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"deleteImageByFolderIdAndNameWithExtension",
					_deleteImageByFolderIdAndNameWithExtensionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, nameWithExtension);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersImagesCount(HttpPrincipal httpPrincipal,
		long groupId, java.util.List<java.lang.Long> folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getFoldersImagesCount",
					_getFoldersImagesCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		HttpPrincipal httpPrincipal, long groupId, long userId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getGroupImages", _getGroupImagesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.imagegallery.model.IGImage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupImagesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getGroupImagesCount", _getGroupImagesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImage(
		HttpPrincipal httpPrincipal, long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImage", _getImageParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, imageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.imagegallery.model.IGImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByFolderIdAndNameWithExtension(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String nameWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImageByFolderIdAndNameWithExtension",
					_getImageByFolderIdAndNameWithExtensionParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, nameWithExtension);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.imagegallery.model.IGImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByLargeImageId(
		HttpPrincipal httpPrincipal, long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImageByLargeImageId",
					_getImageByLargeImageIdParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					largeImageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.imagegallery.model.IGImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageBySmallImageId(
		HttpPrincipal httpPrincipal, long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImageBySmallImageId",
					_getImageBySmallImageIdParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					smallImageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.imagegallery.model.IGImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImages", _getImagesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.imagegallery.model.IGImage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImages", _getImagesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.imagegallery.model.IGImage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getImagesCount(HttpPrincipal httpPrincipal, long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"getImagesCount", _getImagesCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImage(
		HttpPrincipal httpPrincipal, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(IGImageServiceUtil.class.getName(),
					"updateImage", _updateImageParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, imageId,
					groupId, folderId, name, description, file, contentType,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.imagegallery.model.IGImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(IGImageServiceHttp.class);
	private static final Class<?>[] _addImageParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.io.File.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteImageParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteImageByFolderIdAndNameWithExtensionParameterTypes2 =
		new Class[] { long.class, long.class, java.lang.String.class };
	private static final Class<?>[] _getFoldersImagesCountParameterTypes3 = new Class[] {
			long.class, java.util.List.class
		};
	private static final Class<?>[] _getGroupImagesParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getGroupImagesCountParameterTypes5 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getImageParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getImageByFolderIdAndNameWithExtensionParameterTypes7 =
		new Class[] { long.class, long.class, java.lang.String.class };
	private static final Class<?>[] _getImageByLargeImageIdParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getImageBySmallImageIdParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getImagesParameterTypes10 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getImagesParameterTypes11 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getImagesCountParameterTypes12 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _updateImageParameterTypes13 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.io.File.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
}