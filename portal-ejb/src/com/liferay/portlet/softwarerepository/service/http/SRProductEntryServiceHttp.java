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

package com.liferay.portlet.softwarerepository.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.softwarerepository.service.SRProductEntryServiceUtil;

/**
 * <a href="SRProductEntryServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryServiceHttp {
	public static com.liferay.portlet.softwarerepository.model.SRProductEntry addProductEntry(
		HttpPrincipal httpPrincipal, java.lang.String plid,
		java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = type;

			if (type == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = shortDescription;

			if (shortDescription == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = longDescription;

			if (longDescription == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = pageURL;

			if (pageURL == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = repoGroupId;

			if (repoGroupId == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = repoArtifactId;

			if (repoArtifactId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = licenseIds;

			if (licenseIds == null) {
				paramObj8 = new NullWrapper("[J");
			}

			Object paramObj9 = new BooleanWrapper(addCommunityPermissions);
			Object paramObj10 = new BooleanWrapper(addGuestPermissions);
			MethodWrapper methodWrapper = new MethodWrapper(SRProductEntryServiceUtil.class.getName(),
					"addProductEntry",
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

			return (com.liferay.portlet.softwarerepository.model.SRProductEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry addProductEntry(
		HttpPrincipal httpPrincipal, java.lang.String plid,
		java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = type;

			if (type == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = shortDescription;

			if (shortDescription == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = longDescription;

			if (longDescription == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = pageURL;

			if (pageURL == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = repoGroupId;

			if (repoGroupId == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = repoArtifactId;

			if (repoArtifactId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = licenseIds;

			if (licenseIds == null) {
				paramObj8 = new NullWrapper("[J");
			}

			Object paramObj9 = communityPermissions;

			if (communityPermissions == null) {
				paramObj9 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj10 = guestPermissions;

			if (guestPermissions == null) {
				paramObj10 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(SRProductEntryServiceUtil.class.getName(),
					"addProductEntry",
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

			return (com.liferay.portlet.softwarerepository.model.SRProductEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static void deleteProductEntry(HttpPrincipal httpPrincipal,
		long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(productEntryId);
			MethodWrapper methodWrapper = new MethodWrapper(SRProductEntryServiceUtil.class.getName(),
					"deleteProductEntry", new Object[] { paramObj0 });

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

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry getProductEntry(
		HttpPrincipal httpPrincipal, long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(productEntryId);
			MethodWrapper methodWrapper = new MethodWrapper(SRProductEntryServiceUtil.class.getName(),
					"getProductEntry", new Object[] { paramObj0 });
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

			return (com.liferay.portlet.softwarerepository.model.SRProductEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry updateProductEntry(
		HttpPrincipal httpPrincipal, long productEntryId,
		java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(productEntryId);
			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = type;

			if (type == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = shortDescription;

			if (shortDescription == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = longDescription;

			if (longDescription == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = pageURL;

			if (pageURL == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = repoGroupId;

			if (repoGroupId == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = repoArtifactId;

			if (repoArtifactId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = licenseIds;

			if (licenseIds == null) {
				paramObj8 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(SRProductEntryServiceUtil.class.getName(),
					"updateProductEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8
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

			return (com.liferay.portlet.softwarerepository.model.SRProductEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SRProductEntryServiceHttp.class);
}