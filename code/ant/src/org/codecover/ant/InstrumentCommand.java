/******************************************************************************
 * Copyright (c) 2007 Stefan Franke, Robert Hanussek, Benjamin Keil,          *
 *                    Steffen Kieß, Johannes Langauf,                         *
 *                    Christoph Marian Müller, Igor Podolskiy,                *
 *                    Tilmann Scheller, Michael Starzmann, Markus Wittlinger  *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.codecover.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;
import org.codecover.instrumentation.InstrumenterDescriptor;
import org.codecover.model.TestSessionContainer;
import org.codecover.model.utils.Logger;
import org.codecover.model.utils.ProgressHandler;
import org.codecover.model.utils.file.SourceTargetContainer;
import org.codecover.utils.InstrumentUtils;

/**
 * 
 * @author Steffen Kieß
 * @version 1.0 ($Id$)
 * 
 */
public class InstrumentCommand extends Command {
    String containerId;

    String language;

    String instrumenter;

    File destination;

    String charset;

    boolean copyUninstrumented = false;

    boolean override = true;

    FileSet source = null;

    CriteriaList criteria = null;

    /**
     * Sets the containerId.
     * 
     * @param containerId
     *                the containerId to set
     */
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    /**
     * Sets the language.
     * 
     * @param language
     *                the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Sets the instrumenter.
     * 
     * @param instrumenter
     *                the instrumenter to set
     */
    public void setInstrumenter(String instrumenter) {
        this.instrumenter = instrumenter;
    }

    /**
     * Sets the destination.
     * 
     * @param destination
     *                the destination to set
     */
    public void setDestination(File destination) {
        this.destination = destination;
    }

    /**
     * Sets the charset.
     * 
     * @param charset
     *                the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * Sets the copyUninstrumented.
     * 
     * @param copyUninstrumented
     *                the copyUninstrumented to set
     */
    public void setCopyUninstrumented(boolean copyUninstrumented) {
        this.copyUninstrumented = copyUninstrumented;
    }

    /**
     * Sets the override.
     * 
     * @param override
     *                the override to set
     */
    public void setOverride(boolean override) {
        this.override = override;
    }

    /**
     * Adds a configured {@link FileSet} to this command.
     * 
     * @param source
     *                the {@link FileSet} to add.
     */
    public void addConfiguredSource(FileSet source) {
        if (this.source != null) {
            throw new BuildException("There are multiple <source> elements");
        }
        this.source = source;
    }

    /**
     * Adds a configured {@link CriteriaList} to this command.
     * 
     * @param criteria
     *                the {@link CriteriaList} to add.
     */
    public void addConfiguredCriteria(CriteriaList criteria) {
        if (this.criteria != null) {
            throw new BuildException("There are multiple <criteria> elements");
        }
        this.criteria = criteria;
    }

    @Override
    public void run(final Context context) {
        if (this.containerId == null) {
            throw new BuildException("The attribute 'containerId' is missing.");
        }

        if (this.language == null) {
            throw new BuildException("The attribute 'language' is missing.");
        }

        /* TODO: use override */

        if (this.destination == null) {
            throw new BuildException("The attribute 'destination' is missing.");
        }

        if (this.source == null) {
            throw new BuildException("The <source> element is missing.");
        }

        final Logger logger = context.getLogger();

        final ProgressHandler progressHandler = ProgressHandler.NULL;

        final File pRootDirectory = this.source.getDir(getProject());
        final File pDestination = this.destination;
        final String pLanguage = this.language;

        // optional options
        final boolean pHasCriteria = this.criteria != null;
        final List<String> pCriteria;
        if (pHasCriteria) {
            pCriteria = new ArrayList<String>(this.criteria.getCriteria());
        } else {
            pCriteria = Collections.<String> emptyList();
        }
        final boolean pHasCharset = this.charset != null;
        final String pCharset = this.charset;
        final boolean pCopyUninstrumented = this.copyUninstrumented;

        final List<String> pDirectives = Collections.<String> emptyList();
        /* TODO: expose this to ant interface */

        final InstrumentUtils.SaveSessionContainerCallback saveSessionContainerCallback = new InstrumentUtils.SaveSessionContainerCallback() {
            public void saveSessionContainer(
                    TestSessionContainer testSessionContainer) {
                context.setTestSessionContainer(
                        InstrumentCommand.this.containerId,
                        testSessionContainer);
            }
        };

        final InstrumentUtils.GetFilesCallback getFilesCallback = new InstrumentUtils.GetFilesCallback() {
            public Collection<SourceTargetContainer> getFilesToInstrument(
                    InstrumenterDescriptor descriptor, File rootFolderFile,
                    File targetFolderFile) {
                return createSourceTargetContainerSet(rootFolderFile,
                        targetFolderFile, InstrumentCommand.this.source
                                .getDirectoryScanner(getProject())
                                .getIncludedFiles());
            }

            public Collection<SourceTargetContainer> getFilesToCopy(
                    InstrumenterDescriptor descriptor, File rootFolderFile,
                    File targetFolderFile) {
                return createSourceTargetContainerSet(rootFolderFile,
                        targetFolderFile, InstrumentCommand.this.source
                                .getDirectoryScanner(getProject())
                                .getDeselectedFiles());
            }
        };

        InstrumentUtils.instrument(logger, context.getPluginManager(),
                progressHandler, /* pretend */false, pRootDirectory.getPath(),
                pDestination.getPath(), saveSessionContainerCallback,
                pLanguage, pHasCriteria, pCriteria, pHasCharset, pCharset,
                pCopyUninstrumented, pDirectives, this.instrumenter != null,
                this.instrumenter, getFilesCallback);
    }

    private static Set<SourceTargetContainer> createSourceTargetContainerSet(
            File sourceBasePath, File targetBasePath, String[] paths) {
        final Set<SourceTargetContainer> result = new HashSet<SourceTargetContainer>();
        for (String path : paths) {
            result.add(new SourceTargetContainer(new File(sourceBasePath
                    .getPath()
                    + File.separator + path), new File(targetBasePath.getPath()
                    + File.separator + path)));
        }
        return result;
    }
}
