package com.SMWebSer.Domain;

//客户端响应返回的信息
public class CallBack {
	//响应的代码
	/*
	 * 200:登录成功    201:注册成功   202：商品清单          203：商品订单
	 * 400:登录失败    401:注册失败   402：商品清单为空   403：订单为空 
	 */
	private int status;
	//信息
	private String message;
	//json数据
	private Object data;
	
	public CallBack(){}
	
	public CallBack(int status,String message,Object data){
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
