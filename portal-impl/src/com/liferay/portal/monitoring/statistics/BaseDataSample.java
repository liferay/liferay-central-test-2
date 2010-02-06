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

package com.liferay.portal.monitoring.statistics;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.monitoring.RequestStatus;

import java.io.Serializable;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="BaseDataSample.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class BaseDataSample implements DataSample, Serializable {

	public void capture(RequestStatus requestStatus) {
		if (_stopWatch != null) {
			_stopWatch.stop();

			_duration = _stopWatch.getTime();
		}

		_requestStatus = requestStatus;
	}

	public Map<String, String> getAttributes() {
		return _attributes;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDescription() {
		return _description;
	}

	public long getDuration() {
		return _duration;
	}

	public String getName() {
		return _name;
	}

	public String getNamespace() {
		return _namespace;
	}

	public RequestStatus getRequestStatus() {
		return _requestStatus;
	}

	public String getUser() {
		return _user;
	}

	public void prepare() {
		if (_stopWatch == null) {
			_stopWatch = new StopWatch();
		}
		_stopWatch.start();
	}

	public void setAttributes(Map<String, String> attributes) {
		_attributes = attributes;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setUser(String user) {
		_user = user;
	}

	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{attributes=");
		sb.append(_attributes);
		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", description=");
		sb.append(_description);
		sb.append(", duration=");
		sb.append(_duration);
		sb.append(", name=");
		sb.append(_name);
		sb.append(", namespace=");
		sb.append(_namespace);
		sb.append(", requestStatus=");
		sb.append(_requestStatus);
		sb.append(", stopWatch=");
		sb.append(_stopWatch);
		sb.append(", user=");
		sb.append(_user);
		sb.append("}");

		return sb.toString();
	}

	private Map<String, String> _attributes;
	private long _companyId;
	private String _description;
	private long _duration;
	private String _name;
	private String _namespace;
	private RequestStatus _requestStatus;
	private transient StopWatch _stopWatch;
	private String _user;

}