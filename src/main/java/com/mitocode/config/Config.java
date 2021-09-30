package com.mitocode.config;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class Config extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		return super.getClasses();
	}
}
