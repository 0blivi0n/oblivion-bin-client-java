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
package net.uiqui.oblivion.bin.client;

import java.io.Serializable;

public class Value<T> implements Serializable {
	private static final long serialVersionUID = -5104244319327255679L;

	private long version = 0;
	private T content = null;

	protected Value(final T content, final long version) {
		this.content = content;
		this.version = version;
	}

	public long version() {
		return version;
	}

	public T content() {
		return content;
	}

	@Override
	public String toString() {
		return "Value[content=" + content + ", version=" + version + "]";
	}
}
