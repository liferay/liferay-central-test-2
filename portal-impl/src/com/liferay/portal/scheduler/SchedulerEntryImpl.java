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

package com.liferay.portal.scheduler;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.concurrent.TimeUnit;

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
		MessageListener messageListener =
			(MessageListener) InstancePool.get(_listenerClass);
		return new SchedulerEventMessageListenerWrapper(messageListener);
	}

	public String getEventListenerClass() {
		return _listenerClass;
	}

	public Trigger getTrigger() throws SystemException {

		if (_trigger == null) {
			String triggerValue = _triggerValue;
			if (_readProperty) {
				triggerValue = PrefsPropsUtil.getString(triggerValue);
			}

			if (_triggerType == TriggerType.CRON) {
				_trigger = new CronTrigger(
					_listenerClass, _listenerClass, triggerValue);
			}
			else if (_triggerType == TriggerType.SIMPLE) {

				long intervalTime = -1;
				try {
					intervalTime = Long.parseLong(triggerValue);
				}
				catch (NumberFormatException ex) {
					throw new SystemException(
						"Unable to parse interval time from String:" +
						triggerValue);
				}
				_trigger = new IntervalTrigger(
					_listenerClass, _listenerClass,
					TimeUnit.SECONDS.toMillis(intervalTime));
			}
			else {
				throw new SystemException(
					"Unsupport Trigger type:" + _triggerType);
			}
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

	public void setReadProperty(boolean readProperty) {
		_readProperty = readProperty;
	}

	public void setListenerClass(String listenerClass) {
		_listenerClass = listenerClass;
	}

	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	public void setTriggerValue(String triggerValue) {
		_triggerValue = triggerValue;
	}

	public String toString() {
		return "SchedulerImpl[" +
			"decription:" + _description +
			", listenerClass:" + _listenerClass +
			", triggerType:" + _triggerType +
			", triggerValue:" + _triggerValue +
			", propertyKey:" + _readProperty +
			"]";
	}

	private String _description;
	private boolean _readProperty;
	private String _listenerClass;
	private Trigger _trigger;
	private TriggerType _triggerType;
	private String _triggerValue;

}