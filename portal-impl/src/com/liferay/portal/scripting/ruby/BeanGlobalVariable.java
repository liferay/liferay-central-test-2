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

package com.liferay.portal.scripting.ruby;

import org.jruby.Ruby;
import org.jruby.javasupport.Java;
import org.jruby.javasupport.JavaObject;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.Block;
import org.jruby.runtime.IAccessor;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * <a href="BeanGlobalVariable.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 */
class BeanGlobalVariable implements IAccessor {

	public BeanGlobalVariable(Ruby ruby, Object bean, Class<?> type) {
		_ruby = ruby;
		_bean = bean;
		_type = type;
	}

	public IRubyObject getValue() {
		IRubyObject value = JavaUtil.convertJavaToRuby(
			_ruby, _bean, _type);

		if (value instanceof JavaObject) {
			return Java.wrap(_ruby, value);
		}
		else {
			return value;
		}
	}

	public IRubyObject setValue(IRubyObject value) {
		Object bean = Java.ruby_to_java(
			_ruby.getObject(), value, Block.NULL_BLOCK);

		_bean = JavaUtil.convertArgument(_ruby, bean, _type);

		return value;
	}

	private Object _bean;
	private Ruby _ruby;
	private Class<?> _type;

}