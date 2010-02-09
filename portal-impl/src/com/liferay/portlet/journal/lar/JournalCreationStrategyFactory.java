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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="JournalCreationStrategyFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Joel Kozikowski
 */
public class JournalCreationStrategyFactory {

	public static JournalCreationStrategy getInstance() {
		if (_journalCreationStrategy == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.JOURNAL_LAR_CREATION_STRATEGY);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_journalCreationStrategy =
					(JournalCreationStrategy)classLoader.loadClass(
						PropsValues.JOURNAL_LAR_CREATION_STRATEGY).
							newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Return " + _journalCreationStrategy.getClass().getName());
		}

		return _journalCreationStrategy;
	}

	public static void setInstance(
		JournalCreationStrategy journalCreationStrategy) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + journalCreationStrategy.getClass().getName());
		}

		_journalCreationStrategy = journalCreationStrategy;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalCreationStrategyFactory.class);

	private static JournalCreationStrategy _journalCreationStrategy;

}