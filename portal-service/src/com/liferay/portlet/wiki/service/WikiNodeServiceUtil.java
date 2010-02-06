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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WikiNodeServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WikiNodeService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodeService
 * @generated
 */
public class WikiNodeServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiNode addNode(
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addNode(name, description, serviceContext);
	}

	public static void deleteNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteNode(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getNode(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode getNode(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getNode(groupId, name);
	}

	public static void importPages(long nodeId, java.lang.String importer,
		java.io.File[] files, java.util.Map<String, String[]> options)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().importPages(nodeId, importer, files, options);
	}

	public static void subscribeNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().subscribeNode(nodeId);
	}

	public static void unsubscribeNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsubscribeNode(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode updateNode(
		long nodeId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateNode(nodeId, name, description);
	}

	public static WikiNodeService getService() {
		if (_service == null) {
			_service = (WikiNodeService)PortalBeanLocatorUtil.locate(WikiNodeService.class.getName());
		}

		return _service;
	}

	public void setService(WikiNodeService service) {
		_service = service;
	}

	private static WikiNodeService _service;
}