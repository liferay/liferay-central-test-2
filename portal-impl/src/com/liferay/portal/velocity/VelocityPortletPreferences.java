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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import javax.portlet.ReadOnlyException;

/**
 * <a href="VelocityPortletPreferences.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VelocityPortletPreferences {

	public VelocityPortletPreferences() {
		_preferencesImpl = new PortletPreferencesImpl();
	}

	public void reset() {
		_preferencesImpl.reset();
	}

	public void setValue(String key, String value) throws ReadOnlyException {
		_preferencesImpl.setValue(key, value);
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_preferencesImpl.setValues(key, values);
	}

	public String toString() {
		try {
			return PortletPreferencesSerializer.toXML(_preferencesImpl);
		}
		catch (Exception e) {
			_log.error(e, e);

			return PortletConstants.DEFAULT_PREFERENCES;
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(VelocityPortletPreferences.class);

	private PortletPreferencesImpl _preferencesImpl;

}