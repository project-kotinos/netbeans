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

package org.netbeans.modules.cnd.editor.fortran;

import org.netbeans.editor.Acceptor;
import org.netbeans.editor.AcceptorFactory;

/**
 * Extended settings for Fortran.
 */
public class FSettingsFactory {

    public static final int MAXIMUM_TEXT_WIDTH = 132;

    public static Acceptor getDefaultAbbrevResetAcceptor() {
        return AcceptorFactory.NON_JAVA_IDENTIFIER;
    }

    public static Acceptor getDefaultIdentifierAcceptor() {
        return AcceptorFactory.JAVA_IDENTIFIER;
    }

    // public no-arg for creating from services
    public FSettingsFactory() {
    }
}
