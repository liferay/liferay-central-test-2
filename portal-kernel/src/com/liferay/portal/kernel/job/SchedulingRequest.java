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

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

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
 *
 */
public class SchedulingRequest implements Serializable {

	public static final String MESSAGE_BODY_FIELD = "messageBody";

	public static final String REGISTER_TYPE = "REGISTER";

	public static final String UNREGISTER_TYPE = "UNREGISTER";

	public SchedulingRequest() {
	}

	public SchedulingRequest(
    	String cronText, String destinationFilter, String messageBody,
    	String type) {

    	_cronText = cronText;
    	_destinationFilter = destinationFilter;
        _messageBody = messageBody;
        _type = type;
    }

    public String getCronText() {
        return _cronText;
    }

    public void setCronText(String cronText) {
    	_cronText = cronText;
    }

    public String getDestinationFilter() {
    	return _destinationFilter;
    }

	public void setDestinationFilter(String destinationFilter) {
		_destinationFilter = destinationFilter;
	}

	public String getMessageBody() {
		return _messageBody;
	}

	public void setMessageBody(String messageBody) {
		_messageBody = messageBody;
	}

	public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();

		sm.append("cronText");
		sm.append(StringPool.EQUAL);
		sm.append(_cronText);

		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("destinationFilter");
		sm.append(StringPool.EQUAL);
		sm.append(_destinationFilter);

		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("messageBody");
		sm.append(StringPool.EQUAL);
		sm.append(_messageBody);

		return sm.toString();
	}

	private String _cronText;
	private String _destinationFilter;
	private String _messageBody;
    private String _type;

}