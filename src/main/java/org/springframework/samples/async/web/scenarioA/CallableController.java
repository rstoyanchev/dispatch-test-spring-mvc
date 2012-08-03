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

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
@RequestMapping(value="/callableScenario", method=RequestMethod.GET)
public class CallableController extends AbstractController {


	@Autowired
	public CallableController(@Value("${asyncTimeout}") long asyncTimeoutValue) {
		super(asyncTimeoutValue);
	}


	@RequestMapping("/c")
	public Callable<String> handleC(final @RequestParam(value="callableHint", required = false) String hint) {

		return new Callable<String>() {

			public String call() throws Exception {

				Thread.sleep(1000);

				Assert.notNull(LocaleContextHolder.getLocale(), "Locale not available");
				Assert.notNull(RequestContextHolder.getRequestAttributes(), "Request context not available");

				if (IllegalStateException.class.getSimpleName().equals(hint)) {
					throw new IllegalStateException("Exception raised by " + getMethodInfo("handleC", String.class));
				}
				else if (IllegalArgumentException.class.getSimpleName().equals(hint)){
					throw new IllegalArgumentException("Exception raised by " + getMethodInfo("handleC", String.class));
				}
				else if ("timeout".equals(hint)) {
					Thread.sleep(getTimeoutInSeconds() * 1000);
				}

				return ("redirect".equals(hint)) ? "redirect:d" : "forward:d";
			}
		};
	}

}
