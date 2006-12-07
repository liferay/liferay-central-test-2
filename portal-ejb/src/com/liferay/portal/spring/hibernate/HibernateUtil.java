/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.CollectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * <a href="HibernateUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class HibernateUtil {

	public static final String SPRING_HIBERNATE_DATA_SOURCE =
		PropsUtil.get(PropsUtil.SPRING_HIBERNATE_DATA_SOURCE);

	public static final String SPRING_HIBERNATE_SESSION_FACTORY =
		PropsUtil.get(PropsUtil.SPRING_HIBERNATE_SESSION_FACTORY);

	public static final String COUNT_COLUMN_NAME = "COUNT_VALUE";

	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public static DataSource getDataSource() {
		return (DataSource)BeanLocatorUtil.locate(SPRING_HIBERNATE_DATA_SOURCE);
	}

	public static SessionFactoryImplementor getSessionFactory() {
		return getSessionFactory(SPRING_HIBERNATE_SESSION_FACTORY);
	}

	public static SessionFactoryImplementor getSessionFactory(
		String sessionFactoryName) {

		if (sessionFactoryName.equals(SPRING_HIBERNATE_SESSION_FACTORY)) {
			if (_sessionFactory == null) {
				LocalSessionFactoryBean lsfb =
					(LocalSessionFactoryBean)BeanLocatorUtil.locate(
						sessionFactoryName);

				_sessionFactory = (SessionFactoryImplementor)lsfb.getObject();
			}

			return _sessionFactory;
		}
		else {
			SessionFactoryImplementor sessionFactory =
				(SessionFactoryImplementor)_sessionFactories.get(
					sessionFactoryName);

			if (sessionFactory == null) {
				LocalSessionFactoryBean lsfb =
					(LocalSessionFactoryBean)BeanLocatorUtil.locate(
						sessionFactoryName);

				sessionFactory = (SessionFactoryImplementor)lsfb.getObject();

				_sessionFactories.put(sessionFactoryName, sessionFactory);
			}

			return sessionFactory;
		}
	}

	public static Dialect getDialect() {
		return getDialect(SPRING_HIBERNATE_SESSION_FACTORY);
	}

	public static Dialect getDialect(String sessionFactoryName) {
		return getSessionFactory(sessionFactoryName).getDialect();
	}

	public static void closeSession(Session session) {
		try {
			if (session != null) {

				// Let Spring manage sessions

				//session.close();
			}
		}
		catch (HibernateException he) {
			_log.error(he.getMessage());
		}
	}

	public static Session openSession() throws HibernateException {
		return openSession(SPRING_HIBERNATE_SESSION_FACTORY);
	}

	public static Session openSession(String sessionFactoryName)
		throws HibernateException {

		SessionFactoryImplementor sessionFactory =
			getSessionFactory(sessionFactoryName);

		return openSession(sessionFactory);
	}

	public static Session openSession(SessionFactory sessionFactory)
		throws HibernateException {

		// Let Spring manage sessions

		return sessionFactory.getCurrentSession();
	}

	public static String getCountColumnName() {
		return COUNT_COLUMN_NAME;
	}

	private static Log _log = LogFactory.getLog(HibernateUtil.class);

	private static SessionFactoryImplementor _sessionFactory;
	private static Map _sessionFactories = CollectionFactory.getHashMap();

}