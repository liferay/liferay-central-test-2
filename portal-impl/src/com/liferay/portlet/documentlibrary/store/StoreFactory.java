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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class StoreFactory {

	public static Store getInstance() {
		if (_store == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Instantiate " + PropsValues.DL_STORE_IMPL);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_store = (Store)classLoader.loadClass(
					PropsValues.DL_STORE_IMPL).newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _store.getClass().getName());
		}

		return _store;
	}

	public static void setInstance(Store store) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + store.getClass().getName());
		}

		_store = store;
	}

	private static Log _log = LogFactoryUtil.getLog(StoreFactory.class);

	private static Store _store;

}