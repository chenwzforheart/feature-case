package com.cwzsmile.tool;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc on 2019-06-29.
 */
public class JpaEntityPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        TopLevelClass topLevelClass = new TopLevelClass(properties.getProperty("targetPackage") + "." + GeneratorUtil.type2Name(introspectedTable.getBaseRecordType()) + properties.getProperty("suffix"));
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType("java.io.Serializable");
        topLevelClass.addImportedType("javax.persistence.Entity");
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@Entity");
        Field serial = new Field();
        serial.setVisibility(JavaVisibility.PRIVATE);
        serial.setStatic(true);
        serial.setFinal(true);
        serial.setType(new FullyQualifiedJavaType("long"));
        serial.setName("serialVersionUID");
        serial.setInitializationString("1L");
        topLevelClass.addField(serial);

        //表注释
        //1.Table的注释
        String tableRemark = "";
        try {
            tableRemark = ColumnRemarkPlugin.getTableRemark(introspectedTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + tableRemark.replaceAll("\r|\n|\r\n", ""));
        topLevelClass.addJavaDocLine(" */");

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {

            Field field = GeneratorUtil.getJavaBeansField(introspectedColumn);
            topLevelClass.addField(field);
            topLevelClass.addImportedType(field.getType());

            //数据库备注
            String remark = introspectedColumn.getRemarks();
            if (remark != null && remark.trim().length() > 0 && !"null".equals(remark)) {
                field.addJavaDocLine("/**");
                field.addJavaDocLine(" * " + remark.replaceAll("\r|\n|\r\n", ""));
                field.addJavaDocLine(" */");
            }

            //ID注解
            if ("ID".equalsIgnoreCase(introspectedColumn.getActualColumnName())) {
                field.addAnnotation("@Id");
                field.addAnnotation("@GeneratedValue");
                topLevelClass.addImportedType("javax.persistence.Id");
                topLevelClass.addImportedType("javax.persistence.GeneratedValue");
            }

            //Column注解
            field.addAnnotation("@Column" + (introspectedColumn.isNullable() ? "" : "(nullable = false)"));
            topLevelClass.addImportedType("javax.persistence.Column");
        }

        List<GeneratedJavaFile> answer = new ArrayList();
        GeneratedJavaFile gjf = new GeneratedJavaFile(topLevelClass,
                properties.getProperty("targetProject"),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
        answer.add(gjf);
        return answer;
    }
}
