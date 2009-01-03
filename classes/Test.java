/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import org.apache.tools.ant.DirectoryScanner;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.RenderedOp;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.TiledImage;
import java.awt.color.ColorSpace;
import javax.media.jai.BorderExtender;
import java.awt.Point;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.FileLoadDescriptor;
import javax.media.jai.operator.LookupDescriptor;
import javax.media.jai.operator.MosaicDescriptor;
import javax.media.jai.operator.TranslateDescriptor;
import javax.media.jai.operator.BorderDescriptor;
import javax.media.jai.RasterFactory;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.ComponentColorModel;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.Transparency;
import javax.media.jai.PlanarImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * <a href="Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
		String basedir = "../portal-web/docroot/html/themes/_unstyled/images";

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setIncludes(new String[] {"**\\*.png"});

		ds.scan();

		List<RenderedImage> renderedImages = new ArrayList<RenderedImage>();

		String[] files = ds.getIncludedFiles();

		float x = 0;
		float y = 0;

		for (int i = 0; i < files.length; i++) {
			String file = files[i];

			RenderedOp renderedOp = FileLoadDescriptor.create(basedir + "/" + file, null, null, null);

			RenderedImage renderedImage = convert(renderedOp);

			renderedImage = TranslateDescriptor.create(renderedImage, x, y, null, null);

			if ((renderedImage.getHeight() <= 16) &&
				(renderedImage.getWidth() <= 16)) {

				renderedImages.add(renderedImage);

				y += renderedOp.getHeight();
			}
		}

		RenderedOp renderedOp = MosaicDescriptor.create(
			(RenderedImage[])renderedImages.toArray(new RenderedImage[renderedImages.size()]),
			MosaicDescriptor.MOSAIC_TYPE_OVERLAY, null, null, null, null, null);

		ImageIO.write(renderedOp, "png", new File("out.png"));
	}

	public static RenderedImage convert(RenderedOp renderedOp) throws IOException {
		ColorModel colorModel = renderedOp.getColorModel();

		SampleModel sampleModel = renderedOp.getSampleModel();

		if (colorModel instanceof IndexColorModel) {
			IndexColorModel indexColorModel = (IndexColorModel)colorModel;
			
			int mapSize = indexColorModel.getMapSize();
			
			byte[][] data = new byte[4][mapSize];
			
			indexColorModel.getReds(data[0]);
			indexColorModel.getGreens(data[1]);
			indexColorModel.getBlues(data[2]);
			indexColorModel.getAlphas(data[3]);

			LookupTableJAI lookupTableJAI = new LookupTableJAI(data);
			
			renderedOp = LookupDescriptor.create(renderedOp, lookupTableJAI, null);
		}

		// Grays with alpha

		else if (sampleModel.getNumBands() == 2) {
			int bandsCount = 4;
			int width = renderedOp.getWidth();
			int height = renderedOp.getHeight();

			Raster oldData = renderedOp.getData();
			DataBuffer oldDataBuffer = oldData.getDataBuffer();

			List<Byte> byteList = new ArrayList<Byte>(width * height * bandsCount);

			List<Byte> tempByteList = new ArrayList<Byte>(4);

			for (int i = 0; i < oldDataBuffer.getSize(); i++) {
				int mod = (i + 1) % 2;

				int elemPos = i;

				if (mod == 0) {
					tempByteList.add((byte)oldDataBuffer.getElem(elemPos - 1));
					tempByteList.add((byte)oldDataBuffer.getElem(elemPos - 1));
				}

				tempByteList.add((byte)oldDataBuffer.getElem(elemPos));

				if (mod == 0) {
					Collections.reverse(tempByteList);

					byteList.addAll(tempByteList);

					tempByteList.clear();
				}
			}

			byte[] data = new byte[byteList.size()];

			for (int i = 0; i < byteList.size(); i++) {
				data[i] = byteList.get(i);
			}

			DataBuffer dataBuffer = new DataBufferByte(data, data.length);

			SampleModel newSampleModel = RasterFactory.createPixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, width, height, bandsCount);
			ColorModel newColorModel = PlanarImage.createColorModel(newSampleModel);
			Raster raster = RasterFactory.createWritableRaster(newSampleModel, dataBuffer, new Point(0, 0));
	
			TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, newSampleModel, newColorModel);

			tiledImage.setData(raster);
			
			//JAI.create("filestore", tiledImage, "test.png", "PNG");

			//printImage(renderedOp);
			//printImage(tiledImage);

			return tiledImage;

			/*int bandsCount = 4;
			int width = renderedOp.getWidth();
			int height = renderedOp.getHeight();
			WritableRaster oldData = renderedOp.copyData();
			DataBufferByte dataBuffer = (DataBufferByte)oldData.getDataBuffer();
			byte[] data = dataBuffer.getData();
			byte[][] tmp = new byte[bandsCount][];
			tmp[0] = data;
			tmp[1] = data;
			tmp[2] = data;

			byte[] alphaBytes = new byte[data.length];

			for (int i = 0; i < alphaBytes.length; i++) {
				alphaBytes[i] = (byte)255;
			}

			tmp[3] = alphaBytes;
			dataBuffer = new DataBufferByte(tmp, data.length);

			SampleModel newSampleModel = RasterFactory.createPixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, width, height, bandsCount);
			ColorModel newColorModel = PlanarImage.createColorModel(newSampleModel);
			Raster raster = RasterFactory.createWritableRaster(newSampleModel, dataBuffer, new Point(0, 0));
	
			TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, newSampleModel, newColorModel);
			
			tiledImage.setData(raster);

			return tiledImage;*/
		}
		else if (colorModel.getTransparency() != Transparency.TRANSLUCENT) {
			int bandsCount = 4;
			int width = renderedOp.getWidth();
			int height = renderedOp.getHeight();

			Raster oldData = renderedOp.getData();
			DataBuffer oldDataBuffer = oldData.getDataBuffer();

			List<Byte> byteList = new ArrayList<Byte>(width * height * bandsCount);

			List<Byte> tempByteList = new ArrayList<Byte>(4);

			for (int i = 0; i < oldDataBuffer.getSize(); i++) {
				int mod = (i + 1) % 3;

				int elemPos = i;

				tempByteList.add((byte)oldDataBuffer.getElem(elemPos));

				if (mod == 0) {
					tempByteList.add((byte)255);

					Collections.reverse(tempByteList);

					byteList.addAll(tempByteList);

					tempByteList.clear();
				}
			}

			byte[] data = new byte[byteList.size()];

			for (int i = 0; i < byteList.size(); i++) {
				data[i] = byteList.get(i);
			}

			DataBuffer dataBuffer = new DataBufferByte(data, data.length);

			SampleModel newSampleModel = RasterFactory.createPixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, width, height, bandsCount);
			ColorModel newColorModel = PlanarImage.createColorModel(newSampleModel);
			Raster raster = RasterFactory.createWritableRaster(newSampleModel, dataBuffer, new Point(0, 0));
	
			TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, newSampleModel, newColorModel);
			
			tiledImage.setData(raster);
			
			//JAI.create("filestore", tiledImage, "test.png", "PNG");

			//printImage(renderedOp);
			//printImage(tiledImage);

			return tiledImage;
		}
		else {
			//printImage(renderedOp);
		}
		
		return renderedOp;
	}

	public static void printImage(PlanarImage pi) {
		int width = pi.getWidth();
		int height = pi.getHeight();
		SampleModel sm = pi.getSampleModel();
		int nbands = sm.getNumBands();
		Raster inputRaster = pi.getData();
		int[] pixels = new int[nbands*width*height];
		inputRaster.getPixels(0,0,width,height,pixels);
		int offset;
		for(int h=0;h<height;h++)
		 for(int w=0;w<width;w++)
		{
		offset = h*width*nbands+w*nbands;
		System.out.print("at ("+w+","+h+"): ");
		for(int band=0;band<nbands;band++)
		System.out.print(pixels[offset+band]+" ");
		System.out.println();
		}
	}

}