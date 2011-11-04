package se.digitalmannen.svTwUni;


import java.net.URL;
import java.net.MalformedURLException;

// Replaces URLs with html hrefs codes
public class URLInString {
    public static String  makeURLInString(String s) {
    	
    	StringBuffer strb = new StringBuffer ();
//    	  strb.append (" a new string object");
//    	  System.out.println (strb.toString());
        // separete input by spaces ( URLs don't have spaces )
        String [] parts = s.split("\\s");

        // Attempt to convert each item into an URL.   
        for( String item : parts ) try {
            URL url = new URL(item);
            // If possible then replace with anchor...
            //System.out.print("<a href=\"" + url + "\">"+ url + "</a> " );
            strb.append("<a href=\"" + url + "\" target='_blank'>"+ url + "</a> " );
        } catch (MalformedURLException e) {
            // If there was an URL that was not it!...
            //System.out.print( item + " " );
            strb.append(item+ " ");
        }

        //System.out.println();
        return strb.toString();
    }
}

