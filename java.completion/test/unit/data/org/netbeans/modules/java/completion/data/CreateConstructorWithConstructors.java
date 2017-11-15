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

package org.netbeans.modules.java.editor.completion.ElementCreatingJavaCompletionProviderTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

    private int time;
    private double something = 0.0;
    private String text = "";
    private List<String> list;

    public Test() {}
    public Test(int time, List<String> list) {}

    private static class X implements Runnable {

        private byte a;
        private Map<String, Set<String>> map1;
        private Map<String, Set<String>> map2 = new HashMap();

        public X() {}
        public X(byte a, Map<String, Set<String>> map1) {}

        public void run() {
        }

    }

}
