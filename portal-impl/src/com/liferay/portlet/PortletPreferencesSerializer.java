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
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.portal.xml.StAXReaderUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * <a href="PortletPreferencesSerializer.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Zongliang Li
 * @author Shuyang Zhou
 */
public class PortletPreferencesSerializer {

	public static PortletPreferences fromDefaultXML(String xml)
		throws SystemException {

		PortletPreferencesImpl preferences = new PortletPreferencesImpl();

		if (Validator.isNull(xml)) {
			return preferences;
		}

		Map<String, Preference> preferencesMap = preferences.getPreferences();
		XMLEventReader xmlEventReader = null;
		try {
			xmlEventReader = _xmlInputFactory.createXMLEventReader(
				new UnsyncStringReader(xml));

			while (xmlEventReader.hasNext()) {
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					String elementName = startElement.getName().getLocalPart();
					if ("preference".equals(elementName)) {
						Preference preference = _readPreference(xmlEventReader);
						preferencesMap.put(preference.getName(), preference);
					}
				}
			}

			return preferences;
		}
		catch (XMLStreamException xse) {
			throw new SystemException(xse);
		}
		finally {
			if (xmlEventReader != null) {
				try {
					xmlEventReader.close();
				}
				catch (XMLStreamException e) {
				}
			}
		}
	}

	private static Preference _readPreference(XMLEventReader xmlEventReader)
		throws XMLStreamException {

		String name = null;
		List<String> values = new ArrayList<String>();
		boolean readOnly = false;

		while (xmlEventReader.hasNext()) {

			XMLEvent xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isStartElement()) {
				StartElement startElement = xmlEvent.asStartElement();
				String elementName = startElement.getName().getLocalPart();
				if ("name".equals(elementName)) {
					name = StAXReaderUtil.readCharactersIfExist(xmlEventReader);
				}
				else if ("value".equals(elementName)) {
					String value =
						StAXReaderUtil.readCharactersIfExist(xmlEventReader);
					values.add(value);
				}
				else if ("read-only".equals(elementName)) {
					String value =
						StAXReaderUtil.readCharactersIfExist(xmlEventReader);
					readOnly = GetterUtil.getBoolean(value);
				}
			}
			else if (xmlEvent.isEndElement()){
				EndElement endElement = xmlEvent.asEndElement();
				if ("preference".equals(endElement.getName().getLocalPart())) {
					break;
				}
			}
		}
		return new Preference(name, values.toArray(new String[values.size()]),
			readOnly);
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

		Element portletPreferences = new Element("portlet-preferences", false);

		Iterator<Map.Entry<String, Preference>> itr =
			preferencesMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Preference> entry = itr.next();

			Preference preference = entry.getValue();

			Element prefEl = portletPreferences.addElement("preference");

			prefEl.addElement("name", preference.getName());

			for(String value: preference.getValues()) {
				prefEl.addElement("value", value);
			}

			if (preference.isReadOnly()) {
				prefEl.addElement("read-only", "true");
			}
		}

		return portletPreferences.toXMLString();
	}

	private static XMLInputFactory _xmlInputFactory =
		XMLInputFactory.newInstance();

}