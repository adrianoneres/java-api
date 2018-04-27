package com.api.app.dto;

import java.util.List;

public class ListDTO<T> {
	
	private long total;
	
	private List<T> data;
	
	public ListDTO(long total, List<T> data) {
		this.total = total;
		this.data = data;
	}
	
	public long getTotal() {
		return this.total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> dados) {
		this.data = dados;
	}
}
