package com.cwzsmile.tool;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csh9016 on 2019/5/6.
 */
public class DomainClassPlugin extends PluginAdapter {


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        TopLevelClass topLevelClass = new TopLevelClass(properties.getProperty("targetPackage") + "." + GeneratorUtil.type2Name(introspectedTable.getBaseRecordType()) + properties.getProperty("suffix"));
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addAnnotation("@Data");

        for (IntrospectedColumn introspectedColumn : introspectedColumns) {

            Field field = GeneratorUtil.getJavaBeansField(introspectedColumn);
            topLevelClass.addField(field);
            topLevelClass.addImportedType(field.getType());

            String remark = introspectedColumn.getRemarks();
            if (remark != null && remark.trim().length() > 0 && !"null".equals(remark)) {
                field.addJavaDocLine("/**");
                field.addJavaDocLine(" * " + remark.replaceAll("\r|\n|\r\n", ""));
                field.addJavaDocLine(" */");
            }
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
