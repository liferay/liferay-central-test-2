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

package com.liferay.portlet;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="PortletPreferencesSerializer.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Zongliang Li
 */
public class PortletPreferencesSerializer {

	public static PortletPreferences fromDefaultXML(String xml)
		throws SystemException {

		PortletPreferencesImpl preferences = new PortletPreferencesImpl();

		if (Validator.isNull(xml)) {
			return preferences;
		}

		Map<String, Preference> preferencesMap = preferences.getPreferences();

		try {
			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			Iterator<Element> itr1 = root.elements("preference").iterator();

			while (itr1.hasNext()) {
				Element prefEl = itr1.next();

				String name = prefEl.elementTextTrim("name");

				List<String> values = new ArrayList<String>();

				Iterator<Element> itr2 = prefEl.elements("value").iterator();

				while (itr2.hasNext()) {
					Element valueEl = itr2.next();

					/*if (valueEl.nodeCount() <= 0) {
						values.add(valueEl.getText());
					}
					else {
						values.add(valueEl.node(0).asXML());
					}*/

					values.add(valueEl.getTextTrim());
				}

				boolean readOnly = GetterUtil.getBoolean(
					prefEl.elementText("read-only"));

				Preference preference = new Preference(
					name, values.toArray(new String[values.size()]), readOnly);

				preferencesMap.put(name, preference);
			}

			return preferences;
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
	}

	public static PortletPreferencesImpl fromXML(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws SystemException {

		try {
			PortletPreferencesImpl preferences =
				(PortletPreferencesImpl)fromDefaultXML(xml);

			preferences = new PortletPreferencesImpl(
				companyId, ownerId, ownerType, plid, portletId,
				preferences.getPreferences());

			return preferences;
		}
		catch (SystemException se) {
			throw se;
		}
	}

	public static String toXML(PortletPreferencesImpl preferences) {
		Map<String, Preference> preferencesMap = preferences.getPreferences();

		Element portletPreferences = SAXReaderUtil.createElement(
			"portlet-preferences");

		Iterator<Map.Entry<String, Preference>> itr =
			preferencesMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Preference> entry = itr.next();

			Preference preference = entry.getValue();

			Element prefEl = SAXReaderUtil.createElement("preference");

			Element nameEl = SAXReaderUtil.createElement("name");

			nameEl.addText(preference.getName());

			prefEl.add(nameEl);

			String[] values = preference.getValues();

			for (int i = 0; i < values.length; i++) {
				Element valueEl = SAXReaderUtil.createElement("value");

				valueEl.addText(values[i]);

				prefEl.add(valueEl);
			}

			if (preference.isReadOnly()) {
				Element valueEl = SAXReaderUtil.createElement("read-only");

				valueEl.addText("true");

				prefEl.add(valueEl);
			}

			portletPreferences.add(prefEl);
		}

		return portletPreferences.asXML();
	}

}