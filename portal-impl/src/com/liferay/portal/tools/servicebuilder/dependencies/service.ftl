package ${packagePath}.service;

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

@Transactional(isolation = Isolation.PORTAL, rollbackFor = {PortalException.class, SystemException.class})

/**
 * <a href="${entity.name}${sessionTypeName}Service.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * ${packagePath}.service.impl.${entity.name}${sessionTypeName}ServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * ${serviceComments}
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}${sessionTypeName}ServiceUtil
 * @generated
 */
public interface ${entity.name}${sessionTypeName}Service {

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			<#if method.name = "dynamicQuery">
				@SuppressWarnings("unchecked")
			</#if>

			<#if serviceBuilder.isServiceReadOnlyMethod(method, entity.txRequiredList)>
				@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
			</#if>
			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

			<#list method.parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

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