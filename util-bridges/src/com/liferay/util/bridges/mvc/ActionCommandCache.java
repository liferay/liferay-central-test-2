/*
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

package com.liferay.util.bridges.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="ActionCache.java.html"><b><i>View Source</i></b></a>
 * <p/>
 * Cache for Portlet Actions to avoid reflection costs per call.
 *
 * @author Michael C. Han
 */
public class ActionCommandCache {

	public static final String ACTION_PACKAGE_NAME = "action.package.prefix";

	public static final ActionCommand EMPTY = new ActionCommand() {
		public boolean processCommand(
			PortletRequest request, PortletResponse response)
			throws PortletException {
			return false;
		}
	};

	public ActionCommandCache(String packagePrefix) {
		if (!packagePrefix.endsWith(StringPool.PERIOD)) {
			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;
	}

	public ActionCommand getActionCommand(String actionCommandName)
		throws PortletException {

		String className = null;

		try {
			ActionCommand actionCommand = _actionCommandCache.get(
				actionCommandName);

			if (actionCommand == null) {
				className =
					_packagePrefix +
					Character.toUpperCase(actionCommandName.charAt(0)) +
					actionCommandName.substring(1, actionCommandName.length()) +
					_ACTION_COMMAND_POSTFIX;

				actionCommand =
					(ActionCommand)Class.forName(className).newInstance();

				_actionCommandCache.put(actionCommandName, actionCommand);
			}

			return actionCommand;
		}
		catch (Exception e) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find an ActionCommand class: " + className);
			}

			_actionCommandCache.put(actionCommandName, EMPTY);

			return EMPTY;
		}
	}

	public List<ActionCommand> getActionCommandChain(String actionCommandChain)
		throws PortletException {
		List<ActionCommand> actionCommands = _actionCommandChainCache.get(
			actionCommandChain);

		if (actionCommands == null) {

			actionCommands = new ArrayList<ActionCommand>();

			int length = actionCommandChain.length();

			int nextSeparator = actionCommandChain.indexOf(StringPool.COMMA);

			int currentIndex = 0;

			do {
				String parsedName =
					actionCommandChain.substring(currentIndex, nextSeparator);

				ActionCommand actionCommand = getActionCommand(
					parsedName);

				if (actionCommand != EMPTY) {
					actionCommands.add(actionCommand);
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to find ActionCommand: " +
							actionCommandChain);
					}
				}

				currentIndex = nextSeparator + 1;

				nextSeparator = actionCommandChain.indexOf(
					StringPool.COMMA, currentIndex);

				if (nextSeparator == -1) {
					break;
				}
			}
			while ((currentIndex < length));

			_actionCommandChainCache.put(actionCommandChain, actionCommands);
		}

		return actionCommands;
	}

	public boolean isEmpty() {
		return _actionCommandCache.isEmpty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ActionCommandCache.class);

	private static final String _ACTION_COMMAND_POSTFIX = "ActionCommand";

	private Map<String, ActionCommand> _actionCommandCache =
		new ConcurrentHashMap<String, ActionCommand>();

	private Map<String, List<ActionCommand>> _actionCommandChainCache =
		new ConcurrentHashMap<String, List<ActionCommand>>();

	private String _packagePrefix;
}
