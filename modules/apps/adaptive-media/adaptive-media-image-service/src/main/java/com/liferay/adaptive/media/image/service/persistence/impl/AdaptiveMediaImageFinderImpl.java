package com.liferay.adaptive.media.image.service.persistence.impl;

import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageFinder;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Iterator;

/**
 * @author Sergio González
 */
public class AdaptiveMediaImageFinderImpl
	extends AdaptiveMediaImageFinderBaseImpl
	implements AdaptiveMediaImageFinder {

	public static final String COUNT_BY_DL_LIFERAY_FILE_ENTRY =
		AdaptiveMediaImageFinder.class.getName() + ".countByDLLiferayFileEntry";

	public static final String COUNT_BY_BLOGS_FILE_ENTRY =
		AdaptiveMediaImageFinder.class.getName() + ".countByBlogsFileEntry";

	@Override
	public int countByBlogsFileEntries(long companyId, String[] mimeTypes) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), COUNT_BY_BLOGS_FILE_ENTRY);

			if (ArrayUtil.isNotEmpty(mimeTypes)) {
				StringBundler sb = new StringBundler(5);

				sb.append(sql);
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getMimeTypes(mimeTypes, "DLFileEntry"));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				sql = sb.toString();
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(PortalUtil.getClassNameId(BlogsEntry.class.getName()));

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByDLLiferayFileEntries(long companyId, String[] mimeTypes) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), COUNT_BY_DL_LIFERAY_FILE_ENTRY);

			if (ArrayUtil.isNotEmpty(mimeTypes)) {
				StringBundler sb = new StringBundler(5);

				sb.append(sql);
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getMimeTypes(mimeTypes, "DLFileEntry"));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				sql = sb.toString();
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getMimeTypes(String[] mimeTypes, String tableName) {
		if (mimeTypes.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(mimeTypes.length * 3 - 1);

		for (int i = 0; i < mimeTypes.length; i++) {
			sb.append(tableName);
			sb.append(".mimeType = ?");

			if ((i + 1) != mimeTypes.length) {
				sb.append(WHERE_OR);
			}
		}

		return sb.toString();
	}

}