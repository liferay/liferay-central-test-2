/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.asset.service.AssetVocabularyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.asset.service.AssetVocabularyServiceUtil
 * @generated
 */
@ProviderType
public class AssetVocabularyServiceHttp {
	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"addVocabulary", _addVocabularyParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					title, titleMap, descriptionMap, settings, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String title,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"addVocabulary", _addVocabularyParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					title, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteVocabularies(HttpPrincipal httpPrincipal,
		long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"deleteVocabularies", _deleteVocabulariesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> deleteVocabularies(
		HttpPrincipal httpPrincipal, long[] vocabularyIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"deleteVocabularies", _deleteVocabulariesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyIds, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteVocabulary(HttpPrincipal httpPrincipal,
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"deleteVocabulary", _deleteVocabularyParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary fetchVocabulary(
		HttpPrincipal httpPrincipal, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"fetchVocabulary", _fetchVocabularyParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getCompanyVocabularies",
					_getCompanyVocabulariesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		HttpPrincipal httpPrincipal, long[] groupIds) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupsVocabularies",
					_getGroupsVocabulariesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		HttpPrincipal httpPrincipal, long[] groupIds, java.lang.String className) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupsVocabularies",
					_getGroupsVocabulariesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					groupIds, className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		HttpPrincipal httpPrincipal, long[] groupIds,
		java.lang.String className, long classTypePK) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupsVocabularies",
					_getGroupsVocabulariesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					groupIds, className, classTypePK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabularies",
					_getGroupVocabulariesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		HttpPrincipal httpPrincipal, long groupId,
		boolean createDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabularies",
					_getGroupVocabulariesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					createDefaultVocabulary);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		HttpPrincipal httpPrincipal, long groupId,
		boolean createDefaultVocabulary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabularies",
					_getGroupVocabulariesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					createDefaultVocabulary, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabularies",
					_getGroupVocabulariesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabularies",
					_getGroupVocabulariesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		HttpPrincipal httpPrincipal, long[] groupIds) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabularies",
					_getGroupVocabulariesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupVocabulariesCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabulariesCount",
					_getGroupVocabulariesCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupVocabulariesCount(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabulariesCount",
					_getGroupVocabulariesCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupVocabulariesCount(HttpPrincipal httpPrincipal,
		long[] groupIds) {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabulariesCount",
					_getGroupVocabulariesCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay getGroupVocabulariesDisplay(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name,
		int start, int end, boolean addDefaultVocabulary,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabulariesDisplay",
					_getGroupVocabulariesDisplayParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, start, end, addDefaultVocabulary, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabularyDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay getGroupVocabulariesDisplay(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getGroupVocabulariesDisplay",
					_getGroupVocabulariesDisplayParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabularyDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getJSONGroupVocabularies(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getJSONGroupVocabularies",
					_getJSONGroupVocabulariesParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getVocabularies(
		HttpPrincipal httpPrincipal, long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getVocabularies", _getVocabulariesParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetVocabulary>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		HttpPrincipal httpPrincipal, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"getVocabulary", _getVocabularyParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay searchVocabulariesDisplay(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String title,
		int start, int end, boolean addDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"searchVocabulariesDisplay",
					_searchVocabulariesDisplayParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					title, start, end, addDefaultVocabulary);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabularyDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		HttpPrincipal httpPrincipal, long vocabularyId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetVocabularyServiceUtil.class,
					"updateVocabulary", _updateVocabularyParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					vocabularyId, title, titleMap, descriptionMap, settings,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetVocabulary)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetVocabularyServiceHttp.class);
	private static final Class<?>[] _addVocabularyParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.util.Map.class,
			java.util.Map.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addVocabularyParameterTypes1 = new Class[] {
			long.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteVocabulariesParameterTypes2 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _deleteVocabulariesParameterTypes3 = new Class[] {
			long[].class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteVocabularyParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchVocabularyParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCompanyVocabulariesParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getGroupsVocabulariesParameterTypes7 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _getGroupsVocabulariesParameterTypes8 = new Class[] {
			long[].class, java.lang.String.class
		};
	private static final Class<?>[] _getGroupsVocabulariesParameterTypes9 = new Class[] {
			long[].class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes10 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes11 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes12 = new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes13 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes14 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesParameterTypes15 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _getGroupVocabulariesCountParameterTypes16 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getGroupVocabulariesCountParameterTypes17 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getGroupVocabulariesCountParameterTypes18 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _getGroupVocabulariesDisplayParameterTypes19 =
		new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			boolean.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupVocabulariesDisplayParameterTypes20 =
		new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getJSONGroupVocabulariesParameterTypes21 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getVocabulariesParameterTypes22 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _getVocabularyParameterTypes23 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchVocabulariesDisplayParameterTypes24 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			boolean.class
		};
	private static final Class<?>[] _updateVocabularyParameterTypes25 = new Class[] {
			long.class, java.lang.String.class, java.util.Map.class,
			java.util.Map.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
}