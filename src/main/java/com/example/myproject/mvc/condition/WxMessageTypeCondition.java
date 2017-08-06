/*
 * Copyright 2002-2016 the original author or authors.
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

package com.example.myproject.mvc.condition;

import com.example.myproject.module.WxRequest;
import com.example.myproject.module.event.WxEvent;
import com.example.myproject.module.message.WxMessage;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A logical disjunction (' || ') request condition that matches a request
 * against a set of {@link RequestMethod}s.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
public final class WxMessageTypeCondition extends AbstractWxEnumCondition<WxMessage.Type> {


	public WxMessageTypeCondition(WxMessage.Type... types) {
		super(types);
	}

	protected WxMessageTypeCondition(Collection<WxMessage.Type> types) {
		super(Collections.unmodifiableSet(new LinkedHashSet<>(types)));
	}

	/**
	 * Returns a new instance with a union of the HTTP request types
	 * from "this" and the "other" instance.
	 */
	@Override
	public WxMessageTypeCondition combine(AbstractWxEnumCondition other) {
		Set<WxMessage.Type> set = new LinkedHashSet(this.enums);
		set.addAll(other.enums);
		return new WxMessageTypeCondition(set);
	}

	@Override
	protected WxMessageTypeCondition matchEnum(WxRequest wxRequest) {
		WxMessage.Type wxMessageType = wxRequest.getMessageType();
		if (getEnums().contains(wxMessageType)) {
			return new WxMessageTypeCondition(wxMessageType);
		}
		return null;
	}

}
