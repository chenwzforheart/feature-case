package com.cwzsmile.tool;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csh9016 on 2019/6/25.
 */
public class BatchInsertPlugin extends PluginAdapter {

    private List<XmlElement> xmlElementsToAdd;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 加入XML元素
     *
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<XmlElement> elements = this.xmlElementsToAdd;
        if (elements != null) {
            for (XmlElement element : elements) {
                document.getRootElement().addElement(element);
            }
        }

        return true;
    }

    /**
     * 加入接口方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3) {
            addMethod(method, interfaze, introspectedTable);
        }
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3) {
            try {
                this.xmlElementsToAdd = new ArrayList<>();
                this.xmlElementsToAdd.add(generateBatchinsert(introspectedTable));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return false;
    }

    private void addMethod(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        //batch insert
        Method insertBachMethod = new Method("insertBatch");
        insertBachMethod.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + ">"), "list"));
        interfaze.addMethod(insertBachMethod);
    }

    private XmlElement generateBatchinsert(IntrospectedTable introspectedTable) {
        XmlElement insertElement = new XmlElement("insert");
        insertElement.addAttribute(new Attribute("id", "insertBatch"));
        insertElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
        insertElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        TextElement intoText = new TextElement("insert into " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() + " ( ");
        insertElement.addElement(intoText);
        //columnList
        String acls = "";
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            if (!"".equals(acls)) acls += ",";
            acls += introspectedColumn.getActualColumnName();
        }
        TextElement valuesText = new TextElement(acls + " ) values ");
        insertElement.addElement(valuesText);
        //values
        XmlElement valuesOrder = new XmlElement("foreach");
        valuesOrder.addAttribute(new Attribute("collection", "list"));
        valuesOrder.addAttribute(new Attribute("item", "item"));
        valuesOrder.addAttribute(new Attribute("index", "index"));
        valuesOrder.addAttribute(new Attribute("separator", ","));
        String vls = " ( ";
        String cls = "";
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            if (!"".equals(cls)) cls += ",";
            cls += "#{item." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "}";
        }
        vls += cls + " ) ";
        valuesOrder.addElement(new TextElement(vls));
        insertElement.addElement(valuesOrder);
        return insertElement;
    }
}
