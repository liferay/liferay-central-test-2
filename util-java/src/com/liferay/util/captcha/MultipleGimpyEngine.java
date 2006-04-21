/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util.captcha;

import com.octo.captcha.component.image.backgroundgenerator.AbstractBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.EllipseBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.MultipleShapeBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.AbstractFontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.AbstractTextPaster;
import com.octo.captcha.component.image.textpaster.DoubleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.Color;

import java.util.Locale;
import java.util.Random;

/**
 * <a href="MultipleGimpyEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MultipleGimpyEngine extends ImageCaptchaEngine {

	public MultipleGimpyEngine(WordGenerator wordGenerator, Integer width,
							   Integer height, Integer minFontSize,
							   Integer maxFontSize, Integer minWordLength,
							   Integer maxWordLength, Color textColor) {

		AbstractBackgroundGenerator[] backgrounds =
			new AbstractBackgroundGenerator[] {
				new EllipseBackgroundGenerator(width, height),
				new FunkyBackgroundGenerator(width, height),
				//new GradientBackgroundGenerator(width, height),
				new MultipleShapeBackgroundGenerator(width, height)
			};

		AbstractFontGenerator[] fonts = new AbstractFontGenerator[] {
			//new DeformedRandomFontGenerator(minFontSize, maxFontSize),
			new RandomFontGenerator(minFontSize, maxFontSize),
			new TwistedAndShearedRandomFontGenerator(minFontSize, maxFontSize),
			new TwistedRandomFontGenerator(minFontSize, maxFontSize)
		};

		AbstractTextPaster[] pasters = new AbstractTextPaster[] {
			new DoubleRandomTextPaster(minWordLength, maxWordLength, textColor),
			//new DoubleTextPaster(minWordLength, maxWordLength, textColor),
			new RandomTextPaster(minWordLength, maxWordLength, textColor),
			new SimpleTextPaster(minWordLength, maxWordLength, textColor)
		};

		int combinations = backgrounds.length * fonts.length * pasters.length;

		_factories = new ImageCaptchaFactory[combinations];

		for (int i = 0; i < combinations; i++) {
			int backgroundsPos = i / (fonts.length * pasters.length);

			int fontsPos = i / pasters.length;
			if (fontsPos >= fonts.length) {
				fontsPos = fontsPos % fonts.length;
			}

			int pastersPos = i;
			if (pastersPos >= pasters.length) {
				pastersPos = pastersPos % pasters.length;
			}

			WordToImage wordToImage = new ComposedWordToImage(
				fonts[fontsPos], backgrounds[backgroundsPos],
				pasters[pastersPos]);

			_factories[i] = new GimpyFactory(wordGenerator, wordToImage);
		}
	}

	public ImageCaptchaFactory getImageCaptchaFactory() {
        return _factories[_random.nextInt(_factories.length)];
	}

	public ImageCaptcha getNextImageCaptcha() {
		return getImageCaptchaFactory().getImageCaptcha();
	}

	public ImageCaptcha getNextImageCaptcha(Locale locale) {
		return getImageCaptchaFactory().getImageCaptcha(locale);
	}

	private ImageCaptchaFactory[] _factories;
	private Random _random = new Random();

}