package com.ray.event;

import net.dreamlu.event.core.EventListener;

public class UserSaveListener {

	@EventListener
	public void sysout1(UserSaveEvent event) {
		System.out.println("sysout1:"+event.getSource());
	}
	
	@EventListener
	public void sysout2(UserSaveEvent event) {
		System.out.println("sysout2:"+event.getSource());
	}
}
