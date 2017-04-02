package com.course.work.prediction.planning.api.config;

import java.util.Date;

public class TokenInfo {
	private Date expiretionDate;
	private GroupEnum groupEnum;
	private Long userId;

	public TokenInfo(Date expiretionDate, GroupEnum groupEnum, Long userId) {
		super();
		this.expiretionDate = expiretionDate;
		this.groupEnum = groupEnum;
		this.userId = userId;
	}

	public TokenInfo() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpiretionDate() {
		return expiretionDate;
	}

	public void setExpiretionDate(Date expiretionDate) {
		this.expiretionDate = expiretionDate;
	}

	public GroupEnum getGroupEnum() {
		return groupEnum;
	}

	public void setGroupEnum(GroupEnum groupEnum) {
		this.groupEnum = groupEnum;
	}

	@Override
	public String toString() {
		return "TokenInfo [expiretionDate=" + expiretionDate + ", groupEnum=" + groupEnum + ", userId=" + userId + "]";
	}

}
