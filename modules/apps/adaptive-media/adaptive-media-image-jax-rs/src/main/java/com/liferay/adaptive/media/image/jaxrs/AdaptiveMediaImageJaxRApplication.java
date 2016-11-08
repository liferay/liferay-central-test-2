package com.liferay.adaptive.media.image.jaxrs;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@ApplicationPath("/")
@Component(immediate = true, service = Application.class)
public class AdaptiveMediaImageJaxRApplication extends Application {

	private @Reference
	AdaptiveMediaImageRootResource _adaptiveMediaImageRootResource;

	public Set<Object> getSingletons() {
		return Collections.singleton(_adaptiveMediaImageRootResource);
	}

}