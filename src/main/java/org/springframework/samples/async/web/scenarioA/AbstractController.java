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
package org.springframework.samples.async.web.scenarioA;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes(value={"end"})
public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

 	private final long asyncTimeoutValue;


	public AbstractController(long asyncTimeoutValue) {
		this.asyncTimeoutValue = asyncTimeoutValue;
	}

	protected long getTimeoutInSeconds() {
		return this.asyncTimeoutValue * 1000;
	}


	@RequestMapping("/a")
	public String handleA(@RequestParam(required=false, defaultValue="viewName") String end, Model model, HttpServletResponse response) {
		model.addAttribute("end", end);
		logger.debug("\nresponse type {}", response.getClass().getName());
		return "forward:b";
	}

	@RequestMapping("/b")
	public String handleB(HttpServletResponse response) {
		logger.debug("\nresponse type {}", response.getClass().getName());
		return "forward:c";
	}

	// Sub-classes implement "/c"

	@ExceptionHandler
	@ResponseBody
	public String handleIllegalStateException(IllegalStateException ex) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>This content rendered via " + getMethodInfo("handleIllegalStateException", IllegalStateException.class));
		sb.append("<p>Message: \"" + ex.getMessage() + "\"");
		return sb.toString();
	}

	@RequestMapping("/d")
	public String handleD(@ModelAttribute("end") String end, SessionStatus status, HttpServletResponse response) {
		status.setComplete();
		logger.debug("\nresponse type {}", response.getClass().getName());
		return "forward:" + end;
	}

	@RequestMapping("/viewName")
	public String handleViewName() {
		return "page";
	}

	@RequestMapping(value="/responseBody", produces="application/json")
	@ResponseBody
	public Map<String, String> handleResponseBody() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Jad");
		return map;
	}

	String getMethodInfo(String methodName, Class<?>...argTypes) throws NoSuchMethodException {
		return " method [" + this.getClass().getMethod(methodName, argTypes).toString() + "]";
	}

}