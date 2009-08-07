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

package com.liferay.portal.management;

import com.liferay.portal.kernel.management.ManagementService;
import com.liferay.portal.kernel.management.ManagementServiceException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scripting.ScriptingUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="ManagementServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ManagementServiceImpl implements ManagementService {

	public void execute(String language, String script) throws
		ManagementServiceException {
		if (Validator.isNull(language)) {
			throw new IllegalArgumentException(
				"Language type can not be null or empty!");
		}
		if (Validator.isNull(script)) {
			throw new IllegalArgumentException(
				"Script can not be null or empty!");
		}
		if (!ScriptingUtil.getSupportedLanguages().contains(language)) {
			throw new ManagementServiceException(
				"Unsupport language type:" + language);
		}

		Message managementMessage = new Message();
		managementMessage.put(LANGUAGE_KEY, language);
		managementMessage.put(SCRIPT_KEY, script);
		Exception exception = null;
		try {
			Object response =
				MessageBusUtil.sendSynchronousMessage(
					DestinationNames.MANAGEMENT, managementMessage);
			if (Exception.class.isAssignableFrom(response.getClass())) {
				exception = Exception.class.cast(response);
			}
		}
		catch (MessageBusException ex) {
			exception = ex;
		}
		finally {
			if (exception != null) {
				throw new ManagementServiceException(
					"Unable to execute management script.", exception);
			}
		}
	}

}