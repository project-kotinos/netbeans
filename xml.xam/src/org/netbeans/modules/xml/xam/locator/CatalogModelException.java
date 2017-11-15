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

package org.netbeans.modules.xml.xam.locator;

/**
 *
 * @author girix
 */
public class CatalogModelException extends Exception {
    static final long serialVersionUID = 19800701l;
    /**
     * Creates a new instance of <code>CatalogModelException</code> without detail message.
     */
    public CatalogModelException() {
    }
    
    
    /**
     * Constructs an instance of <code>CatalogModelException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public CatalogModelException(String msg) {
        super(msg);
    }
    
    public CatalogModelException(Throwable cause){
        super();
        this.initCause(cause);
    }
}
