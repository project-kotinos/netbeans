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

package org.netbeans.junit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Behaviour of logging with respect to console output.
 *
 * @author Jaroslav Tulach
 */
public class StdOutNoLogTest extends NbTestCase {
    private static final ByteArrayOutputStream os = new ByteArrayOutputStream();
    static {
        PrintStream ps = new PrintStream(os);
        System.setErr(ps);
        System.setOut(ps);
    }

    private Logger log;
    
    public StdOutNoLogTest(String testName) {
        super(testName);
    }

    @Override
    protected Level logLevel() {
        return Level.WARNING;
    }
    
    @Override
    protected void setUp() throws Exception {
        os.reset();

        log = Logger.getLogger(getName());
    }


    public void testNoConsoleOnLevelWARNING() {
        log.warning("Ahoj");
        if (os.toString().indexOf("Ahoj") != -1) {
            fail("Should log: " + os);
        }
    }
}

