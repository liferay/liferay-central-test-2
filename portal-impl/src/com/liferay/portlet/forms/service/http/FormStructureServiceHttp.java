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

package com.liferay.portlet.forms.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.forms.service.FormStructureServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.forms.service.FormStructureServiceUtil} service utility. The
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
 * @see       FormStructureServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.forms.service.FormStructureServiceUtil
 * @generated
 */
public class FormStructureServiceHttp {
	public static com.liferay.portlet.forms.model.FormStructure addFormStructure(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String formStructureId, boolean autoFormStructureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(FormStructureServiceUtil.class.getName(),
					"addFormStructure", _addFormStructureParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					formStructureId, autoFormStructureId, name, description,
					xsd, serviceContext);

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

			return (com.liferay.portlet.forms.model.FormStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFormStructure(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(FormStructureServiceUtil.class.getName(),
					"deleteFormStructure", _deleteFormStructureParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					formStructureId);

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

	public static com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(FormStructureServiceUtil.class.getName(),
					"fetchByG_F", _fetchByG_FParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					formStructureId);

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

			return (com.liferay.portlet.forms.model.FormStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.forms.model.FormStructure getFormStructure(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(FormStructureServiceUtil.class.getName(),
					"getFormStructure", _getFormStructureParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					formStructureId);

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

			return (com.liferay.portlet.forms.model.FormStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(FormStructureServiceUtil.class.getName(),
					"updateFormStructure", _updateFormStructureParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					formStructureId, name, description, xsd, serviceContext);

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

			return (com.liferay.portlet.forms.model.FormStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FormStructureServiceHttp.class);
	private static final Class<?>[] _addFormStructureParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, boolean.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFormStructureParameterTypes1 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _fetchByG_FParameterTypes2 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getFormStructureParameterTypes3 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _updateFormStructureParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
}