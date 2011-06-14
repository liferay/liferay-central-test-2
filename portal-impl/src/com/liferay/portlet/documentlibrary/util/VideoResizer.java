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
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

/**
 * @author Juan González
 * @author Sergio González
 */
public class VideoResizer extends MediaToolAdapter {
	public VideoResizer(Integer aHeight, Integer aWidth) {
		_height = aHeight;
		_width = aWidth;
	}

	@Override
	public void onAudioSamples(IAudioSamplesEvent event) {
		IAudioSamples samples = event.getAudioSamples();

		if (_resampler == null) {
			_resampler = IAudioResampler.make(
				samples.getChannels(), samples.getChannels(), 44100,
				samples.getSampleRate());
		}

		if (event.getAudioSamples().getNumSamples() > 0) {
			IAudioSamples out = IAudioSamples.make(
				samples.getNumSamples(), samples.getChannels());

			_resampler.resample(out, samples, samples.getNumSamples());

			AudioSamplesEvent asc = new AudioSamplesEvent(
				event.getSource(), out, event.getStreamIndex());

			super.onAudioSamples(asc);

			out.delete();
		}
	}

	@Override
	public void onVideoPicture(IVideoPictureEvent event) {
		IVideoPicture pic = event.getPicture();

		if (_videoResampler == null) {
			_videoResampler = IVideoResampler.make(
				_width, _height, pic.getPixelType(), pic.getWidth(),
				pic.getHeight(), pic.getPixelType());
		}

		IVideoPicture out = IVideoPicture.make(
			pic.getPixelType(), _width, _height);

		_videoResampler.resample(out, pic);

		IVideoPictureEvent asc = new VideoPictureEvent(
			event.getSource(), out, event.getStreamIndex());

		super.onVideoPicture(asc);

		out.delete();
	}

	private IVideoResampler _videoResampler = null;
	private IAudioResampler _resampler = null;
	private int _height;
	private int _width;

}