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
package net.uiqui.oblivion.bin.client.model;

import java.util.ArrayList;
import java.util.List;

import net.uiqui.oblivion.mercury.api.JSON;

public class Nodes {
	public static List<Server> getOnlineNodes(final List<JSON> nodes) {
		List<Server> ret = new ArrayList<Server>();
		
		for (JSON node : nodes) {
			if (node.value("online")) {
				String server = node.value("server");
				Long port = node.value("port");
				ret.add(new Server(server, port.intValue()));
			}
		}
		
		return ret;
	}
}
