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
import com.liferay.portal.util.BaseTestCase;

import java.util.Map;

/**
 * <a href="PortletPreferencesSerializerTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 */
public class PortletPreferencesSerializerTest extends BaseTestCase {

	public void testComplexPortletPreferences() throws Exception {

		// Can not pridict HashMap's store order, so no expect result xml
		// Just make sure the deserializer can grab the correct values from
		// serializer's output

		// Serialize
		PortletPreferencesImpl preferences = new PortletPreferencesImpl();
		// Blank preference
		preferences.setValue("", "");
		// Empty preference
		preferences.setValues("name1", new String[0]);
		// One blank value and one normal value preference
		preferences.setValues("name2", new String[]{"", "value1"});
		// Read only preference
		preferences.getPreferences().put(
			"name3", new Preference(
				"name3", new String[]{"value2", "value3"}, true));

		String resultXML = PortletPreferencesSerializer.toXML(preferences);

		// Deserialize
		PortletPreferencesImpl resultPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(resultXML);

		Map<String, Preference> preferenceMap =
			resultPreferences.getPreferences();

		assertEquals(4, preferenceMap.size());

		Preference blankPreference = preferenceMap.get("");
		assertNotNull(blankPreference);
		assertEquals("", blankPreference.getName());
		String[] values = blankPreference.getValues();
		assertEquals(1, values.length);
		assertEquals("", values[0]);
		assertFalse(blankPreference.isReadOnly());

		Preference emptyPreference = preferenceMap.get("name1");
		assertNotNull(emptyPreference);
		assertEquals("name1", emptyPreference.getName());
		values = emptyPreference.getValues();
		assertEquals(0, values.length);
		assertFalse(emptyPreference.isReadOnly());

		Preference twoValuesPreference = preferenceMap.get("name2");
		assertNotNull(twoValuesPreference);
		assertEquals("name2", twoValuesPreference.getName());
		values = twoValuesPreference.getValues();
		assertEquals(2, values.length);
		assertEquals("", values[0]);
		assertEquals("value1", values[1]);
		assertFalse(twoValuesPreference.isReadOnly());

		Preference readOnlyPreference = preferenceMap.get("name3");
		assertNotNull(readOnlyPreference);
		assertEquals("name3", readOnlyPreference.getName());
		values = readOnlyPreference.getValues();
		assertEquals(2, values.length);
		assertEquals("value2", values[0]);
		assertEquals("value3", values[1]);
		assertTrue(readOnlyPreference.isReadOnly());

	}

	public void testEmptyPortletPreferences() throws SystemException{
		String expectXML = "<portlet-preferences></portlet-preferences>";

		// Serialize
		PortletPreferencesImpl emptyPreferences = new PortletPreferencesImpl();
		String resultXML = PortletPreferencesSerializer.toXML(emptyPreferences);

		assertEquals(expectXML, resultXML);

		// Deserialize
		PortletPreferencesImpl resultPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					expectXML);

		assertEquals(0, resultPreferences.getPreferences().size());
	}

	public void testEmptyPreference() throws Exception {
		String expectXML =
			"<portlet-preferences><preference><name>emptyPreference</name>" +
			"</preference></portlet-preferences>";

		// Serialize
		PortletPreferencesImpl preferences = new PortletPreferencesImpl();
		preferences.setValues("emptyPreference", new String[0]);
		String resultXML = PortletPreferencesSerializer.toXML(preferences);

		assertEquals(expectXML, resultXML);

		// Deserialize
		PortletPreferencesImpl resultPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					expectXML);

		Map<String, Preference> preferenceMap =
			resultPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());
		Preference preference = preferenceMap.values().iterator().next();
		assertEquals("emptyPreference", preference.getName());
		assertEquals(0, preference.getValues().length);
		assertFalse(preference.isReadOnly());
	}

	public void testMultiplePreferences() throws Exception {
		String expectXML =
			"<portlet-preferences><preference><name>testPreference</name>" +
			"<value>testValue1</value><value>testValue2</value>" +
			"</preference></portlet-preferences>";

		// Serialize
		PortletPreferencesImpl preferences = new PortletPreferencesImpl();
		String[] values = {"testValue1", "testValue2"};
		preferences.setValues("testPreference", values);
		String resultXML = PortletPreferencesSerializer.toXML(preferences);

		assertEquals(expectXML, resultXML);

		// Deserialize
		PortletPreferencesImpl resultPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					expectXML);

		Map<String, Preference> preferenceMap =
			resultPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());
		Preference preference = preferenceMap.values().iterator().next();
		assertEquals("testPreference", preference.getName());
		values = preference.getValues();
		assertEquals(2, values.length);
		assertEquals("testValue1", values[0]);
		assertEquals("testValue2", values[1]);
	}

	public void testSingleBlankPreference() throws Exception {
		String expectXML =
			"<portlet-preferences><preference><name>testPreference</name>" +
			"<value></value></preference></portlet-preferences>";

		// Serialize
		PortletPreferencesImpl preferences = new PortletPreferencesImpl();
		preferences.setValue("testPreference", "");
		String resultXML = PortletPreferencesSerializer.toXML(preferences);

		assertEquals(expectXML, resultXML);

		// Deserialize
		PortletPreferencesImpl resultPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					expectXML);

		Map<String, Preference> preferenceMap =
			resultPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());
		Preference preference = preferenceMap.values().iterator().next();
		assertEquals("testPreference", preference.getName());
		String[] values = preference.getValues();
		assertEquals(1, values.length);
		assertEquals("", values[0]);
	}

	public void testSinglePreference() throws Exception {
		String expectXML =
			"<portlet-preferences><preference><name>testPreference</name>" +
			"<value>testValue</value></preference></portlet-preferences>";

		// Serialize
		PortletPreferencesImpl preferences = new PortletPreferencesImpl();
		preferences.setValue("testPreference", "testValue");
		String resultXML = PortletPreferencesSerializer.toXML(preferences);

		assertEquals(expectXML, resultXML);

		// Deserialize
		PortletPreferencesImpl resultPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					expectXML);

		Map<String, Preference> preferenceMap =
			resultPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());
		Preference preference = preferenceMap.values().iterator().next();
		assertEquals("testPreference", preference.getName());
		String[] values = preference.getValues();
		assertEquals(1, values.length);
		assertEquals("testValue", values[0]);
	}

}