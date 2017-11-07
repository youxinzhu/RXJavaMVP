package com.yxz.mylibrary.entity;

/**
 * @author yxz
 *	api返回
 * @param <T>
 */
public class HttpResult2<T> {
    private int stateCode;//状态码
    private String stateInfo;//信息
    private T data;
    
	public HttpResult2() {
		super();
	}
	public HttpResult2(int stateCode, String stateInfo, T data) {
		super();
		this.stateCode = stateCode;
		this.stateInfo = stateInfo;
		this.data = data;
	}
	public int getstateCode() {
		return stateCode;
	}
	public void setstateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public String getstateInfo() {
		return stateInfo;
	}
	public void setstateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "HttpResult [stateCode=" + stateCode + ", stateInfo="
				+ stateInfo + ", data=" + data + "]";
	}
    
}
