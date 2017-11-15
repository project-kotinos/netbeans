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
package org.netbeans.modules.java.editor.overridden;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Jan Lahoda
 */
public class IsOverriddenPopup extends JPanel implements FocusListener {
    
    private String caption;
    private List<ElementDescription> declarations;
    
    /** Creates new form IsOverriddenPopup */
    public IsOverriddenPopup(String caption, List<ElementDescription> declarations) {
        this.caption = caption;
        this.declarations = declarations;

        Collections.sort(declarations, new Comparator<ElementDescription>() {
            public int compare(ElementDescription o1, ElementDescription o2) {
                if (o1.isOverridden() == o2.isOverridden()) {
                    return o1.getDisplayName().compareTo(o2.getDisplayName());
                }
                
                if (o1.isOverridden()) {
                    return 1;
                }
                
                return -1;
            }
        });
        
        initComponents();
        
        jList1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addFocusListener(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setFocusCycleRoot(true);
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(caption
        );
        jLabel1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jLabel1, gridBagConstraints);

        jList1.setModel(createListModel());
        jList1.setCellRenderer(new RendererImpl());
        jList1.setSelectedIndex(0);
        jList1.setVisibleRowCount(declarations.size()
        );
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList1KeyPressed(evt);
            }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            openSelected();
        }
    }//GEN-LAST:event_jList1MouseClicked
    
    private void jList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && evt.getModifiers() == 0) {
            openSelected();
        }
    }//GEN-LAST:event_jList1KeyPressed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    private void openSelected() {
        ElementDescription desc = (ElementDescription) jList1.getSelectedValue();
        
        if (desc != null) {
            desc.open();
        }
        
        PopupUtil.hidePopup();
    }
    
    private ListModel createListModel() {
        DefaultListModel dlm = new DefaultListModel();
        
        for (ElementDescription el: declarations) {
            dlm.addElement(el);
        }
        
        return dlm;
    }
    
    private static class RendererImpl extends DefaultListCellRenderer {

        private static final int DARKER_COLOR_COMPONENT = 5;
        private boolean selected;

        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);            
            if (value instanceof ElementDescription) {
                ElementDescription desc = (ElementDescription) value;
                
                setIcon(desc.getIcon());
                setText(desc.getDisplayName());
            }
            selected = isSelected;
            Color bgColor;
            if (!isSelected) {
                bgColor = list.getBackground();
                if ((index % 2) == 0) { // every second item slightly different
                    bgColor = new Color(
                            Math.abs(bgColor.getRed() - DARKER_COLOR_COMPONENT),
                            Math.abs(bgColor.getGreen() - DARKER_COLOR_COMPONENT),
                            Math.abs(bgColor.getBlue() - DARKER_COLOR_COMPONENT)
                    );
                }
                // quick check Component.setBackground() always fires change
                if (getBackground() != bgColor) {
                    setBackground(bgColor);
                }
            }
            return this;
        }

        public @Override void paintComponent(Graphics g) {
            Color bgColor = getBackground();
            Color fgColor = getForeground();

            // Clear the background
            g.setColor(bgColor);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(fgColor);

            // Render the item
            CompletionUtilities.renderHtml(new ImageIcon(ImageUtilities.icon2Image(getIcon())), getText(), null, g, getFont(), fgColor, getWidth(), getHeight(), selected);
        }
    }
    
    public void focusGained(FocusEvent arg0) {
        jList1.requestFocus();
        jList1.requestFocusInWindow();
    }
    
    public void focusLost(FocusEvent arg0) {
    }
    
}
