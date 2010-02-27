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

package com.liferay.portlet.calendar.asset;

import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.calendar.model.CalEvent;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="CalEventAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Juan Fernández
 */
public class CalEventAssetRenderer extends BaseAssetRenderer {

	public CalEventAssetRenderer(CalEvent event){
		_event = event;
	}

	public long getClassPK() {
		return _event.getEventId();
	}

	public long getGroupId() {
		return _event.getGroupId();
	}

	public String getSummary() {
		return _event.getTitle();
	}

	public String getTitle() {
		return _event.getTitle();
	}

	public long getUserId() {
		return _event.getUserId();
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(WebKeys.CALENDAR_EVENT, _event);

			return "/html/portlet/calendar/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private CalEvent _event;

}