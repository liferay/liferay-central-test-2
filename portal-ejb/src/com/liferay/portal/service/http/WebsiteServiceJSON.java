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

import com.liferay.portal.service.WebsiteServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="WebsiteServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsiteServiceJSON {
	public static JSONObject addWebsite(java.lang.String className,
		java.lang.String classPK, java.lang.String url, int typeId,
		boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Website returnValue = WebsiteServiceUtil.addWebsite(className,
				classPK, url, typeId, primary);

		return WebsiteJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteWebsite(long websiteId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		WebsiteServiceUtil.deleteWebsite(websiteId);
	}

	public static JSONObject getWebsite(long websiteId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Website returnValue = WebsiteServiceUtil.getWebsite(websiteId);

		return WebsiteJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONArray getWebsites(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = WebsiteServiceUtil.getWebsites(className,
				classPK);

		return WebsiteJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject updateWebsite(long websiteId,
		java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Website returnValue = WebsiteServiceUtil.updateWebsite(websiteId,
				url, typeId, primary);

		return WebsiteJSONSerializer.toJSONObject(returnValue);
	}
}