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
package org.netbeans.modules.css.model.impl;

import org.netbeans.modules.css.lib.api.Node;
import org.netbeans.modules.css.model.api.AtRule;
import org.netbeans.modules.css.model.api.BodyItem;
import org.netbeans.modules.css.model.api.Element;
import org.netbeans.modules.css.model.api.Model;
import org.netbeans.modules.css.model.api.Rule;

/**
 * Temporary solution for not yet implemented models for | media | page |
 * counterStyle | fontFace | moz_document
 *
 * @author marekfukala
 */
public class BodyItemI extends ModelElement implements BodyItem {

    private Element element;

    private final ModelElementListener elementListener = new ModelElementListener.Adapter() {

        @Override
        public void elementAdded(Rule rule) {
            element = rule;
        }

        @Override
        public void elementAdded(AtRule atRule) {
            element = atRule;
        }
        
    };

    public BodyItemI(Model model) {
        super(model);
    }

    public BodyItemI(Model model, Node node) {
        super(model, node);
        initChildrenElements();
    }
    
    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public void setElement(Element element) {
        super.setElement(element);
    }

    @Override
    protected ModelElementListener getElementListener() {
        return elementListener;
    }
    
      @Override
    protected Class getModelClass() {
        return BodyItem.class;
    }
    
}
