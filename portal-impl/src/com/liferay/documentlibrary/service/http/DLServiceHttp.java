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

package com.liferay.documentlibrary.service.http;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * <a href="DLServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.documentlibrary.service.DLServiceUtil</code> service
 * utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it requires an
 * additional <code>com.liferay.portal.security.auth.HttpPrincipal</code>
 * parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <code>tunnel.servlet.hosts.allowed</code> in
 * portal.properties to configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.documentlibrary.service.DLServiceUtil
 * @see com.liferay.documentlibrary.service.http.DLServiceSoap
 *
 */
public class DLServiceHttp {
	public static void addDirectory(HttpPrincipal httpPrincipal,
		long companyId, long repositoryId, java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new LongWrapper(repositoryId);

			Object paramObj2 = dirName;

			if (dirName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"addDirectory",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long groupId, long repositoryId,
		java.lang.String fileName, long fileEntryId,
		java.lang.String properties, java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(groupId);

			Object paramObj3 = new LongWrapper(repositoryId);

			Object paramObj4 = fileName;

			if (fileName == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new LongWrapper(fileEntryId);

			Object paramObj6 = properties;

			if (properties == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = modifiedDate;

			if (modifiedDate == null) {
				paramObj7 = new NullWrapper("java.util.Date");
			}

			Object paramObj8 = serviceContext;

			if (serviceContext == null) {
				paramObj8 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			Object paramObj9 = file;

			if (file == null) {
				paramObj9 = new NullWrapper("java.io.File");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"addFile",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long groupId, long repositoryId,
		java.lang.String fileName, long fileEntryId,
		java.lang.String properties, java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(groupId);

			Object paramObj3 = new LongWrapper(repositoryId);

			Object paramObj4 = fileName;

			if (fileName == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new LongWrapper(fileEntryId);

			Object paramObj6 = properties;

			if (properties == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = modifiedDate;

			if (modifiedDate == null) {
				paramObj7 = new NullWrapper("java.util.Date");
			}

			Object paramObj8 = serviceContext;

			if (serviceContext == null) {
				paramObj8 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			Object paramObj9 = bytes;

			if (bytes == null) {
				paramObj9 = new NullWrapper("[B");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"addFile",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteDirectory(HttpPrincipal httpPrincipal,
		long companyId, java.lang.String portletId, long repositoryId,
		java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(repositoryId);

			Object paramObj3 = dirName;

			if (dirName == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"deleteDirectory",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(repositoryId);

			Object paramObj3 = fileName;

			if (fileName == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"deleteFile",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long repositoryId,
		java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(repositoryId);

			Object paramObj3 = fileName;

			if (fileName == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new DoubleWrapper(versionNumber);

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"deleteFile",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static byte[] getFile(HttpPrincipal httpPrincipal, long companyId,
		long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new LongWrapper(repositoryId);

			Object paramObj2 = fileName;

			if (fileName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"getFile", new Object[] { paramObj0, paramObj1, paramObj2 });

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

				throw new com.liferay.portal.SystemException(e);
			}

			return (byte[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static byte[] getFile(HttpPrincipal httpPrincipal, long companyId,
		long repositoryId, java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new LongWrapper(repositoryId);

			Object paramObj2 = fileName;

			if (fileName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new DoubleWrapper(versionNumber);

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"getFile",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

				throw new com.liferay.portal.SystemException(e);
			}

			return (byte[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String[] getFileNames(HttpPrincipal httpPrincipal,
		long companyId, long repositoryId, java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new LongWrapper(repositoryId);

			Object paramObj2 = dirName;

			if (dirName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"getFileNames",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getFileSize(HttpPrincipal httpPrincipal, long companyId,
		long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new LongWrapper(repositoryId);

			Object paramObj2 = fileName;

			if (fileName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"getFileSize",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void reIndex(HttpPrincipal httpPrincipal,
		java.lang.String[] ids) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = ids;

			if (ids == null) {
				paramObj0 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"reIndex", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long groupId, long repositoryId,
		java.lang.String fileName, double versionNumber,
		java.lang.String sourceFileName, long fileEntryId,
		java.lang.String properties, java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(groupId);

			Object paramObj3 = new LongWrapper(repositoryId);

			Object paramObj4 = fileName;

			if (fileName == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new DoubleWrapper(versionNumber);

			Object paramObj6 = sourceFileName;

			if (sourceFileName == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = new LongWrapper(fileEntryId);

			Object paramObj8 = properties;

			if (properties == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = modifiedDate;

			if (modifiedDate == null) {
				paramObj9 = new NullWrapper("java.util.Date");
			}

			Object paramObj10 = serviceContext;

			if (serviceContext == null) {
				paramObj10 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			Object paramObj11 = file;

			if (file == null) {
				paramObj11 = new NullWrapper("java.io.File");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"updateFile",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long groupId, long repositoryId,
		java.lang.String fileName, double versionNumber,
		java.lang.String sourceFileName, long fileEntryId,
		java.lang.String properties, java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(groupId);

			Object paramObj3 = new LongWrapper(repositoryId);

			Object paramObj4 = fileName;

			if (fileName == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new DoubleWrapper(versionNumber);

			Object paramObj6 = sourceFileName;

			if (sourceFileName == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = new LongWrapper(fileEntryId);

			Object paramObj8 = properties;

			if (properties == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = modifiedDate;

			if (modifiedDate == null) {
				paramObj9 = new NullWrapper("java.util.Date");
			}

			Object paramObj10 = serviceContext;

			if (serviceContext == null) {
				paramObj10 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			Object paramObj11 = bytes;

			if (bytes == null) {
				paramObj11 = new NullWrapper("[B");
			}

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"updateFile",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateFile(HttpPrincipal httpPrincipal, long companyId,
		java.lang.String portletId, long groupId, long repositoryId,
		long newRepositoryId, java.lang.String fileName, long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(groupId);

			Object paramObj3 = new LongWrapper(repositoryId);

			Object paramObj4 = new LongWrapper(newRepositoryId);

			Object paramObj5 = fileName;

			if (fileName == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = new LongWrapper(fileEntryId);

			MethodWrapper methodWrapper = new MethodWrapper(DLServiceUtil.class.getName(),
					"updateFile",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLServiceHttp.class);
}