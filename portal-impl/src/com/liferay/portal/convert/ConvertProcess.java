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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.MaintenanceUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="ConvertProcess.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public abstract class ConvertProcess {

	public void convert() throws ConvertException {
		try {
			if (getPath() != null) {
				return;
			}

			StopWatch stopWatch = null;

			if (_log.isInfoEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();

				_log.info("Starting conversion for " + getClass().getName());
			}

			doConvert();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished conversion for " + getClass().getName() + " in " +
						stopWatch.getTime() + " ms");
			}
		}
		catch (Exception e) {
			throw new ConvertException(e);
		}
		finally {
			setParameterValues(null);

			MaintenanceUtil.cancel();
		}
	}

	public abstract String getDescription();

	public String getParameterDescription() {
		return null;
	}

	public String[] getParameterNames() {
		return null;
	}

	public String[] getParameterValues() {
		return _paramValues;
	}

	public String getPath() {
		return null;
	}

	public abstract boolean isEnabled();

	public void setParameterValues(String[] values) {
		_paramValues = values;
	}

	protected abstract void doConvert() throws Exception;

	private static Log _log = LogFactoryUtil.getLog(ConvertProcess.class);

	private String[] _paramValues = null;

}