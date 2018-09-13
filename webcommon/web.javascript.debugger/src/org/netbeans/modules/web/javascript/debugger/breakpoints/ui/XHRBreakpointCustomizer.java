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
package org.netbeans.modules.web.javascript.debugger.breakpoints.ui;

import java.beans.PropertyChangeListener;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.modules.web.javascript.debugger.breakpoints.XHRBreakpoint;
import org.netbeans.spi.debugger.ui.Controller;
import org.openide.util.HelpCtx;

/**
 *
 * @author Martin Entlicher
 */
public class XHRBreakpointCustomizer extends javax.swing.JPanel implements ControllerProvider, HelpCtx.Provider {

    private final XHRBreakpoint xb;
    private boolean createBreakpoint;
    private final CustomizerController controller;
    
    private static XHRBreakpoint createBreakpoint() {
        return new XHRBreakpoint("");
    }
    
    /**
     * Creates new form XHRBreakpointCustomizer
     */
    public XHRBreakpointCustomizer() {
        this(createBreakpoint());
        createBreakpoint = true;
    }
    
    /**
     * Creates new form XHRBreakpointCustomizer
     */
    public XHRBreakpointCustomizer(XHRBreakpoint xb) {
        this.xb = xb;
        initComponents();
        controller = new CustomizerController();
        urlSubstringTextField.setText(xb.getUrlSubstring());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        urlSubstringLabel = new javax.swing.JLabel();
        urlSubstringTextField = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(urlSubstringLabel, org.openide.util.NbBundle.getMessage(XHRBreakpointCustomizer.class, "XHRBreakpointCustomizer.urlSubstringLabel.text")); // NOI18N

        urlSubstringTextField.setText(org.openide.util.NbBundle.getMessage(XHRBreakpointCustomizer.class, "XHRBreakpointCustomizer.urlSubstringTextField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(urlSubstringLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlSubstringTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlSubstringLabel)
                    .addComponent(urlSubstringTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel urlSubstringLabel;
    private javax.swing.JTextField urlSubstringTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new org.openide.util.HelpCtx("NetbeansDebuggerXHRBreakpointJavaScript"); // NOI18N
    }
    
    private class CustomizerController implements Controller {
        
        @Override
        public boolean ok() {
            xb.setUrlSubstring(urlSubstringTextField.getText()); 
            if (createBreakpoint) {
                DebuggerManager.getDebuggerManager().addBreakpoint(xb);
            }
            return true;
        }

        @Override
        public boolean cancel() {
            return true;
        }

        @Override
        public boolean isValid() {
            return true;
        }
        
        @Override
        public void addPropertyChangeListener(PropertyChangeListener l) {
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener l) {
        }
        
    }
}