package myApp;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
public class ParsePeople {
	
	boolean inPeople = false;
	boolean inPerson = false;
	boolean inId = false;
	boolean inFirstName = false;
	boolean inLastName = false;
	boolean inCar = false;
	boolean inCity = false;
	boolean inCountry = false;
	
	Person currentPerson;
	List<Person> currentPeopleList;
	
	public List<Person> doParsePeople(String s){
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser pullParser = factory.newPullParser();
			pullParser.setInput(new StringReader(s));
			System.out.println(s);
			processDocument(pullParser);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return currentPeopleList;
	}
	public void processDocument(XmlPullParser pullParser) throws XmlPullParserException, IOException{
		int eventType = pullParser.getEventType();
		do {
			if(eventType == XmlPullParser.START_DOCUMENT) {
				System.out.println("Start document");
			} else if(eventType == XmlPullParser.END_DOCUMENT) {
				System.out.println("End document");
			}else if (eventType == XmlPullParser.START_TAG) {
				processStartElement(pullParser);
			} 
			else if (eventType == XmlPullParser.END_TAG) {
				processEndElement(pullParser);
			} 
			else if (eventType == XmlPullParser.TEXT) {
				processText(pullParser);
			}
			eventType = pullParser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
		}
	public void processStartElement(XmlPullParser event) {
		String name = event.getName();
		if (name.equals("people")) {
			inPeople = true;
			currentPeopleList = new ArrayList<Person>();
		} 
		else if(name.equals("person")) {
			inPerson = true;
			currentPerson = new Person();
		}
		else if (name.equals("id")) {
			inId = true;
		} 
		else if (name.equals("firstName")) {
			inFirstName = true;
		} 
		else if (name.equals("lastName")) {
			inLastName = true;
		} 
		else if (name.equals("car")) {
			inCar = true;
		}
		else if (name.equals("city")) {
			inCity = true;
		}
		else if (name.equals("country")) {
			inCountry = true;
		}
	}
	public void processText(XmlPullParser event) throws XmlPullParserException {
		if(inId) {
			String s = event.getText();
			currentPerson.setId(s);
		}
		if(inFirstName) {
			String s = event.getText();
			currentPerson.setFirstName(s);
		}
		if(inLastName) {
			String s = event.getText();
			currentPerson.setLastName(s);
		}
		if(inCar) {
			String s = event.getText();
			currentPerson.setCar(s);
		}
		if(inCity) {
			String s = event.getText();
			currentPerson.setCity(s);
		}
		if(inCountry) {
			String s = event.getText();
			currentPerson.setCountry(s);
		}
	}
	public void processEndElement(XmlPullParser event) {
		String name = event.getName();
		if (name.equals("people")) {
			inPeople = false;
		} 
		else if(name.equals("person")) {
			inPerson = false;
			currentPeopleList.add(currentPerson);
		}
		else if (name.equals("id")) {
			inId = false;
		} 
		else if (name.equals("firstName")) {
			inFirstName = false;
		} 
		else if (name.equals("lastName")) {
			inLastName = false;
		} 
		else if (name.equals("car")) {
			inCar = false;
		}
		else if (name.equals("city")) {
			inCity = false;
		}
		else if (name.equals("country")) {
			inCountry = false;
		}
	}
	
	
}
