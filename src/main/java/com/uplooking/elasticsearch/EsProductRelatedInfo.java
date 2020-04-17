package com.uplooking.elasticsearch;

import java.util.List;

public class EsProductRelatedInfo {
    public void setBrandNames(List<String> brandNameList) {
    }

    public void setProductCategoryNames(List<String> productCategoryNameList) {
    }

    public void setProductAttrs(List<ProductAttr> attrList) {
    }

    public static class ProductAttr {
        public void setAttrId(Long key) {
        }

        public void setAttrValues(List<String> attrValueList) {
        }

        public void setAttrName(String attrName) {
        }
    }
}
