/*
 * Copyright 2017 L0G1C (David B) - logiclodge.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.test.core;

import com.example.test.dto.request.TestRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.example.test.dto.request.AddFriendRequest;
import com.example.test.dto.request.FriendsListRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author L0G1C (David B) <a
 *         href=https://github.com/Binary-L0G1C/java-unity-websocket-connector>
 *         https://github.com/Binary-L0G1C/java-unity-websocket-connector </a>
 */
@JsonTypeInfo( //
		use = JsonTypeInfo.Id.NAME, //
		include = JsonTypeInfo.As.PROPERTY, //
		property = "type", //
		// defaultImpl = Event.class, //
		visible = true)
@JsonSubTypes({ //
		@Type(value = FriendsListRequest.class, name = "FRIENDSLIST"), //
		@Type(value = AddFriendRequest.class, name = "ADDFRIEND"), //
		@Type(value = TestRequest.class, name = "TEST"),
})
@Schema
public abstract class ClientRequest {
	private RequestType type;

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
}