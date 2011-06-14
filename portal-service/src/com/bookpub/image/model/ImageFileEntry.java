package com.bookpub.image.model;

import java.io.Serializable;

public class ImageFileEntry implements Serializable {

	private static final long serialVersionUID = -6242814664073117930L;
	
	public String getImPath() {
		return imPath;
	}

	public void setImPath(String imPath) {
		this.imPath = imPath;
	}

	public String getWorkPath() {
		return workPath;
	}

	public void setWorkPath(String workPath) {
		this.workPath = workPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public String getTargetExtension() {
		return targetExtension;
	}

	public void setTargetExtension(String targetExtension) {
		this.targetExtension = targetExtension;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getScaleType() {
		return scaleType;
	}

	public void setScaleType(int scaleType) {
		this.scaleType = scaleType;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString(){
		
		String string = "[IMPath=" + imPath +
			"]\n[workPath=" + workPath +
			"]\n[fileName=" + fileName + 
			"]\n[fileExtension=" + fileExtension + 
			"]\n[targetFileName=" + targetFileName +
			"]\n[targetExtension=" + targetFileName +
			"]\n[scaleType=" + scaleType +
			"]\n[W=" + width + " H=" + height +
			"]\n[Rows=" + rows + " Columns=" + columns +
			"]\n[Total=" + total + " Size=" + size +
			"]";
		
		return string;
	}
	
	String imPath;
	
	String workPath;

	String fileName;
	
	String fileExtension;
	
	String targetFileName;
	
	String targetExtension;
	
	int width;
	
	int height;
	
	int scaleType;
	
	int rows;
	
	int columns;
	
	int total;
	
	int size;

}
