package ${packagePath}.service.persistence;

public class ${entity.name}FinderUtil {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic()>
			public static ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions(method.returns.dimensions)} ${method.name}(

			<#list method.parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions(parameter.type.dimensions)} ${parameter.name}

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

				getFinder().${method.name}(

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

	public static ${entity.name}Finder getFinder() {
		return _finder;
	}

	public void setFinder(${entity.name}Finder finder) {
		_finder = finder;
	}

	private static ${entity.name}Finder _finder;

}