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

package com.liferay.portal.events;

import com.liferay.portal.events.data.PageTemplatesAction;
import com.liferay.portal.events.data.SiteTemplatesAction;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;

/**
 * @author Juan Fernández
  */
public class DefaultDataAction extends SimpleAction {

	public DefaultDataAction() {
		_sta = new SiteTemplatesAction();
		_pta = new PageTemplatesAction();
	}

	public void run(String[] ids) throws ActionException {
		_sta.run(ids);
		_pta.run(ids);
	}

	private SiteTemplatesAction _sta = null;
	private PageTemplatesAction _pta = null;
}