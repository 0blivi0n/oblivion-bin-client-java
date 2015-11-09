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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.uiqui.oblivion.bin.client.error.CacheException;
import net.uiqui.oblivion.bin.client.model.Caches;
import net.uiqui.oblivion.bin.client.model.Nodes;
import net.uiqui.oblivion.bin.client.model.Server;
import net.uiqui.oblivion.mercury.Mercury;
import net.uiqui.oblivion.mercury.api.JSON;
import net.uiqui.oblivion.mercury.api.MercuryRequest;
import net.uiqui.oblivion.mercury.api.MercuryResponse;
import net.uiqui.oblivion.mercury.api.Proplist;
import net.uiqui.oblivion.mercury.api.Resource;

public class APIClient {
	private Mercury client = null;

	public APIClient(final String server, final int port, final int refreshInterval) {
		final OblibionCluster cluster = new OblibionCluster(this, server, port, refreshInterval);
		this.client = new Mercury(cluster);
	}

	public List<String> caches() throws IOException, CacheException {
		final String[] resource = Resource.build("caches");
		final Map<String, Object> params = Proplist.build("sort", true);
		final MercuryRequest request = MercuryRequest.build("GET", resource, params);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			final JSON json = response.payload();
			final List<JSON> caches = json.value("caches");
			return Caches.cacheNames(caches);
		} else {
			throw error(response);
		}
	}

	public void flush(final String cache) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys");
		final MercuryRequest request = MercuryRequest.build("DELETE", resource);
		final MercuryResponse response = call(request);

		if (response.status() != 202) {
			throw error(response);
		}
	}
	
	public long size(final String cache) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys");
		final MercuryRequest request = MercuryRequest.build("GET", resource);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			return response.payload();
		} else {
			throw error(response);
		}
	}	

	public List<String> keys(final String cache) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys");
		final Map<String, Object> params = Proplist.build("list", true);
		final MercuryRequest request = MercuryRequest.build("GET", resource, params);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			final JSON json = response.payload();
			return json.value("keys");
		} else {
			throw error(response);
		}
	}

	public long put(final String cache, final String key, final Object value) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys", key);
		final MercuryRequest request = MercuryRequest.build("PUT", resource, value);
		return store(request);
	}

	public long put(final String cache, final String key, final Object value, long version) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys", key);
		final Map<String, Object> params = Proplist.build("version", version);
		final MercuryRequest request = MercuryRequest.build("PUT", resource, params, value);
		return store(request);
	}

	private long store(final MercuryRequest request) throws IOException, CacheException {
		final MercuryResponse response = call(request);

		if (response.status() == 201) {
			final Map<String, Object> params = response.params();
			return (Long) params.get("version");
		} else {
			throw error(response);
		}
	}

	public void delete(final String cache, final String key) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys", key);
		final MercuryRequest request = MercuryRequest.build("DELETE", resource);
		remove(request);
	}

	public void delete(final String cache, final String key, long version) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys", key);
		final Map<String, Object> params = Proplist.build("version", version);
		final MercuryRequest request = MercuryRequest.build("DELETE", resource, params);
		remove(request);
	}

	private void remove(final MercuryRequest request) throws IOException, CacheException {
		final MercuryResponse response = call(request);

		if (response.status() != 200) {
			throw error(response);
		}
	}

	public long version(final String cache, final String key) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys", key);
		final MercuryRequest request = MercuryRequest.build("VERSION", resource);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			final Map<String, Object> params = response.params();
			return (Long) params.get("version");
		} else {
			throw error(response);
		}
	}

	public Response get(final String cache, final String key) throws IOException, CacheException {
		final String[] resource = Resource.build("caches", cache, "keys", key);
		final MercuryRequest request = MercuryRequest.build("GET", resource);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			final Map<String, Object> params = response.params();
			final Long version = (Long) params.get("version");
			
			return new Response(response.payload(), version);
		} else if (response.status() == 404) {
			return null;
		} else {
			throw error(response);
		}
	}
	
	protected List<Server> nodes() throws IOException, CacheException {
		final String[] resource = Resource.build("nodes");
		final MercuryRequest request = MercuryRequest.build("GET", resource);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			final JSON json = response.payload();
			final List<JSON> nodes =  json.value("nodes");
			return Nodes.onlineNodes(nodes);
		} else {
			throw error(response);
		}
	}	
	
	public String systemVersion() throws IOException, CacheException {
		final String[] resource = Resource.build("system");
		final MercuryRequest request = MercuryRequest.build("GET", resource);
		final MercuryResponse response = call(request);

		if (response.status() == 200) {
			final JSON json = response.payload();
			return json.value("version");
		} else {
			throw error(response);
		}
	}	
		
	private MercuryResponse call(final MercuryRequest request) throws IOException {
		try {
			return client.call(request);
		} catch (Exception e) {
			throw new IOException("Error executing remote call", e);
		}
	}
	
	private CacheException error(final MercuryResponse response) {
		final JSON json = response.payload();
		return CacheException.build(response.status(), json);
	}
}
