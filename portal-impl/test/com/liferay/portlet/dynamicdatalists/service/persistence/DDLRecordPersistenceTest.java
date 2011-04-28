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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatalists.NoSuchRecordException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDLRecordPersistence)PortalBeanLocatorUtil.locate(DDLRecordPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDLRecord ddlRecord = _persistence.create(pk);

		assertNotNull(ddlRecord);

		assertEquals(ddlRecord.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		_persistence.remove(newDDLRecord);

		DDLRecord existingDDLRecord = _persistence.fetchByPrimaryKey(newDDLRecord.getPrimaryKey());

		assertNull(existingDDLRecord);
	}

	public void testUpdateNew() throws Exception {
		addDDLRecord();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDLRecord newDDLRecord = _persistence.create(pk);

		newDDLRecord.setUuid(randomString());
		newDDLRecord.setClassNameId(nextLong());
		newDDLRecord.setClassPK(nextLong());
		newDDLRecord.setRecordSetId(nextLong());

		_persistence.update(newDDLRecord, false);

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		assertEquals(existingDDLRecord.getUuid(), newDDLRecord.getUuid());
		assertEquals(existingDDLRecord.getRecordId(), newDDLRecord.getRecordId());
		assertEquals(existingDDLRecord.getClassNameId(),
			newDDLRecord.getClassNameId());
		assertEquals(existingDDLRecord.getClassPK(), newDDLRecord.getClassPK());
		assertEquals(existingDDLRecord.getRecordSetId(),
			newDDLRecord.getRecordSetId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		assertEquals(existingDDLRecord, newDDLRecord);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRecordException");
		}
		catch (NoSuchRecordException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DDLRecord existingDDLRecord = _persistence.fetchByPrimaryKey(newDDLRecord.getPrimaryKey());

		assertEquals(existingDDLRecord, newDDLRecord);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDLRecord missingDDLRecord = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDLRecord);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordId",
				newDDLRecord.getRecordId()));

		List<DDLRecord> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDLRecord existingDDLRecord = result.get(0);

		assertEquals(existingDDLRecord, newDDLRecord);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordId", nextLong()));

		List<DDLRecord> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordId"));

		Object newRecordId = newDDLRecord.getRecordId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordId",
				new Object[] { newRecordId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRecordId = result.get(0);

		assertEquals(existingRecordId, newRecordId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDLRecord addDDLRecord() throws Exception {
		long pk = nextLong();

		DDLRecord ddlRecord = _persistence.create(pk);

		ddlRecord.setUuid(randomString());
		ddlRecord.setClassNameId(nextLong());
		ddlRecord.setClassPK(nextLong());
		ddlRecord.setRecordSetId(nextLong());

		_persistence.update(ddlRecord, false);

		return ddlRecord;
	}

	private DDLRecordPersistence _persistence;
}