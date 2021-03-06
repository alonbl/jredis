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

package org.jredis.ri.alphazero;

import static org.testng.Assert.fail;
import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;
import org.jredis.ri.alphazero.support.Log;
import org.testng.annotations.AfterTest;

/**
 * As of now, this class simply runs the same set of {@link JRedis} contract
 * compliance using {@link JRedisService} as the provider.
 * 
 * TODO: figure out a good way to meaningfully test service (e.g. concurrent
 * and random method usage ..)
 *
 * @author  Joubin Houshyar (alphazero@sensesay.net)
 * @version alpha.0, Nov 6, 2009
 * @since   alpha.0
 * 
 */
//@Test(sequential = true, suiteName="JRedisPipelineService-tests")
//public class JRedisPipelineServiceTest extends JRedisProviderTestsBase {
public class JRedisPipelineServiceTest extends ConcurrentJRedisProviderTestsBase {

	// ------------------------------------------------------------------------
	// TEST SETUP 
	// ------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see org.jredis.ri.ProviderTestBase#newProviderInstance()
	 */
	@Override
	protected JRedis newProviderInstance () {
		JRedis provider = null;
		try {
			ConnectionSpec connectionSpec = DefaultConnectionSpec.newSpec(this.host, this.port, this.db2, this.password.getBytes());
			provider = new JRedisPipelineService(connectionSpec);
        }
        catch (ClientRuntimeException e) {
        	Log.error(e.getLocalizedMessage());
        }
        return provider;
	}
	// ------------------------------------------------------------------------
	// The Tests
	// ========================================================= JRedisClient
	/**
	 * We define and run any additional, provider specific tests here.  The
	 * basic generally applicable JRedis interface method test are defined 
	 * in the super class.
	 * 
	 * Here we test Quit in a post test method to insure all tests have been
	 * completed.
	 */
	// ------------------------------------------------------------------------
	
	/**
	 * Test method for {@link org.jredis.ri.alphazero.JRedisSupport#auth(java.lang.String)}.
	 */
	@AfterTest
	public void testQuit() {
		Log.log("TEST: QUIT command ");
		try {
			JRedis service = getProviderInstance();
			service.quit ();
		} 
		catch (Exception e) {
			fail("QUIT" + e);
		}
	}
}
