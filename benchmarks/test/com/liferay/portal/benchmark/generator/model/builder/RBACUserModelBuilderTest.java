/*
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

package com.liferay.portal.benchmark.generator.model.builder;

import com.liferay.portal.benchmark.generator.util.CommonRoles;
import com.liferay.portal.benchmark.generator.util.DefaultIDGenerator;
import com.liferay.portal.benchmark.model.Role;
import com.liferay.portal.benchmark.model.builder.ModelBuilderConstants;
import com.liferay.portal.benchmark.model.builder.ModelBuilderContext;
import com.liferay.portal.benchmark.model.builder.RBACUserModelBuilder;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import org.junit.Test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="RBACUserModelBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RBACUserModelBuilderTest {
	@Test
	public void testBuildUser() throws Exception {
		DefaultIDGenerator idGenerator = new DefaultIDGenerator();
		RBACUserModelBuilder builder = new RBACUserModelBuilder();
		builder.setCompanyId(10106);
		builder.setIdGenerator(idGenerator);
		builder.setOwnerName("test");
		builder.setOwnerId(10127);


		StringWriter sql = new StringWriter();
		StringWriter logins = new StringWriter();
		ModelBuilderContext context = new ModelBuilderContext();
		context.put(ModelBuilderConstants.FIRST_NAME_KEY, "John");
		context.put(ModelBuilderConstants.LAST_NAME_KEY, "Doe");
		context.put(ModelBuilderConstants.PASSWORD_KEY, "test");
		context.put(ModelBuilderConstants.DOMAIN_KEY, "liferay.com");
		List<Role> roles = new ArrayList<Role>();
		roles.add(CommonRoles.POWER_USER(10106));
		roles.add(CommonRoles.USER(10106));
		context.put(ModelBuilderConstants.ROLES_KEY, roles);
		Map<String, Object> templateContext =
				builder.createProducts(context);
		FreeMarkerUtil.process("com/liferay/portal/benchmark/generator/ftl/db/mysql/5_1/create_user_rbac.ftl",
							   templateContext, sql);
		FreeMarkerUtil.process("com/liferay/portal/benchmark/generator/ftl/user_list.ftl",
							   templateContext, logins);
		Map<String, Object> counters = new HashMap<String, Object>();
		counters.put("counters", idGenerator.report());
		FreeMarkerUtil.process("com/liferay/portal/benchmark/generator/ftl/db/mysql/update_counters.ftl",
							   counters, sql);
		System.out.println(sql.toString());
		System.out.println(logins.toString());

	}
}
