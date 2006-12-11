/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.service.impl;

import com.liferay.portal.kernel.jbi.WorkflowComponent;
import com.liferay.portal.kernel.jbi.WorkflowComponentException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.workflow.jbi.WorkflowURL;
import com.liferay.portlet.workflow.model.WorkflowDefinition;
import com.liferay.portlet.workflow.model.WorkflowInstance;
import com.liferay.portlet.workflow.model.WorkflowTask;
import com.liferay.portlet.workflow.model.WorkflowTaskFormElement;
import com.liferay.portlet.workflow.model.WorkflowToken;
import com.liferay.portlet.workflow.service.WorkflowComponentService;
import com.liferay.portlet.workflow.service.WorkflowDefinitionServiceUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.io.StringReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="WorkflowComponentServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WorkflowComponentServiceImpl extends PrincipalBean
	implements WorkflowComponentService, WorkflowComponent {

	public List getCurrentTasks(
			long instanceId, long tokenId)
		throws WorkflowComponentException {

		try {
			String xml = getCurrentTasksXml(instanceId, tokenId);

			return parseList(xml, "tasks");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getCurrentTasksXml(
			long instanceId, long tokenId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getCurrentTasksXml");
			url.setParameter("instanceId", instanceId);
			url.setParameter("tokenId", tokenId);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String deploy(String xml) throws WorkflowComponentException {
		try {
			WorkflowURL url = getWorkflowURL();

			String formattedXml = StringUtil.replace(
				xml,
				new String[] {
					"\n", "\r", "\t"
				},
				new String[] {
					"", "", ""
				});

			url.setParameter(Constants.CMD, "deploy");
			url.setParameter("xml", formattedXml);

			String content = url.getContent();

			String definitionId = parseString(content, "definitionId");

			WorkflowDefinitionServiceUtil.addDefinition(
				GetterUtil.getLong(definitionId), xml);

			return definitionId;
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public Object getDefinition(long definitionId)
		throws WorkflowComponentException {

		try {
			String xml = getDefinitionXml(definitionId);

			return parseDefinition(xml);
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public List getDefinitions(
			long definitionId, String name, int begin, int end)
		throws WorkflowComponentException {

		try {
			String xml = getDefinitionsXml(definitionId, name, begin, end);

			return parseList(xml, "definitions");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getDefinitionsXml(
			long definitionId, String name, int begin, int end)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getDefinitionsXml");
			url.setParameter("definitionId", definitionId);
			url.setParameter("name", name);
			url.setParameter("begin", begin);
			url.setParameter("end", end);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public int getDefinitionsCount(long definitionId, String name)
		throws WorkflowComponentException {

		try {
			String xml = getDefinitionsCountXml(definitionId, name);

			return parseInt(xml, "count");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getDefinitionsCountXml(long definitionId, String name)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getDefinitionsCountXml");
			url.setParameter("definitionId", definitionId);
			url.setParameter("name", name);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getDefinitionXml(long definitionId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getDefinitionXml");
			url.setParameter("definitionId", definitionId);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public List getInstances(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean andOperator, int begin, int end)
		throws WorkflowComponentException {

		try {
			String xml = getInstancesXml(
				definitionId, instanceId, definitionName, definitionVersion,
				startDateGT, startDateLT, endDateGT, endDateLT, hideEndedTasks,
				andOperator, begin, end);

			return parseList(xml, "instances");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public int getInstancesCount(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean andOperator)
		throws WorkflowComponentException {

		try {
			String xml = getInstancesCountXml(
				definitionId, instanceId, definitionName, definitionVersion,
				startDateGT, startDateLT, endDateGT, endDateLT, hideEndedTasks,
				andOperator);

			return parseInt(xml, "count");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getInstancesCountXml(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean andOperator)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getInstancesCountXml");
			url.setParameter("definitionId", definitionId);
			url.setParameter("instanceId", instanceId);
			url.setParameter("definitionName", definitionName);
			url.setParameter("definitionVersion", definitionVersion);
			url.setParameter("startDateGT", startDateGT);
			url.setParameter("startDateLT", startDateLT);
			url.setParameter("endDateGT", endDateGT);
			url.setParameter("endDateLT", endDateLT);
			url.setParameter("hideEndedTasks", hideEndedTasks);
			url.setParameter("andOperator", andOperator);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getInstancesXml(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean andOperator, int begin, int end)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getInstancesXml");
			url.setParameter("definitionId", definitionId);
			url.setParameter("instanceId", instanceId);
			url.setParameter("definitionName", definitionName);
			url.setParameter("definitionVersion", definitionVersion);
			url.setParameter("startDateGT", startDateGT);
			url.setParameter("startDateLT", startDateLT);
			url.setParameter("endDateGT", endDateGT);
			url.setParameter("endDateLT", endDateLT);
			url.setParameter("hideEndedTasks", hideEndedTasks);
			url.setParameter("andOperator", andOperator);
			url.setParameter("begin", begin);
			url.setParameter("end", end);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public List getTaskFormElements(long taskId)
		throws WorkflowComponentException {

		try {
			String xml = getTaskFormElementsXml(taskId);

			return parseList(xml, "taskFormElements");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getTaskFormElementsXml(long taskId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getTaskFormElementsXml");
			url.setParameter("taskId", taskId);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public List getTaskTransitions(long taskId)
		throws WorkflowComponentException {

		try {
			String xml = getTaskTransitionsXml(taskId);

			return parseList(xml, "taskTransitions");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getTaskTransitionsXml(long taskId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getTaskTransitionsXml");
			url.setParameter("taskId", taskId);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public List getUserTasks(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks, boolean andOperator,
			int begin, int end)
		throws WorkflowComponentException {

		try {
			String xml = getUserTasksXml(
				instanceId, taskName, definitionName, assignedTo, createDateGT,
				createDateLT, startDateGT, startDateLT, endDateGT, endDateLT,
				hideEndedTasks, andOperator, begin, end);

			return parseList(xml, "tasks");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public int getUserTasksCount(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks, boolean andOperator)
		throws WorkflowComponentException {

		try {
			String xml = getUserTasksCountXml(
				instanceId, taskName, definitionName, assignedTo, createDateGT,
				createDateLT, startDateGT, startDateLT, endDateGT, endDateLT,
				hideEndedTasks, andOperator);

			return parseInt(xml, "count");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getUserTasksCountXml(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks, boolean andOperator)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getUserTasksCountXml");
			url.setParameter("instanceId", instanceId);
			url.setParameter("taskName", taskName);
			url.setParameter("definitionName", definitionName);
			url.setParameter("assignedTo", assignedTo);
			url.setParameter("createDateGT", createDateGT);
			url.setParameter("createDateLT", createDateLT);
			url.setParameter("startDateGT", startDateGT);
			url.setParameter("startDateLT", startDateLT);
			url.setParameter("endDateGT", endDateGT);
			url.setParameter("endDateLT", endDateLT);
			url.setParameter("hideEndedTasks", hideEndedTasks);
			url.setParameter("andOperator", andOperator);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getUserTasksXml(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks, boolean andOperator,
			int begin, int end)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "getUserTasksXml");
			url.setParameter("instanceId", instanceId);
			url.setParameter("taskName", taskName);
			url.setParameter("definitionName", definitionName);
			url.setParameter("assignedTo", assignedTo);
			url.setParameter("createDateGT", createDateGT);
			url.setParameter("createDateLT", createDateLT);
			url.setParameter("startDateGT", startDateGT);
			url.setParameter("startDateLT", startDateLT);
			url.setParameter("endDateGT", endDateGT);
			url.setParameter("endDateLT", endDateLT);
			url.setParameter("hideEndedTasks", hideEndedTasks);
			url.setParameter("andOperator", andOperator);
			url.setParameter("begin", begin);
			url.setParameter("end", end);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public void signalInstance(long instanceId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "signalInstance");
			url.setParameter("instanceId", instanceId);

			url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public void signalToken(long instanceId, long tokenId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "signalToken");
			url.setParameter("instanceId", instanceId);
			url.setParameter("tokenId", tokenId);

			url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String startWorkflow(long definitionId)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "startWorkflow");
			url.setParameter("definitionId", definitionId);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public Map updateTask(long taskId, String transition, Map parameterMap)
		throws WorkflowComponentException {

		try {
			String xml = updateTaskXml(taskId, transition, parameterMap);

			return parseErrors(xml);
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String updateTaskXml(
			long taskId, String transition, Map parameterMap)
		throws WorkflowComponentException {

		try {
			WorkflowURL url = getWorkflowURL();

			url.setParameter(Constants.CMD, "updateTaskXml");
			url.setParameter("taskId", taskId);
			url.setParameter("transition", transition);
			url.addParameterMap(parameterMap);

			return url.getContent();
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	protected WorkflowURL getWorkflowURL() {
		WorkflowURL url = null;

		try {
			url = new WorkflowURL(getUser());
		}
		catch (Exception e) {
			url = new WorkflowURL();
		}

		return url;
	}

	protected Date parseDate(String date) throws ParseException {
		if (Validator.isNull(date)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");

		return sdf.parse(date);
	}

	protected WorkflowDefinition parseDefinition(String xml)
		throws DocumentException, ParseException {

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		return parseDefinition(root.element("definition"));
	}

	protected WorkflowDefinition parseDefinition(Element el) {
		long definitionId = GetterUtil.getLong(
			el.elementText("definitionId"));
		String name = el.elementText("name");
		String type = el.elementText("type");
		double version = GetterUtil.getDouble(el.elementText("version"));

		WorkflowDefinition definition = new WorkflowDefinition();

		definition.setDefinitionId(definitionId);
		definition.setName(name);
		definition.setType(type);
		definition.setVersion(version);

		return definition;
	}

	protected List parseDefinitions(Element root) {
		List definitions = new ArrayList();

		Iterator itr = root.elements("definition").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			WorkflowDefinition definition = parseDefinition(el);

			definitions.add(definition);
		}

		return definitions;
	}

	protected Map parseErrors(String xml) throws DocumentException {
		Map errors = new HashMap();

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Iterator itr = root.elements("error").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			String name = el.elementText("name");
			String code = el.elementText("code");

			errors.put(name, code);
		}

		return errors;
	}

	protected List parseInstances(Element root) throws ParseException {
		List instances = new ArrayList();

		Iterator itr = root.elements("instance").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			long instanceId = GetterUtil.getLong(el.elementText("instanceId"));
			Date startDate = parseDate(el.elementText("startDate"));
			Date endDate = parseDate(el.elementText("endDate"));
			boolean ended = GetterUtil.getBoolean(el.elementText("ended"));

			List definitions = parseDefinitions(el);

			WorkflowDefinition definition =
				(WorkflowDefinition)definitions.get(0);

			List tokens = parseTokens(el);

			WorkflowToken token = new WorkflowToken();

			if (tokens.size() > 0) {
				token = (WorkflowToken)tokens.get(0);
			}

			WorkflowInstance instance = new WorkflowInstance();

			instance.setInstanceId(instanceId);
			instance.setDefinition(definition);
			instance.setStartDate(startDate);
			instance.setEndDate(endDate);
			instance.setToken(token);
			instance.setEnded(ended);

			instances.add(instance);
		}

		return instances;
	}

	protected int parseInt(String xml, String name)
		throws DocumentException {

		return GetterUtil.getInteger(parseString(xml, name));
	}

	protected List parseList(String xml, String name)
		throws DocumentException, ParseException {

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		if (name.equals("definitions")) {
			return parseDefinitions(root);
		}
		else if (name.equals("instances")) {
			return parseInstances(root);
		}
		else if (name.equals("taskFormElements")) {
			return parseTaskFormElements(root);
		}
		else if (name.equals("tasks")) {
			return parseTasks(root);
		}
		else if (name.equals("taskTransitions")) {
			return parseTaskTransitions(root);
		}
		else if (name.equals("tokens")) {
			return parseTokens(root);
		}
		else {
			throw new DocumentException("List name " + name + " not valid");
		}
	}

	protected String parseString(String xml, String name)
		throws DocumentException {

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Element el = root.element(name);

		if (el != null) {
			return el.getText();
		}
		else {
			return StringPool.BLANK;
		}
    }

	protected List parseTaskFormElements(Element root) {
		List taskFormElements = new ArrayList();

		Iterator itr1 = root.elements("taskFormElement").iterator();

		while (itr1.hasNext()) {
			Element el = (Element)itr1.next();

			String type = el.elementText("type");
			String displayName = el.elementText("displayName");
			String variableName = el.elementText("variableName");
			String value = el.elementText("value");

			List valueList = new ArrayList();

			Iterator itr2 = el.element("values").elements("value").iterator();

			while (itr2.hasNext()) {
				Element valueEl = (Element)itr2.next();

				valueList.add(valueEl.getText());
			}

			boolean readable = GetterUtil.getBoolean(
				el.elementText("readable"));
			boolean writable = GetterUtil.getBoolean(
				el.elementText("writable"));
			boolean required = GetterUtil.getBoolean(
				el.elementText("required"));

			WorkflowTaskFormElement taskFormElement =
				new WorkflowTaskFormElement();

			taskFormElement.setType(type);
			taskFormElement.setDisplayName(displayName);
			taskFormElement.setVariableName(variableName);
			taskFormElement.setValue(value);
			taskFormElement.setValueList(valueList);
			taskFormElement.setReadable(readable);
			taskFormElement.setWritable(writable);
			taskFormElement.setRequired(required);

			taskFormElements.add(taskFormElement);
		}

		return taskFormElements;
	}

	protected List parseTasks(Element root) throws ParseException {
		List tasks = new ArrayList();

		Iterator itr = root.elements("task").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			long taskId = GetterUtil.getLong(el.elementText("taskId"));

			if (taskId == 0) {
				break;
			}

			String name = el.elementText("name");
			String assignedUserId = el.elementText("assignedUserId");
			Date createDate = parseDate(el.elementText("createDate"));
			Date startDate = parseDate(el.elementText("startDate"));
			Date endDate = parseDate(el.elementText("endDate"));

			List instances = parseInstances(el);

			WorkflowInstance instance = (WorkflowInstance)instances.get(0);

			WorkflowTask task = new WorkflowTask();

			task.setTaskId(taskId);
			task.setName(name);
			task.setInstance(instance);
			task.setAssignedUserId(assignedUserId);
			task.setCreateDate(createDate);
			task.setStartDate(startDate);
			task.setEndDate(endDate);

			tasks.add(task);
		}

		return tasks;
	}

	protected List parseTaskTransitions(Element root) {
		List taskTransitions = new ArrayList();

		Iterator itr = root.elements("transition").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			String name = el.getText();

			taskTransitions.add(name);
		}

		return taskTransitions;
	}

	protected List parseTokens(Element root) throws ParseException {
		List tokens = new ArrayList();

		Iterator itr = root.elements("token").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			long tokenId = GetterUtil.getLong(el.elementText("tokenId"));
			String name = el.elementText("name");
			String type = el.elementText("type");

			List tasks = parseTasks(el);
			List children = parseTokens(el);

			WorkflowToken token = new WorkflowToken();

			token.setTokenId(tokenId);
			token.setName(name);
			token.setType(type);
			token.setTasks(tasks);
			token.setChildren(children);

			tokens.add(token);
		}

		return tokens;
	}

}