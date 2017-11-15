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

package org.netbeans.modules.quicksearch;

import org.netbeans.junit.NbTestCase;
import org.netbeans.junit.RandomlyFails;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Dafe Simonek
 */
public class SlowProviderTest extends NbTestCase {
    
    private static final int MAX_TIME = 1000;
    private static final int WAIT_TIME = 4000;

    public SlowProviderTest(String testName) {
        super(testName);
    }

    @RandomlyFails
    public void testResponsiveness () throws Exception {
        UnitTestUtils.prepareTest(new String [] { "/org/netbeans/modules/quicksearch/resources/testSlowProvider.xml" });

        System.out.println("Testing resposiveness against slow providers...");
        
        long startTime = System.currentTimeMillis();
        
        org.openide.util.Task t =
                CommandEvaluator.evaluate("sample text", ResultsModel.getInstance());
        
        long endTime = System.currentTimeMillis();

        assertFalse("Evaluator is slower then expected, max allowed time is " +
                MAX_TIME + " millis, but was " + (endTime - startTime) + " millis.",
                (endTime - startTime) > 1000);

        System.out.println("Testing CommandEvaluator.Wait4AllTask...");
        RequestProcessor.getDefault().post(t);

        // should be still running
        assertFalse(t.isFinished());

        // wait for all providers
        t.waitFinished();

        long waitTime = System.currentTimeMillis();

        assertTrue("Waiting for slow providers doesn't work, waited " +
                (waitTime - startTime) + " millis, but should at least " +
                WAIT_TIME + " millis.", (waitTime - startTime) > WAIT_TIME);


    }

    
    public static class SlowProvider implements SearchProvider {

        public void evaluate(SearchRequest request, SearchResponse response) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println("SlowProvider interrupted...");
            }
        }
        
    }

}
