/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.async.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		FilterRegistration.Dynamic registration;

		EnumSet<DispatcherType> allDispatcherTypes =
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC);

//		registration = servletContext.addFilter("urlRewriteFilter", UrlRewriteFilter.class);
//		registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
//		registration.setInitParameter("logLevel", "DEBUG");
//		registration.setAsyncSupported(true);

		registration = servletContext.addFilter("osivFilter", OpenSessionInViewFilter.class);
		registration.addMappingForServletNames(allDispatcherTypes, false, "main");
		registration.setAsyncSupported(true);

		registration = servletContext.addFilter("requestLoggingFilter", CommonsRequestLoggingFilter.class);
		registration.addMappingForServletNames(allDispatcherTypes, false, "main");
		registration.setAsyncSupported(true);

		super.onStartup(servletContext);
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfig.class };
	}

	@Override
	protected String getServletName() {
		return "main";
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/main/*" };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setAsyncSupported(true);
	}

}
