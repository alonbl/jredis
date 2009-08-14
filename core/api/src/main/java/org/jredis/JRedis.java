/*
 *   Copyright 2009 Joubin Houshyar
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *    
 *   http://www.apache.org/licenses/LICENSE-2.0
 *    
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.jredis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * <p>This is effectively a one to one mapping to Redis commands.  And that
 * is basically it.
 * <p>Beyond that , just be aware that an implementation may throw {@link ClientRuntimeException}
 * or an extension to report problems (typically connectivity) or features {@link NotSupportedException}
 * or bugs.  These are {@link RuntimeException}.
 * 
 * @author  joubin (alphazero@sensesay.net)
 * @version alpha.0, 04/02/09
 * @since   alpha.0
 * 
 */
public interface JRedis {
	
	// ------------------------------------------------------------------------
	// Semantic context methods
	// ------------------------------------------------------------------------

	// TODO: reach a decision on whether to include this or not.
//	/**
//	 * Provides for access to an interface providing standard Java collections
//	 * semantics on the specified parametric type.  
//	 * <p>
//	 * The type <code>T</code> can be of 3 categories:
//	 * <ol>
//	 * <li>It is 
//	 * </ol>
//	 * @param <T> a Java class type that you wish to perform {@link Set}, 
//	 * {@link List}, or {@link Map}, operations. 
//	 * @return the {@link JavaSemantics} for type <code>T</code>, if the type specified meets
//	 * the required initialization characteristics.
//	 */
//	public <T> JavaSemantics<T>  semantic (Class<T>  type) throws ClientRuntimeException;
	
	// ------------------------------------------------------------------------
	// Security and User Management
	// NOTE: Moved to ConnectionSpec
	// ------------------------------------------------------------------------

	
	// ------------------------------------------------------------------------
	// "Connection Handling"
	// ------------------------------------------------------------------------

	/**
	 * Ping redis
	 * @return true (unless not authorized)
	 * @throws RedisException (as of ver. 0.09) in case of unauthorized access
	 */
	public JRedis ping () throws RedisException;

	/**
	 * Disconnects the client.
	 * @Redis QUIT
	 */
	public void quit ();
	
	// ------------------------------------------------------------------------
	// "Commands operating on string values"
	// ------------------------------------------------------------------------

	/**
	 * Bind the value to key.  
	 * @Redis SET
	 * @param key any UTF-8 {@link String}
	 * @param value any bytes.  For current data size limitations, refer to
	 * Redis documentation.
	 * @throws RedisException on user error.
	 * @throws ProviderException on un-documented features/bug
	 * @throws ClientRuntimeException on errors due to operating environment (Redis or network)
	 */
	public void set (String key, byte[] value) throws RedisException;
	/**
	 * Convenient method for {@link String} data binding
	 * @Redis SET
	 * @param key
	 * @param stringValue
	 * @throws RedisException
	 * @see {@link JRedis#set(String, byte[])}
	 */
	public void set (String key, String stringValue) throws RedisException;
	/**
	 * Convenient method for {@link String} numeric values binding
	 * @Redis SET
	 * @param key
	 * @param numberValue
	 * @throws RedisException
	 * @see {@link JRedis#set(String, byte[])}
	 */
	public void set (String key, Number numberValue) throws RedisException;
	/**
	 * Binds the given java {@link Object} to the key.  Serialization format is
	 * implementation specific.  Simple implementations may apply the basic {@link Serializable}
	 * protocol.
	 * @Redis SET
	 * @param <T>
	 * @param key
	 * @param object
	 * @throws RedisException
	 * @see {@link JRedis#set(String, byte[])}
	 */
	public <T extends Serializable> 
		   void set (String key, T object) throws RedisException;

