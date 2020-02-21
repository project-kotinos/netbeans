/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.remote.test;

import junit.framework.Test;
import org.netbeans.junit.NbTestSuite;
import org.netbeans.modules.remote.impl.fs.RemoteFSTCKTestCase;
import org.openide.filesystems.URLMapperTestHidden;

/**
 *
 */
public class RemoteFSTCKUrlMapperTest extends RemoteFSTCKTestCase {
   
    public RemoteFSTCKUrlMapperTest(Test test) {
        super(test);
    }
    
    public static Test suite() {
        NbTestSuite suite = new NbTestSuite();
        suite.addTestSuite(URLMapperTestHiddenEx.class);
        return new RemoteFSTCKUrlMapperTest(suite);
    }    

    public static class URLMapperTestHiddenEx extends URLMapperTestHidden {

        public URLMapperTestHiddenEx(String name) {
            super(name);
        }

        @Override
        protected void setUp() throws Exception {
            RemoteTestSuiteBase.registerTestSetup(this);
            super.setUp();
        }        
        
        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
            RemoteTestSuiteBase.registerTestTearDown(this);
        }
    }
}
