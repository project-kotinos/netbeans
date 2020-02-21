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

package org.netbeans.modules.mercurial.remote.ui.diff;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.netbeans.api.diff.Difference;
import org.netbeans.api.diff.StreamSource;
import org.netbeans.modules.mercurial.remote.Mercurial;
import org.netbeans.modules.mercurial.remote.VersionsCache;
import org.netbeans.modules.mercurial.remote.ui.log.HgLogMessage.HgRevision;
import org.netbeans.modules.remotefs.versioning.api.VCSFileProxySupport;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.netbeans.modules.versioning.util.Utils;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.CookieSet;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;

/**
 * Stream source for diffing CVS managed files.
 *
 * 
 */
public class DiffStreamSource extends StreamSource {

    private final VCSFileProxy      baseFile;
    private final HgRevision    revision;
    private final String    title;
    private String          mimeType;
    private Boolean         start;

    /**
     * Null is a valid value if base file does not exist in this revision. 
     */ 
    private VCSFileProxy            remoteFile;
    private Boolean         canWriteBaseFile;
    private final VCSFileProxy fileInRevision;
    private VCSFileProxy encodingHolder;

    /**
     * Creates a new StreamSource implementation for Diff engine.
     * 
     * @param baseFile
     * @param revision file revision, may be null if the revision does not exist (ie for new files)
     * @param title title to use in diff panel
     */ 
    public DiffStreamSource(VCSFileProxy fileInRevision, VCSFileProxy baseFile, HgRevision revision, String title) {
        this.baseFile = VCSFileProxySupport.isMac(baseFile) ? baseFile.normalizeFile() : baseFile;
        this.fileInRevision = fileInRevision;
        this.revision = revision;
        this.title = title;
        this.start = true;
    }

    /** Creates DiffStreamSource for nonexiting files. */
    public DiffStreamSource(String title) {
        this.baseFile = null;
        this.fileInRevision = null;
        this.revision = null;
        this.title = title;
        this.start = true;
    }

