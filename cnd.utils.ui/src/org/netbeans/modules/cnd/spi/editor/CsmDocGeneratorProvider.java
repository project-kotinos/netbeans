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

package org.netbeans.modules.cnd.spi.editor;

import java.util.List;
import javax.swing.text.Document;
import org.openide.util.Lookup;

/**
 *
 */
public abstract class CsmDocGeneratorProvider {
    private static final CsmDocGeneratorProvider DEFAULT = new Default();

    public static CsmDocGeneratorProvider getDefault(){
        return DEFAULT;
    }

    protected CsmDocGeneratorProvider() {
    }

    public abstract Function getFunction(Document doc, int position);

    public static interface Function {
        String getName();
        String getSignature();
        String getReturnType();
        List<Parameter> getParametes();
    }

    public static interface Parameter {
        String getType();
        String getName();
    }

    private static final class Default extends CsmDocGeneratorProvider {
        private final Lookup.Result<CsmDocGeneratorProvider> res;
        Default() {
            res = Lookup.getDefault().lookupResult(CsmDocGeneratorProvider.class);
        }

        private CsmDocGeneratorProvider getService(){
            for (CsmDocGeneratorProvider selector : res.allInstances()) {
                return selector;
            }
            return null;
        }

        @Override
        public Function getFunction(Document doc, int position) {
            CsmDocGeneratorProvider provider = getService();
            if (provider != null) {
                return provider.getFunction(doc, position);
            }
            return null;
        }
    }
}
