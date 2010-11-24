package ${packagePath}.model;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

/**
 * <p>
 * This class is a wrapper for {@link ${entity.name}}.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}
 * @generated
 */
public class ${entity.name}Wrapper implements ${entity.name} {

	public ${entity.name}Wrapper(${entity.name} ${entity.varName}) {
		_${entity.varName} = ${entity.varName};
	}

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			${serviceBuilder.getJavadocComment(method)}
			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

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
				<#if method.name == "clone" && (parameters?size == 0)>
					return new ${entity.name}Wrapper((${entity.name})_${entity.varName}.clone());
				<#elseif method.name == "toEscapedModel" && (parameters?size == 0)>
					return new ${entity.name}Wrapper(_${entity.varName}.toEscapedModel());
				<#else>
					<#if method.returns.value != "void">
						return
					</#if>

					_${entity.varName}.${method.name}(

					<#list method.parameters as parameter>
						${parameter.name}

						<#if parameter_has_next>
							,
						</#if>
					</#list>

					);
				</#if>
			}
		</#if>
	</#list>

	public ${entity.name} getWrapped${entity.name}() {
		return _${entity.varName};
	}

	private ${entity.name} _${entity.varName};

}