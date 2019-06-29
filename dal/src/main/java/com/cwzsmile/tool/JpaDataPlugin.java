package com.cwzsmile.tool;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc on 2019-06-29.
 */
public class JpaDataPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        String base = GeneratorUtil.type2Name(introspectedTable.getBaseRecordType());
        String jiekouName = properties.getProperty("targetPackage") + "." + base + "Repository";
        Interface jiekou = new Interface(jiekouName);
        jiekou.setVisibility(JavaVisibility.PUBLIC);
        jiekou.addImportedType(new FullyQualifiedJavaType("org.springframework.data.jpa.repository.JpaRepository"));
        jiekou.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType().replace(".model", ".entity")));
        jiekou.addSuperInterface(new FullyQualifiedJavaType("JpaRepository<" + base + ",Long>"));

        List<GeneratedJavaFile> answer = new ArrayList();
        GeneratedJavaFile gjf = new GeneratedJavaFile(jiekou,
                properties.getProperty("targetProject"),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
        answer.add(gjf);
        return answer;
    }
}
