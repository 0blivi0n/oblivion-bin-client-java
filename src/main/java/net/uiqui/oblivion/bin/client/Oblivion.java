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

import java.io.IOException;
import java.util.List;

import net.uiqui.oblivion.bin.client.api.APIClient;
import net.uiqui.oblivion.bin.client.error.CacheException;

public class Oblivion {
	private APIClient apiClient = null;
	
	private Oblivion(final Builder builder) {
		this.apiClient = new APIClient(builder.server, builder.port, builder.refreshInterval);
	}

	public <X> CacheContext<X> newCacheContext(final String cache) {
		return new CacheContext<X>(cache, apiClient);
	}

	public List<String> caches() throws IOException, CacheException {
		return apiClient.caches();
	}

	public String version() throws IOException, CacheException {
		return apiClient.systemVersion();
	}	

	public static class Builder {
		private String server = "localhost";
		private int port = 12521; 
		private int refreshInterval = 60000;

		public Builder server(final String server) {
			this.server = server;
			return this;
		}

		public Builder port(final int port) {
			this.port = port;
			return this;
		}

		public Builder refreshInterval(final int refreshInterval) {
			this.refreshInterval = refreshInterval;
			return this;
		}		

		public Oblivion build() {
			return new Oblivion(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
