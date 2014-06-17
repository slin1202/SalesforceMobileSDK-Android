/*
 * Copyright (c) 2014, salesforce.com, inc.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of salesforce.com, inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of salesforce.com, inc.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.salesforce.androidsdk.sobjectsdk.manager;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.test.InstrumentationTestCase;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.auth.HttpAccess;
import com.salesforce.androidsdk.auth.OAuth2;
import com.salesforce.androidsdk.auth.OAuth2.TokenEndpointResponse;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestClient.ClientInfo;
import com.salesforce.androidsdk.sobjectsdk.TestCredentials;
import com.salesforce.androidsdk.sobjectsdk.TestForceApp;
import com.salesforce.androidsdk.sobjectsdk.manager.CacheManager.CachePolicy;
import com.salesforce.androidsdk.sobjectsdk.model.SalesforceObject;
import com.salesforce.androidsdk.sobjectsdk.model.SalesforceObjectType;
import com.salesforce.androidsdk.sobjectsdk.model.SalesforceObjectTypeLayout;
import com.salesforce.androidsdk.util.EventsListenerQueue;
import com.salesforce.androidsdk.util.EventsObservable.EventType;

/**
 * Test class for MetadataManager.
 *
 * @author bhariharan
 */
public class MetadataManagerTest extends InstrumentationTestCase {

	private static final String ACCOUNT = "Account";
	private static final String CASE = "Case";
	private static final String OPPORTUNITY = "Opportunity";
	private static final long REFRESH_INTERVAL = 24 * 60 * 60 * 1000;

