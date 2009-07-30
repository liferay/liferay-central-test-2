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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.BaseModelExtension;

import java.util.List;

/**
 * <a href="BaseModelExtensionHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class BaseModelExtensionHandler<T>
	extends AbstractModelExtensionHandler<T> {

	public Object extendSingle(BaseModel<T> model) {
		if (model instanceof BaseModelExtension) {
			return model;
		}
		else {
			BaseModelExtension<T> extension = getBaseModelExtension().extend(
				model);

			if (_log.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();

				sb.append("Extending ");
				sb.append(model.getClass().getName());
				sb.append(" to ");
				sb.append(extension.getClass().getName());

				_log.debug(sb.toString());
			}

			return extension;
		}
	}

	public Object extendList(List<BaseModel<T>> models) {
		return new WrappedList(models);
	}

	private static final Log _log =
		LogFactoryUtil.getLog(BaseModelExtensionHandler.class);

}