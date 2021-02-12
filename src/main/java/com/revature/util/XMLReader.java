package com.revature.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class XMLReader {

    private static XMLReader reader = new XMLReader();
    private static Set<Database> databaseSet;

    private XMLReader() {
        databaseSet = new HashSet<>();

        File inputFile = new File("src/main/resources/aestivate.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;

        try {
            builder = dbFactory.newDocumentBuilder();
            doc = builder.parse(inputFile);
            NodeList databaseNodes = doc.getElementsByTagName("Database");
            XMLReader.parseXML(databaseNodes);
        } catch (ParserConfigurationException e) {
            System.out.println("Error in creating the builder");
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("SAX exception found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("aestivate.xml file not found in resources");
            e.printStackTrace();
        } catch (XMLParseException e) {
            System.out.println("Missing elements or attributes from database configuration in XML file");
            e.printStackTrace();
        }
    }

    private static void parseXML(NodeList databaseNodes) throws XMLParseException {
        if (databaseNodes.getLength() == 0) throw new XMLParseException("No Database tags found");

        for (int i = 0; i < databaseNodes.getLength(); i++) {
            Database db = new Database();
            Node node = databaseNodes.item(i);

            // We made a NodeList of Database elements, so any item in the list will be an element
            Element database = (Element) node;
            db.setDatabase(database.getAttribute("name"));

            // The configuration should have one Url element, with an attribute url set to a string
            // To get there, get the Url element from database element, which there should be exactly one of,
            // cast as element, then get attribute for url, which is a string of the url.
            // This is the same for login name and password

            Element url = (Element) database.getElementsByTagName("Url").item(0);
            db.setUrl(url.getAttribute("url"));

            Element login = (Element) database.getElementsByTagName("Login").item(0);
            db.setLoginName(login.getAttribute("login"));

            Element password = (Element) database.getElementsByTagName("Password").item(0);
            db.setPassword(password.getAttribute("password"));

            if (Database.validate(db)) {
                throw new XMLParseException("Missing one or more elements or attributes from Database configuration" +
                        " in the XML file");
            }

            databaseSet.add(db);
            System.out.println(db.toString());
        }
    }

    public static Set<Database> getDatabaseSet() {
        return databaseSet;
    }

    public static class Database {
        String sqlDatabase;
        String url;
        String loginName;
        String password;

        public Database(){
            this.sqlDatabase = null;
            this.url = null;
            this.loginName = null;
            this.password = null;
        }

        public static boolean validate(Database database) {
            return ((database.getSqlDatabase() == null || database.getSqlDatabase().trim().equals("")) ||
                    (database.getUrl() == null || database.getUrl().trim().equals("")) ||
                    (database.getLoginName() == null || database.getLoginName().trim().equals("")) ||
                    (database.getPassword() == null || database.getPassword().equals("")));
        }

        public String getSqlDatabase() {
            return sqlDatabase;
        }

        public String getUrl() {
            return url;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setDatabase(String sqlDatabase) {
            this.sqlDatabase = sqlDatabase;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Database database1 = (Database) o;
            return Objects.equals(sqlDatabase, database1.sqlDatabase) && Objects.equals(url, database1.url) && Objects.equals(loginName, database1.loginName) && Objects.equals(password, database1.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sqlDatabase, url, loginName, password);
        }

        @Override
        public String toString() {
            return "Database{" +
                    "sqlDatabase='" + sqlDatabase + '\'' +
                    ", url='" + url + '\'' +
                    ", loginName='" + loginName + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
