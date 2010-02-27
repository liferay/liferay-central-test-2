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

package com.liferay.util.xml;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * <a href="DocUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DocUtil {

	public static void add(Element element, String name, boolean text) {
		add(element, name, String.valueOf(text));
	}

	public static void add(Element element, String name, double text) {
		add(element, name, String.valueOf(text));
	}

	public static void add(Element element, String name, float text) {
		add(element, name, String.valueOf(text));
	}

	public static void add(Element element, String name, int text) {
		add(element, name, String.valueOf(text));
	}

	public static void add(Element element, String name, long text) {
		add(element, name, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace) {

		QName qName = SAXReaderUtil.createQName(name, namespace);

		return element.addElement(qName);
	}

	public static void add(
		Element element, String name, Namespace namespace, boolean text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, double text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, float text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, int text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, long text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, Object text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, short text) {

		add(element, name, namespace, String.valueOf(text));
	}

	public static void add(
		Element element, String name, Namespace namespace, String text) {

		QName qName = SAXReaderUtil.createQName(name, namespace);

		Element childElement = element.addElement(qName);

		childElement.addText(GetterUtil.getString(text));
	}

	public static void add(Element element, String name, Object text) {
		add(element, name, String.valueOf(text));
	}

	public static void add(Element element, String name, short text) {
		add(element, name, String.valueOf(text));
	}

	public static void add(Element element, String name, String text) {
		Element childElement = element.addElement(name);

		childElement.addText(GetterUtil.getString(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, boolean text) {

		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, double text) {

		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(org.dom4j.Element element, String name, float text) {
		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(org.dom4j.Element element, String name, int text) {
		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(org.dom4j.Element element, String name, long text) {
		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, Object text) {

		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static org.dom4j.Element add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace) {

		org.dom4j.QName qName = new org.dom4j.QName(name, namespace);

		return element.addElement(qName);
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		boolean text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		double text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		float text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		int text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		long text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		Object text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		short text) {

		add(element, name, namespace, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, org.dom4j.Namespace namespace,
		String text) {

		org.dom4j.QName qName = new org.dom4j.QName(name, namespace);

		org.dom4j.Element childElement = element.addElement(qName);

		childElement.addText(GetterUtil.getString(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(org.dom4j.Element element, String name, short text) {
		add(element, name, String.valueOf(text));
	}

	/**
	 * @deprecated
	 */
	public static void add(
		org.dom4j.Element element, String name, String text) {

		org.dom4j.Element childElement = element.addElement(name);

		childElement.addText(GetterUtil.getString(text));
	}

}