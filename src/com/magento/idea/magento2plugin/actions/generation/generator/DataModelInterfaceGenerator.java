/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */

package com.magento.idea.magento2plugin.actions.generation.generator;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.magento.idea.magento2plugin.actions.generation.data.DataModelInterfaceData;
import com.magento.idea.magento2plugin.actions.generation.generator.util.DirectoryGenerator;
import com.magento.idea.magento2plugin.actions.generation.generator.util.FileFromTemplateGenerator;
import com.magento.idea.magento2plugin.bundles.CommonBundle;
import com.magento.idea.magento2plugin.bundles.ValidatorBundle;
import com.magento.idea.magento2plugin.indexes.ModuleIndex;
import com.magento.idea.magento2plugin.magento.files.DataModelInterface;
import com.magento.idea.magento2plugin.util.GetFirstClassOfFile;
import com.magento.idea.magento2plugin.util.GetPhpClassByFQN;

import javax.swing.JOptionPane;
import java.util.Properties;

public class DataModelInterfaceGenerator extends FileGenerator {
    private final Project project;
    private final DataModelInterfaceData interfaceData;
    private final DirectoryGenerator directoryGenerator;
    private final FileFromTemplateGenerator fileFromTemplateGenerator;
    private final GetFirstClassOfFile getFirstClassOfFile;
    private final ValidatorBundle validatorBundle;
    private final CommonBundle commonBundle;

    public DataModelInterfaceGenerator(
            final Project project,
            final DataModelInterfaceData interfaceData
    ) {
        super(project);

        this.project = project;
        this.interfaceData = interfaceData;
        this.directoryGenerator = DirectoryGenerator.getInstance();
        this.fileFromTemplateGenerator = FileFromTemplateGenerator.getInstance(project);
        this.getFirstClassOfFile = GetFirstClassOfFile.getInstance();
        this.validatorBundle = new ValidatorBundle();
        this.commonBundle = new CommonBundle();
    }

    @Override
    public PsiFile generate(String actionName) {
        final PsiFile[] files = new PsiFile[1];

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PhpClass modelInterface = GetPhpClassByFQN.getInstance(project).execute(
                    interfaceData.getFQN()
            );

            if (modelInterface != null) {
                final String errorMessage = this.validatorBundle.message(
                        "validator.file.alreadyExists",
                        "Data Model Interface"
                );
                JOptionPane.showMessageDialog(
                        null,
                        errorMessage,
                        commonBundle.message("common.error"),
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                modelInterface = createInterface(actionName);

                if (modelInterface == null) {
                    final String errorMessage = this.validatorBundle.message(
                            "validator.file.cantBeCreated",
                            "Data Model Interface"
                    );
                    JOptionPane.showMessageDialog(
                            null,
                            errorMessage,
                            commonBundle.message("common.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    files[0] = modelInterface.getContainingFile();
                }
            }
        });

        return files[0];
    }

    @Override
    protected void fillAttributes(Properties attributes) {
        attributes.setProperty("NAME", interfaceData.getName());
        attributes.setProperty("NAMESPACE", interfaceData.getNamespace());
    }

    private PhpClass createInterface(String actionName) {
        PsiDirectory parentDirectory = ModuleIndex.getInstance(project)
                .getModuleDirectoryByModuleName(interfaceData.getModuleName());
        final PsiFile interfaceFile;
        final Properties attributes = getAttributes();

        for (String directory: DataModelInterface.DIRECTORY.split("/")) {
            parentDirectory = directoryGenerator.findOrCreateSubdirectory(
                    parentDirectory, directory
            );
        }

        interfaceFile = fileFromTemplateGenerator.generate(
                new DataModelInterface(interfaceData.getName()),
                attributes,
                parentDirectory,
                actionName
        );

        return interfaceFile == null ? null : getFirstClassOfFile.execute((PhpFile) interfaceFile);
    }
}