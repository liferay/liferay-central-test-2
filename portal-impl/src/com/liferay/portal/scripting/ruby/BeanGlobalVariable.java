/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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