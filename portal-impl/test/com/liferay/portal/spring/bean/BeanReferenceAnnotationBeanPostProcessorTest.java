package com.liferay.portal.spring.bean;

import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.BeanReference;
import junit.framework.TestCase;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ByteArrayResource;

public class BeanReferenceAnnotationBeanPostProcessorTest extends TestCase {

	public void testSpringConfigErrorReported() throws Exception {

		BeanReferenceAnnotationBeanPostProcessor processor =
			new BeanReferenceAnnotationBeanPostProcessor();

		XmlBeanFactory factory =
			new XmlBeanFactory(new ByteArrayResource(_CONFIG_XML.getBytes()));
		processor.setBeanFactory(factory);

		String errorMessage = "";

		try {
			processor.postProcessBeforeInitialization(this, "dummybean");
		}
		catch (BeanLocatorException ble) {
			errorMessage = ble.getMessage();
		}

		assertTrue(
			"Error message should contain spring configuration error #1",
			errorMessage.contains(_EXPECTED_ERR_MSG_1));

		assertTrue(
			"Error message should contain spring configuration error #2",
			errorMessage.contains(_EXPECTED_ERR_MSG_2));
	}
	

	@BeanReference(type = BeanReferenceAnnotationBeanPostProcessorTest.class)
	protected BeanReferenceAnnotationBeanPostProcessorTest referencedObject;

	private static final String _CONFIG_XML = "<?xml version=\"1.0\"?>" +
		"<beans xmlns=\"http://www.springframework.org/schema/beans\" " +
		"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
		"default-destroy-method=\"destroy\" " +
		"default-init-method=\"afterPropertiesSet\" " +
		"xsi:schemaLocation=\"http://www.springframework.org/schema/beans " +
		"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\"> " +
		"</beans>";

	private static final String _EXPECTED_ERR_MSG_1 =
		"No bean named \'" +
			BeanReferenceAnnotationBeanPostProcessorTest.class.getName() + '\'';
	
	private static final String _EXPECTED_ERR_MSG_2 =
		"and PortalBeanLocator failed with: BeanLocator has not been set";

}
