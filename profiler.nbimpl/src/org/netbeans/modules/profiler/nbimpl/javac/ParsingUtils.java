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
package org.netbeans.modules.profiler.nbimpl.javac;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.java.source.*;

/**
 *
 * @author Jaroslav Bachorik
 */
public class ParsingUtils {
    private static final Logger LOG = Logger.getLogger(ParsingUtils.class.getName());

    /**
     * Invokes a scan sensitive task at safe point and waits for it to finish
     * @param cpInfo classpath
     * @param t task to run
     */
    public static void invokeScanSensitiveTask(final ClasspathInfo cpInfo, final ScanSensitiveTask<CompilationController> t) {
        invokeScanSensitiveTask(JavaSource.create(cpInfo), t);
    }
    
    public static void invokeScanSensitiveTask(final JavaSource js, final ScanSensitiveTask<CompilationController> t) {
        try {
            boolean wasScanning = SourceUtils.isScanInProgress();
            if (!t.requiresUpToDate()) {
                js.runUserActionTask(t, true);
            }
            if (t.requiresUpToDate() || (wasScanning && t.shouldRetry())) {
                js.runWhenScanFinished(t, true).get();
            }
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
