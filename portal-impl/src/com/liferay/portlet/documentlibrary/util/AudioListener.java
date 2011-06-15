/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.util;

import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;

/**
 * @author Juan González
 * @author Sergio González
 */
public class AudioListener extends MediaToolAdapter {

	public void onAudioSamples(IAudioSamplesEvent iAudioSamplesEvent) {
		IAudioSamples iaudioSamples = iAudioSamplesEvent.getAudioSamples();

		if (_iAudioResampler == null) {
			_iAudioResampler = IAudioResampler.make(
				iaudioSamples.getChannels(), iaudioSamples.getChannels(),
				44100, iaudioSamples.getSampleRate());
		}

		IAudioSamples iAudioSamples = iAudioSamplesEvent.getAudioSamples();

		if (iAudioSamples.getNumSamples() > 0) {
			IAudioSamples resampledIAudioSamples = IAudioSamples.make(
				iaudioSamples.getNumSamples(), iaudioSamples.getChannels());

			_iAudioResampler.resample(
				resampledIAudioSamples, iaudioSamples,
				iaudioSamples.getNumSamples());

			AudioSamplesEvent audioSamplesEvent = new AudioSamplesEvent(
				iAudioSamplesEvent.getSource(), resampledIAudioSamples,
				iAudioSamplesEvent.getStreamIndex());

			super.onAudioSamples(audioSamplesEvent);

			resampledIAudioSamples.delete();
		}
	}

	private IAudioResampler _iAudioResampler;

}