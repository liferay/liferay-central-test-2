/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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


/**
 * <a href="RegionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RegionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RegionService
 * @generated
 */
public class RegionServiceWrapper implements RegionService {
	public RegionServiceWrapper(RegionService regionService) {
		_regionService = regionService;
	}

	public com.liferay.portal.model.Region addRegion(long countryId,
		java.lang.String regionCode, java.lang.String name, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _regionService.addRegion(countryId, regionCode, name, active);
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions()
		throws com.liferay.portal.SystemException {
		return _regionService.getRegions();
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId) throws com.liferay.portal.SystemException {
		return _regionService.getRegions(countryId);
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions(
		boolean active) throws com.liferay.portal.SystemException {
		return _regionService.getRegions(active);
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId, boolean active)
		throws com.liferay.portal.SystemException {
		return _regionService.getRegions(countryId, active);
	}

	public com.liferay.portal.model.Region getRegion(long regionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _regionService.getRegion(regionId);
	}

	public RegionService getWrappedRegionService() {
		return _regionService;
	}

	private RegionService _regionService;
}