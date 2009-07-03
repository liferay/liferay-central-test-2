/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 *
 */
class BeanGlobalVariable implements IAccessor {

	public BeanGlobalVariable(Ruby runtime, Object bean, Class type) {
		this._runtime = runtime;
		this._bean = bean;
		this._type = type;
	}

	public IRubyObject getValue() {
		IRubyObject result = JavaUtil.convertJavaToRuby(_runtime, _bean, _type);

		return result instanceof JavaObject ?
			Java.wrap(_runtime, result) : result;
	}

	public IRubyObject setValue(IRubyObject value) {
		this._bean = JavaUtil.convertArgument(
			_runtime, Java.ruby_to_java(
				_runtime.getObject(), value, Block.NULL_BLOCK),
			_type);
		return value;
	}

	private Ruby _runtime;
	private Object _bean;
	private Class _type;

}