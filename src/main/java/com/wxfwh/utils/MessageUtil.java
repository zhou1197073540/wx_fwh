package com.wxfwh.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wxfwh.exception.AesException;
import com.wxfwh.message.ImageMessage;
import com.wxfwh.message.TextMessage;
import com.wxfwh.wxAes.WXBizMsgCrypt;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wxfwh.constant.Constant.appId;
import static com.wxfwh.constant.Constant.encodingAesKey;
import static com.wxfwh.constant.Constant.token;

public class MessageUtil {
    /**
     * 解析微信发来的请求（XML）
     */
    @SuppressWarnings("unchecked")
    public static Map parseXmlToMap(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map map = new HashMap();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    public static String parseXmlToXmlStr(HttpServletRequest request) throws Exception {
        InputStream is = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        // 没次读取的内容
        String line = null;
        // 最终读取的内容
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        is.close();
        return sb.toString();
    }

    public static Map<String, String> decryptMessage(String msgSignature,String timestamp,String nonce,HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        String xmlStr=parseXmlToXmlStr(request);
        String result2 = pc.decryptMsg(msgSignature, timestamp, nonce,xmlStr);
        Document doc = DocumentHelper.parseText(result2);
//		// 得到跟节点
		Element root = doc.getRootElement();
        return parseXml2Map(root, map);
    }

    private static Map<String, String> parseXml2Map(Element root, Map<String, String> map) {
        List children = root.elements();
        if(children != null && children.size() > 0) {
            for(int i = 0; i < children.size(); i++) {
                Element child = (Element)children.get(i);
                map.put(child.getName(), child.getTextTrim());
            }
        }
        return map;
    }

    public static String ecryptMsg(String replayMsg,String timeStamp, String nonce) {
        WXBizMsgCrypt pc;
        String result ="";
        try {
            pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            result = pc.encryptMsg(replayMsg, timeStamp, nonce);
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 扩展xstream使其支持CDATA
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;
                String name=null;
                public void startNode(String names, Class clazz) {
                    name=names;
                    super.startNode(name, clazz);
                }
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata && !"CreateTime".equals(name)) {
                        writer.write(String.format("<![CDATA[%s]]>",text));
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String messageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(textMessage);
    }

    /**
     * 图片消息对象转换成xml
     *
     * @param imageMessage 图片消息对象
     * @return xml
     */
    public static String messageToXml(ImageMessage imageMessage) {
        xstream.alias("xml", imageMessage.getClass());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(imageMessage);
    }

    /**
     * 语音消息对象转换成xml
     *
     * @param voiceMessage 语音消息对象
     * @return xml
     */
//    public static String messageToXml(VoiceMessage voiceMessage) {
//        xstream.alias("xml", voiceMessage.getClass());
//        return xstream.toXML(voiceMessage);
//    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
//    public static String messageToXml(NewsMessage newsMessage) {
//        xstream.alias("xml", newsMessage.getClass());
//        xstream.alias("item", new Article().getClass());
//        return xstream.toXML(newsMessage);
//    }
}
