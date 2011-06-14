package com.bookpub.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import com.bookpub.image.model.ImageFileEntry;

public class IMImageService {

	/**
	 * @param args
	 * processes
	 * upload image - 
	 * 1) scale it to original one and save as part IM
	 * 2) scale it to 1:1
	 * 3) scale it to 2:1
	 * 4) scale it to 4:1 
	 * 5) scale it to 8:1
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String sourceFileName = "desert";
			//"demo";
		String sourceFileExtension = "jpg";
		
		IMImageService.convertImage(sourceFileName, sourceFileExtension);
	
	}
	
	public static void convertImage(
			String fileName, 
			String fileExtension ) 
		throws Exception {
		
		String fileLocation = 
			_DEFAULT_WORK_PATH + fileName + 
			_DEFAULT_IMAGE_EXTENSION_DOT + fileExtension;
		
		ImageFileEntry entry = getImageFileEntry(fileLocation);
		
		String targetFileLocation = 
			entry.getWorkPath() + 
			entry.getTargetFileName() + _DEFAULT_IMAGE_EXTENSION_DOT +
			entry.getTargetExtension();
		
		System.out.println(entry.toString());
		
		int maxDimension = entry.getWidth();
		
		if(maxDimension == 0)
			maxDimension = _DEFAULT_MAXIMUM_DIMENSION;
		
		// scale image
		resize(entry.getImPath(), 
				fileLocation, 
				targetFileLocation, 
				maxDimension);
		
		entry = getImageFileEntry(targetFileLocation);
		
		System.out.println(entry.toString());
		
		// crop image
		crop(entry.getImPath(), 
				targetFileLocation, 
				targetFileLocation, 
				_DEFAULT_WIDTH, 
				_DEFAULT_HEIGHT);
	}
	
	private static void crop(
			String imPath,
			String fileLocation, 
			String targetFileLocation,
			int width,
			int height ) 
		throws Exception {
		
		ConvertCmd cmd = new ConvertCmd();
		
		//cmd.setAsyncMode(true);
		cmd.setSearchPath(imPath);
		
		IMOperation op = new IMOperation();
		
		op.addImage(fileLocation);
		op.crop(width, height);
		op.addImage(targetFileLocation);

		// execute the operation
		cmd.run(op);
	}
	
	private static void resize(
			String imPath,
			String fileLocation, 
			String targetFileLocation,
			int maxDimension ) 
		throws Exception {
		
		ConvertCmd cmd = new ConvertCmd();
		
		//cmd.setAsyncMode(true);
		cmd.setSearchPath(imPath);
		
		IMOperation op = new IMOperation();
		
		op.addImage(fileLocation);
		op.resize(maxDimension);
		op.addImage(targetFileLocation);

		// execute the operation
		cmd.run(op);
	}
	
	private static ImageFileEntry getImageFileEntry(String fileLocation) {
		
		ImageFileEntry entry = new ImageFileEntry();
		
		entry.setImPath(_DEFAULT_IM_PATH);
		
		entry.setWorkPath(_DEFAULT_WORK_PATH);
		
		try {
			
			File file = new File(fileLocation);
			
			String fileName = file.getName();
			
			InputStream inputStream = new FileInputStream(file);
			
			byte[] bytes = loadBytesFromStream(inputStream);
			
			BufferedImage img = ImageIO.read(file);
			
			entry.setWidth(img.getWidth());
			entry.setHeight(img.getHeight());
			
			entry.setFileName(getFileNameWithoutExtension(fileName));
			entry.setFileExtension(getFileExtension(fileName));
			
			entry.setTargetFileName(getFileNameWithoutExtension(fileName));
			entry.setTargetExtension(_DEFAULT_IMAGE_EXTENSION_PNG);
			
			entry.setSize(bytes.length);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entry;
	}
	
	private static String getFileNameWithoutExtension(
			String fileName){
		
		int dotPos = fileName.lastIndexOf(_DEFAULT_IMAGE_EXTENSION_DOT);
        
		String name = fileName.substring(0, dotPos);
        
        return name;
	}
	
	private static String getFileExtension(
			String fileName){
		
		int dotPos = fileName.lastIndexOf(_DEFAULT_IMAGE_EXTENSION_DOT);
        
		String extension = fileName.substring(dotPos + 1);
        
        return extension;
	}
	
	protected static byte[] loadBytesFromStream(
			InputStream inputStream) {
		return loadBytesFromStream(inputStream, _DEFAULT_CHUNK_SIZE );
	}
	
	private static byte[] loadBytesFromStream(InputStream in, int chunkSize ) {
		
		byte[] thebytes = null;
		
		if( chunkSize < 1 )
			chunkSize = _DEFAULT_CHUNK_SIZE;
		
		int count;
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		byte[] b = new byte[chunkSize];
		
		try {
			while( ( count = in.read( b, 0, chunkSize ) ) > 0 )
				bo.write( b, 0, count );
			
			thebytes = bo.toByteArray();
			
		} catch (Exception e) {
			
		}
		
		try {
			bo.close();
			bo = null;
		} catch (Exception e) {
			
		}
		
		return thebytes;
	}
	
	// chunk size
	private static int _DEFAULT_CHUNK_SIZE = 256;
	
	protected static final int _DEFAULT_MAXIMUM_DIMENSION = 640;
	
	// pixel
	protected static final int _DEFAULT_WIDTH = 128;
	// pixel
	protected static final int _DEFAULT_HEIGHT = 128;
	// pixel
	protected static final int _DEFAULT_MINIMUN_DPI = 300;
	
	protected static final String _DEFAULT_IMAGE_EXTENSION_PNG = "png";
	
	protected static final String _DEFAULT_IMAGE_EXTENSION_JPG = "jpg";
	
	protected static final String _DEFAULT_IMAGE_EXTENSION_DOT = ".";
	
	protected static final String _DEFAULT_WORK_PATH = 
		"C:/Liferay-Portal/Demo/data/ocr/image/";
	
	protected static final String _DEFAULT_IM_PATH = 
		"C:/Liferay-Portal/Demo/native/ImageMagic-win64/ImageMagick-6.6.9-Q16";

}
