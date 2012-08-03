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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping(value="/deferredResultScenario", method=RequestMethod.GET)
public class DeferredResultController extends AbstractController {

	private DeferredResult<String> deferredResult;


	@Autowired
	public DeferredResultController(@Value("${asyncTimeout}") long asyncTimeoutValue) {
		super(asyncTimeoutValue);
	}


	@RequestMapping("/c")
	public DeferredResult<String> handleC(final @RequestParam(required = false) String timeoutValue) {

		this.deferredResult = (timeoutValue != null) ?
				new DeferredResult<String>(timeoutValue) : new DeferredResult<String>();

		return this.deferredResult;
	}

	@RequestMapping("/ping")
	@ResponseBody
	public String handlePing(final @RequestParam(value="deferredResultHint", required = false) String hint) throws Exception {

		Assert.notNull(this.deferredResult);

		// TODO: DeferredResult mechanism for processing an exception

		if ("timeout".equals(hint)) {
			Thread.sleep(getTimeoutInSeconds());
		}

		deferredResult.set("forward:d");

		deferredResult = null;

		return "Close this tab to see the results of the processing";
	}

}
