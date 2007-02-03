/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.RegionServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="RegionServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RegionServiceJSON {
	public static JSONArray getRegions()
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		java.util.List returnValue = RegionServiceUtil.getRegions();

		return RegionJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRegions(java.lang.String countryId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		java.util.List returnValue = RegionServiceUtil.getRegions(countryId);

		return RegionJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRegions(boolean active)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		java.util.List returnValue = RegionServiceUtil.getRegions(active);

		return RegionJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRegions(java.lang.String countryId,
		boolean active)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		java.util.List returnValue = RegionServiceUtil.getRegions(countryId,
				active);

		return RegionJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject getRegion(java.lang.String regionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Region returnValue = RegionServiceUtil.getRegion(regionId);

		return RegionJSONSerializer.toJSONObject(returnValue);
	}
}