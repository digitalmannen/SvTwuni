package se.digitalmannen.svTwUni;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Tweet;

public class WriteTable {
	private List<Tweet> lTweets = new ArrayList<Tweet>();
	private List<Tweet> tTweets = new ArrayList<Tweet>();

	
	public void writeTableToFile(GetLectureTweets lt, GetTagTweet tt, String tag, String tagStartDate, String tagStartTime, String tagEndDate, String tagEndTime, String svTwUni, String lectureStartDate, String lectureStartTime, String lectureEndDate, String lectureEndTime, String lectureName){
		
		//lTweets =  lt.getTweetArray("dmutv", "2011-11-01", "22:59:00", "2011-11-01", "23:01:00");
		//tTweets = tt.getTweetArray("tsttwsv", "2011-11-01", "22:59:00", "2011-11-01", "23:01:00");
		//FetchLectureProssesLabel.setText("Working...");
		lTweets =  lt.getTweetArray(svTwUni,lectureStartDate,lectureStartTime,lectureEndDate,lectureEndTime);
		//FetchLectureProssesLabel.setText("Done");
		//FetchtagProsseslabel.setText("Working...");
		tTweets = tt.getTweetArray(tag,tagStartDate,tagStartTime,tagEndDate,tagEndTime);
		//FetchtagProsseslabel.setText("Done");
		 Tweet[] ltArray = new Tweet[lTweets.size()];
		 lTweets.toArray(ltArray);
		 
		 Tweet[] ttArray = new Tweet[tTweets.size()];
		 tTweets.toArray(ttArray);
		 
		 //Progressbaeren
		// writeProgressBar.setMaximum(ltArray.length-1);
		// writeProgressBar.setIndeterminate(true);
		 //start the writing
		try {
			// Create file 
			  FileWriter fstream = new FileWriter(tag+".txt");
			  BufferedWriter outFile = new BufferedWriter(fstream);
			 
			  outFile.write("<table border=1>\n");
			 outFile.write("<tr><td><h3>Lecturer</h3><h4>"+ lectureName + "</h4></td><td><h3>Room</h3><h4>#"+ tag + "</h4></td></tr>\n");
			 for (int i=0; i < ltArray.length;i++){
				 outFile.write("<tr>\n");
				 outFile.write("<td valign='top' width='55%'>" + "<span class='timesince'>" + ltArray[i].getCreatedAt() + "</span><br />" + URLInString.makeURLInString(ltArray[i].getText()) + "</td><td bgcolor='#E0E0E0' valign='top'> <td>\n</tr>\n");
				 //F8F8F8
				 //outFile.write("<tr><td> </td><td bgcolor='#E0E0E0' valign='top'>");
				 if (i < ltArray.length -1){
					 for (int j =0; j < ttArray.length; j++){
						 if (ttArray[j].getCreatedAt().after(ltArray[i].getCreatedAt()) 
								 && ttArray[j].getCreatedAt().before(ltArray[i+1].getCreatedAt())){
							 outFile.write("<tr><td> </td><td bgcolor='#E0E0E0' valign='top'>");
							 outFile.write("<span class='timesince'>" + ltArray[i].getCreatedAt() + "</span><br />" + "<a href='https://twitter.com/#!/"
									 + ttArray[j].getFromUser() 
									 +  "' target='_blank'>@" +ttArray[j].getFromUser() + "</a>\n");
							 outFile.write(URLInString.makeURLInString(ttArray[j].getText()) + "</td></tr>\n"); 
						 }	 
					 }
				 }
				 else {
					 for (int j =0; j < ttArray.length; j++){
						 if (ttArray[j].getCreatedAt().after(ltArray[i].getCreatedAt())){
							 outFile.write("<tr><td> </td><td bgcolor='#E0E0E0' valign='top'>");
							 outFile.write("<span class='timesince'>" + ltArray[i].getCreatedAt() + "</span><br />" + "<a href='https://twitter.com/#!/"
									 + ttArray[j].getFromUser() 
									 +  "' target='_blank'>@" +ttArray[j].getFromUser() + "</a>\n");
							 outFile.write(URLInString.makeURLInString(ttArray[j].getText()) + "</td></tr>\n"); 
						 }	 
					 }
					 
				 }
					 
				 //outFile.write("</td>");
				 //outFile.write("</tr>\n");
				// writeProgressBar.setValue(i);
				// writeProgressBar.repaint();
				
				 
				 
			 }
			 outFile.write("</table>\n");
			//Close the output stream
			 outFile.close();
			 //System.out.println("Done");
			 //writeProgressBar.setIndeterminate(false);
			 
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}


	
	
	
	
}
