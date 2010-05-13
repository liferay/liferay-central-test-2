package ${packagePath}.service.base;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import ${beanLocatorUtil};
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import javax.sql.DataSource;

<#if sessionTypeName == "">
	import com.liferay.portal.service.base.PrincipalBean;
</#if>

<#if entity.hasColumns()>
	<#if entity.hasCompoundPK()>
		import ${packagePath}.service.persistence.${entity.name}PK;
	</#if>

	import ${packagePath}.model.${entity.name};
	import ${packagePath}.model.impl.${entity.name}Impl;

	import com.liferay.portal.kernel.dao.orm.DynamicQuery;
	import com.liferay.portal.kernel.exception.PortalException;
	import com.liferay.portal.kernel.util.OrderByComparator;

	import java.util.List;
</#if>

<#list referenceList as tempEntity>
	<#if tempEntity.hasLocalService()>
		import ${tempEntity.packagePath}.service.${tempEntity.name}LocalService;
	</#if>

	<#if tempEntity.hasRemoteService()>
		import ${tempEntity.packagePath}.service.${tempEntity.name}Service;
	</#if>

	<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Persistence;
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Util;
	</#if>

	<#if tempEntity.hasFinderClass() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Finder;
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}FinderUtil;
	</#if>
</#list>

<#if sessionTypeName == "Local">
	public abstract class ${entity.name}LocalServiceBaseImpl implements ${entity.name}LocalService {
<#else>
	public abstract class ${entity.name}ServiceBaseImpl extends PrincipalBean implements ${entity.name}Service {
</#if>

<#if sessionTypeName == "Local" && entity.hasColumns()>
	public ${entity.name} add${entity.name}(${entity.name} ${entity.varName}) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "add" + entity.name, [packagePath + ".model." + entity.name], ["SystemException"])} {
		${entity.varName}.setNew(true);

		return ${entity.varName}Persistence.update(${entity.varName}, false);
	}

	public ${entity.name} create${entity.name}(${entity.PKClassName} ${entity.PKVarName}) {
		return ${entity.varName}Persistence.create(${entity.PKVarName});
	}

	public void delete${entity.name}(${entity.PKClassName} ${entity.PKVarName}) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "delete" + entity.name, [entity.PKClassName], ["PortalException", "SystemException"])} {
		${entity.varName}Persistence.remove(${entity.PKVarName});
	}

	public void delete${entity.name}(${entity.name} ${entity.varName}) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "delete" + entity.name, [packagePath + ".model." + entity.name], ["SystemException"])} {
		${entity.varName}Persistence.remove(${entity.varName});
	}

	@SuppressWarnings("unchecked")
	public List dynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
		return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end) throws SystemException {
		return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end, OrderByComparator orderByComparator) throws SystemException {
		return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public long dynamicQueryCount(DynamicQuery dynamicQuery) throws SystemException {
		return ${entity.varName}Persistence.countWithDynamicQuery(dynamicQuery);
	}

	public ${entity.name} get${entity.name}(${entity.PKClassName} ${entity.PKVarName}) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "get" + entity.name, [entity.PKClassName], ["PortalException", "SystemException"])} {
		return ${entity.varName}Persistence.findByPrimaryKey(${entity.PKVarName});
	}

	<#if entity.hasUuid() && entity.hasColumn("groupId")>
		public ${entity.name} get${entity.name}ByUuidAndGroupId(String uuid, long groupId) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "get" + entity.name, [entity.PKClassName], ["PortalException", "SystemException"])} {
			return ${entity.varName}Persistence.findByUUID_G(uuid, groupId);
		}
	</#if>

	public List<${entity.name}> get${entity.names}(int start, int end) throws SystemException {
		return ${entity.varName}Persistence.findAll(start, end);
	}

	public int get${entity.names}Count() throws SystemException {
		return ${entity.varName}Persistence.countAll();
	}

	public ${entity.name} update${entity.name}(${entity.name} ${entity.varName}) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "update" + entity.name, [packagePath + ".model." + entity.name], ["SystemException"])} {
		${entity.varName}.setNew(false);

		return ${entity.varName}Persistence.update(${entity.varName}, true);
	}

	public ${entity.name} update${entity.name}(${entity.name} ${entity.varName}, boolean merge) ${serviceBuilder.getServiceBaseThrowsExceptions(methods, "update" + entity.name, [packagePath + ".model." + entity.name, "boolean"], ["SystemException"])} {
		${entity.varName}.setNew(false);

		return ${entity.varName}Persistence.update(${entity.varName}, merge);
	}
</#if>

<#list referenceList as tempEntity>
	<#if tempEntity.hasLocalService()>
		public ${tempEntity.name}LocalService get${tempEntity.name}LocalService() {
			return ${tempEntity.varName}LocalService;
		}

		public void set${tempEntity.name}LocalService(${tempEntity.name}LocalService ${tempEntity.varName}LocalService) {
			this.${tempEntity.varName}LocalService = ${tempEntity.varName}LocalService;
		}
	</#if>

	<#if tempEntity.hasRemoteService()>
		public ${tempEntity.name}Service get${tempEntity.name}Service() {
			return ${tempEntity.varName}Service;
		}

		public void set${tempEntity.name}Service(${tempEntity.name}Service ${tempEntity.varName}Service) {
			this.${tempEntity.varName}Service = ${tempEntity.varName}Service;
		}
	</#if>

	<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		public ${tempEntity.name}Persistence get${tempEntity.name}Persistence() {
			return ${tempEntity.varName}Persistence;
		}

		public void set${tempEntity.name}Persistence(${tempEntity.name}Persistence ${tempEntity.varName}Persistence) {
			this.${tempEntity.varName}Persistence = ${tempEntity.varName}Persistence;
		}
	</#if>

	<#if tempEntity.hasFinderClass() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		public ${tempEntity.name}Finder get${tempEntity.name}Finder() {
			return ${tempEntity.varName}Finder;
		}

		public void set${tempEntity.name}Finder(${tempEntity.name}Finder ${tempEntity.varName}Finder) {
			this.${tempEntity.varName}Finder = ${tempEntity.varName}Finder;
		}
	</#if>
</#list>

protected void runSQL(String sql) throws SystemException {
	try {
		<#if entity.hasColumns()>
			DataSource dataSource = ${entity.varName}Persistence.getDataSource();
		<#else>
			DataSource dataSource = InfrastructureUtil.getDataSource();
		</#if>

		SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource, sql, new int[0]);

		sqlUpdate.update();
	}
	catch (Exception e) {
		throw new SystemException(e);
	}
}

<#list referenceList as tempEntity>
	<#if tempEntity.hasLocalService()>
		@BeanReference(type = ${tempEntity.name}LocalService.class)
		protected ${tempEntity.name}LocalService ${tempEntity.varName}LocalService;
	</#if>

	<#if tempEntity.hasRemoteService()>
		@BeanReference(type = ${tempEntity.name}Service.class)
		protected ${tempEntity.name}Service ${tempEntity.varName}Service;
	</#if>

	<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		@BeanReference(type = ${tempEntity.name}Persistence.class)
		protected ${tempEntity.name}Persistence ${tempEntity.varName}Persistence;
	</#if>

	<#if tempEntity.hasFinderClass() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		@BeanReference(type = ${tempEntity.name}Finder.class)
		protected ${tempEntity.name}Finder ${tempEntity.varName}Finder;
	</#if>
</#list>

}