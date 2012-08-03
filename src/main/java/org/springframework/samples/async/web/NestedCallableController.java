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
package org.springframework.samples.async.web;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/nestedCallableScenario", method=RequestMethod.GET)
public class NestedCallableController {

	private int count;


	@RequestMapping
	public Callable<Object> handleA() {

		return new Callable<Object>() {

			public Object call() throws Exception {

				if (count >= 3) {
					count = 0;
					return "page";
				}
				else {
					Thread.sleep(4000);
					count++;
					return this;
				}
			}
		};
	}

}
