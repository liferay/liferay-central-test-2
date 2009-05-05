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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourcePermission;

/**
 * <a href="ServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class ServiceUtil {

	public static BaseModel getModel(Resource resource)
		throws PortalException, SystemException {

		return getService().getModel(resource);
	}

	public static BaseModel getModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		return getService().getModel(resourcePermission);
	}

	public static BaseModel getModel(String modelName, String primKey)
		throws PortalException, SystemException {

		return getService().getModel(modelName, primKey);
	}

	public static Service getService() {
		return _service;
	}

	public void setService(Service service) {
		_service = service;
	}

	public static String toHtml(BaseModel model) {
		return getService().toHtml(model);
	}

	private static Service _service;

}