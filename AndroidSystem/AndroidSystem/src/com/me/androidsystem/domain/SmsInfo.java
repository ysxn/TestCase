package com.me.androidsystem.domain;

public class SmsInfo {
	private String linkman;//联系人
	private String number;//联系号码
	private String content;
	private String date;
	private String type;//联系类型
	public SmsInfo(){}
	public SmsInfo(String linkman, String number, String content, String date,
			String type) {
		this.linkman = linkman;
		this.number = number;
		this.content = content;
		this.date = date;
		this.type = type;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
