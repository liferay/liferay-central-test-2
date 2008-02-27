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

package com.liferay.portlet;

import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

/**
 * <a href="ResourceResponseFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceResponseFactory {

	public static ResourceResponseImpl create(
			ResourceRequestImpl req, HttpServletResponse res,
			String portletName, long companyId)
		throws Exception {

		return create(req, res, portletName, companyId, 0);
	}

	public static ResourceResponseImpl create(
			ResourceRequestImpl req, HttpServletResponse res,
			String portletName, long companyId, long plid)
		throws Exception {

		if (PropsValues.COMMONS_POOL_ENABLED) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Borrowing:\t" + _instance._pool.getNumIdle() + "\t" +
						_instance._pool.getNumActive());
			}
		}

		ResourceResponseImpl resourceResImpl = null;

		if (PropsValues.COMMONS_POOL_ENABLED) {
			resourceResImpl =
				(ResourceResponseImpl)_instance._pool.borrowObject();
		}
		else {
			resourceResImpl = new ResourceResponseImpl();
		}

		resourceResImpl.init(req, res, portletName, companyId, plid);

		return resourceResImpl;
	}

	public static void recycle(ResourceResponseImpl resourceResImpl)
		throws Exception {

		if (PropsValues.COMMONS_POOL_ENABLED) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Recycling:\t" + _instance._pool.getNumIdle() + "\t" +
						_instance._pool.getNumActive());
			}

			_instance._pool.returnObject(resourceResImpl);
		}
		else if (resourceResImpl != null) {
			resourceResImpl.recycle();
		}
	}

	private ResourceResponseFactory() {
		_pool = new StackObjectPool(new Factory());
	}

	private static Log _log = LogFactory.getLog(ResourceResponseFactory.class);

	private static ResourceResponseFactory _instance =
		new ResourceResponseFactory();

	private ObjectPool _pool;

	private class Factory extends BasePoolableObjectFactory {

		public Object makeObject() {
			return new ResourceResponseImpl();
		}

		public void passivateObject(Object obj) {
			ResourceResponseImpl resourceResImpl = (ResourceResponseImpl)obj;

			resourceResImpl.recycle();
		}

	}

}