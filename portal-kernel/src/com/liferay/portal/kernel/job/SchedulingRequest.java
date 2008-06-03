/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.job;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="SchedulingRequest.java.html"><b><i>View Source</i></b></a>
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
 *
 */
public class SchedulingRequest implements Serializable {

	public static final String PING = "PING";

	public static final String REGISTER_TYPE = "REGISTER";

	public static final String RETRIEVE_TYPE = "RETRIEVE";

	public static final String UNREGISTER_TYPE = "UNREGISTER";

	public SchedulingRequest() {
	}

	public SchedulingRequest(String type) {
		this(null, type);
	}

	public SchedulingRequest(String groupName, String type) {
		this(groupName, null, type);
	}

	public SchedulingRequest(String groupName, String jobName, String type) {
		this(null, null, groupName, jobName, null, null, null, type);
	}

	public SchedulingRequest(
			String cronText, String groupName, String jobName,
			String messageBody, Date startDate, Date endDate) {

		this(
			cronText, null, groupName, jobName, messageBody, startDate, endDate,
			null);
	}

	public SchedulingRequest(
	    	String cronText, String destinationName, String groupName,
	    	String messageBody, Date startDate, Date endDate, String type) {

		this(
			cronText, destinationName, groupName, null, messageBody, startDate,
			endDate, type);
	}

	public SchedulingRequest(
	    	String cronText, String destinationName, String groupName,
	    	String jobName, String messageBody, Date startDate, Date endDate,
	    	String type) {

    	_cronText = cronText;
    	_destinationName = destinationName;
    	_groupName = groupName;
    	_jobName = jobName;
        _messageBody = messageBody;
        _startDate = startDate;
        _endDate = endDate;
        _type = type;
    }

    public String getCronText() {
        return _cronText;
    }

    public void setCronText(String cronText) {
    	_cronText = cronText;
    }

    public String getDestinationName() {
    	return _destinationName;
    }

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public String getGroupName() {
    	return _groupName;
    }

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public String getJobName() {
    	return _jobName;
    }

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public String getMessageBody() {
		return _messageBody;
	}

	public void setMessageBody(String messageBody) {
		_messageBody = messageBody;
	}

	public String getResponseId() {
		return _responseId;
	}

	public void setResponseId(String responseId) {
		_responseId = responseId;
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

	public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

	private String _cronText;
	private String _destinationName;
	private String _groupName;
	private String _jobName;
	private String _messageBody;
	private String _responseId;
	private Date _startDate;
	private Date _endDate;
    private String _type;

}