    private Context targetContext;
    private EventsListenerQueue eq;
    private MetadataManager metadataManager;
    private HttpAccess httpAccess;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        targetContext = getInstrumentation().getTargetContext();
        final Application app = Instrumentation.newApplication(TestForceApp.class,
        		targetContext);
        getInstrumentation().callApplicationOnCreate(app);
        TestCredentials.init(getInstrumentation().getContext());
        eq = new EventsListenerQueue();
        if (SalesforceSDKManager.getInstance() == null) {
            eq.waitForEvent(EventType.AppCreateComplete, 5000);
        }
    	MetadataManager.reset();
    	CacheManager.hardReset();
        metadataManager = MetadataManager.getInstance(initRestClient());
    }

    @Override
    public void tearDown() throws Exception {
        if (eq != null) {
            eq.tearDown();
            eq = null;
        }
    	httpAccess.resetNetwork();
    	MetadataManager.reset();
    	CacheManager.hardReset();
        super.tearDown();
    }

    /**
     * Test for 'loadSmartScopeObjectTypes' (from the server).
     */
    public void testLoadSmartScopeTypesFromServer() {
    	final List<SalesforceObjectType> smartScopes = metadataManager.loadSmartScopeObjectTypes(CachePolicy.RELOAD_AND_RETURN_CACHE_DATA,
    			REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for global 'loadMRUObjects' (from the server).
     */
    public void testLoadGlobalMRUObjectsFromServer() {
    	final List<SalesforceObject> mruObjects = metadataManager.loadMRUObjects(null,
    			50, CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for account 'loadMRUObjects' (from the server).
     */
    public void testLoadAccountMRUObjectsFromServer() {
    	final List<SalesforceObject> mruObjects = metadataManager.loadMRUObjects(ACCOUNT,
    			50, CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for case 'loadMRUObjects' (from the server).
     */
    public void testLoadCaseMRUObjectsFromServer() {
    	final List<SalesforceObject> mruObjects = metadataManager.loadMRUObjects(CASE,
    			50, CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for 'loadAllObjectTypes' (from the server).
     */
    public void testLoadAllObjectTypesFromServer() {
    	final List<SalesforceObjectType> objectTypes = metadataManager.loadAllObjectTypes(CachePolicy.RELOAD_AND_RETURN_CACHE_DATA,
    			REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for account 'loadObjectType' (from the server).
     */
    public void testLoadAccountObjectTypeFromServer() {
    	final SalesforceObjectType account = metadataManager.loadObjectType(ACCOUNT,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for opportunity 'loadObjectType' (from the server).
     */
    public void testLoadOpportunityObjectTypeFromServer() {
    	final SalesforceObjectType opportunity = metadataManager.loadObjectType(OPPORTUNITY,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for account, case, opportunity 'loadObjectTypesLayout' (from the server).
     */
    public void testLoadAccountObjectTypeLayoutFromServer() {
    	final List<String> objectTypeNames = new ArrayList<String>();
    	objectTypeNames.add(ACCOUNT);
    	objectTypeNames.add(CASE);
    	objectTypeNames.add(OPPORTUNITY);
    	final List<SalesforceObjectType> objectTypes = metadataManager.loadObjectTypes(objectTypeNames,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	final List<SalesforceObjectTypeLayout> objectLayouts = metadataManager.loadObjectTypesLayout(objectTypes,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: assert against static data.
    	 */
    }

    /**
     * Test for 'loadSmartScopeObjectTypes' (from the cache).
     */
    public void testLoadSmartScopeTypesFromCache() {
    	final List<SalesforceObjectType> smartScopes = metadataManager.loadSmartScopeObjectTypes(CachePolicy.RELOAD_AND_RETURN_CACHE_DATA,
    			REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final List<SalesforceObjectType> cachedSmartScopes = metadataManager.loadSmartScopeObjectTypes(CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD,
    			REFRESH_INTERVAL);
    }

    /**
     * Test for global 'loadMRUObjects' (from the cache).
     */
    public void testLoadGlobalMRUObjectsFromCache() {
    	final List<SalesforceObject> mruObjects = metadataManager.loadMRUObjects(null,
    			50, CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final List<SalesforceObject> cachedMruObjects = metadataManager.loadMRUObjects(null,
    			50, CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD, REFRESH_INTERVAL);
    }

    /**
     * Test for account 'loadMRUObjects' (from the cache).
     */
    public void testLoadAccountMRUObjectsFromCache() {
    	final List<SalesforceObject> mruObjects = metadataManager.loadMRUObjects(ACCOUNT,
    			50, CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final List<SalesforceObject> cachedMruObjects = metadataManager.loadMRUObjects(ACCOUNT,
    			50, CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD, REFRESH_INTERVAL);
    }

    /**
     * Test for case 'loadMRUObjects' (from the cache).
     */
    public void testLoadCaseMRUObjectsFromCache() {
    	final List<SalesforceObject> mruObjects = metadataManager.loadMRUObjects(CASE,
    			50, CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final List<SalesforceObject> cachedMruObjects = metadataManager.loadMRUObjects(CASE,
    			50, CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD, REFRESH_INTERVAL);
    }

    /**
     * Test for 'loadAllObjectTypes' (from the cache).
     */
    public void testLoadAllObjectTypesFromCache() {
    	final List<SalesforceObjectType> objectTypes = metadataManager.loadAllObjectTypes(CachePolicy.RELOAD_AND_RETURN_CACHE_DATA,
    			REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final List<SalesforceObjectType> cachedObjectTypes = metadataManager.loadAllObjectTypes(CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD,
    			REFRESH_INTERVAL);
    }

    /**
     * Test for account 'loadObjectType' (from the cache).
     */
    public void testLoadAccountObjectTypeFromCache() {
    	final SalesforceObjectType account = metadataManager.loadObjectType(ACCOUNT,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final SalesforceObjectType cachedAccount = metadataManager.loadObjectType(ACCOUNT,
    			CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD, REFRESH_INTERVAL);
    }

    /**
     * Test for opportunity 'loadObjectType' (from the cache).
     */
    public void testLoadOpportunityObjectTypeFromCache() {
    	final SalesforceObjectType opportunity = metadataManager.loadObjectType(OPPORTUNITY,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final SalesforceObjectType cachedOpportunity = metadataManager.loadObjectType(OPPORTUNITY,
    			CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD, REFRESH_INTERVAL);
    }

    /**
     * Test for account, case, opportunity 'loadObjectTypesLayout' (from the cache).
     */
    public void testLoadAccountObjectTypeLayoutFromCache() {
    	final List<String> objectTypeNames = new ArrayList<String>();
    	objectTypeNames.add(ACCOUNT);
    	objectTypeNames.add(CASE);
    	objectTypeNames.add(OPPORTUNITY);
    	final List<SalesforceObjectType> objectTypes = metadataManager.loadObjectTypes(objectTypeNames,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	final List<SalesforceObjectTypeLayout> objectLayouts = metadataManager.loadObjectTypesLayout(objectTypes,
    			CachePolicy.RELOAD_AND_RETURN_CACHE_DATA, REFRESH_INTERVAL);
    	/*
    	 * TODO: Turn off network and assert between live and cached data.
    	 */
    	final List<SalesforceObjectTypeLayout> cachedObjectLayouts = metadataManager.loadObjectTypesLayout(objectTypes,
    			CachePolicy.RETURN_CACHE_DATA_DONT_RELOAD, REFRESH_INTERVAL);
    }

    private RestClient initRestClient() throws Exception {
        httpAccess = new HttpAccess(null, null);
        final TokenEndpointResponse refreshResponse = OAuth2.refreshAuthToken(httpAccess,
        		new URI(TestCredentials.INSTANCE_URL), TestCredentials.CLIENT_ID,
        		TestCredentials.REFRESH_TOKEN);
        final String authToken = refreshResponse.authToken;
        final ClientInfo clientInfo = new ClientInfo(TestCredentials.CLIENT_ID,
        		new URI(TestCredentials.INSTANCE_URL),
        		new URI(TestCredentials.LOGIN_URL),
        		new URI(TestCredentials.IDENTITY_URL),
        		TestCredentials.ACCOUNT_NAME, TestCredentials.USERNAME,
        		TestCredentials.USER_ID, TestCredentials.ORG_ID, null, null);
        return new RestClient(clientInfo, authToken, httpAccess, null);
    }
}
