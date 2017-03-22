/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.tools.ImportPackage;
import com.liferay.source.formatter.ElementComparator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLSpringFileCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (fileName.endsWith("-spring.xml")) {
			_checkSpringXML(sourceFormatterMessages, fileName, content);
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkSpringXML(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		checkElementOrder(
			sourceFormatterMessages, fileName, document.getRootElement(),
			"bean", null, new SpringBeanElementComparator("id"));
	}

	private class SpringBeanElementComparator extends ElementComparator {

		public SpringBeanElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(Element element1, Element element2) {
			String elementName1 = getElementName(element1);
			String elementName2 = getElementName(element2);

			if ((elementName1 == null) || (elementName2 == null)) {
				return 0;
			}

			int startsWithWeight = StringUtil.startsWithWeight(
				elementName1, elementName2);

			if (startsWithWeight != 0) {
				String startKey = elementName1.substring(0, startsWithWeight);

				if (startKey.contains(".service.")) {
					return _compareServiceElements(elementName1, elementName2);
				}
			}

			if ((StringUtil.count(elementName1, StringPool.PERIOD) > 1) &&
				(StringUtil.count(elementName2, StringPool.PERIOD) > 1)) {

				ImportPackage importPackage1 = new ImportPackage(
					elementName1, false, elementName1);
				ImportPackage importPackage2 = new ImportPackage(
					elementName2, false, elementName2);

				return importPackage1.compareTo(importPackage2);
			}

			if (StringUtil.count(elementName1, StringPool.PERIOD) > 1) {
				return -1;
			}

			return super.compare(element1, element2);
		}

		@Override
		public String getElementName(Element element) {
			String elementName = super.getElementName(element);

			if ((elementName != null) &&
				(StringUtil.count(elementName, StringPool.PERIOD) > 1)) {

				return elementName;
			}

			return element.attributeValue("class");
		}

		private int _compareServiceElements(String name1, String name2) {
			SpringBeanServiceElement springBeanServiceElemen1 =
				new SpringBeanServiceElement(name1);
			SpringBeanServiceElement springBeanServiceElemen2 =
				new SpringBeanServiceElement(name2);

			return springBeanServiceElemen1.compareTo(springBeanServiceElemen2);
		}

		private class SpringBeanServiceElement
			implements Comparable<SpringBeanServiceElement> {

			public SpringBeanServiceElement(String name) {
				_beanObjectName = StringPool.BLANK;
				_type = -1;

				Matcher matcher = _localServicePattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _LOCAL_SERVICE;

					return;
				}

				matcher = _servicePattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _SERVICE;

					return;
				}

				matcher = _persistencePattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _PERSISTENCE;

					return;
				}

				matcher = _finderPattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _FINDER;
				}
			}

			@Override
			public int compareTo(
				SpringBeanServiceElement springBeanServiceElement) {

				if (_beanObjectName.equals(
						springBeanServiceElement.getBeanObjectName())) {

					return _type - springBeanServiceElement.getType();
				}

				NaturalOrderStringComparator comparator =
					new NaturalOrderStringComparator();

				return comparator.compare(
					_beanObjectName,
					springBeanServiceElement.getBeanObjectName());
			}

			public String getBeanObjectName() {
				return _beanObjectName;
			}

			public int getType() {
				return _type;
			}

			private static final int _FINDER = 4;

			private static final int _LOCAL_SERVICE = 1;

			private static final int _PERSISTENCE = 3;

			private static final int _SERVICE = 2;

			private String _beanObjectName;
			private final Pattern _finderPattern = Pattern.compile(
				"\\.service\\.persistence\\.(\\w+)Finder");
			private final Pattern _localServicePattern = Pattern.compile(
				"\\.service\\.(\\w+)LocalService");
			private final Pattern _persistencePattern = Pattern.compile(
				"\\.service\\.persistence\\.(\\w+)Persistence");
			private final Pattern _servicePattern = Pattern.compile(
				"\\.service\\.(\\w+)Service");
			private int _type;

		}

	}

}