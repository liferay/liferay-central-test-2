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

import com.xuggle.mediatool.IMediaCoder;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * @author Juan González
 * @author Sergio González
 */
public class VideoListener extends MediaToolAdapter {
	public VideoListener(Integer aHeight, Integer aWidth) {
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

		if (_resampler != null && samples.getNumSamples() > 0) {
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
	public void onAddStream(IAddStreamEvent event) {
		IMediaCoder mediaCoder = event.getSource();

		IContainer container = mediaCoder.getContainer();

		int streamIndex = event.getStreamIndex();

		IStream stream = container.getStream(streamIndex);

		IStreamCoder streamCoder = stream.getStreamCoder();

		if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
			streamCoder.setSampleRate(44100);
		}
		else if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
			streamCoder.setHeight(_height);
			streamCoder.setWidth(_width);
		}

		super.onAddStream(event);
	}

	private IAudioResampler _resampler = null;

	private int _height;
	private int _width;

}