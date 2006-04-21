/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.servlet.TunnelUtil;
import com.liferay.portal.shared.util.BooleanWrapper;
import com.liferay.portal.shared.util.IntegerWrapper;
import com.liferay.portal.shared.util.MethodWrapper;
import com.liferay.portal.shared.util.NullWrapper;
import com.liferay.portal.shared.util.StackTraceUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingCartServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingCartServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartServiceHttp {
	public static void deleteCart(HttpPrincipal httpPrincipal,
		java.lang.String cartId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = cartId;

			if (cartId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCartServiceUtil.class.getName(),
					"deleteCart", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart emptyCart(
		HttpPrincipal httpPrincipal, java.lang.String sessionId,
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = sessionId;

			if (sessionId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = companyId;

			if (companyId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCartServiceUtil.class.getName(),
					"emptyCart", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portlet.shopping.model.ShoppingCart)returnObj;
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getCart(
		HttpPrincipal httpPrincipal, java.lang.String sessionId,
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = sessionId;

			if (sessionId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = companyId;

			if (companyId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCartServiceUtil.class.getName(),
					"getCart", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portlet.shopping.model.ShoppingCart)returnObj;
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static java.util.Map getItems(HttpPrincipal httpPrincipal,
		java.lang.String companyId, java.lang.String itemIds)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = itemIds;

			if (itemIds == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCartServiceUtil.class.getName(),
					"getItems", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (java.util.Map)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		HttpPrincipal httpPrincipal, java.lang.String sessionId,
		java.lang.String companyId, java.lang.String itemIds,
		java.lang.String couponIds, int altShipping, boolean insure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = sessionId;

			if (sessionId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = companyId;

			if (companyId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = itemIds;

			if (itemIds == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = couponIds;

			if (couponIds == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new IntegerWrapper(altShipping);
			Object paramObj5 = new BooleanWrapper(insure);
			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCartServiceUtil.class.getName(),
					"updateCart",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portlet.shopping.model.ShoppingCart)returnObj;
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static boolean hasAdmin(HttpPrincipal httpPrincipal,
		java.lang.String cartId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = cartId;

			if (cartId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCartServiceUtil.class.getName(),
					"hasAdmin", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(ShoppingCartServiceHttp.class);
}