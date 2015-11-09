oblivion-bin-client
===================

##Introduction

  0blivi0n's binary API client for JAVA applications.
  
  
##Instalation

Maven dependency:
 
 ```xml
<dependency>
    <groupId>org.oblivion-cache</groupId>
    <artifactId>oblivion-bin-client</artifactId>
    <version>0.5.1</version>
</dependency>
 ```
##Examples
 
### Connection setup

 ```java
Oblivion oblivion = Oblivion.builder()
		.server("uiqui.net")
		.build(); // uses default port
	
// Obtain server's version
System.out.println("Oblivion-cache " + oblivion.version());
 ```
 
### Cache setup

 ```java
// We can obtain the list of caches existing on the server
List<String> cacheList = oblivion.caches();
		
// Cache "docs" stores json documents
CacheContext<JSON> docCache = oblivion.newCacheContext("docs");

// Cache "longs" stores numbers
CacheContext<Long> longCache = oblivion.newCacheContext("longs");

// Cache "lists" stores arrays of strings
CacheContext<List<String>> listCache = oblivion.newCacheContext("lists");
 ```
 
### Store data

 ```java
JSON jrocha = new JSON()
		.field("id", 1)
		.field("name", "Joaquim Rocha")
		.field("languages", Arrays.asList("Erlang", "JAVA", "Ruby"));

// PUT method returns cache entry version
long jrVersion = docCache.put("jrocha", jrocha);

// Keys are automatically converted to string
longCache.put(1L, 1L);

// Any string can be used as a key
long previousVersion = ...;
listCache.put("nice programing languages", Arrays.asList("Erlang", "JAVA", "Ruby"), previousVersion);
 ```  
 
### Retrieve data

 ```java
JSON jrocha = docCache.get("jrocha");

// We can retrieve a Value object with the cache entry content and it's version
Value<Long> l2 = longCache.getValue(2L);
System.out.println("content: " + l2.content());
System.out.println("version: " + l2.version());

// We can retieve the cache entry version alone using the version() method
long nplVersion = listCache.version("nice programing languages");

// We can obtain the size of the cache using the size() method
long size = docCache.size();

// And obtain the list of all keys stored in the cache using the keys() method
List<String> longKeys = longCache.keys();
 ```  
 
### Delete data

 ```java
// Delete entry if entry didn't changed
docCache.delete(jrocha, jrVersion);

longCache.delete(1L);

// Deletes all cache entrys
listCache.flush();
 ```   

##License
[Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