	/**
	 * @Redis SETNX
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisException
	 */
	public boolean setnx (String key, byte[] value) throws RedisException;
	public boolean setnx (String key, String stringValue) throws RedisException;
	public boolean setnx (String key, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   boolean setnx (String key, T object) throws RedisException;

	/**
	 * @Redis GET
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public byte[] get (String key)  throws RedisException;

	public byte[] getset (String key, byte[] value) throws RedisException;
	public byte[] getset (String key, String stringValue) throws RedisException;
	public byte[] getset (String key, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		byte[] getset (String key, T object) throws RedisException;

	
	/**
	 * @Redis MGET
	 * @param key
	 * @param moreKeys
	 * @return
	 * @throws RedisException
	 */
	public List<byte[]> mget(String key, String...moreKeys) throws RedisException;

	/**
	 * @Redis INCR
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public long incr (String key) throws RedisException;

	/**
	 * @Redis INCRBY
	 * @param key
	 * @param delta
	 * @return
	 * @throws RedisException
	 */
	public long incrby (String key, int delta) throws RedisException;

	/**
	 * @Redis DECR
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public long decr (String key) throws RedisException;

	/**
	 * @Redis DECRBY
	 * @param key
	 * @param delta
	 * @return
	 * @throws RedisException
	 */
	public long decrby (String key, int delta) throws RedisException;

	/**
	 * @Redis EXISTS
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public boolean exists(String key) throws RedisException;

	/**
	 * @Redis DEL
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public boolean del (String key) throws RedisException;

	/**
	 * @Redis TYPE
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public RedisType type (String key) throws RedisException;
	
	
	// ------------------------------------------------------------------------
	// "Commands operating on the key space"
	// ------------------------------------------------------------------------
	
	/**
	 * @Redis KEYS
	 * @param pattern
	 * @return
	 * @throws RedisException
	 */
	public List<String> keys (String pattern) throws RedisException;
	
	/**
	 * Convenience method.  Equivalent to calling <code>jredis.keys("*");</code>
	 * @Redis KEYS
	 * @return
	 * @throws RedisException
	 * @see {@link JRedis#keys(String)}
	 */
	public List<String> keys () throws RedisException;

	/**
	 * @Redis RANDOMKEY
	 * @return
	 * @throws RedisException
	 */
	public String randomkey() throws RedisException;
	
	/**
	 * @Redis RENAME
	 * @param oldkey
	 * @param newkey
	 * @throws RedisException
	 */
	public void rename (String oldkey, String newkey) throws RedisException;
	
	/**
	 * @Redis RENAMENX
	 * @param oldkey
	 * @param brandnewkey
	 * @return
	 * @throws RedisException
	 */
	public boolean renamenx (String oldkey, String brandnewkey) throws RedisException;
	
	/**
	 * @Redis DBSIZE
	 * @return
	 * @throws RedisException
	 */
	public long dbsize () throws RedisException;
	
	/**
	 * @Redis EXPIRE
	 * @param key
	 * @param ttlseconds
	 * @return
	 * @throws RedisException
	 */
	public boolean expire (String key, int ttlseconds) throws RedisException; 
	
	/**
	 * @Redis TTL
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public long ttl (String key) throws RedisException;
	
	// ------------------------------------------------------------------------
	// Commands operating on lists
	// ------------------------------------------------------------------------

	/**
	 * @Redis RPUSH
	 * @param listkey
	 * @param value
	 * @throws RedisException
	 */
	public void rpush (String listkey, byte[] value) throws RedisException;
	public void rpush (String listkey, String stringValue) throws RedisException;
	public void rpush (String listkey, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   void rpush (String listkey, T object) throws RedisException;
	
	/**
	 * @Redis LPUSH
	 * @param listkey
	 * @param value
	 * @throws RedisException
	 */
	public void lpush (String listkey, byte[] value) throws RedisException;
	public void lpush (String listkey, String stringValue) throws RedisException;
	public void lpush (String listkey, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   void lpush (String listkey, T object) throws RedisException;
	
	/**
	 * @Redis LSET
	 * @param key
	 * @param index
	 * @param value
	 * @throws RedisException
	 */
	public void lset (String key, long index, byte[] value) throws RedisException;
	public void lset (String key, long index, String stringValue) throws RedisException;
	public void lset (String key, long index, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   void lset (String key, long index, T object) throws RedisException;
	

	/**
	 * @Redis LREM
	 * @param listKey
	 * @param value
	 * @param count
	 * @return
	 * @throws RedisException
	 */
	public long lrem (String listKey, byte[] value,       int count) throws RedisException;
	public long lrem (String listKey, String stringValue, int count) throws RedisException;
	public long lrem (String listKey, Number numberValue, int count) throws RedisException;
	public <T extends Serializable> 
		   long lrem (String listKey, T object, int count) throws RedisException;
	
	/**
	 * Given a 'list' key, returns the number of items in the list.
	 * @Redis LLEN
	 * @param listkey
	 * @return
	 * @throws RedisException
	 */
	public long llen (String listkey) throws RedisException;
	
	/**
	 * @Redis LRANGE
	 * @param listkey
	 * @param from
	 * @param to
	 * @return
	 * @throws RedisException
	 */
	public List<byte[]> lrange (String listkey, long from, long to) throws RedisException; 

	/**
	 * @Redis LTRIM
	 * @param listkey
	 * @param keepFrom
	 * @param keepTo
	 * @throws RedisException
	 */
	public void ltrim (String listkey, long keepFrom, long keepTo) throws RedisException;
	
	/**
	 * @Redis LINDEX
	 * @param listkey
	 * @param index
	 * @return
	 * @throws RedisException
	 */
	public byte[] lindex (String listkey, long index) throws RedisException;
	
	/**
	 * @Redis LPOP
	 * @param listKey
	 * @return
	 * @throws RedisException
	 */
	public byte[] lpop (String listKey) throws RedisException;
	
	/**
	 * @Redis RPOP
	 * @param listKey
	 * @return
	 * @throws RedisException
	 */
	public byte[] rpop (String listKey) throws RedisException;

	// ------------------------------------------------------------------------
	// Commands operating on sets
	// ------------------------------------------------------------------------
	
	/**
	 * @Redis SADD
	 * @param setkey
	 * @param member
	 * @return
	 * @throws RedisException
	 */
	public boolean sadd (String setkey, byte[] member) throws RedisException;
	public boolean sadd (String setkey, String stringValue) throws RedisException;
	public boolean sadd (String setkey, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   boolean sadd (String setkey, T object) throws RedisException;

	/**
	 * @Redis SREM
	 * @param setKey
	 * @param member
	 * @return
	 * @throws RedisException
	 */
	public boolean srem (String setKey, byte[] member) throws RedisException;
	public boolean srem (String setKey, String stringValue) throws RedisException;
	public boolean srem (String setKey, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   boolean srem (String setKey, T object) throws RedisException;

	/**
	 * @Redis SISMEMBER
	 * @param setKey
	 * @param member
	 * @return
	 * @throws RedisException
	 */
	public boolean sismember (String setKey, byte[] member) throws RedisException;
	public boolean sismember (String setKey, String stringValue) throws RedisException;
	public boolean sismember (String setKey, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   boolean sismember (String setKey, T object) throws RedisException;
	
	/**
	 * @Redis SMOVE
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @return
	 * @throws RedisException
	 */
	public boolean smove (String srcKey, String destKey, byte[] member) throws RedisException;
	public boolean smove (String srcKey, String destKey, String stringValue) throws RedisException;
	public boolean smove (String srcKey, String destKey, Number numberValue) throws RedisException;
	public <T extends Serializable> 
		   boolean smove (String srcKey, String destKey, T object) throws RedisException;
	
	/**
	 * @Redis SCARD
	 * @param setKey
	 * @return
	 * @throws RedisException
	 */
	public long scard (String setKey) throws RedisException;	
	
	/**
	 * @Redis SINTER
	 * @param set1
	 * @param sets
	 * @return
	 * @throws RedisException
	 */
	public List<byte[]> sinter (String set1, String...sets) throws RedisException;
	/**
	 * @Redis SINTERSTORE
	 * @param destSetKey
	 * @param sets
	 * @throws RedisException
	 */
	public void sinterstore (String destSetKey, String...sets) throws RedisException;

	/**
	 * @Redis SUNION
	 * @param set1
	 * @param sets
	 * @return
	 * @throws RedisException
	 */
	public List<byte[]> sunion (String set1, String...sets) throws RedisException;
	
	/**
	 * @Redis SUNIONSTORE
	 * @param destSetKey
	 * @param sets
	 * @throws RedisException
	 */
	public void sunionstore (String destSetKey, String...sets) throws RedisException;

	/**
	 * @Redis SDIFF
	 * @param set1
	 * @param sets
	 * @return
	 * @throws RedisException
	 */
	public List<byte[]> sdiff (String set1, String...sets) throws RedisException;
	
	/**
	 * @Redis SDIFFSTORE
	 * @param destSetKey
	 * @param sets
	 * @throws RedisException
	 */
	public void sdiffstore (String destSetKey, String...sets) throws RedisException;

	/**
	 * @Redis SMEMBERS
	 * @param setkey
	 * @return
	 * @throws RedisException
	 */
	public List<byte[]> smembers (String setkey) throws RedisException;
	
	// ------------------------------------------------------------------------
	// Multiple databases handling commands
	// ------------------------------------------------------------------------
	
//	@Deprecated
//	public JRedis select (int index) throws RedisException;

	/**
	 * Flushes the db you selected when connecting to Redis server.  Typically,
	 * implementations will select db 0 on connecting if non was specified.  Remember
	 * that there is no roll-back.
	 * @Redis FLUSHDB
	 * @return
	 * @throws RedisException
	 */
	public JRedis flushdb () throws RedisException;

	/**
	 * Flushes all dbs in the connect Redis server, regardless of which db was selected
	 * on connect time.  Remember that there is no rollback.
	 * @Redis FLUSHALL
	 * @return
	 * @throws RedisException
	 */
	public JRedis flushall () throws RedisException;

	/**
	 * Moves the given key from the currently selected db to the one indicated
	 * by <code>dbIndex</code>.
	 * @Redis MOVE
	 * @param key
	 * @param dbIndex
	 * @return
	 * @throws RedisException
	 */
	public boolean move (String key, int dbIndex) throws RedisException;
	
	// ------------------------------------------------------------------------
	// Sorting
	// ------------------------------------------------------------------------
	
	/**
	 * Usage:
	 * <p>Usage:
	 * <p><code><pre>
	 * List<byte[]>  results = redis.sort("my-list-or-set-key").BY("weight*").LIMIT(1, 11).GET("object*").DESC().ALPHA().exec();
	 * for(byte[] item : results) {
	 *     // do something with item ..
	 *  }
	 * </pre></code>
	 * <p>Sort specification elements are all options.  You could simply say:
	 * <p><code><pre>
	 * List<byte[]>  results = redis.sort("my-list-or-set-key").exec();
	 * for(byte[] item : results) {
	 *     // do something with item ..
	 *  }
	 * </pre></code>
	 * <p>Sort specification elements are also can appear in any order -- the client implementation will send them to the server
	 * in the order expected by the protocol, although it is good form to specify the predicates in natural order:
	 * <p><code><pre>
	 * List<byte[]>  results = redis.sort("my-list-or-set-key").GET("object*").DESC().ALPHA().BY("weight*").LIMIT(1, 11).exec();
	 * for(byte[] item : results) {
	 *     // do something with item ..
	 *  }
	 * </pre></code>
	 * 
	 * @Redis SORT
	 */
	public Sort sort(String key);
	
	// ------------------------------------------------------------------------
	// Persistence control commands
	// ------------------------------------------------------------------------

	/**
	 * @Redis SAVE
	 * @throws RedisException
	 */
	public void save() throws RedisException;

	/**
	 * @Redis BGSAVE
	 * @throws RedisException
	 */
	public void bgsave () throws RedisException;

	/**
	 * @Redis LASTSAVE
	 * @return
	 * @throws RedisException
	 */
	public long lastsave () throws RedisException;

//	@Deprecated
//	public void shutdown () throws RedisException;

// ------------------------------------------------------------------------
// Remote server control commands
// ------------------------------------------------------------------------

	/**
	 * @Redis INFO
	 * @return
	 * @throws RedisException
	 */
	public Map<String, String>	info ()  throws RedisException;
}