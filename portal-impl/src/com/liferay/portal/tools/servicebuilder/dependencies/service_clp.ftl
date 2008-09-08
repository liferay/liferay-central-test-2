package ${packagePath}.service;

import ${packagePath}.model.${entity.name}Clp;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.FloatWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.ShortWrapper;
import com.liferay.portal.model.BaseModel;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ${entity.name}${sessionTypeName}ServiceClp implements ${entity.name}${sessionTypeName}Service {

	public ${entity.name}${sessionTypeName}ServiceClp(ClassLoaderProxy classLoaderProxy) {
		_classLoaderProxy = classLoaderProxy;
		_classLoader = classLoaderProxy.getClassLoader();
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign returnTypeName = method.returns.value + method.returnsGenericsName + serviceBuilder.getDimensions(method.returns.dimensions)>
			<#assign parameters = method.parameters>

			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions(method.returns.dimensions)} ${method.name}(

			<#list parameters as parameter>
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
				<#list parameters as parameter>
					<#assign parameterTypeName = parameter.type.value + serviceBuilder.getDimensions(parameter.type.dimensions)>

					Object paramObj${parameter_index} =

					<#if parameterTypeName == "boolean">
						new BooleanWrapper(${parameter.name});
					<#elseif parameterTypeName == "double">
						new DoubleWrapper(${parameter.name});
					<#elseif parameterTypeName == "float">
						new FloatWrapper(${parameter.name});
					<#elseif parameterTypeName == "int">
						new IntegerWrapper(${parameter.name});
					<#elseif parameterTypeName == "long">
						new LongWrapper(${parameter.name});
					<#elseif parameterTypeName == "short">
						new ShortWrapper(${parameter.name});
					<#else>
						translateInput(${parameter.name});

						if (${parameter.name} == null) {
							paramObj${parameter_index} = new NullWrapper("${serviceBuilder.getClassName(parameter.type)}");
						}
					</#if>
				</#list>

				<#if returnTypeName != "void">
					Object returnObj = null;
				</#if>

				try {
					<#if returnTypeName != "void">
						returnObj =
					</#if>

					_classLoaderProxy.invoke("${method.name}",

					<#if parameters?size == 0>
						new Object[0]
					<#else>
						new Object[] {
							<#list parameters as parameter>
								paramObj${parameter_index}

								<#if parameter_has_next>
									,
								</#if>
							</#list>
						}
					</#if>

					);
				}
				catch (Throwable t) {
					<#list method.exceptions as exception>
						if (t instanceof ${exception.value}) {
							throw (${exception.value})t;
						}
					</#list>

					if (t instanceof RuntimeException) {
						throw (RuntimeException)t;
					}
					else {
						throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
					}
				}

				<#if returnTypeName != "void">
					<#if returnTypeName == "boolean">
						return ((Boolean)returnObj).booleanValue();
					<#elseif returnTypeName == "double">
						return ((Double)returnObj).doubleValue();
					<#elseif returnTypeName == "float">
						return ((Float)returnObj).floatValue();
					<#elseif returnTypeName == "int">
						return ((Integer)returnObj).intValue();
					<#elseif returnTypeName == "long">
						return ((Long)returnObj).longValue();
					<#elseif returnTypeName == "short">
						return ((Short)returnObj).shortValue();
					<#else>
						return (${returnTypeName})translateOutput(returnObj);
					</#if>
				</#if>
			}
		</#if>
	</#list>

	protected Object translateInput(BaseModel oldModel) {
		Class oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(${entity.name}Clp.class.getName())) {
			${entity.name}Clp oldCplModel = (${entity.name}Clp)oldModel;

			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

			try {
				Thread.currentThread().setContextClassLoader(_classLoader);

				try {
					Class newModelClass = Class.forName("${packagePath}.model.impl.${entity.name}Impl", true, _classLoader);

					Object newModel = newModelClass.newInstance();

					<#list entity.regularColList as column>
						Method method${column_index} = newModelClass.getMethod("set${column.methodName}", new Class[] {
							<#if column.isPrimitiveType()>
								${serviceBuilder.getPrimitiveObj(column.type)}.TYPE
							<#else>
								${column.type}.class
							</#if>
						});

						<#if column.isPrimitiveType()>
							${serviceBuilder.getPrimitiveObj(column.type)}
						<#else>
							${column.type}
						</#if>

						value${column_index} =

						<#if column.isPrimitiveType()>
							new ${serviceBuilder.getPrimitiveObj(column.type)}(
						</#if>

						oldCplModel.get${column.methodName}()

						<#if column.isPrimitiveType()>
							)
						</#if>

						;

						method${column_index}.invoke(newModel, value${column_index});
					</#list>

					return newModel;
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
			finally {
				Thread.currentThread().setContextClassLoader(contextClassLoader);
			}
		}

		return oldModel;
	}

	protected Object translateInput(List oldList) {
		List newList = new ArrayList(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	protected Object translateInput(Object obj) {
		if (obj instanceof BaseModel) {
			return translateInput((BaseModel)obj);
		}
		else if (obj instanceof List) {
			return translateInput((List)obj);
		}
		else {
			return obj;
		}
	}

	protected Object translateOutput(BaseModel oldModel) {
		Class oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals("${packagePath}.model.impl.${entity.name}Impl")) {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

			try {
				Thread.currentThread().setContextClassLoader(_classLoader);

				try {
					${entity.name}Clp newModel = new ${entity.name}Clp();

					<#list entity.regularColList as column>
						Method method${column_index} = oldModelClass.getMethod("get${column.methodName}");

						<#if column.isPrimitiveType()>
							${serviceBuilder.getPrimitiveObj(column.type)}
						<#else>
							${column.type}
						</#if>

						value${column_index} =
						
						(

						<#if column.isPrimitiveType()>
							${serviceBuilder.getPrimitiveObj(column.type)}
						<#else>
							${column.type}
						</#if>

						)
						
						method${column_index}.invoke(oldModel, (Object[])null);

						newModel.set${column.methodName}(value${column_index}
						
						<#if column.isPrimitiveType()>
							.${column.type}Value()
						</#if>

						);
					</#list>

					return newModel;
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
			finally {
				Thread.currentThread().setContextClassLoader(contextClassLoader);
			}
		}

		return oldModel;
	}

	protected Object translateOutput(List oldList) {
		List newList = new ArrayList(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	protected Object translateOutput(Object obj) {
		if (obj instanceof BaseModel) {
			return translateOutput((BaseModel)obj);
		}
		else if (obj instanceof List) {
			return translateOutput((List)obj);
		}
		else {
			return obj;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(${entity.name}${sessionTypeName}ServiceClp.class);

	private ClassLoaderProxy _classLoaderProxy;
	private ClassLoader _classLoader;

}