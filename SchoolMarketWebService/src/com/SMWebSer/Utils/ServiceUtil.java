package com.SMWebSer.Utils;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.SMWebSer.service.Service;

public class ServiceUtil {

	private ApplicationContext applicationContext;
	private Service service;
	private ServletContext servletContext;
	
	public ServiceUtil(ServletContext servletContext){
		this.servletContext = servletContext;
	}
	
	public Service getServiceBean(){
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		service = (Service) applicationContext.getBean("service");
		return service;
	}

}
