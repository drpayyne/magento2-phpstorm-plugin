package com.magento.idea.magento2plugin.actions.generation.generator.util;

import com.intellij.openapi.project.Project;
import com.magento.idea.magento2plugin.magento.files.ModuleFileInterface;
import com.magento.idea.magento2plugin.magento.files.QueueConsumerXml;

public class FindOrCreateQueueConsumerXml extends FindOrCreateQueueXml {
    /**
     * Constructor.
     */
    public FindOrCreateQueueConsumerXml(Project project) {
        super(project);
    }

    @Override
    protected ModuleFileInterface instantiateXmlFile() {
        return new QueueConsumerXml();
    }
}
