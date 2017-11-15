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

package org.netbeans.modules.db.metadata.model.spi;

import org.netbeans.modules.db.metadata.model.MetadataAccessor;
import org.netbeans.modules.db.metadata.model.api.Column;
import org.netbeans.modules.db.metadata.model.api.Index;
import org.netbeans.modules.db.metadata.model.api.IndexColumn;
import org.netbeans.modules.db.metadata.model.api.Ordering;

/**
 *
 * @author David Van Couvering
 */
public abstract class IndexColumnImplementation {
    private IndexColumn column;

    public final IndexColumn getIndexColumn() {
        if (column == null) {
            column = MetadataAccessor.getDefault().createIndexColumn(this);
        }
        return column;
    }

    public abstract Column getColumn();

    public abstract int getPosition();

    public abstract Ordering getOrdering();

    public abstract String getName();

    public abstract Index getParent();
}
