package com.liferay.adaptive.media.image.configuration;

import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public interface ImageAdaptiveMediaConfigurationEntry {

	public String getName();

	public Map<String, String> getProperties();

	public String getUUID();

}