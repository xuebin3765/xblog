package com.xblog.commons.response;
/**
 * 分页类
 */
public class RespPage {

	private int start; // 每页开始的索引号

	private int size;//页码大小

	//总记录数
	private int totalItems;
	
	public RespPage(int pageIndex, int pageSize){

		if(pageSize < 1) pageSize = 20;
		if(pageIndex < 1) pageIndex = 1;
		this.size = pageSize ;
		this.start = (pageIndex-1) * pageSize;

	}

	public int getStart() {
		return start;
	}

	public int getSize() {
		return size;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
}
