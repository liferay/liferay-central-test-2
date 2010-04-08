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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Time;

/**
 * <a href="SchedulerEntryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class SchedulerEntryImpl implements SchedulerEntry {

	public String getDescription() {
		return _description;
	}

	public MessageListener getEventListener() {
		return _eventListener;
	}

	public String getEventListenerClass() {
		return _eventListenerClass;
	}

	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	public Trigger getTrigger() throws SchedulerException {
		if (_trigger != null) {
			return _trigger;
		}

		String triggerValue = _triggerValue;

		if (_readProperty) {
			try {
				triggerValue = PrefsPropsUtil.getString(triggerValue);
			}
			catch (Exception e) {
				throw new SchedulerException(
					"Unable to get trigger value " + triggerValue, e);
			}
		}

		if (_triggerType == TriggerType.CRON) {
			_trigger = new CronTrigger(
				_eventListenerClass, _eventListenerClass, triggerValue);
		}
		else if (_triggerType == TriggerType.SIMPLE) {
			long intervalTime = GetterUtil.getLong(triggerValue);

			if (_timeUnit.equals(TimeUnit.DAY)) {
				intervalTime = intervalTime * Time.DAY;
			}
			else if (_timeUnit.equals(TimeUnit.HOUR)) {
				intervalTime = intervalTime * Time.HOUR;
			}
			else if (_timeUnit.equals(TimeUnit.MINUTE)) {
				intervalTime = intervalTime * Time.MINUTE;
			}
			else if (_timeUnit.equals(TimeUnit.WEEK)) {
				intervalTime = intervalTime * Time.WEEK;
			}
			else {
				intervalTime = intervalTime * Time.SECOND;
			}

			_trigger = new IntervalTrigger(
				_eventListenerClass, _eventListenerClass, intervalTime);
		}
		else {
			throw new SchedulerException(
				"Unsupport trigger type " + _triggerType);
		}

		return _trigger;
	}

	public TriggerType getTriggerType() {
		return _triggerType;
	}

	public String getTriggerValue() {
		return _triggerValue;
	}

	public boolean isReadProperty() {
		return _readProperty;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEventListener(MessageListener eventListener) {
		_eventListener = eventListener;
	}

	public void setEventListenerClass(String eventListenerClass) {
		_eventListenerClass = eventListenerClass;
	}

	public void setReadProperty(boolean readProperty) {
		_readProperty = readProperty;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		_timeUnit = timeUnit;
	}

	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	public void setTriggerValue(String triggerValue) {
		_triggerValue = triggerValue;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{description=");
		sb.append(_description);
		sb.append(", eventListener=");
		sb.append(_eventListener);
		sb.append(", eventListenerClass=");
		sb.append(_eventListenerClass);
		sb.append(", readProperty=");
		sb.append(_readProperty);
		sb.append(", timeUnit=");
		sb.append(_timeUnit);
		sb.append(", trigger=");
		sb.append(_trigger);
		sb.append(", triggerType=");
		sb.append(_triggerType);
		sb.append(", triggerValue=");
		sb.append(_triggerValue);
		sb.append("}");

		return sb.toString();
	}

	private String _description;
	private MessageListener _eventListener;
	private String _eventListenerClass;
	private boolean _readProperty;
	private TimeUnit _timeUnit;
	private Trigger _trigger;
	private TriggerType _triggerType;
	private String _triggerValue;

}