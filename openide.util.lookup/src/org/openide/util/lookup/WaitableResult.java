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
package org.openide.util.lookup;

import java.util.Collection;
import org.openide.util.Lookup;


/** A special subclass of lookup that is able to wait before queries.
 *
 * @author  Jaroslav Tulach
 */
abstract class WaitableResult<T> extends Lookup.Result<T> {
    /** Used by proxy results to synchronize before lookup.
     */
    protected abstract void beforeLookup(Lookup.Template t);

    /** Needed to group notification of outside the package listeners
     * after all AbstractLookup and ProxyLookups have been updated.
     * @param evAndListeners LookupEvent, LookupListener, LookupEvent, LookupListener, etc.
     */
    protected abstract void collectFires(Collection<Object> evAndListeners);

    protected abstract Collection<? extends Object> allInstances(boolean callBeforeLookup);
    protected abstract Collection<? extends Lookup.Item<T>> allItems(boolean callBeforeLookup);
}
