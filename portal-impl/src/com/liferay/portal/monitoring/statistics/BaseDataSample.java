package com.liferay.portal.monitoring.statistics;

import com.liferay.portal.monitoring.RequestStatus;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="BaseSample.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
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

	public String getName() {
		return _name;
	}

	public long getDuration() {
		return _duration;
	}

	public RequestStatus getRequestStatus() {
		return _requestStatus;
	}

	public String getNamespace() {
		return _namespace;
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

	public void setDescription(String description) {
		_description = description;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public String toString() {
		return "BaseDataSample{" +
			   "_companyId=" + _companyId +
			   ", _description='" + _description + '\'' +
			   ", _name='" + _name + '\'' +
			   ", _duration=" + _duration +
			   ", _namespace='" + _namespace + '\'' +
			   ", _requestStatus=" + _requestStatus +
			   ", _user='" + _user + '\'' +
			   ", _attributes=" + _attributes +
			   ", _stopWatch=" + _stopWatch +
			   '}';
	}

	protected long _companyId;
	private String _description;
	protected String _name;
	private long _duration;
	protected String _namespace;
	private RequestStatus _requestStatus;
	protected String _user;
	private Map<String, String> _attributes;
	private transient StopWatch _stopWatch;
}
