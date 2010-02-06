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

package com.liferay.portlet.communities.messaging;

import java.util.Date;
import java.util.Map;

/**
 * <a href="LayoutsRemotePublisherRequest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 */
public class LayoutsRemotePublisherRequest
	extends LayoutsLocalPublisherRequest {

	public LayoutsRemotePublisherRequest() {
	}

	public LayoutsRemotePublisherRequest(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, boolean secureConnection,
		long remoteGroupId, boolean remotePrivateLayout, Date startDate,
		Date endDate) {

		_userId = userId;
		_sourceGroupId = sourceGroupId;
		_privateLayout = privateLayout;
		_layoutIdMap = layoutIdMap;
		_parameterMap = parameterMap;
		_remoteAddress = remoteAddress;
		_remotePort = remotePort;
		_secureConnection = secureConnection;
		_remoteGroupId = remoteGroupId;
		_remotePrivateLayout = remotePrivateLayout;
		_startDate = startDate;
		_endDate = endDate;
	}

	public String getCronText() {
		return _cronText;
	}

	public void setCronText(String cronText) {
		_cronText = cronText;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public Map<Long, Boolean> getLayoutIdMap() {
		return _layoutIdMap;
	}

	public void setLayoutIdMap(Map<Long, Boolean> layoutIdMap) {
		_layoutIdMap = layoutIdMap;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public String getRemoteAddress() {
		return _remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		_remoteAddress = remoteAddress;
	}

	public int getRemotePort() {
		return _remotePort;
	}

	public void setRemotePort(int remotePort) {
		_remotePort = remotePort;
	}

	public boolean isSecureConnection() {
		return _secureConnection;
	}

	public void setSecureConnection(boolean secureConnection) {
		_secureConnection = secureConnection;
	}

	public long getRemoteGroupId() {
		return _remoteGroupId;
	}

	public void setRemoteGroupId(long remoteGroupId) {
		_remoteGroupId = remoteGroupId;
	}

	public boolean isRemotePrivateLayout() {
		return _remotePrivateLayout;
	}

	public void setRemotePrivateLayout(boolean remotePrivateLayout) {
		_remotePrivateLayout = remotePrivateLayout;
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

	public Date getScheduledFireTime() {
		return _scheduledFireTime;
	}

	public void setScheduledFireTime(Date scheduledFireTime) {
		_scheduledFireTime = scheduledFireTime;
	}

	private String _cronText;
	private long _userId;
	private long _sourceGroupId;
	private boolean _privateLayout;
	private Map<Long, Boolean> _layoutIdMap;
	private Map<String, String[]> _parameterMap;
	private String _remoteAddress;
	private int _remotePort;
	private boolean _secureConnection;
	private long _remoteGroupId;
	private boolean _remotePrivateLayout;
	private Date _startDate;
	private Date _endDate;
	private Date _scheduledFireTime;

}