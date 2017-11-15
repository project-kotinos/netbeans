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

package org.netbeans.modules.db.explorer.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.netbeans.api.db.explorer.node.NodeProvider;
import org.netbeans.api.db.explorer.node.NodeProviderFactory;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.metadata.model.api.Action;
import org.netbeans.modules.db.metadata.model.api.ForeignKey;
import org.netbeans.modules.db.metadata.model.api.ForeignKeyColumn;
import org.netbeans.modules.db.metadata.model.api.Metadata;
import org.netbeans.modules.db.metadata.model.api.MetadataElementHandle;
import org.netbeans.modules.db.metadata.model.api.MetadataModel;
import org.netbeans.modules.db.metadata.model.api.MetadataModelException;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Rob Englander
 */
public class ForeignKeyColumnNodeProvider extends NodeProvider {

    // lazy initialization holder class idiom for static fields is used
    // for retrieving the factory
    public static NodeProviderFactory getFactory() {
        return FactoryHolder.FACTORY;
    }

    private static class FactoryHolder {
        static final NodeProviderFactory FACTORY = new NodeProviderFactory() {
            @Override
            public ForeignKeyColumnNodeProvider createInstance(Lookup lookup) {
                ForeignKeyColumnNodeProvider provider = new ForeignKeyColumnNodeProvider(lookup);
                return provider;
            }
        };
    }

    private final DatabaseConnection connection;
    private final MetadataElementHandle<ForeignKey> handle;

    private ForeignKeyColumnNodeProvider(Lookup lookup) {
        super(lookup, foreignKeyColumnComparator);
        connection = getLookup().lookup(DatabaseConnection.class);
        handle = getLookup().lookup(MetadataElementHandle.class);
    }

    @Override
    protected synchronized void initialize() {
        final List<Node> newList = new ArrayList<>();
        
        boolean connected = connection.isConnected();
        MetadataModel metaDataModel = connection.getMetadataModel();
        if (connected && metaDataModel != null) {
            try {
                metaDataModel.runReadAction(
                    new Action<Metadata>() {
                        @Override
                        public void run(Metadata metaData) {
                            ForeignKey key = handle.resolve(metaData);
                            if (key != null) {
                                Collection<ForeignKeyColumn> columns = key.getColumns();
                                for (ForeignKeyColumn column : columns) {
                                    MetadataElementHandle<ForeignKeyColumn> h = MetadataElementHandle.create(column);
                                    Collection<Node> matches = getNodes(h);
                                    if (matches.size() > 0) {
                                        newList.addAll(matches);
                                    } else {
                                        NodeDataLookup lookup = new NodeDataLookup();
                                        lookup.add(connection);
                                        lookup.add(h);

                                        newList.add(ForeignKeyColumnNode.create(lookup, ForeignKeyColumnNodeProvider.this));
                                    }
                                }
                            }
                        }
                    }
                );
            } catch (MetadataModelException e) {
                NodeRegistry.handleMetadataModelException(this.getClass(), connection, e, true);
            }
        }

        setNodes(newList);
    }

    private static final Comparator<Node> foreignKeyColumnComparator = new Comparator<Node>() {
        @Override
        public int compare(Node node1, Node node2) {
            ForeignKeyColumnNode n1 = (ForeignKeyColumnNode)node1;
            ForeignKeyColumnNode n2 = (ForeignKeyColumnNode)node2;
            int result = 1;
            if (n1.getPosition() < n2.getPosition()) {
                result = -1;
            }
            return result;
        }
    };
}
