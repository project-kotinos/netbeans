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
package org.netbeans.modules.maven.embedder.impl;

import java.util.List;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.PluginResolutionException;
import org.apache.maven.plugin.internal.DefaultPluginDependenciesResolver;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.WorkspaceReader;

/**
 *
 * @author mkleint
 */
public class NbPluginDependenciesResolver extends DefaultPluginDependenciesResolver {

    @Override
    public Artifact resolve(Plugin plugin, List<RemoteRepository> repositories, RepositorySystemSession session) throws PluginResolutionException {
        WorkspaceReader wr = session.getWorkspaceReader();
        NbWorkspaceReader nbwr = null;
        if (wr instanceof NbWorkspaceReader) {
            nbwr = (NbWorkspaceReader)wr;
            //this only works reliably because the NbWorkspaceReader is part of the session, not a component
            nbwr.silence();
        }
        try {
            return super.resolve(plugin, repositories, session);
        } finally {
            if (nbwr != null) {
                nbwr.normal();
            }
        }
    }

    @Override
    public DependencyNode resolve(Plugin plugin, Artifact pluginArtifact, DependencyFilter dependencyFilter, List<RemoteRepository> repositories, RepositorySystemSession session) throws PluginResolutionException {
        
        WorkspaceReader wr = session.getWorkspaceReader();
        NbWorkspaceReader nbwr = null;
        if (wr instanceof NbWorkspaceReader) {
            nbwr = (NbWorkspaceReader)wr;
            //this only works reliably because the NbWorkspaceReader is part of the session, not a component
            nbwr.silence();
        }
        try {
            return super.resolve(plugin, pluginArtifact, dependencyFilter, repositories, session);
        } finally {
            if (nbwr != null) {
                nbwr.normal();
            }
        }
    }
    
}
