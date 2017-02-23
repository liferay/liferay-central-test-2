/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEntryImpl implements SchedulerEntry {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #SchedulerEntryImpl(String, Trigger, String)}
	 */
	@Deprecated
	public SchedulerEntryImpl() {
		this(StringPool.BLANK, null, StringPool.BLANK);
	}

	public SchedulerEntryImpl(String eventListenerClass, Trigger trigger) {
		this(eventListenerClass, trigger, StringPool.BLANK);
	}

	public SchedulerEntryImpl(
		String eventListenerClass, Trigger trigger, String description) {

		_eventListenerClass = eventListenerClass;
		_trigger = trigger;
		_description = description;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getEventListenerClass() {
		return _eventListenerClass;
	}

	@Override
	public Trigger getTrigger() {
		return _trigger;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setEventListenerClass(String eventListenerClass) {
		_eventListenerClass = eventListenerClass;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setTrigger(Trigger trigger) {
		_trigger = trigger;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append(", description=, eventListenerClass=");
		sb.append(_eventListenerClass);
		sb.append(", trigger=");
		sb.append(_trigger);
		sb.append("}");

		return sb.toString();
	}

	private String _description;
	private String _eventListenerClass;
	private Trigger _trigger;

}