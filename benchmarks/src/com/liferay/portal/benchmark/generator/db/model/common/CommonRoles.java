/*
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

package com.liferay.portal.benchmark.generator.db.model.common;

import com.liferay.portal.benchmark.generator.db.model.Role;
import com.liferay.portal.benchmark.generator.db.model.Scope;

/**
 * <a href="CommonRoles.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class CommonRoles {
	public static Role ADMINISTRATOR(long companyId) {
		return new Role(10113, companyId, 0, 0, "Administrator", Scope.COMPANY);
	}

	public static Role GUEST(long companyId) {
		return new Role(10114, companyId, 0, 0, "Guest", Scope.COMPANY);
	}

	public static Role OWNER(long companyId) {
		return new Role(10115, companyId, 0, 0, "Owner", Scope.COMPANY);
	}

	public static Role POWER_USER(long companyId) {
		return new Role(10116, companyId, 0, 0, "Power User", Scope.COMPANY);
	}

	public static Role USER(long companyId) {
		return new Role(10117, companyId, 0, 0, "User", Scope.COMPANY);
	}

	public static Role COMMUNITY_ADMIN(long companyId) {
		return new Role(10118, companyId, 0, 0, "Community Administrator",
						Scope.COMMUNITY);
	}

	public static Role COMMUNITY_MEMBER(long companyId) {
		return new Role(10119, companyId, 0, 0, "Community Member",
						Scope.COMMUNITY);
	}

	public static Role COMMUNITY_OWNER(long companyId) {
		return new Role(10120, companyId, 0, 0, "Community Owner",
						Scope.COMPANY);
	}

	public static Role ORG_ADMIN(long companyId) {
		return new Role(10118, companyId, 0, 0, "Organization Administrator",
						Scope.ORGANIZATION);
	}

	public static Role ORG_MEMBER(long companyId) {
		return new Role(10119, companyId, 0, 0, "Organization Member",
						Scope.ORGANIZATION);
	}

	public static Role ORG_OWNER(long companyId) {
		return new Role(10120, companyId, 0, 0, "Organization Owner",
						Scope.ORGANIZATION);
	}
}
