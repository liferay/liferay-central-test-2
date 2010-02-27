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

package com.liferay.portlet.polls.model;


/**
 * <a href="PollsQuestion.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PollsQuestion table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.polls.model.impl.PollsQuestionImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsQuestionModel
 * @see       com.liferay.portlet.polls.model.impl.PollsQuestionImpl
 * @see       com.liferay.portlet.polls.model.impl.PollsQuestionModelImpl
 * @generated
 */
public interface PollsQuestion extends PollsQuestionModel {
	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> getChoices()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getVotesCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isExpired();
}