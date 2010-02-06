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

package com.liferay.portlet.expando.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ExpandoColumnServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ExpandoColumnService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnService
 * @generated
 */
public class ExpandoColumnServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addColumn(tableId, name, type);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addColumn(tableId, name, type, defaultData);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumn(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateColumn(columnId, name, type);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateColumn(columnId, name, type, defaultData);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateTypeSettings(columnId, typeSettings);
	}

	public static ExpandoColumnService getService() {
		if (_service == null) {
			_service = (ExpandoColumnService)PortalBeanLocatorUtil.locate(ExpandoColumnService.class.getName());
		}

		return _service;
	}

	public void setService(ExpandoColumnService service) {
		_service = service;
	}

	private static ExpandoColumnService _service;
}