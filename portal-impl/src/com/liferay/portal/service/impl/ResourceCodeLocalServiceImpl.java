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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.base.ResourceCodeLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="ResourceCodeLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourceCodeLocalServiceImpl
	extends ResourceCodeLocalServiceBaseImpl {

	public ResourceCode addResourceCode(long companyId, String name, int scope)
		throws SystemException {

		long codeId = counterLocalService.increment(
			ResourceCode.class.getName());

		ResourceCode resourceCode = resourceCodePersistence.create(codeId);

		resourceCode.setCompanyId(companyId);
		resourceCode.setName(name);
		resourceCode.setScope(scope);

		try {
			resourceCodePersistence.update(resourceCode, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {companyId=" + companyId + ", name=" +
						name + ", scope=" + scope + "}");
			}

			resourceCode = resourceCodePersistence.fetchByC_N_S(
				companyId, name, scope, false);

			if (resourceCode == null) {
				throw se;
			}
		}

		return resourceCode;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkResourceCodes() throws SystemException {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return;
		}

		if (_resourceCodes.isEmpty()) {
			List<ResourceCode> resourceCodes =
				resourceCodePersistence.findAll();

			for (ResourceCode resourceCode : resourceCodes) {
				String key = encodeKey(
					resourceCode.getCompanyId(), resourceCode.getName(),
					resourceCode.getScope());

				_resourceCodes.put(key, resourceCode);
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkResourceCodes(long companyId, String name)
		throws SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return;
		}

		getResourceCode(companyId, name, ResourceConstants.SCOPE_COMPANY);
		getResourceCode(companyId, name, ResourceConstants.SCOPE_GROUP);
		getResourceCode(
			companyId, name, ResourceConstants.SCOPE_GROUP_TEMPLATE);
		getResourceCode(companyId, name, ResourceConstants.SCOPE_INDIVIDUAL);
	}

	public ResourceCode getResourceCode(long codeId)
		throws PortalException, SystemException {

		return resourceCodePersistence.findByPrimaryKey(codeId);
	}

	public ResourceCode getResourceCode(long companyId, String name, int scope)
		throws SystemException {

		// Always cache the resource code. This table exists to improve
		// performance. Create the resource code if one does not exist.

		String key = encodeKey(companyId, name, scope);

		ResourceCode resourceCode = _resourceCodes.get(key);

		if (resourceCode == null) {
			resourceCode = resourceCodePersistence.fetchByC_N_S(
				companyId, name, scope);

			if (resourceCode == null) {
				resourceCode = resourceCodeLocalService.addResourceCode(
					companyId, name, scope);
			}

			_resourceCodes.put(key, resourceCode);
		}

		return resourceCode;
	}

	protected String encodeKey(long companyId, String name, int scope) {
		StringBundler sb = new StringBundler(5);

		sb.append(companyId);
		sb.append(StringPool.POUND);
		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(scope);

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(
		ResourceCodeLocalServiceImpl.class);

	private static Map<String, ResourceCode> _resourceCodes =
		new ConcurrentHashMap<String, ResourceCode>();

}