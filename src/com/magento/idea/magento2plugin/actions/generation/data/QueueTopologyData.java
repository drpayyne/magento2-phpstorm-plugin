package com.magento.idea.magento2plugin.actions.generation.data;

public class QueueTopologyData {
    private String exchangeName;
    private String bindingId;
    private String bindingTopic;
    private String bindingQueue;
    private String moduleName;

    public QueueTopologyData(
            String exchangeName,
            String bindingId,
            String bindingTopic,
            String bindingQueue,
            String moduleName
    ) {
        this.exchangeName = exchangeName;
        this.bindingId = bindingId;
        this.bindingTopic = bindingTopic;
        this.bindingQueue = bindingQueue;
        this.moduleName = moduleName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getBindingId() {
        return bindingId;
    }

    public String getBindingTopic() {
        return bindingTopic;
    }

    public String getBindingQueue() {
        return bindingQueue;
    }

    public String getModuleName() {
        return moduleName;
    }
}