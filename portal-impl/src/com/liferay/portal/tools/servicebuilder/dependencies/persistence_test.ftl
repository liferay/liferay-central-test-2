<#assign parentPKColumn = "">

<#if entity.isHierarchicalTree()>
	<#assign pkColumn = entity.getPKList()?first>

	<#assign parentPKColumn = entity.getColumn("parent" + pkColumn.methodName)>
</#if>

package ${packagePath}.service.persistence;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

import ${packagePath}.${noSuchEntity}Exception;
import ${packagePath}.model.${entity.name};

import ${beanLocatorUtil};
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

public class ${entity.name}PersistenceTest extends BasePersistenceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		_persistence = (${entity.name}Persistence)${beanLocatorUtilShortName}.locate(${entity.name}Persistence.class.getName());
	}

	public void testCreate() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		assertNotNull(${entity.varName});

		assertEquals(${entity.varName}.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		_persistence.remove(new${entity.name});

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		assertNull(existing${entity.name});
	}

	public void testUpdateNew() throws Exception {
		add${entity.name}();
	}

	public void testUpdateExisting() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} new${entity.name} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				new${entity.name}.set${column.methodName}(

				<#if column.type == "boolean">
					randomBoolean()
				<#elseif column.type == "double">
					nextDouble()
				<#elseif column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "Date">
					nextDate()
				<#elseif column.type == "String">
					randomString()
				</#if>

				);
			</#if>
		</#list>

		_persistence.update(new${entity.name}, false);

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		<#list entity.regularColList as column>
			<#if column.type == "Date">
				assertEquals(Time.getShortTimestamp(existing${entity.name}.get${column.methodName}()), Time.getShortTimestamp(new${entity.name}.get${column.methodName}()));
			<#else>
				assertEquals(existing${entity.name}.get${column.methodName}(), new${entity.name}.get${column.methodName}());
			</#if>
		</#list>
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		assertEquals(existing${entity.name}, new${entity.name});
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw ${noSuchEntity}Exception");
		}
		catch (${noSuchEntity}Exception nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		assertEquals(existing${entity.name}, new${entity.name});
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} missing${entity.name} = _persistence.fetchByPrimaryKey(pk);

		assertNull(missing${entity.name});
	}

	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}", new${entity.name}.get${column.methodName}()));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}", new${entity.name}.get${column.methodName}()));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		${entity.name} existing${entity.name} = result.get(0);

		assertEquals(existing${entity.name}, new${entity.name});
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}",

				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}",

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#assign column = entity.PKList[0]>

		<#if entity.hasCompoundPK()>
			<#assign propertyName = "id.${column.name}">
		<#else>
			<#assign propertyName = "${column.name}">
		</#if>

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("${propertyName}"));

		Object new${column.methodName} = new${entity.name}.get${column.methodName}();

		dynamicQuery.add(RestrictionsFactoryUtil.in("${propertyName}", new Object[] {new${column.methodName}}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existing${column.methodName} = result.get(0);

		assertEquals(existing${column.methodName}, new${column.methodName});
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#assign column = entity.PKList[0]>

		<#if entity.hasCompoundPK()>
			<#assign propertyName = "id.${column.name}">
		<#else>
			<#assign propertyName = "${column.name}">
		</#if>

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("${propertyName}"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("${propertyName}", new Object[] {

		<#if column.type == "int">
			nextInt()
		<#elseif column.type == "long">
			nextLong()
		<#elseif column.type == "String">
			randomString()
		</#if>

		}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ${entity.name} add${entity.name}() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				${entity.varName}.set${column.methodName}(

				<#if column.type == "boolean">
					randomBoolean()
				<#elseif column.type == "double">
					nextDouble()
				<#elseif column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "Date">
					nextDate()
				<#elseif column.type == "String">
					randomString()
				</#if>

				);
			</#if>
		</#list>

		_persistence.update(${entity.varName}, false);

		return ${entity.varName};
	}

	private ${entity.name}Persistence _persistence;

}