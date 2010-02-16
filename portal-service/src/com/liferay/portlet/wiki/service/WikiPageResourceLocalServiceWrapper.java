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

package com.liferay.portlet.wiki.service;


/**
 * <a href="WikiPageResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WikiPageResourceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageResourceLocalService
 * @generated
 */
public class WikiPageResourceLocalServiceWrapper
	implements WikiPageResourceLocalService {
	public WikiPageResourceLocalServiceWrapper(
		WikiPageResourceLocalService wikiPageResourceLocalService) {
		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	public com.liferay.portlet.wiki.model.WikiPageResource addWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.addWikiPageResource(wikiPageResource);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource createWikiPageResource(
		long resourcePrimKey) {
		return _wikiPageResourceLocalService.createWikiPageResource(resourcePrimKey);
	}

	public void deleteWikiPageResource(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageResourceLocalService.deleteWikiPageResource(resourcePrimKey);
	}

	public void deleteWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiPageResourceLocalService.deleteWikiPageResource(wikiPageResource);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource getWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getWikiPageResource(resourcePrimKey);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> getWikiPageResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getWikiPageResources(start, end);
	}

	public int getWikiPageResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getWikiPageResourcesCount();
	}

	public com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.updateWikiPageResource(wikiPageResource);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.updateWikiPageResource(wikiPageResource,
			merge);
	}

	public void deletePageResource(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageResourceLocalService.deletePageResource(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource getPageResource(
		long pageResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getPageResource(pageResourcePrimKey);
	}

	public long getPageResourcePrimKey(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getPageResourcePrimKey(nodeId,
			title);
	}

	public WikiPageResourceLocalService getWrappedWikiPageResourceLocalService() {
		return _wikiPageResourceLocalService;
	}

	private WikiPageResourceLocalService _wikiPageResourceLocalService;
}