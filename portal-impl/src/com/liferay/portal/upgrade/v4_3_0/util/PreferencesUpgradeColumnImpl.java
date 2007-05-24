/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.util.Validator;

/**
 * <a href="PreferencesUpgradeColumnImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Alexander Chow
 *
 */
public class PreferencesUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PreferencesUpgradeColumnImpl(ValueMapper groupIdMapper) {
		super("preferences");

		_groupIdMapper = groupIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String xml = (String)oldValue;

		PortletPreferencesImpl prefs = (PortletPreferencesImpl)
			PortletPreferencesSerializer.fromDefaultXML(xml);

		String groupId = (String)prefs.getValue("group-id", null);

		if (Validator.isNotNull(groupId)) {
			Long newGroupId =
				(Long)_groupIdMapper.getNewValue(new Long(groupId));

			prefs.setValue("group-id", newGroupId.toString());

			xml = PortletPreferencesSerializer.toXML(prefs);
		}

		return xml;
	}

	private ValueMapper _groupIdMapper;

}