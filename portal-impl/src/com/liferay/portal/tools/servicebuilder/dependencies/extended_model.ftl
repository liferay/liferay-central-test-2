package ${packagePath}.model;

/**
 * <a href="${entity.name}.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ${entity.table} table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link ${packagePath}.model.impl.${entity.name}Impl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}Model
 * @see       ${packagePath}.model.impl.${entity.name}Impl
 * @see       ${packagePath}.model.impl.${entity.name}ModelImpl
 * @generated
 */
 public interface ${entity.name} extends ${entity.name}Model {

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic()>
			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions("${method.returns.dimensions}")} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions("${parameter.type.dimensions}")} ${parameter.name}<#if parameter_has_next>,</#if>
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

			;
		</#if>
	</#list>

}