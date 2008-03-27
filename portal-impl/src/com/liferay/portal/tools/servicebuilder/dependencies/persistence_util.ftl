package ${packagePath}.service.persistence;

import java.util.Date;

public class ${entity.name}Util {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic()>
			<#if method.name == "update">
				<#if arrayUtil.getLength(method.parameters) == 1>
	/**
	 * @deprecated Use <code>update(${entity.name} ${entity.varName}, boolean merge)</code>.
	 */
				<#else>
	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param		${entity.varName} the entity to add, update, or merge
	 * @param		merge boolean value for whether to merge the entity. The
	 *				default value is false. Setting merge to true is more
	 *				expensive and should only be true when ${entity.varName} is
	 *				transient. See LEP-5473 for a detailed discussion of this
	 *				method.
	 * @return		true if the portlet can be displayed via Ajax
	 */
				</#if>
			</#if>

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
		return _getUtil()._persistence;
	}

	public void setPersistence(${entity.name}Persistence persistence) {
		_persistence = persistence;
	}

	private static ${entity.name}Util _getUtil() {
		if (_util == null) {
			_util = (${entity.name}Util) ${beanLocatorUtilPackage}.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ${entity.name}Util.class.getName();

	private static ${entity.name}Util _util;

	private ${entity.name}Persistence _persistence;

}