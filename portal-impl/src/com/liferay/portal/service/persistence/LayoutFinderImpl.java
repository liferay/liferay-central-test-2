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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutSoap;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="LayoutFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutFinderImpl
	extends BasePersistenceImpl<Layout> implements LayoutFinder {

	public static String FIND_BY_NULL_FRIENDLY_URL =
		LayoutFinder.class.getName() + ".findByNullFriendlyURL";

	public static String FIND_BY_C_P_P =
		LayoutFinder.class.getName() + ".findByC_P_P";

	public List<Layout> findByNullFriendlyURL() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NULL_FRIENDLY_URL);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Layout", LayoutImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<LayoutReference> findByC_P_P(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue)
		throws SystemException {

		String preferences =
			"%<preference><name>" + preferencesKey + "</name><value>" +
				preferencesValue + "</value>%";

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_P_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("layoutPlid", Type.LONG);
			q.addScalar("preferencesPortletId", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(portletId);
			qPos.add(portletId + "_INSTANCE_%");
			qPos.add(preferences);

			List<LayoutReference> layoutReferences =
				new ArrayList<LayoutReference>();

			Iterator<Object[]> itr = q.list().iterator();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				Long layoutPlid = (Long)array[0];
				String preferencesPortletId = (String)array[1];

				Layout layout = LayoutUtil.findByPrimaryKey(
					layoutPlid.longValue());

				layoutReferences.add(
					new LayoutReference(
						LayoutSoap.toSoapModel(layout), preferencesPortletId));
			}

			return layoutReferences;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}