/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */

package com.magento.idea.magento2plugin.magento.files;

import com.intellij.lang.Language;
import com.intellij.lang.xml.XMLLanguage;
import com.magento.idea.magento2plugin.magento.packages.database.TableColumnTypes;
import java.util.ArrayList;
import java.util.List;

public class ModuleDbSchemaXml implements ModuleFileInterface {
    private static final ModuleDbSchemaXml INSTANCE = new ModuleDbSchemaXml();
    public static final String FILE_NAME = "db_schema.xml";
    public static final String TEMPLATE = "Magento Module Declarative Schema XML";

    //attributes
    public static final String XML_ATTR_TABLE_NAME = "name";
    public static final String XML_ATTR_TABLE_RESOURCE = "resource";
    public static final String XML_ATTR_TABLE_ENGINE = "engine";
    public static final String XML_ATTR_TABLE_COMMENT = "comment";
    public static final String XML_ATTR_CONSTRAINT_TABLE_NAME = "table";
    public static final String XML_ATTR_CONSTRAINT_REFERENCE_TABLE_NAME = "referenceTable";
    public static final String XML_ATTR_CONSTRAINT_COLUMN_NAME = "column";
    public static final String XML_ATTR_CONSTRAINT_REFERENCE_COLUMN_NAME = "referenceColumn";
    public static final String XML_ATTR_CONSTRAINT_REFERENCE_ID_NAME = "referenceId";
    public static final String XML_ATTR_INDEX_TYPE_NAME = "indexType";
    public static final String XML_ATTR_COLUMN_NAME = "name";
    public static final String XML_ATTR_COLUMN_TYPE = "xsi:type";
    public static final String XML_ATTR_COLUMN_PADDING = "padding";
    public static final String XML_ATTR_COLUMN_UNSIGNED = "unsigned";
    public static final String XML_ATTR_COLUMN_NULLABLE = "nullable";
    public static final String XML_ATTR_COLUMN_IDENTITY = "identity";
    public static final String XML_ATTR_COLUMN_COMMENT = "comment";
    public static final String XML_ATTR_COLUMN_DEFAULT = "default";
    public static final String XML_ATTR_COLUMN_LENGTH = "length";
    public static final String XML_ATTR_COLUMN_SCALE = "scale";
    public static final String XML_ATTR_COLUMN_PRECISION = "precision";
    public static final String XML_ATTR_COLUMN_ON_UPDATE = "on_update";

    //constant attributes values
    public static final String XML_ATTR_TYPE_PK = "primary";
    public static final String XML_ATTR_REFERENCE_ID_PK = "PRIMARY";
    public static final String XML_ATTR_INDEX_TYPE_BTREE = "btree";
    public static final String XML_ATTR_INDEX_TYPE_FULLTEXT = "fulltext";//NOPMD
    public static final String XML_ATTR_INDEX_TYPE_HASH = "hash";//NOPMD

    //tags
    public static final String XML_TAG_SCHEMA = "schema";
    public static final String XML_TAG_TABLE = "table";
    public static final String XML_TAG_COLUMN = "column";
    public static final String XML_TAG_CONSTRAINT = "constraint";
    public static final String XML_TAG_INDEX = "index";

    /**
     * Get allowed attributes for column by its type.
     *
     * @param columnType String
     *
     * @return List
     */
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NcssCount"})
    public static List<String> getAllowedAttributes(final TableColumnTypes columnType) {
        final List<String> allowedAttributes = new ArrayList<>();

        switch (columnType) {
            case BLOB:
            case MEDIUMBLOB:
            case LONGBLOB:
            case DATE:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            case VARBINARY:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_DEFAULT);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_LENGTH);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            case TINYINT:
            case SMALLINT:
            case INT:
            case BIGINT:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_PADDING);
                allowedAttributes.add(XML_ATTR_COLUMN_UNSIGNED);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_IDENTITY);
                allowedAttributes.add(XML_ATTR_COLUMN_DEFAULT);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            case DECIMAL:
            case DOUBLE:
            case FLOAT:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_DEFAULT);
                allowedAttributes.add(XML_ATTR_COLUMN_SCALE);
                allowedAttributes.add(XML_ATTR_COLUMN_PRECISION);
                allowedAttributes.add(XML_ATTR_COLUMN_UNSIGNED);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            case VARCHAR:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_LENGTH);
                allowedAttributes.add(XML_ATTR_COLUMN_DEFAULT);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            case BOOLEAN:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_DEFAULT);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            case DATETIME:
            case TIMESTAMP:
                allowedAttributes.add(XML_ATTR_COLUMN_NAME);
                allowedAttributes.add(XML_ATTR_COLUMN_ON_UPDATE);
                allowedAttributes.add(XML_ATTR_COLUMN_NULLABLE);
                allowedAttributes.add(XML_ATTR_COLUMN_DEFAULT);
                allowedAttributes.add(XML_ATTR_COLUMN_COMMENT);
                break;
            default:
                break;
        }
        allowedAttributes.add(XML_ATTR_COLUMN_TYPE);

        return allowedAttributes;
    }

    /**
     * Generate index key reference id.
     *
     * @param tableName String
     * @param indexColumnsNames List
     *
     * @return String
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    public static String generateIndexReferenceId(
            final String tableName,
            final List<String> indexColumnsNames
    ) {
        final StringBuilder stringBuilder = new StringBuilder(tableName.toUpperCase());

        for (final String indexName : indexColumnsNames) {
            stringBuilder.append('_').append(indexName.toUpperCase());
        }

        return stringBuilder.toString();
    }

    public static ModuleDbSchemaXml getInstance() {
        return INSTANCE;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @Override
    public Language getLanguage() {
        return XMLLanguage.INSTANCE;
    }
}
