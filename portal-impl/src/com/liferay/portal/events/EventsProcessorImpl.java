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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="EventsProcessorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class EventsProcessorImpl implements EventsProcessor {

	public void process(
			String key, String[] classes, String[] ids,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session)
		throws ActionException {

		for (String className : classes) {
			if (Validator.isNull(className)) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Process event " + className);
			}

			Object event = InstancePool.get(className);

			processEvent(event, ids, request, response, session);
		}

		if (Validator.isNull(key)) {
			return;
		}

		List<Object> events = _getEvents(key);

		for (Object event : events) {
			processEvent(event, ids, request, response, session);
		}
	}

	public void processEvent(
			Object event, String[] ids, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
		throws ActionException {

		if (event instanceof Action) {
			Action action = (Action)event;

			try {
				action.run(request, response);
			}
			catch (ActionException ae) {
				throw ae;
			}
			catch (Exception e) {
				throw new ActionException(e);
			}
		}
		else if (event instanceof SessionAction) {
			SessionAction sessionAction = (SessionAction)event;

			try {
				sessionAction.run(session);
			}
			catch (ActionException ae) {
				throw ae;
			}
			catch (Exception e) {
				throw new ActionException(e);
			}
		}
		else if (event instanceof SimpleAction) {
			SimpleAction simpleAction = (SimpleAction)event;

			simpleAction.run(ids);
		}
	}

	public void registerEvent(String key, Object event) {
		List<Object> events = _getEvents(key);

		events.add(event);
	}

	public void unregisterEvent(String key, Object event) {
		List<Object> events = _getEvents(key);

		events.remove(event);
	}

	private List<Object> _getEvents(String key) {
		List<Object> events = _eventsMap.get(key);

		if (events == null) {
			events = new ArrayList<Object>();

			_eventsMap.put(key, events);
		}

		return events;
	}

	private static Log _log = LogFactoryUtil.getLog(EventsProcessorImpl.class);

	private Map<String, List<Object>> _eventsMap =
		new HashMap<String, List<Object>>();

}