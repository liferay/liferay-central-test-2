package ${packagePath}.service.persistence;

import java.util.Date;

public class ${entity.name}Util {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
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
		return _persistence;
	}

	public void setPersistence(${entity.name}Persistence persistence) {
		_persistence = persistence;
	}

	private static ${entity.name}Persistence _persistence;

}