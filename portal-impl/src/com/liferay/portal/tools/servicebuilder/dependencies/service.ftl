package ${packagePath}.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

@Transactional(isolation = Isolation.PORTAL, rollbackFor = {PortalException.class, SystemException.class})
public interface ${entity.name}${sessionTypeName}Service {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			<#if serviceBuilder.isServiceReadOnlyMethod(method, entity.txRequiredList)>
				@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
			</#if>
			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions(method.returns.dimensions)} ${method.name}(

			<#list method.parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions(parameter.type.dimensions)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#if sessionTypeName == "Local">
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.value}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			<#else>
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.value}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			</#if>

			;
		</#if>
	</#list>

}