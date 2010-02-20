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

package com.liferay.util.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * <a href="ActionCommandCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ActionCommandCache {

	public static final String ACTION_PACKAGE_NAME = "action.package.prefix";

	public static final ActionCommand EMPTY = new ActionCommand() {

		public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse) {

			return false;
		}

	};

	public ActionCommandCache(String packagePrefix) {
		if (!packagePrefix.endsWith(StringPool.PERIOD)) {
			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;
	}

	public ActionCommand getActionCommand(String actionCommandName) {
		String className = null;

		try {
			ActionCommand actionCommand = _actionCommandCache.get(
				actionCommandName);

			if (actionCommand == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_packagePrefix);
				sb.append(Character.toUpperCase(actionCommandName.charAt(0)));
				sb.append(
					actionCommandName.substring(1, actionCommandName.length()));
				sb.append(_ACTION_COMMAND_POSTFIX);

				className = sb.toString();

				actionCommand =
					(ActionCommand)Class.forName(className).newInstance();

				_actionCommandCache.put(actionCommandName, actionCommand);
			}

			return actionCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate ActionCommand " + className);
			}

			_actionCommandCache.put(actionCommandName, EMPTY);

			return EMPTY;
		}
	}

	public List<ActionCommand> getActionCommandChain(
		String actionCommandChain) {

		List<ActionCommand> actionCommands = _actionCommandChainCache.get(
			actionCommandChain);

		if (actionCommands == null) {
			actionCommands = new ArrayList<ActionCommand>();

			int nextSeparator = actionCommandChain.indexOf(StringPool.COMMA);

			int currentIndex = 0;

			while (currentIndex < actionCommandChain.length()) {
				String parsedName = actionCommandChain.substring(
					currentIndex, nextSeparator);

				ActionCommand actionCommand = getActionCommand(
					parsedName);

				if (actionCommand != EMPTY) {
					actionCommands.add(actionCommand);
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to find ActionCommand " +
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

			_actionCommandChainCache.put(actionCommandChain, actionCommands);
		}

		return actionCommands;
	}

	public boolean isEmpty() {
		return _actionCommandCache.isEmpty();
	}

	private static final String _ACTION_COMMAND_POSTFIX = "ActionCommand";

	private static Log _log = LogFactoryUtil.getLog(ActionCommandCache.class);

	private Map<String, ActionCommand> _actionCommandCache =
		new ConcurrentHashMap<String, ActionCommand>();
	private Map<String, List<ActionCommand>> _actionCommandChainCache =
		new ConcurrentHashMap<String, List<ActionCommand>>();
	private String _packagePrefix;

}