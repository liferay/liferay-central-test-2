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

package com.liferay.portal.workflow.rest.internal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowTaskTransitionOperationModel {

	@XmlElement
	public String getComment() {
		return _comment;
	}

	@XmlElement
	public String getTransition() {
		return _transitionName;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setTransitionName(String transitionName) {
		_transitionName = transitionName;
	}

	private String _comment;
	private String _transitionName;

}