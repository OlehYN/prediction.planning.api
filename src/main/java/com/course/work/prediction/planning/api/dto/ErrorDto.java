package com.course.work.prediction.planning.api.dto;

public class ErrorDto {
	private String errorCode;
	private int code;

	public ErrorDto(String errorCode, int code) {
		super();
		this.errorCode = errorCode;
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ErrorDto [errorCode=" + errorCode + ", code=" + code + "]";
	}

}
