/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */

package com.magento.idea.magento2plugin.magento.files;

import com.intellij.lang.Language;
import com.jetbrains.php.lang.PhpLanguage;
import com.magento.idea.magento2plugin.actions.generation.generator.util.NamespaceBuilder;
import com.magento.idea.magento2plugin.magento.packages.Package;
import org.jetbrains.annotations.NotNull;

public class EntityDataMapperFile implements ModuleFileInterface {

    public static final String CLASS_NAME_SUFFIX = "DataMapper";
    public static final String FILE_EXTENSION = "php";
    public static final String TEMPLATE = "Magento Entity Data Mapper";
    private static final String DIRECTORY = "Mapper";
    private final String className;
    private String namespaceFqn;
    private String classFqn;

    /**
     * Entity data mapper file constructor.
     *
     * @param entityName String
     */
    public EntityDataMapperFile(final @NotNull String entityName) {
        this.className = entityName.concat(CLASS_NAME_SUFFIX);
    }

    /**
     * Get namespace.
     *
     * @param moduleName String
     *
     * @return String
     */
    public @NotNull String getNamespace(
            final @NotNull String moduleName
    ) {
        if (namespaceFqn == null) {
            final NamespaceBuilder namespaceBuilder = new NamespaceBuilder(
                    moduleName,
                    className,
                    DIRECTORY
            );
            namespaceFqn = namespaceBuilder.getNamespace();
        }

        return namespaceFqn;
    }

    /**
     * Get class FQN.
     *
     * @param moduleName String
     *
     * @return String
     */
    public @NotNull String getClassFqn(
            final @NotNull String moduleName
    ) {
        if (classFqn == null) {
            classFqn = getNamespace(moduleName)
                    .concat(Package.fqnSeparator)
                    .concat(className);
        }

        return classFqn;
    }

    /**
     * Get class name.
     *
     * @return String
     */
    public @NotNull String getClassName() {
        return className;
    }

    /**
     * Get directory for entity data mapper file.
     *
     * @return String
     */
    public @NotNull String getDirectory() {
        return DIRECTORY;
    }

    @Override
    public String getFileName() {
        return className.concat("." + FILE_EXTENSION);
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @Override
    public Language getLanguage() {
        return PhpLanguage.INSTANCE;
    }
}
