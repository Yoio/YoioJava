/*
* Copyright 2015-2018 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.yoiojava.view.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.yoiojava.view.View;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class VelocityImpl implements View {
	public VelocityImpl() {
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		Template t = ve.getTemplate("hellovelocity.vm");
		VelocityContext context = new VelocityContext();
		context.put("name", "Liang");
//		StringWriter writer = new StringWriter();
//		t.merge(context, WebUtils.show(msg););
//		System.out.println(writer.toString());
	}

	/**
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void render() {
	}

	@Override
	public void assign(String alis, Object value) {
	}

}
