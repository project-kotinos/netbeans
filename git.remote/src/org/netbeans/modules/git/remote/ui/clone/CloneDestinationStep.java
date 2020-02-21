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
package org.netbeans.modules.git.remote.ui.clone;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.MissingResourceException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.git.remote.cli.GitBranch;
import org.netbeans.modules.git.remote.cli.GitURI;
import org.netbeans.modules.git.remote.Git;
import org.netbeans.modules.git.remote.client.GitProgressSupport;
import org.netbeans.modules.git.remote.ui.wizards.AbstractWizardPanel;
import org.netbeans.modules.remotefs.versioning.api.VCSFileProxySupport;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileSystem;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 */
public class CloneDestinationStep extends AbstractWizardPanel implements DocumentListener, ItemListener, WizardDescriptor.FinishablePanel<WizardDescriptor> {
    
    static final String CLONE_TARGET_DIRECTORY = "cloneDestinationStep.cloneDirectory"; //NOI18N
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private final CloneDestinationPanel panel;
    private final FileSystem fileSystem;

    public CloneDestinationStep(FileSystem fs) {
        this.fileSystem = fs;
        panel = new CloneDestinationPanel(fs);
        panel.directoryField.getDocument().addDocumentListener(this);
        panel.nameField.getDocument().addDocumentListener(this);
        panel.remoteTextField.getDocument().addDocumentListener(this);
        panel.branchesComboBox.addItemListener(this);
        panel.branchesComboBox.setRenderer(new BranchRenderer());
        
        validateNoEmptyFields();
    }
    
    @Override
    public JComponent getJComponent() {        
        return panel;
    }
    
    @Override
    public HelpCtx getHelp() {
        return new HelpCtx(CloneDestinationStep.class);
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        validateNoEmptyFields();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        validateNoEmptyFields();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {        
        validateNoEmptyFields();
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        validateNoEmptyFields();
    }

    @Override
    public boolean isFinishPanel () {
        return true;
    }
    
    @Override
    protected boolean validateBeforeNext() {
        if (validateNoEmptyFields()) {
            return false;
        }
        GitProgressSupport support = new GitProgressSupport() {
           @Override
           protected void perform() {
               setEnabled(false);
               try {
                    VCSFileProxy dest = getDestination();
                    if(!dest.exists()) {
                        return;
                    }
                    if(dest.isFile()) {
                        setValid(false, new Message(NbBundle.getMessage(CloneDestinationStep.class, "MSG_DEST_IS_FILE_ERROR"), false));
                        return;
                    }
                    VCSFileProxy[] files = dest.listFiles();
                    if(files != null && files.length > 0) {
                        setValid(false, new Message(NbBundle.getMessage(CloneDestinationStep.class, "MSG_DEST_IS_NOT_EMPTY_ERROR"), false));
                    }
               } finally {
                   setEnabled(true);
               }
           }
       };
       support.start(Git.getInstance().getRequestProcessor(), getDestination(), NbBundle.getMessage(CloneDestinationStep.class, "MSG_VALIDATING_DESTINATION")).waitFinished();
       return isValid();
    }

    private boolean validateNoEmptyFields() throws MissingResourceException {
        String parent = panel.getDirectory();
        if (parent == null || parent.trim().isEmpty()) {
            setValid(false, new Message(NbBundle.getMessage(CloneDestinationStep.class, "MSG_EMPTY_PARENT_ERROR"), true));
            return true;
        }
        String name = panel.getCloneName();
        if (name == null || name.trim().isEmpty()) {
            setValid(false, new Message(NbBundle.getMessage(CloneDestinationStep.class, "MSG_EMPTY_NAME_ERROR"), true));
            return true;
        }
        String remoteName = panel.getRemoteName();
        if (remoteName == null || remoteName.trim().isEmpty()) {
            setValid(false, new Message(NbBundle.getMessage(CloneDestinationStep.class, "MSG_EMPTY_REMOTE_ERROR"), true));
            return true;
        }
        setValid(true, null);
        return false;
    }

    void setBranches(List<? extends GitBranch> branches) {
        if(branches == null) {
            return;
        }
        DefaultComboBoxModel model;
        if (branches.isEmpty()) {
            model = new DefaultComboBoxModel(new GitBranch[] { null });
            panel.branchesComboBox.setEnabled(false);
        } else {
            model = new DefaultComboBoxModel(branches.toArray(new GitBranch[branches.size()]));
            panel.branchesComboBox.setEnabled(true);
        }
        panel.branchesComboBox.setModel(model);
        GitBranch activeBranch = null;
        for (GitBranch branch : branches) {
            if(branch.isActive()) {
                activeBranch = branch;
                break;
            }
        }
        if(activeBranch != null) {
            panel.branchesComboBox.setSelectedItem(activeBranch);
        }
    }
    
    void setDestinationFolder (String folder) {
        panel.setDirectory(folder);
    }

    VCSFileProxy getDestination() {
        return VCSFileProxySupport.getResource(fileSystem, panel.getDirectory() + "/" + panel.getCloneName());
    }
    
    String getRemoteName() {
        return panel.remoteTextField.getText();
    }
    
    GitBranch getBranch() {
        return (GitBranch) panel.branchesComboBox.getSelectedItem();
    }

    boolean scanForProjects() {
        return panel.scanForProjectsCheckBox.isSelected();
    }

    void initCloneName (GitURI uri) {
        String path = uri.getPath();
        // get the last path element
        String[] pathElements = path.split("[/\\\\]"); //NOI18N
        String lastElem = ""; //NOI18N
        for (int i = pathElements.length - 1; i >= 0; --i) {
            lastElem = pathElements[i];
            if (!lastElem.isEmpty()) {
                break;
            }
        }
        if (!lastElem.isEmpty()) {
            // is it of the usual form abcdrepository.git ?
            if (lastElem.endsWith(".git")) { //NOI18N
                lastElem = lastElem.substring(0, lastElem.length() - 4);
            }
            if (!lastElem.isEmpty()) {
                panel.nameField.setText(lastElem);
            }
        }
    }
    
    @NbBundle.Messages({
        "CTL_CloneDestinationStep.branch.noBranch=No Branch"
    })
    private static class BranchRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean bln, boolean bln1) {
            if(o instanceof GitBranch) {
                GitBranch b = (GitBranch) o;
                return super.getListCellRendererComponent(jlist, b.getName() + (b.isActive() ? "*" : ""), i, bln, bln1);
            } else if (o == null) {
                o = Bundle.CTL_CloneDestinationStep_branch_noBranch();
            }
            return super.getListCellRendererComponent(jlist, o, i, bln, bln1);
        }
    }
    
    private void setEnabled(boolean en) {
        panel.branchesComboBox.setEnabled(en);
        panel.directoryField.setEnabled(en);
        panel.nameField.setEnabled(en);
        panel.remoteTextField.setEnabled(en);
        panel.scanForProjectsCheckBox.setEnabled(en);
    }        

}

