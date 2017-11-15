/**
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

package org.netbeans.modules.autoupdate.services;

import java.io.IOException;
import org.netbeans.api.autoupdate.OperationException;

/**
 *
 * @author Jiri Rechtacek
 */
public class TargetClusterWhenGlobalUndeclaredTest extends TargetClusterTestCase {
    
    public TargetClusterWhenGlobalUndeclaredTest (String testName) {
        super (testName);
    }
    
    @Override
    protected String getCodeName (String target, Boolean global) {
        return "org.yourorghere.testupdatemodule";
    }
    
    public void testUpdateGlobalUndeclared () throws IOException, OperationException {
        // Otherwise (no cluster name specified), if marked global, maybe put it into an "extra" cluster
        assertEquals ("Goes into extraDir", "extra", getTargetCluster (null, Boolean.TRUE).getName ());
    }
    
}
