package ${packagePath}.service.persistence;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.service.persistence.BasePersistence;

import java.util.Date;

/**
 * <a href="${entity.name}Persistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}PersistenceImpl
 * @see       ${entity.name}Util
 * @generated
 */
public interface ${entity.name}Persistence extends BasePersistence {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#if method.name == "update">
				<#if arrayUtil.getLength(method.parameters) == 1>
	/**
	 * @deprecated Use {@link #update(${entity.name}, boolean merge)}.
	 */
				<#else>
	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  ${entity.varName} the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when ${entity.varName} is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
				</#if>
			</#if>

			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions("${method.returns.dimensions}")} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
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

			;
		</#if>
	</#list>

}