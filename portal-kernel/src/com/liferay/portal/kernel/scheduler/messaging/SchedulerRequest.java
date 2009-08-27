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

	public static enum TriggerType{
		CRON, SIMPLE
	}

	public static final String COMMAND_REGISTER = "REGISTER";

	public static final String COMMAND_RETRIEVE = "RETRIEVE";

	public static final String COMMAND_SHUTDOWN = "SHUTDOWN";

	public static final String COMMAND_STARTUP = "STARTUP";

	public static final String COMMAND_UNREGISTER = "UNREGISTER";

	@Deprecated
	public SchedulerRequest() {
	}

	@Deprecated
	public SchedulerRequest(String command) {
		_command = command;
	}

	@Deprecated
	public SchedulerRequest(
		String command, String jobName, String groupName) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
	}

	@Deprecated
	public SchedulerRequest(
		String command, String jobName, String groupName, String cronText,
		Date startDate, Date endDate, String description, String destination,
		String messageBody) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
		_triggerType=TriggerType.CRON;
		_cronText = cronText;
		_startDate = startDate;
		_endDate = endDate;
		_description = description;
		_destination = destination;
		_messageBody = messageBody;
	}

	@Deprecated
	public SchedulerRequest(
		String command, String jobName, String groupName, long interval,
		Date startDate, Date endDate, String description, String destination,
		String messageBody) {

		_command = command;
		_jobName = jobName;
		_groupName = groupName;
		_triggerType=TriggerType.SIMPLE;
		_interval = interval;
		_startDate = startDate;
		_endDate = endDate;
		_description = description;
		_destination = destination;
		_messageBody = messageBody;
	}

	public static SchedulerRequest createRegisterRequest(
		String groupName, String cronText, Date startDate, Date endDate,
		String description, String destination, String messageBody){
		return new SchedulerRequest(
			COMMAND_REGISTER, null, groupName, cronText, startDate, endDate,
			description, destination, messageBody);
	}

	public static SchedulerRequest createRegisterRequest(
		String groupName, long interval, Date startDate, Date endDate,
		String description, String destination, String messageBody){
		return new SchedulerRequest(
			COMMAND_REGISTER, null, groupName, interval, startDate, endDate,
			description, destination, messageBody);
	}

	public static SchedulerRequest createRetrieveRequest(String groupName){
		return new SchedulerRequest(COMMAND_RETRIEVE, null, groupName);
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		String jobName, String groupName, long interval,
		Date startDate, Date endDate, String description, String messageBody){
		return new SchedulerRequest(
			null, jobName, groupName, interval, startDate, endDate, description,
			null, messageBody);
	}

	public static SchedulerRequest createRetrieveResponseRequest(
		String jobName, String groupName, String cronText,
		Date startDate, Date endDate, String description, String messageBody){
		return new SchedulerRequest(
			null, jobName, groupName, cronText, startDate, endDate, description,
			null, messageBody);
	}

	public static SchedulerRequest createShutdownRequest(){
		return new SchedulerRequest(COMMAND_SHUTDOWN);
	}

	public static SchedulerRequest createStartupRequest(){
		return new SchedulerRequest(COMMAND_STARTUP);
	}

	public static SchedulerRequest createUnregisterRequest(String groupName){
		return new SchedulerRequest(COMMAND_UNREGISTER, groupName, groupName);
	}

	public String getCommand() {
		return _command;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public String getJobName() {
		return _jobName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public String getGroupName() {
		return _groupName;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public String getCronText() {
		return _cronText;
	}

	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getDestination() {
		return _destination;
	}

	public void setDestination(String destination) {
		_destination = destination;
	}

	public long getInterval() {
		return _interval;
	}

	public void setInterval(long interval) {
		this._interval = interval;
	}

	public String getMessageBody() {
		return _messageBody;
	}

	public void setMessageBody(String messageBody) {
		_messageBody = messageBody;
	}

	public TriggerType getTriggerType() {
		return _triggerType;
	}

	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	private TriggerType _triggerType;
	private String _command;
	private String _jobName;
	private String _groupName;
	private String _cronText;
	private long _interval;
	private Date _startDate;
	private Date _endDate;
	private String _description;
	private String _destination;
	private String _messageBody;

}