package com.magento.idea.magento2plugin.reference.provider.mftf;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.*;
import com.intellij.util.ProcessingContext;
import com.intellij.util.indexing.FileBasedIndex;
import com.magento.idea.magento2plugin.reference.xml.PolyVariantReferenceBase;
import com.magento.idea.magento2plugin.stubs.indexes.mftf.ActionGroupIndex;
import com.magento.idea.magento2plugin.util.xml.XmlPsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActionGroupReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        List<PsiReference> psiReferences = new ArrayList<>();

        String origValue = StringUtil.unquoteString(element.getText());

        Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance()
            .getContainingFiles(
                ActionGroupIndex.KEY,
                origValue,
                GlobalSearchScope.getScopeRestrictedByFileTypes(
                    GlobalSearchScope.allScope(element.getProject()),
                    XmlFileType.INSTANCE
                )
            );

        PsiManager psiManager = PsiManager.getInstance(element.getProject());

        List<PsiElement> psiElements = new ArrayList<>();

        for (VirtualFile virtualFile: containingFiles) {
            XmlFile xmlFile = (XmlFile) psiManager.findFile(virtualFile);

            if (xmlFile == null) {
                continue;
            }

            Collection<XmlAttributeValue> valueElements = XmlPsiTreeUtil
                    .findAttributeValueElements(xmlFile, "actionGroup", "name", origValue);

            psiElements.addAll(valueElements);
        }

        if (psiElements.size() > 0) {
            psiReferences.add(new PolyVariantReferenceBase(element, psiElements));
        }

        return psiReferences.toArray(new PsiReference[psiReferences.size()]);
    }
}
