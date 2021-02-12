import com.revature.util.XMLReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class xmlReader {

    public static void main(String[] args) {
        Set<XMLReader.Database> set = XMLReader.getDatabaseSet();


       /* try {
            File inputFile = new File("src/main/resources/aestivate.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);

            doc.getDocumentElement().normalize();

            System.out.println("The document location is: "+doc.getDocumentURI());
            System.out.println("Root element: "+doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("Database");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                System.out.println("The current Element: "+node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println(element.getAttribute("name"));

                    NodeList confInfo = element.getElementsByTagName("Url");
                    Node node1 = confInfo.item(0);

                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                        Element url = (Element) node1;
                        System.out.println("url is: "+url.getAttribute("url"));
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("SAX exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
        }*/
    }
}
