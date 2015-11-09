/*
 * 0blivi0n-cache
 * ==============
 * Java BIN Client
 * 
 * Copyright (C) 2015 Joaquim Rocha <jrocha@gmailbox.org>
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.uiqui.oblivion.bin.client.api;

public class Response {
	private long version = 0;
	private Object content = null;
	
	public Response(final Object content, final long version) {
		this.content = content;
		this.version = version;
	}

	public long version() {
		return version;
	}

	public Object content() {
		return content;
	}
}
