package com.cwzsmile.tool;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csh9016 on 2019/5/6.
 */
public class ServiceClassPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        String jiekouName = properties.getProperty("targetPackage") + "." + GeneratorUtil.type2Name(introspectedTable.getBaseRecordType()) + "Service";
        Interface jiekou = new Interface(jiekouName);
        jiekou.setVisibility(JavaVisibility.PUBLIC);

        String shixianName = properties.getProperty("targetPackage") + ".impl." + GeneratorUtil.type2Name(introspectedTable.getBaseRecordType()) + "ServiceImpl";
        TopLevelClass shixian = new TopLevelClass(shixianName);
        shixian.setVisibility(JavaVisibility.PUBLIC);
        shixian.addSuperInterface(new FullyQualifiedJavaType(jiekouName));

        shixian.addImportedType(jiekouName);
        shixian.addImportedType(introspectedTable.getMyBatis3JavaMapperType());
        shixian.addImportedType("org.springframework.stereotype.Service"); //$NON-NLS-1$
        shixian.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        shixian.addAnnotation("@Service");
        Field mapper = new Field();
        mapper.setType(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        mapper.setName(GeneratorUtil.type2Variable(introspectedTable.getMyBatis3JavaMapperType()));
        mapper.setVisibility(JavaVisibility.PRIVATE);
        mapper.addAnnotation("@Autowired");
        shixian.addField(mapper);


        List<GeneratedJavaFile> answer = new ArrayList();
        GeneratedJavaFile gjf = new GeneratedJavaFile(jiekou,
                properties.getProperty("targetProject"),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
        GeneratedJavaFile gjf1 = new GeneratedJavaFile(shixian,
                properties.getProperty("targetProject"),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
        answer.add(gjf);
        answer.add(gjf1);
        return answer;
    }
}
