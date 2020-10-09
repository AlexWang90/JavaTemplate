package test;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Created by alexwang on 2018/8/3.
 */
public class XmlParser {
    private Logger logger = LoggerFactory.getLogger(XmlParser.class);

    public void testParser(){
        System.out.println(this.getClass().getClassLoader().getResource("test.xml"));
        File xmlFile = new File(this.getClass().getClassLoader().getResource("test.xml").getFile());
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("student");
            for(int i=0; i< nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    System.out.println("Student rollno:" + element.getAttribute("rollno"));
                    System.out.println("First Name:" + element.getElementsByTagName("firstname").item(0).getTextContent());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        XmlParser xmlParser = new XmlParser();
        xmlParser.testParser();
    }
}
