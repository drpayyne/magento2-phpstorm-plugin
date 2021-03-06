/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */

package com.magento.idea.magento2plugin.actions.generation.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.magento.idea.magento2plugin.actions.generation.data.UiComponentDataProviderData;

public class UiComponentGridDataProviderGeneratorTest extends BaseGeneratorTestCase {
    private static final String EXPECTED_DIRECTORY = "src/app/code/Foo/Bar/Ui/Component/Listing";
    private static final String MODULE_NAME = "Foo_Bar";
    private static final String PROVIDER_CLASS_NAME = "GridDataProvider";
    private static final String PROVIDER_NAMESPACE = "Foo\\Bar\\Ui\\Listing";
    private static final String PROVIDER_PATH = "Ui/Component/Listing";

    /**
     * Test data provider class file generation with custom type.
     */
    public void testGenerateCustomDataProvider() {
        final PsiFile dataProviderFile = generateDataProvider();
        final String filePath = this.getFixturePath("GridDataProvider.php");
        final PsiFile expectedFile = myFixture.configureByFile(filePath);

        assertGeneratedFileIsCorrect(
                expectedFile,
                EXPECTED_DIRECTORY,
                dataProviderFile
        );
    }

    private PsiFile generateDataProvider() {
        final Project project = myFixture.getProject();
        final UiComponentDataProviderData providerData = new UiComponentDataProviderData(
                PROVIDER_CLASS_NAME,
                PROVIDER_NAMESPACE,
                PROVIDER_PATH
        );
        final UiComponentDataProviderGenerator generator;
        generator = new UiComponentDataProviderGenerator(
                providerData,
                MODULE_NAME,
                project
        );

        return generator.generate("test");
    }
}
