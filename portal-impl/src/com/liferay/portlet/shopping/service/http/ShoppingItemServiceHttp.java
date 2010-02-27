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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;

/**
 * <a href="ShoppingItemServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.shopping.service.ShoppingItemServiceUtil} service utility. The
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
 * @see       ShoppingItemServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.shopping.service.ShoppingItemServiceUtil
 * @generated
 */
public class ShoppingItemServiceHttp {
	public static void addBookItems(HttpPrincipal httpPrincipal, long groupId,
		long categoryId, java.lang.String[] isbns)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(categoryId);

			Object paramObj2 = isbns;

			if (isbns == null) {
				paramObj2 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingItemServiceUtil.class.getName(),
					"addBookItems",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
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

	public static com.liferay.portlet.shopping.model.ShoppingItem addItem(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(categoryId);

			Object paramObj2 = sku;

			if (sku == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = name;

			if (name == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = description;

			if (description == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = properties;

			if (properties == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = fieldsQuantities;

			if (fieldsQuantities == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = new BooleanWrapper(requiresShipping);

			Object paramObj8 = new IntegerWrapper(stockQuantity);

			Object paramObj9 = new BooleanWrapper(featured);

			Object paramObj10 = sale;

			if (sale == null) {
				paramObj10 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj11 = new BooleanWrapper(smallImage);

			Object paramObj12 = smallImageURL;

			if (smallImageURL == null) {
				paramObj12 = new NullWrapper("java.lang.String");
			}

			Object paramObj13 = smallFile;

			if (smallFile == null) {
				paramObj13 = new NullWrapper("java.io.File");
			}

			Object paramObj14 = new BooleanWrapper(mediumImage);

			Object paramObj15 = mediumImageURL;

			if (mediumImageURL == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = mediumFile;

			if (mediumFile == null) {
				paramObj16 = new NullWrapper("java.io.File");
			}

			Object paramObj17 = new BooleanWrapper(largeImage);

			Object paramObj18 = largeImageURL;

			if (largeImageURL == null) {
				paramObj18 = new NullWrapper("java.lang.String");
			}

			Object paramObj19 = largeFile;

			if (largeFile == null) {
				paramObj19 = new NullWrapper("java.io.File");
			}

			Object paramObj20 = itemFields;

			if (itemFields == null) {
				paramObj20 = new NullWrapper("java.util.List");
			}

			Object paramObj21 = itemPrices;

			if (itemPrices == null) {
				paramObj21 = new NullWrapper("java.util.List");
			}

			Object paramObj22 = serviceContext;

			if (serviceContext == null) {
				paramObj22 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingItemServiceUtil.class.getName(),
					"addItem",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
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

			return (com.liferay.portlet.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteItem(HttpPrincipal httpPrincipal, long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(itemId);

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingItemServiceUtil.class.getName(),
					"deleteItem", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
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

	public static com.liferay.portlet.shopping.model.ShoppingItem getItem(
		HttpPrincipal httpPrincipal, long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(itemId);

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingItemServiceUtil.class.getName(),
					"getItem", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
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

			return (com.liferay.portlet.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem updateItem(
		HttpPrincipal httpPrincipal, long itemId, long groupId,
		long categoryId, java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(itemId);

			Object paramObj1 = new LongWrapper(groupId);

			Object paramObj2 = new LongWrapper(categoryId);

			Object paramObj3 = sku;

			if (sku == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = name;

			if (name == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = description;

			if (description == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = properties;

			if (properties == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = fieldsQuantities;

			if (fieldsQuantities == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new BooleanWrapper(requiresShipping);

			Object paramObj9 = new IntegerWrapper(stockQuantity);

			Object paramObj10 = new BooleanWrapper(featured);

			Object paramObj11 = sale;

			if (sale == null) {
				paramObj11 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj12 = new BooleanWrapper(smallImage);

			Object paramObj13 = smallImageURL;

			if (smallImageURL == null) {
				paramObj13 = new NullWrapper("java.lang.String");
			}

			Object paramObj14 = smallFile;

			if (smallFile == null) {
				paramObj14 = new NullWrapper("java.io.File");
			}

			Object paramObj15 = new BooleanWrapper(mediumImage);

			Object paramObj16 = mediumImageURL;

			if (mediumImageURL == null) {
				paramObj16 = new NullWrapper("java.lang.String");
			}

			Object paramObj17 = mediumFile;

			if (mediumFile == null) {
				paramObj17 = new NullWrapper("java.io.File");
			}

			Object paramObj18 = new BooleanWrapper(largeImage);

			Object paramObj19 = largeImageURL;

			if (largeImageURL == null) {
				paramObj19 = new NullWrapper("java.lang.String");
			}

			Object paramObj20 = largeFile;

			if (largeFile == null) {
				paramObj20 = new NullWrapper("java.io.File");
			}

			Object paramObj21 = itemFields;

			if (itemFields == null) {
				paramObj21 = new NullWrapper("java.util.List");
			}

			Object paramObj22 = itemPrices;

			if (itemPrices == null) {
				paramObj22 = new NullWrapper("java.util.List");
			}

			Object paramObj23 = serviceContext;

			if (serviceContext == null) {
				paramObj23 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingItemServiceUtil.class.getName(),
					"updateItem",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
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

			return (com.liferay.portlet.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingItemServiceHttp.class);
}