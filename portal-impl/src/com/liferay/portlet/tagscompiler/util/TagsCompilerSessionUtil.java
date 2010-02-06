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

package com.liferay.portlet.tagscompiler.util;

import com.liferay.portal.util.WebKeys;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * <a href="TagsCompilerSessionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TagsCompilerSessionUtil {

	public static void addEntries(
		PortletRequest portletRequest, List<String> entries) {

		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.addAll(entries);
	}

	public static void addEntry(PortletRequest portletRequest, String entry) {
		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.add(entry);
	}

	public static void clearEntries(PortletRequest portletRequest) {
		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.clear();
	}

	public static Collection<String> getEntries(PortletRequest portletRequest) {
		Set<String> entriesSet = _getEntriesSet(portletRequest);

		return entriesSet;
	}

	public static void removeEntries(
		PortletRequest portletRequest, List<String> entries) {

		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.removeAll(entries);
	}

	public static void setEntries(
		PortletRequest portletRequest, List<String> entries) {

		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.clear();

		entriesSet.addAll(entries);
	}

	private static Set<String> _getEntriesSet(PortletRequest portletRequest) {
		PortletSession portletSession = portletRequest.getPortletSession();

		Set<String> entriesSet = (Set<String>)portletSession.getAttribute(
			WebKeys.TAGS_COMPILER_ENTRIES, PortletSession.APPLICATION_SCOPE);

		if (entriesSet == null) {
			entriesSet = new TreeSet<String>();

			portletSession.setAttribute(
				WebKeys.TAGS_COMPILER_ENTRIES, entriesSet,
				PortletSession.APPLICATION_SCOPE);
		}

		return entriesSet;
	}

}