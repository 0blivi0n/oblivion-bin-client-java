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
import net.uiqui.oblivion.bin.client.api.Response;
import net.uiqui.oblivion.bin.client.error.CacheException;

public class CacheContext<T> {
	private String cache = null;
	private APIClient apiClient = null;

	protected CacheContext(final String cache, final APIClient apiClient) {
		this.cache = cache;
		this.apiClient = apiClient;
	}

	public long version(final Object key) throws IOException, CacheException {
		final String keyStr = key.toString();
		return apiClient.version(cache, keyStr);
	}		

	@SuppressWarnings("unchecked")
	public T get(final Object key) throws IOException, CacheException {
		final String keyStr = key.toString();
		final Response value = apiClient.get(cache, keyStr);
		
		if (value == null) {
			return null;
		}
		
		return (T) value.getContent();
	}	

	@SuppressWarnings("unchecked")
	public Value<T> getValue(final Object key) throws IOException, CacheException {
		final String keyStr = key.toString();
		final Response value = apiClient.get(cache, keyStr);
		
		if (value == null) {
			return null;
		}
		
		return new Value<T>((T) value.getContent(), value.getVersion());
	}	

	public long put(final Object key, final T value) throws IOException, CacheException {
		final String keyStr = key.toString();
		return apiClient.put(cache, keyStr, value);
	}	

	public long put(final Object key, final T value, long version) throws IOException, CacheException {
		final String keyStr = key.toString();
		return apiClient.put(cache, keyStr, value, version);
	}	

	public void delete(final Object key) throws IOException, CacheException {
		final String keyStr = key.toString();
		apiClient.delete(cache, keyStr);
	}	

	public void delete(final Object key, long version) throws IOException, CacheException {
		final String keyStr = key.toString();
		apiClient.delete(cache, keyStr, version);
	}	

	public List<String> keys() throws IOException, CacheException {
		return apiClient.keys(cache);
	}

	public void flush() throws IOException, CacheException {
		apiClient.flush(cache);
	}

	public long size() throws IOException, CacheException {
		return apiClient.size(cache);
	}
}
