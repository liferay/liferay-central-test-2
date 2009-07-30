package com.ext.portlet.reports;

import com.liferay.portal.PortalException;

/**
 * <a href="NoSuchEntryException.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class NoSuchEntryException extends PortalException {

	public NoSuchEntryException() {
		super();
	}

	public NoSuchEntryException(String msg) {
		super(msg);
	}

	public NoSuchEntryException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchEntryException(Throwable cause) {
		super(cause);
	}

}