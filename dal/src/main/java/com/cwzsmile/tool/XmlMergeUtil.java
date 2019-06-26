package com.cwzsmile.tool;

import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DomWriter;
import org.mybatis.generator.internal.XmlFileMergerJaxp;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by csh9016 on 2019/6/26.
 * 覆盖同id的元素，其他新写的元素不影响
 */
public class XmlMergeUtil {

    public static String fileRead(File file) throws Exception {
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }

    public static String getMergedSource(GeneratedXmlFile generatedXmlFile,
                                         File existingFile) throws ShellException {

        try {
            return getMergedSource(generatedXmlFile.getFormattedContent(),
                    fileRead(existingFile), //$NON-NLS-1$
                    existingFile.getName());
        } catch (IOException e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        } catch (SAXException e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        } catch (ParserConfigurationException e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        } catch (Exception e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        }
    }

    public static String getMergedSource(String newFile,
                                         String existingFile,
                                         String existingFileName) throws IOException, SAXException,
            ParserConfigurationException, ShellException, Exception {

        // 创建xml解析器
        SAXReader saxReader = new SAXReader();
        org.dom4j.Document a = saxReader.read(new StringReader(newFile));
        org.dom4j.Document b = saxReader.read(new StringReader(existingFile));
        List<org.dom4j.Element> aa = a.selectNodes("/mapper/*[@id]");
        List<org.dom4j.Element> bb = b.selectNodes("/mapper/*[@id]");

        //新加元素
        org.dom4j.Document ret = (org.dom4j.Document) a.clone();
        List<org.dom4j.Element> retEle = ret.getRootElement().elements();
        retEle.forEach(s->{
            s.getParent().remove(s);
        });
        List<org.dom4j.Element> newAdd = new ArrayList<>();
        for (int i = 0; i < aa.size(); i++) {
            org.dom4j.Element tempA = aa.get(i);
            boolean mz = false;
            org.dom4j.Element tempB = null;
            for (int j = 0; j < bb.size(); j++) {
                tempB = bb.get(i);
                if (tempB.attribute("id").getValue().equals(tempA.attribute("id").getValue())) {
                    mz = true;
                    break;
                } else {
                    newAdd.add(tempB);
                }
            }
            if (mz) {
                ret.getRootElement().add(tempB);
            }else {
                ret.getRootElement().add(tempA);
            }
        }
        newAdd.forEach(s->{
            ret.add(s);
        });

        return "";
    }

    private static String prettyPrint(Document document) throws ShellException {
        DomWriter dw = new DomWriter();
        String s = dw.toString(document);
        return s;
    }

    private static boolean isWhiteSpace(Node node) {
        boolean rc = false;

        if (node != null && node.getNodeType() == Node.TEXT_NODE) {
            Text tn = (Text) node;
            if (tn.getData().trim().length() == 0) {
                rc = true;
            }
        }

        return rc;
    }

    private static class NullEntityResolver implements EntityResolver {
        /**
         * returns an empty reader. This is done so that the parser doesn't
         * attempt to read a DTD. We don't need that support for the merge and
         * it can cause problems on systems that aren't Internet connected.
         */
        @Override
        public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException {

            StringReader sr = new StringReader(""); //$NON-NLS-1$

            return new InputSource(sr);
        }
    }

}
