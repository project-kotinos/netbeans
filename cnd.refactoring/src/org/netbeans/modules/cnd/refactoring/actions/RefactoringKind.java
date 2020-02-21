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
package org.netbeans.modules.cnd.refactoring.actions;

/**
 *
 */
public enum RefactoringKind {
    CHANGE_FUNCTION_PARAMETERS("change-function-parameters"), //NOI18N
    ENCAPSULATE_FIELDS("encapsulate-fields"), //NOI18N

    CREATE_VARIABLE("introduce-variable"), //NOI18N
    CREATE_CONSTANT("introduce-constant"), //NOI18N
    CREATE_FIELD("introduce-field"), //NOI18N
    CREATE_METHOD("introduce-method"), //NOI18N
    CREATE_PARAMETER("introduce-parameter"), //NOI18N
    
    INLINE_REFACTORING("inline-refactoring"); //NOI18N

    private final String key; //Action ID
    private RefactoringKind(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
