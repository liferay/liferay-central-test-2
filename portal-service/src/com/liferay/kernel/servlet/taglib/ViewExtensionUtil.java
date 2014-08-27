/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.kernel.servlet.taglib;

import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;

import java.util.List;

/**
 * This class exists with the sole purpose of holding the Service Trackers in
 * the portal class loader. If we put the service trackers in util taglib they
 * would be recreated in every class loader since util-taglib is copied to
 * modules and plugins.
 *
 * @author Carlos Sierra Andrés
 */
public class ViewExtensionUtil {

	public static List<ViewExtension> getViewExtensions(String extensionId) {
		return _instance._viewExtensions.getService(extensionId);
	}

	private ViewExtensionUtil() {
		_viewExtensions = ServiceTrackerMapFactory.createListServiceTracker(
			ViewExtension.class, "extension-id");

		_viewExtensions.open();
	}

	private	static final ViewExtensionUtil _instance = new ViewExtensionUtil();

	private ServiceTrackerMap<String, List<ViewExtension>> _viewExtensions;

}