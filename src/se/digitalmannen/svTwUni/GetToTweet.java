package se.digitalmannen.svTwUni;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class GetToTweet {
	//private Tweet[] tweetArray;
	private List<Tweet> allTweets = new ArrayList<Tweet>();
	public GetToTweet(){
		
	}
	
	/**
	 * @param tag - the #tag to follow
	 * @param sDate - start Date
	 * @param sTime - start Time
	 * @param eDate - end Date
	 * @param eTime - end Time
	 * @return - list of the tweets
	 */
	public List<Tweet> getTweetArray(String user, String sDate, String sTime, String eDate, String eTime){
		int page =1;
		//int i=1;
		Twitter twitter = new TwitterFactory().getInstance();
		//Query query = new Query("#tsttwsv since:2011-11-01");
		//Query query = new Query("#svpol  since:2011-11-02");
		//Query query = new Query("from:SvTwuni since:2011-10-04");
		//Query query = new Query("from:dmutv since:2011-11-01");
		
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dfm.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
		//set tomorrow
		Date now = new Date();
		DateFormat dfmToMorrow = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();  
		try {
			now = dfmToMorrow.parse(eDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(now);  
		cal.add(Calendar.DAY_OF_YEAR, 1); // <--  
		//Date teDate = cal.getTime();  
		
		//Start time
		Date startTime = new Date();
		try {
			startTime = dfm.parse(sDate + " " + sTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//end time
		Date endTime = new Date();
		try {
			endTime = dfm.parse(eDate + " " + eTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//start the query
		//Query query = new Query("#" + tag +" since:" + sDate + " until:" + teDate);
		Query query = new Query("to:" + user  + " since:" + sDate);// + " until:" + teDate);

	    try {
	    	while(page !=-1 && page < 16) {
		    	query.setPage(page);
		    	query.rpp(100);
		    	page++;
		        QueryResult result = twitter.search(query);
		        
		       
		        List<Tweet> tweets = result.getTweets();
		        if(tweets.isEmpty()){
		        	page =-1;
		        }
//		        progressBar.setMaximum(tweets.size());
		        for (Tweet tweet : tweets) {
		        	if(tweet.getCreatedAt().after(startTime) && !tweet.getCreatedAt().after(endTime)){
		        		//Remove tes uni twitter form feed
		        		if (!tweet.getFromUser().matches("svTwuni")){
		        			allTweets.add(tweet);
		        		}
		        	}
//		        	i++;
//		        	progressBar.setValue(i);
		        }
	        
	    	}
	    	Collections.reverse(allTweets);
	    	
	    	
	    } catch (TwitterException te) {
	        te.printStackTrace();
	        System.out.println("Failed to search tweets: " + te.getMessage());
	        }
	    return allTweets;
	}
}

