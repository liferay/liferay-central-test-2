/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.filter.RenderRequestWrapper;

/**
 * <a href="RenderRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RenderRequestImpl
	extends PortletRequestImpl implements RenderRequest {

	public static RenderRequestImpl getRenderRequestImpl(
		RenderRequest renderRequest) {

		RenderRequestImpl renderRequestImpl = null;

		if (renderRequest instanceof RenderRequestImpl) {
			renderRequestImpl = (RenderRequestImpl)renderRequest;
		}
		else {
			renderRequestImpl = (RenderRequestImpl)
				(((RenderRequestWrapper)renderRequest).getRequest());
		}

		return renderRequestImpl;
	}

	public String getETag() {
		return null;
	}

	public String getLifecycle() {
		return PortletRequest.RENDER_PHASE;
	}

}