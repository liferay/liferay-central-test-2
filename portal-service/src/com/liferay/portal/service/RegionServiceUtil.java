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

package com.liferay.portal.service;

public class RegionServiceUtil {
	public static com.liferay.portal.model.Region addRegion(long countryId,
		java.lang.String regionCode, java.lang.String name, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addRegion(countryId, regionCode, name, active);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions()
		throws com.liferay.portal.SystemException {
		return getService().getRegions();
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId) throws com.liferay.portal.SystemException {
		return getService().getRegions(countryId);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		boolean active) throws com.liferay.portal.SystemException {
		return getService().getRegions(active);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId, boolean active)
		throws com.liferay.portal.SystemException {
		return getService().getRegions(countryId, active);
	}

	public static com.liferay.portal.model.Region getRegion(long regionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRegion(regionId);
	}

	public static RegionService getService() {
		if (_service == null) {
			throw new RuntimeException("RegionService is not set");
		}

		return _service;
	}

	public void setService(RegionService service) {
		_service = service;
	}

	private static RegionService _service;
}