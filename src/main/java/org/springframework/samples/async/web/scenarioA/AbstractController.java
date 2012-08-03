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

 	private final long asyncTimeoutValue;


	public AbstractController(long asyncTimeoutValue) {
		this.asyncTimeoutValue = asyncTimeoutValue;
	}

	protected long getTimeoutInSeconds() {
		return this.asyncTimeoutValue * 1000;
	}


	@RequestMapping("/a")
	public String handleA(@RequestParam(required=false, defaultValue="viewName") String end, Model model) {
		model.addAttribute("end", end);
		return "forward:b";
	}

	@RequestMapping("/b")
	public String handleB() {
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
	public String handleD(@ModelAttribute("end") String end, SessionStatus status) {
		status.setComplete();
		return "forward:" + end;
	}

	@RequestMapping("/viewName")
	public String handleViewName() {
		return "page";
	}

	@RequestMapping("/responseBody")
	@ResponseBody
	public String handleResponseBody() throws Exception {
		return "This content rendered via " + getMethodInfo("handleResponseBody");
	}

	String getMethodInfo(String methodName, Class<?>...argTypes) throws NoSuchMethodException {
		return " method [" + this.getClass().getMethod(methodName, argTypes).toString() + "]";
	}

}