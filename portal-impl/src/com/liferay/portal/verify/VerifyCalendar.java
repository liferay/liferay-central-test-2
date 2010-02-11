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

package com.liferay.portal.verify;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;

import java.util.List;

/**
 * <a href="VerifyCalendar.java.html"><b><i>View Source</i></b></a>
 *
 * @author Juan Fernández
 */
public class VerifyCalendar extends VerifyProcess {

	protected void doVerify() throws Exception {

		List<CalEvent> events = CalEventLocalServiceUtil.getNoAssetEvents();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + events.size() + " events with no asset");
		}

		for (CalEvent event : events) {
			if (_log.isDebugEnabled()) {
				_log.debug("Creating asset for event " + event.getEventId());
			}

			AssetEntryLocalServiceUtil.updateEntry(event.getUserId(),
				event.getGroupId(), CalEvent.class.getName(),
				event.getEventId(), null, null, true, null, null, null, null,
				ContentTypes.TEXT_HTML, event.getTitle(),
				event.getDescription(), null, null, 0, 0, null, false);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Calendar event's assets verified");
		}

	}

	private static Log _log = LogFactoryUtil.getLog(VerifyCalendar.class);

}