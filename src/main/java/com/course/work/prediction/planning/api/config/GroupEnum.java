package com.course.work.prediction.planning.api.config;

public enum GroupEnum {
	FREE_USER("Free user"), PREMIUM_USER("Premium user");

	private final String groupName;

	private GroupEnum(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return groupName;
	}
}
