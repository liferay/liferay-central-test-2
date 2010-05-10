package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.Date;
import java.util.List;

/**
 * <a href="${entity.name}Util.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}Persistence
 * @see       ${entity.name}PersistenceImpl
 * @generated
 */
public class ${entity.name}Util {

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(${entity.name})
	 */
	public static void clearCache(${entity.name} ${entity.varName}) {
		getPersistence().clearCache(${entity.varName});
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ${entity.name} remove(${entity.name} ${entity.varName}) throws SystemException {
		return getPersistence().remove(${entity.varName});
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ${entity.name} update(${entity.name} ${entity.varName}, boolean merge) throws SystemException {
		return getPersistence().update(${entity.varName}, merge);
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isBasePersistenceMethod(method)>
			public static ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions("${method.returns.dimensions}")} ${method.name} (

			<#list method.parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions("${parameter.type.dimensions}")} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
					return
				</#if>

				getPersistence().${method.name}(

				<#list method.parameters as parameter>
					${parameter.name}

					<#if parameter_has_next>
						,
					</#if>
				</#list>

				);
			}
		</#if>
	</#list>

	public static ${entity.name}Persistence getPersistence() {
		if (_persistence == null) {
			<#if pluginName != "">
				_persistence = (${entity.name}Persistence)PortletBeanLocatorUtil.locate(${packagePath}.service.ClpSerializer.SERVLET_CONTEXT_NAME, ${entity.name}Persistence.class.getName());
			<#else>
				_persistence = (${entity.name}Persistence)PortalBeanLocatorUtil.locate(${entity.name}Persistence.class.getName());
			</#if>
		}

		return _persistence;
	}

	public void setPersistence(${entity.name}Persistence persistence) {
		_persistence = persistence;
	}

	private static ${entity.name}Persistence _persistence;

}