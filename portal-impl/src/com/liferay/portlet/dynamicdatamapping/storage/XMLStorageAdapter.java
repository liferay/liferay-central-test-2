/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.ComparisonOperator;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;
import com.liferay.portlet.dynamicdatamapping.storage.query.FieldCondition;
import com.liferay.portlet.dynamicdatamapping.storage.query.FieldConditionImpl;
import com.liferay.portlet.dynamicdatamapping.storage.query.Junction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class XMLStorageAdapter extends BaseStorageAdapter {

	protected long doCreate(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			DDMContent.class.getName());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement(_ROOT);

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			_appendField(
				rootElement, field.getName(), String.valueOf(field.getValue()));
		}

		DDMContent ddmContent = DDMContentLocalServiceUtil.addContent(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DDMStorageLink.class.getName(), null, document.formattedString(),
			serviceContext);

		DDMStorageLinkLocalServiceUtil.addStorageLink(
			classNameId, ddmContent.getPrimaryKey(), structureId,
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	protected void doDeleteByClass(long classPK)
		throws Exception {

		DDMContentLocalServiceUtil.deleteDDMContent(classPK);

		DDMStorageLinkLocalServiceUtil.deleteClassStorageLink(classPK);
	}

	protected void doDeleteByStructure(long structureId)
		throws Exception {

		List<DDMStorageLink> storageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				structureId);

		for (DDMStorageLink storageLink : storageLinks) {
			DDMContentLocalServiceUtil.deleteDDMContent(
				storageLink.getClassPK());
		}

		DDMStorageLinkLocalServiceUtil.deleteStructureStorageLinks(structureId);
	}

	protected List<Fields> doGetFieldsListByClasses(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			structureId, classPKs, fieldNames, null, orderByComparator);
	}

	protected List<Fields> doGetFieldsListByStructure(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(structureId, fieldNames, null, orderByComparator);
	}

	protected Map<Long, Fields> doGetFieldsMapByClasses(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		return _doQuery(structureId, classPKs, fieldNames);
	}

	protected List<Fields> doQuery(
			long structureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			structureId, fieldNames, condition, orderByComparator);
	}

	protected int doQueryCount(long structureId, Condition condition)
		throws Exception {

		XPath xPathCondition = null;

		if (condition != null) {
			xPathCondition = parseCondition(condition);
		}

		long[] classPKs = _getStructureClassPKs(structureId);

		DDMContent content = null;
		Document document = null;
		int counter = 0;

		for (long classPK : classPKs) {
			content = DDMContentLocalServiceUtil.getContent(classPK);

			document = SAXReaderUtil.read(content.getXml());

			if ((xPathCondition == null) ||
				((xPathCondition != null) &&
				  xPathCondition.booleanValueOf(document))) {

				counter++;
			}
		}

		return counter;
	}

	protected void doUpdate(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws Exception {

		DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(classPK);

		Document document = null;
		Element rootElement = null;

		if (merge) {
			document = SAXReaderUtil.read(ddmContent.getXml());

			rootElement = document.getRootElement();
		}
		else {
			document = SAXReaderUtil.createDocument();

			rootElement = document.addElement(_ROOT);
		}

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			String fieldName = field.getName();
			String fieldValue = String.valueOf(field.getValue());

			Element dynamicElement = _getElementByName(document, fieldName);

			if (dynamicElement == null) {
				_appendField(rootElement, fieldName, fieldValue);
			}
			else {
				_appendField(rootElement, fieldName, fieldValue);
			}
		}

		ddmContent.setModifiedDate(serviceContext.getModifiedDate(null));
		ddmContent.setXml(document.formattedString());

		DDMContentLocalServiceUtil.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getXml(), serviceContext);
	}

	protected XPath parseCondition(Condition condition) {
		return SAXReaderUtil.createXPath(
			_DYNAMIC_ELEMENT_XPATH.concat(
				StringPool.OPEN_BRACKET).concat(
					_toXPath(condition)).concat(StringPool.CLOSE_BRACKET));
	}

	private Element _appendField(
		Element rootElement, String fieldName, String value) {

		Element dynamicElement = rootElement.addElement(_DYNAMIC_ELEMENT);

		dynamicElement.addElement(_DYNAMIC_CONTENT);

		_updateField(dynamicElement, fieldName, value);

		return dynamicElement;
	}

	private List<Fields> _doQuery(
			long structureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			structureId, _getStructureClassPKs(structureId), fieldNames,
			condition, orderByComparator);
	}

	private Map<Long, Fields> _doQuery(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		Map<Long, Fields> restuls = new HashMap<Long, Fields>();

		List<Fields> fields = _doQuery(
			structureId, classPKs, fieldNames, null, null);

		for (int i = 0; i < fields.size(); i++) {
			restuls.put(classPKs[i], fields.get(i));
		}

		return restuls;
	}

	private List<Fields> _doQuery(
			long structureId, long[] classPKs, List<String> fieldNames,
			Condition condition, OrderByComparator orderByComparator)
		throws Exception {

		XPath xPathCondition = null;
		List<Fields> results = new ArrayList<Fields>();

		if (condition != null) {
			xPathCondition = parseCondition(condition);
		}

		DDMContent content = null;
		Document document = null;

		for (long classPK : classPKs) {
			content = DDMContentLocalServiceUtil.getContent(classPK);

			document = SAXReaderUtil.read(content.getXml());

			if ((xPathCondition == null) ||
				((xPathCondition != null) &&
				  xPathCondition.booleanValueOf(document))) {

				Fields fields = new Fields();

				List<Element> dynamicElements =
					document.getRootElement().elements(_DYNAMIC_ELEMENT);

				for (Element dynamicElement : dynamicElements) {
					String name = dynamicElement.attributeValue(_NAME);
					String value = dynamicElement.elementText(_DYNAMIC_CONTENT);

					if (((fieldNames == null) ||
						(fieldNames != null) && fieldNames.contains(name))) {

						fields.put(new Field(name, value));
					}
				}

				results.add(fields);
			}
		}

		if (orderByComparator != null) {
			Collections.sort(results, orderByComparator);
		}

		return results;
	}

	private Element _getElementByName(Document document, String name) {
		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@name='" + name + "']");

		List<Node> nodes = xPathSelector.selectNodes(document);

		if (nodes.size() == 1) {
			return (Element)nodes.get(0);
		}
		else {
			return null;
		}
	}

	private long[] _getStructureClassPKs(long structureId)
		throws Exception {

		List<Long> classPKs = new ArrayList<Long>();

		List<DDMStorageLink> storageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				structureId);

		for (DDMStorageLink storageLink : storageLinks) {
			classPKs.add(storageLink.getClassPK());
		}

		return ArrayUtil.toArray(classPKs.toArray(new Long[classPKs.size()]));
	}

	private String _toXPath(Condition condition) {
		StringBundler sb = new StringBundler();

		if (condition.isJunction()) {
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(_toXPath((Junction)condition));
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}
		else {
			sb.append(_toXPath((FieldConditionImpl)condition));
		}

		return sb.toString();
	}

	private String _toXPath(FieldCondition fieldCondition) {
		StringBundler sb = new StringBundler();

		ComparisonOperator comparisonOperator =
			fieldCondition.getComparisonOperator();

		String name = StringUtil.quote(
			String.valueOf(fieldCondition.getName()));

		String value = StringUtil.quote(
			String.valueOf(fieldCondition.getValue()));

		sb.append(StringPool.OPEN_PARENTHESIS);

		String xpathConnector = _CONNECTOR;
		String xpathPrefix = _PREFIX_NAME;
		String xpathSuffix = StringPool.BLANK;

		if (comparisonOperator == ComparisonOperator.LIKE) {
			xpathConnector = _CONNECTOR_LIKE;
			xpathSuffix = StringPool.CLOSE_PARENTHESIS;
		}

		sb.append(xpathPrefix);
		sb.append(name);
		sb.append(xpathConnector);
		sb.append(value);
		sb.append(xpathSuffix);

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String _toXPath(Junction junction) {
		StringBundler sb = new StringBundler();

		String logicalOperator = junction.getLogicalOperator().toString();

		Iterator<Condition> itr = junction.iterator();

		while (itr.hasNext()) {
			Condition condition = itr.next();

			sb.append(_toXPath(condition));

			if (itr.hasNext()) {
				sb.append(StringPool.SPACE);
				sb.append(logicalOperator.toLowerCase());
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private void _updateField(
		Element dynamicElement, String fieldName, String value) {

		Element dynamicContent = dynamicElement.element(_DYNAMIC_CONTENT);

		dynamicElement.addAttribute(_NAME, fieldName);

		dynamicContent.clearContent();

		dynamicContent.addCDATA(value);
	}

	private final String _CONNECTOR = " and dynamic-content= ";

	private final String _CONNECTOR_LIKE = " and contains(dynamic-content, ";

	private final String _DYNAMIC_CONTENT = "dynamic-content";

	private final String _DYNAMIC_ELEMENT = "dynamic-element";

	private final String _DYNAMIC_ELEMENT_XPATH = "//dynamic-element";

	private final String _NAME = "name";

	private final String _PREFIX_NAME = "@name=";

	private final String _ROOT = "root";

}