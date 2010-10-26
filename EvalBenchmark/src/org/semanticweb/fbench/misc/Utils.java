package org.semanticweb.fbench.misc;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;

public class Utils {

	public static String dateToXsd(Calendar calendar) {
		return DatatypeConverter.printDateTime(calendar);
	}
	
	public static String nowToXsd() {
		return dateToXsd( new GregorianCalendar() );
	}
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println("now: " + nowToXsd());
	}
}
