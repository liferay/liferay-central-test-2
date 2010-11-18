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

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Tina Tian
 */
public class JobState implements Cloneable, Serializable {

	public JobState(TriggerState triggerState) {
		this(triggerState, _DEFAULT_MAX_EXCEPTION_NUMBER);
	}

	public JobState(TriggerState triggerState, int maxExceptionNumber) {
		if (maxExceptionNumber <= 0) {
			maxExceptionNumber = _DEFAULT_MAX_EXCEPTION_NUMBER;
		}

		_maxExceptionNumber = maxExceptionNumber;
		_triggerState = triggerState;
	}

	public void addException(Exception exception) {
		if (_exceptions == null) {
			_exceptions = new LinkedList<ObjectValuePair<Exception, Date>>();
		}

		_exceptions.add(
			new ObjectValuePair<Exception, Date>(exception, new Date()));

		while (_exceptions.size() > _maxExceptionNumber) {
			_exceptions.poll();
		}
	}

	public void clearExceptions() {
		if (_exceptions != null && !_exceptions.isEmpty()) {
			_exceptions.clear();
		}
	}

	public Object clone() {
		JobState jobState = new JobState(_triggerState, _maxExceptionNumber);

		if (_exceptions != null) {
			jobState._exceptions =
				new LinkedList<ObjectValuePair<Exception, Date>>();

			jobState._exceptions.addAll(_exceptions);
		}

		if (_triggerTimeInfomation != null) {
			jobState._triggerTimeInfomation = new HashMap<String, Date>();

			jobState._triggerTimeInfomation.putAll(_triggerTimeInfomation);
		}

		return jobState;
	}

	public ObjectValuePair<Exception, Date>[] getExceptions() {
		if (_exceptions == null) {
			return null;
		}

		return _exceptions.toArray(new ObjectValuePair[_exceptions.size()]);
	}

	public Date getTriggerTimeInfomation(String key) {
		if (_triggerTimeInfomation == null) {
			return null;
		}

		return _triggerTimeInfomation.get(key);
	}

	public TriggerState getTriggerState() {
		return _triggerState;
	}

	public void setTriggerTimeInfomation(String key, Date date) {
		if (_triggerTimeInfomation == null) {
			_triggerTimeInfomation = new HashMap<String, Date>();
		}

		_triggerTimeInfomation.put(key, date);
	}

	public void setTriggerState(TriggerState triggerState) {
		_triggerState = triggerState;
	}

	private static final int _DEFAULT_MAX_EXCEPTION_NUMBER = 10;

	private Queue<ObjectValuePair<Exception, Date>> _exceptions;
	private int _maxExceptionNumber;
	private Map<String, Date> _triggerTimeInfomation;
	private TriggerState _triggerState;

}