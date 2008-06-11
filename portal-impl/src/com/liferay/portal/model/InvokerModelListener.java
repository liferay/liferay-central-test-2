/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.ModelListenerException;

/**
 * <a href="InvokerModelListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class InvokerModelListener implements ModelListener {

	public InvokerModelListener(ModelListener modelListener) {
		this(modelListener, Thread.currentThread().getContextClassLoader());
	}

	public InvokerModelListener(
		ModelListener modelListener, ClassLoader classLoader) {

		_modelListener = modelListener;
		_classLoader = classLoader;
	}

	public void onBeforeCreate(BaseModel model) throws ModelListenerException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(_classLoader);

		try {
			_modelListener.onBeforeCreate(model);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterCreate(BaseModel model) throws ModelListenerException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(_classLoader);

		try {
			_modelListener.onAfterCreate(model);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeRemove(BaseModel model) throws ModelListenerException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(_classLoader);

		try {
			_modelListener.onBeforeRemove(model);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterRemove(BaseModel model)throws ModelListenerException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(_classLoader);

		try {
			_modelListener.onAfterRemove(model);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeUpdate(BaseModel model)throws ModelListenerException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(_classLoader);

		try {
			_modelListener.onBeforeUpdate(model);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterUpdate(BaseModel model) throws ModelListenerException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(_classLoader);

		try {
			_modelListener.onAfterUpdate(model);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	private ModelListener _modelListener;
	private ClassLoader _classLoader;

}