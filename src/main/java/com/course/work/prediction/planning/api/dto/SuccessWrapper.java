package com.course.work.prediction.planning.api.dto;

public class SuccessWrapper<T> {
	private String code = "200";
	private T data;

	public SuccessWrapper(T object) {
		this.data = object;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(T object) {
		this.data = object;
	}

	@Override
	public String toString() {
		return "SuccessWrapper [code=" + code + ", data=" + data + "]";
	}

}
