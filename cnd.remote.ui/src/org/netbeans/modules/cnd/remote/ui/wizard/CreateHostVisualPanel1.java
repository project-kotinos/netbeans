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
package org.netbeans.modules.cnd.remote.ui.wizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.cnd.remote.ui.networkneighbour.NetworkNeighbourhoodList;
import org.openide.util.NbBundle;

/*
 * package
 */ final class CreateHostVisualPanel1 extends CreateHostVisualPanelBase {

    private final NetworkNeighbourhoodList networkNeighbourhoodList;
    private final ChangeListener listener;
    private final CreateHostData data;

    public CreateHostVisualPanel1(CreateHostData data, final ChangeListener listener) {
        this.data = data;
        this.listener = listener;

        initComponents();
        lblUser.setVisible(data.isManagingUser());
        textUser.setVisible(data.isManagingUser());

        proxySettingsPanel.setVisible(false);
        cbProxySettings.setSelected(false);
        cbProxySettings.setIcon(getCollapsedIcon());

        networkNeighbourhoodList = new NetworkNeighbourhoodList();
        serversListPanel.add(networkNeighbourhoodList, BorderLayout.CENTER);
        hostSelectionPanel.attach(networkNeighbourhoodList);

        hostSelectionPanel.addChangeListener(listener);

        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        hostSelectionPanel.requestFocus();
                    }
                });
            }
        });
        adjustPreferredSize(); // otherwise GUI editor spoils the form
    }

    private void fireChange() {
        if (listener != null) {
            listener.stateChanged(null);
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        fireChange();
    }

    void init() {
        textUser.setText(data.getUserName());
        hostSelectionPanel.set(data.getHostName(), data.getPort());
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(getClass(), "CreateHostVisualPanel1.Title");
    }

    public String getHostname() {
        return hostSelectionPanel.getHostname();
    }

    public String getUser() {
        return textUser.getText().trim();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textUser.setEnabled(enabled);
        hostSelectionPanel.setEnabled(enabled);
        proxySettingsPanel.setEnabled(enabled);
        cbProxySettings.setEnabled(enabled);
        lblUser.setEnabled(enabled);
        lblNeighbouthood.setEnabled(enabled);
        serversListPanel.setEnabled(enabled);
    }

    public int getPort() {
        return hostSelectionPanel.getPort();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUser = new javax.swing.JLabel();
        textUser = new javax.swing.JTextField();
        hostSelectionPanel = new org.netbeans.modules.cnd.remote.ui.networkneighbour.HostSelectionPanel();
        lblNeighbouthood = new javax.swing.JLabel();
        serversListPanel = new javax.swing.JPanel();
        cbProxySettings = new javax.swing.JCheckBox();
        proxySettingsPanel = new org.netbeans.modules.cnd.remote.ui.proxysettings.ProxySettingsPanel();

        setPreferredSize(new java.awt.Dimension(534, 409));
        setRequestFocusEnabled(false);

        lblUser.setLabelFor(textUser);
        org.openide.awt.Mnemonics.setLocalizedText(lblUser, org.openide.util.NbBundle.getMessage(CreateHostVisualPanel1.class, "CreateHostVisualPanel1.lblUser.text")); // NOI18N

        textUser.setText(org.openide.util.NbBundle.getMessage(CreateHostVisualPanel1.class, "CreateHostVisualPanel1.textUser.text")); // NOI18N

        lblNeighbouthood.setLabelFor(serversListPanel);
        org.openide.awt.Mnemonics.setLocalizedText(lblNeighbouthood, org.openide.util.NbBundle.getMessage(CreateHostVisualPanel1.class, "CreateHostVisualPanel1.lblNeighbouthood.text")); // NOI18N

        serversListPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(cbProxySettings, org.openide.util.NbBundle.getMessage(CreateHostVisualPanel1.class, "CreateHostVisualPanel1.cbProxySettings.text")); // NOI18N
        cbProxySettings.setFocusPainted(false);
        cbProxySettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProxySettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblNeighbouthood)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hostSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serversListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textUser, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                        .addGap(119, 119, 119))
                    .addComponent(proxySettingsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbProxySettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(textUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hostSelectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblNeighbouthood)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serversListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProxySettings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proxySettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbProxySettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProxySettingsActionPerformed
        proxySettingsPanel.setVisible(cbProxySettings.isSelected());
        cbProxySettings.setIcon(cbProxySettings.isSelected() ? getExpandedIcon() : getCollapsedIcon());
    }//GEN-LAST:event_cbProxySettingsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbProxySettings;
    private org.netbeans.modules.cnd.remote.ui.networkneighbour.HostSelectionPanel hostSelectionPanel;
    private javax.swing.JLabel lblNeighbouthood;
    private javax.swing.JLabel lblUser;
    private org.netbeans.modules.cnd.remote.ui.proxysettings.ProxySettingsPanel proxySettingsPanel;
    private javax.swing.JPanel serversListPanel;
    private javax.swing.JTextField textUser;
    // End of variables declaration//GEN-END:variables
    static final boolean isGtk = "GTK".equals(UIManager.getLookAndFeel().getID()); //NOI18N

    static Icon getExpandedIcon() {
        return UIManager.getIcon(isGtk ? "Tree.gtk_expandedIcon" : "Tree.expandedIcon"); //NOI18N
    }

    static Icon getCollapsedIcon() {
        return UIManager.getIcon(isGtk ? "Tree.gtk_collapsedIcon" : "Tree.collapsedIcon"); //NOI18N
    }

    boolean isProxyValid() {
        return proxySettingsPanel.isValidState();
    }

    void applyProxyChangesIfNeed() {
        proxySettingsPanel.applyProxyChangesIfNeed();
    }
}
