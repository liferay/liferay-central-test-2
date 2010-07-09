/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.SystemException;
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
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlEventReader = xmlInputFactory.createXMLEventReader(
				new UnsyncStringReader(xml));

			while (xmlEventReader.hasNext()) {
				XMLEvent xmlEvent = xmlEventReader.nextEvent();

				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();

					String elementName = startElement.getName().getLocalPart();

					if (elementName.equals("preference")) {
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
				catch (XMLStreamException xse) {
				}
			}
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

		Element portletPreferencesElement = new Element(
			"portlet-preferences", false);

		Iterator<Map.Entry<String, Preference>> itr =
			preferencesMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Preference> entry = itr.next();

			Preference preference = entry.getValue();

			Element preferenceElement = portletPreferencesElement.addElement(
				"preference");

			preferenceElement.addElement("name", preference.getName());

			for (String value : preference.getValues()) {
				preferenceElement.addElement("value", value);
			}

			if (preference.isReadOnly()) {
				preferenceElement.addElement("read-only", Boolean.TRUE);
			}
		}

		return portletPreferencesElement.toXMLString();
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

				if (elementName.equals("name")) {
					name = StAXReaderUtil.read(xmlEventReader);
				}
				else if (elementName.equals("value")) {
					String value = StAXReaderUtil.read(xmlEventReader);

					values.add(value);
				}
				else if (elementName.equals("read-only")) {
					String value = StAXReaderUtil.read(xmlEventReader);

					readOnly = GetterUtil.getBoolean(value);
				}
			}
			else if (xmlEvent.isEndElement()){
				EndElement endElement = xmlEvent.asEndElement();

				String elementName = endElement.getName().getLocalPart();

				if (elementName.equals("preference")) {
					break;
				}
			}
		}

		return new Preference(
			name, values.toArray(new String[values.size()]), readOnly);
	}

}