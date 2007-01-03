/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.AddressServiceUtil;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * <a href="AddressServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressServiceHttp {
	public static com.liferay.portal.model.Address addAddress(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, java.lang.String regionId,
		java.lang.String countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = classPK;

			if (classPK == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = street1;

			if (street1 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = street2;

			if (street2 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = street3;

			if (street3 == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = city;

			if (city == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = zip;

			if (zip == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = regionId;

			if (regionId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = countryId;

			if (countryId == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = new IntegerWrapper(typeId);
			Object paramObj10 = new BooleanWrapper(mailing);
			Object paramObj11 = new BooleanWrapper(primary);
			MethodWrapper methodWrapper = new MethodWrapper(AddressServiceUtil.class.getName(),
					"addAddress",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Address)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static void deleteAddress(HttpPrincipal httpPrincipal, long addressId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(addressId);
			MethodWrapper methodWrapper = new MethodWrapper(AddressServiceUtil.class.getName(),
					"deleteAddress", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static com.liferay.portal.model.Address getAddress(
		HttpPrincipal httpPrincipal, long addressId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(addressId);
			MethodWrapper methodWrapper = new MethodWrapper(AddressServiceUtil.class.getName(),
					"getAddress", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Address)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static java.util.List getAddresses(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = classPK;

			if (classPK == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(AddressServiceUtil.class.getName(),
					"getAddresses", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static com.liferay.portal.model.Address updateAddress(
		HttpPrincipal httpPrincipal, long addressId, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, java.lang.String regionId,
		java.lang.String countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(addressId);
			Object paramObj1 = street1;

			if (street1 == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = street2;

			if (street2 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = street3;

			if (street3 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = city;

			if (city == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = zip;

			if (zip == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = regionId;

			if (regionId == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = countryId;

			if (countryId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new IntegerWrapper(typeId);
			Object paramObj9 = new BooleanWrapper(mailing);
			Object paramObj10 = new BooleanWrapper(primary);
			MethodWrapper methodWrapper = new MethodWrapper(AddressServiceUtil.class.getName(),
					"updateAddress",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Address)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AddressServiceHttp.class);
}