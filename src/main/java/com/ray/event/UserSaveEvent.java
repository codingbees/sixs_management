package com.ray.event;

import net.dreamlu.event.core.ApplicationEvent;

@SuppressWarnings("rawtypes")
public class UserSaveEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 754483501649396L;
	@SuppressWarnings("unchecked")
	public UserSaveEvent(Object source) {
		super(source);
	}
}
