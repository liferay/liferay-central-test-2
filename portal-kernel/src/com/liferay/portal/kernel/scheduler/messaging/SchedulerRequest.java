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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.TriggerType;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="SchedulerRequest.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * A request to schedule a job for the scheduling engine. You may specify the
 * timing of the job via the cron syntax. See
 * http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html for a
 * description of the syntax.
 * </p>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 */
public class SchedulerRequest implements Serializable {

	public static final String COMMAND_REGISTER = "REGISTER";

	public static final String COMMAND_RETRIEVE = "RETRIEVE";

	public static final String COMMAND_SHUTDOWN = "SHUTDOWN";

	public static final String COMMAND_STARTUP = "STARTUP";

	public static final String COMMAND_UNREGISTER = "UNREGISTER";

	public static SchedulerRequest createRegisterRequest(
		String groupName, long interval, Date startDate, Date endDate,
		String description, String destination, Message message) {

		return new SchedulerRequest(
			COMMAND_REGISTER, null, groupName, interval, startDate, endDate,
			description, destination, message);
	}

	public static SchedulerRequest createRegisterRequest(
		String groupName, String cronText, Date startDate, Date endDate,
		String description, String destination, Message message) {

		return new SchedulerRequest(
			COMMAND_REGISTER, null, groupName, cronText, startDate, endDate,
			description, destination, message);
	}

	public static SchedulerRequest createRetrieveRequest(String groupName) {
		return new SchedulerRequest(COMMAND_RETRIEVE, null, groupName);
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		String jobName, String groupName, long interval, Date startDate,
		Date endDate, String description, Message message) {

		return new SchedulerRequest(
			null, jobName, groupName, interval, startDate, endDate, description,
			null, message);
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		String jobName, String groupName, String cronText,
		Date startDate, Date endDate, String description, Message message) {

		return new SchedulerRequest(
			null, jobName, groupName, cronText, startDate, endDate, description,
			null, message);
	}

	public static SchedulerRequest createShutdownRequest() {
		return new SchedulerRequest(COMMAND_SHUTDOWN);
	}

	public static SchedulerRequest createStartupRequest() {
		return new SchedulerRequest(COMMAND_STARTUP);
	}

	public static SchedulerRequest createUnregisterRequest(String groupName) {
		return new SchedulerRequest(COMMAND_UNREGISTER, groupName, groupName);
	}

	/**
	 * @deprecated
	 */
	public SchedulerRequest() {
	}

	/**
	 * @deprecated
	 */
	public SchedulerRequest(String command) {
		_command = command;
	}

	/**
	 * @deprecated
	 */
	public SchedulerRequest(
		String command, String jobName, String groupName) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
	}

	/**
	 * @deprecated
	 */
	public SchedulerRequest(
		String command, String jobName, String groupName, long interval,
		Date startDate, Date endDate, String description, String destination,
		Message message) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
		_triggerType = TriggerType.SIMPLE;
		_interval = interval;
		_startDate = startDate;
		_endDate = endDate;
		_description = description;
		_destination = destination;
		_message = message;
	}

	/**
	 * @deprecated
	 */
	public SchedulerRequest(
		String command, String jobName, String groupName, String cronText,
		Date startDate, Date endDate, String description, String destination,
		Message message) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
		_triggerType = TriggerType.CRON;
		_cronText = cronText;
		_startDate = startDate;
		_endDate = endDate;
		_description = description;
		_destination = destination;
		_message = message;
	}

	public String getCommand() {
		return _command;
	}

	public String getCronText() {
		return _cronText;
	}

	public String getDescription() {
		return _description;
	}

	public String getDestination() {
		return _destination;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public String getGroupName() {
		return _groupName;
	}

	public long getInterval() {
		return _interval;
	}

	public String getJobName() {
		return _jobName;
	}

	public Message getMessage() {
		return _message;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public TriggerType getTriggerType() {
		return _triggerType;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDestination(String destination) {
		_destination = destination;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setInterval(long interval) {
		this._interval = interval;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setMessage(Message message) {
		_message = message;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	private String _command;
	private String _cronText;
	private String _description;
	private String _destination;
	private Date _endDate;
	private String _groupName;
	private long _interval;
	private String _jobName;
	private Message _message;
	private Date _startDate;
	private TriggerType _triggerType;

}