    @Override
    public String getName() {
        if (baseFile != null) {
            return baseFile.getName();
        } else {
            return NbBundle.getMessage(DiffStreamSource.class, "LBL_Diff_Anonymous"); // NOI18N
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public synchronized String getMIMEType() {
        if (baseFile.isDirectory()) {
            // http://www.rfc-editor.org/rfc/rfc2425.txt
            return "content/unknown"; // "text/directory";  //HACK no editor for directory MIME type => NPE while constructing EditorKit // NOI18N
        }

        try {
            init();
        } catch (IOException e) {
            return null; // XXX use error manager HACK null  potentionally kills DiffViewImpl, NPE while constructing EditorKit
        }
        return mimeType;
    }

    @Override
    public synchronized Reader createReader() throws IOException {
        if (baseFile.isDirectory()) {
            // XXX return directory listing?
            // could be nice te return sorted directory content
            // such as vim if user "edits" directory // NOI18N
            return new StringReader(NbBundle.getMessage(DiffStreamSource.class, "LBL_Diff_NoFolderDiff")); // NOI18N
        }
        init();
        if (revision == null || remoteFile == null) {
            return null;
        }
        if (!mimeType.startsWith("text/")) { // NOI18N
            return null;
        } else {
            return Utils.createReader(remoteFile.toFileObject());  
        }
    }

    @Override
    public Writer createWriter(Difference[] conflicts) throws IOException {
        throw new IOException("Operation not supported"); // NOI18N
    }

    @Override
    public boolean isEditable() {
        return HgRevision.CURRENT.equals(revision) && isPrimary() && isBaseFileWritable();
    }

    private boolean isBaseFileWritable () {
        if (canWriteBaseFile == null) {
            FileObject fo = baseFile.toFileObject();
            canWriteBaseFile = fo != null && fo.canWrite();
        }
        return canWriteBaseFile;
    }

    private boolean isPrimary() {
        FileObject fo = baseFile.toFileObject();
        if (fo != null) {
            try {
                DataObject dao = DataObject.find(fo);
                return fo.equals(dao.getPrimaryFile());
            } catch (DataObjectNotFoundException e) {
                // no dataobject, never mind
            }
        }
        return true;
    }

    @Override
    public synchronized Lookup getLookup() {
        try {
            init();
        } catch (IOException e) {
            return Lookups.fixed();
        }
        if (remoteFile == null || !isPrimary()) {
            return Lookups.fixed();
        }
        FileObject remoteFo = remoteFile.toFileObject();
        if (remoteFo == null) {
            return Lookups.fixed();
        }

        return Lookups.fixed(remoteFo);
    }
    
    @Override
    public void close () {
        if (start) {
            // not yet initialized
            return;
        }
        EditorCookie.Observable ec = getEditableCookie(remoteFile);
        if (ec != null && ec.getOpenedPanes() == null && !ec.isModified()) {
            ec.close();
        }
        super.close();
    }
    
    /**
     * Loads data.
     */
    synchronized void init() throws IOException {
        if (baseFile.isDirectory()) {
            return;
        }
        if (start == false) {
            return;
        }
        start = false;
        if (remoteFile != null || revision == null) {
            return;
        }
        mimeType = Mercurial.getInstance().getMimeType(baseFile);
        try {
            if (isEditable()) {
                // we cannot move editable documents because that would break Document sharing
                remoteFile = VersionsCache.getInstance().getFileRevision(baseFile, revision);
            } else {
                VCSFileProxy tempFolder = VCSFileProxySupport.getTempFolder(baseFile, true);
                // To correctly get content of the base file, we need to checkout all files that belong to the same
                // DataObject. One example is Form files: data loader removes //GEN:BEGIN comments from the java file but ONLY
                // if it also finds associate .form file in the same directory
                Set<VCSFileProxy> allFiles = VCSFileProxySupport.getAllDataObjectFiles(baseFile);
                Map<VCSFileProxy, VCSFileProxy> allFilePairs = new HashMap<>(allFiles.size());
                boolean renamed = !baseFile.equals(fileInRevision);
                for (VCSFileProxy f : allFiles) {
                    if (renamed) {
                        allFilePairs.put(renameFile(f, baseFile, fileInRevision), f);
                    } else {
                        allFilePairs.put(f, f);
                    }
                }
                for (Map.Entry<VCSFileProxy, VCSFileProxy> entry : allFilePairs.entrySet()) {
                    VCSFileProxy file = entry.getKey();
                    VCSFileProxy currentPair = entry.getValue();
                    boolean isBase = file.equals(fileInRevision);
                    try {
                        VCSFileProxy rf = VersionsCache.getInstance().getFileRevision(file, revision);
                        if (rf == null || !rf.exists()) {
                            remoteFile = null;
                            return;
                        }
                        VCSFileProxy newRemoteFile = VCSFileProxy.createFileProxy(tempFolder, file.getName());
                        Utils.copyStreamsCloseAll(VCSFileProxySupport.getOutputStream(newRemoteFile), rf.getInputStream(false));
                        VCSFileProxySupport.deleteOnExit(newRemoteFile);
                        if (isBase) {
                            remoteFile = newRemoteFile;
                            encodingHolder = currentPair;
                            if (encodingHolder.exists()) {
                                VCSFileProxySupport.associateEncoding(encodingHolder, newRemoteFile);
                            } else if (remoteFile != null) {
                                boolean created = false;
                                try {
                                    if (encodingHolder.getParentFile().exists()) {
                                        created = VCSFileProxySupport.createNew(encodingHolder);
                                        VCSFileProxySupport.associateEncoding(encodingHolder, newRemoteFile);
                                    }
                                } catch (IOException ex) {
                                    // not interested
                                } finally {
                                    if (created) {
                                        VCSFileProxySupport.delete(encodingHolder);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (isBase) {
                            throw e;
                        }
                        // we cannot check out peer file so the dataobject will not be constructed properly
                    }
                }
            }
            if (!baseFile.exists() && remoteFile != null && remoteFile.exists()) {
                mimeType = Mercurial.getInstance().getMimeType(remoteFile);
            }
        } catch (Exception e) {
            throw new IOException("Can not load remote file for " + baseFile, e); //NOI18N
        }
    }
    
    private static EditorCookie.Observable getEditableCookie (VCSFileProxy file) {
        EditorCookie.Observable editorCookie = null;
        if (file == null) {
            return null;
        }
        FileObject fileObj = file.toFileObject();
        if (fileObj != null) {
            try {
                DataObject dao = DataObject.find(fileObj);
                if (dao instanceof MultiDataObject) {
                    MultiDataObject mdao = (MultiDataObject) dao;
                    for (MultiDataObject.Entry entry : mdao.secondaryEntries()) {
                        if (fileObj == entry.getFile() && entry instanceof CookieSet.Factory) {
                            CookieSet.Factory factory = (CookieSet.Factory) entry;
                            EditorCookie ec = factory.createCookie(EditorCookie.class);
                            if (ec instanceof EditorCookie.Observable) {
                                editorCookie = (EditorCookie.Observable) ec;
                            }
                        }
                    }
                }
                if (editorCookie == null) {
                    EditorCookie cookie = dao.getLookup().lookup(EditorCookie.class);
                    if (cookie instanceof EditorCookie.Observable) {
                        editorCookie = (EditorCookie.Observable) cookie;
                    }
                }
            } catch (DataObjectNotFoundException ex) {
            }
        }
        return editorCookie;
    }

    private VCSFileProxy renameFile (VCSFileProxy toRename, VCSFileProxy baseFile, VCSFileProxy renamedBaseFile) {
        VCSFileProxy parent = renamedBaseFile.getParentFile();
        String baseFileName = baseFile.getName();
        String renamedFileName = renamedBaseFile.getName();
        String toRenameFileName = toRename.getName();
        String retval = toRenameFileName;
        if (!renamedFileName.equals(baseFileName)) {
            String baseNameNoExt = getFileNameNoExt(baseFileName);
            String renamedNameNoExt = getFileNameNoExt(renamedFileName);
            if (toRenameFileName.startsWith(baseNameNoExt)) {
                retval = renamedNameNoExt;
                if (toRenameFileName.length() > baseNameNoExt.length()) {
                    retval += toRenameFileName.substring(baseNameNoExt.length());
                }
            }
        }
        return VCSFileProxy.createFileProxy(parent, retval);
    }

    private String getFileNameNoExt (String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos != -1) {
            return fileName.substring(0, pos);
        }
        return fileName;
    }
}
