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
package org.netbeans.modules.cnd.makeproject.options;

import org.netbeans.modules.cnd.utils.NamedOption;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 */
@ServiceProvider(path=NamedOption.MAKE_PROJECT_CATEGORY, service=NamedOption.class, position=900)
public class RebuildPropsChanged extends NamedOption {
    public static final String REBUILD_PROP_CHANGED = "rebuildPropChanged"; // NOI18N

    @Override
    public String getName() {
        return REBUILD_PROP_CHANGED;
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(RebuildPropsChanged.class, "REBUILD_PROP_CHANGED_TXT"); //NOI18N
    }

    @Override
    public String getDescription() {
        return NbBundle.getMessage(RebuildPropsChanged.class, "REBUILD_PROP_CHANGED_AD"); //NOI18N
    }

    @Override
    public OptionKind getKind() {
        return OptionKind.Boolean;
    }

    @Override
    public Object getDefaultValue() {
        return false;
    }
}
