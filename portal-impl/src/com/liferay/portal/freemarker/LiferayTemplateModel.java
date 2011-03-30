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

package com.liferay.portal.freemarker;

import com.liferay.portlet.journal.util.TemplateNode;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author Mika Koivisto
 */
public class LiferayTemplateModel extends SimpleHash {

	public LiferayTemplateModel(TemplateNode node, ObjectWrapper wrapper) {
		super(node, wrapper);

		_beanModel = new BeanModel(node, (BeansWrapper)wrapper);
		_node = node;
		_wrapper = wrapper;
	}

	public TemplateModel get(String key) throws TemplateModelException {

		TemplateModel result = super.get(key);

		if (result != null) {
			return result;
		}

		return _beanModel.get(key);
	}

	private BeanModel _beanModel;
	private TemplateNode _node;
	private ObjectWrapper _wrapper;

}
