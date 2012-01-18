/**
 * @author martin
 * @version 1.01
 */


package se.digitalmannen.svTwUni;

import java.awt.EventQueue;

import javax.swing.JFrame;


import twitter4j.Tweet;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JProgressBar;

public class SvTwuni {

	private JFrame mainForm;
	private List<Tweet> lTweets = new ArrayList<Tweet>();
	private List<Tweet> tTweets = new ArrayList<Tweet>();
	
	private JTextField lecturerTextField;
	private JTextField tagTextField; 
	private JFormattedTextField startDateTextField;
	private JFormattedTextField startTimeTextField;
	private JCheckBox chckbxUseStartTime;
	private JFormattedTextField endDateTextField;
	private JFormattedTextField endTimeTextField;
	private JProgressBar writeProgressBar = new JProgressBar();
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private Date nowDate = new Date();
	
	private String lectureName;
	private String tag;
	private String tagStartDate;
	private String tagStartTime;
	private String tagEndDate; 
	private String tagEndTime;
	private String svTwUni = "svTwuni";
	private String lectureStartDate;
	private String lectureStartTime;
	private String lectureEndDate; 
	private String lectureEndTime;
	//file dialog
//	private JavaFilter fJavaFilter = new JavaFilter ();
//	private File fFile  = new File ("default.txt"); 
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SvTwuni window = new SvTwuni();
					window.mainForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SvTwuni() {
		initialize();
		//writeTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainForm = new JFrame();
		mainForm.setTitle("Svenska Twitteruniversitetet v 1.01");
		mainForm.setBounds(100, 100, 424, 212);
		mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainForm.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Lecturer");
		lblNewLabel.setBounds(12, 12, 70, 15);
		mainForm.getContentPane().add(lblNewLabel);
		
		lecturerTextField = new JTextField();
		lecturerTextField.setToolTipText("<html>the name of teh Lecturer</html>");
		lblNewLabel.setLabelFor(lecturerTextField);
		lecturerTextField.setBounds(12, 28, 114, 19);
		mainForm.getContentPane().add(lecturerTextField);
		lecturerTextField.setColumns(10);
		
		JLabel lbltag = new JLabel("#-Tag");
		lbltag.setBounds(12, 51, 70, 15);
		mainForm.getContentPane().add(lbltag);
		
		tagTextField = new JTextField();
		
		tagTextField.setToolTipText("<html>The hashtag with out # as prefix</html>");
		tagTextField.setBounds(12, 68, 114, 19);
		mainForm.getContentPane().add(tagTextField);
		tagTextField.setColumns(10);
		
		JLabel lblStratDate = new JLabel("Start Date");
		lblStratDate.setBounds(228, 12, 92, 15);
		mainForm.getContentPane().add(lblStratDate);
		
		startDateTextField = new JFormattedTextField(dateFormat);
		startDateTextField.setToolTipText("<html>Format yyyy-mm-dd</html>");
		startDateTextField.setValue(new Date());
		startDateTextField.setBounds(228, 28, 92, 19);
		mainForm.getContentPane().add(startDateTextField);
		
		JLabel lblStartTime = new JLabel("Start Time");
		lblStartTime.setBounds(335, 12, 84, 15);
		mainForm.getContentPane().add(lblStartTime);
		
		startTimeTextField = new JFormattedTextField(timeFormat);
		startTimeTextField.setToolTipText("<html>format HH:MM</html>");
		startTimeTextField.setValue(new Date());
		startTimeTextField.setBounds(332, 28, 75, 19);
		mainForm.getContentPane().add(startTimeTextField);
		
//		chckbxUseStartTime = new JCheckBox("Same start and end date ");
//		chckbxUseStartTime.setSelected(true);
//		chckbxUseStartTime.setBounds(228, 50, 211, 23);
//		mainForm.getContentPane().add(chckbxUseStartTime);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(228, 51, 92, 15);
		mainForm.getContentPane().add(lblEndDate);
		
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(335, 51, 70, 15);
		mainForm.getContentPane().add(lblEndTime);
		
		endDateTextField = new JFormattedTextField(dateFormat);
		
		endDateTextField.setToolTipText("<html>Format yyyy-mm-dd</html>");
		endDateTextField.setValue(new Date());
		endDateTextField.setBounds(228, 68, 92, 19);
		mainForm.getContentPane().add(endDateTextField);
		
		endTimeTextField = new JFormattedTextField(timeFormat);
		endTimeTextField.setToolTipText("<html>format HH:MM</html>");
		endTimeTextField.setValue(new Date());
		endTimeTextField.setBounds(332, 68, 75, 19);
		mainForm.getContentPane().add(endTimeTextField);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getAllTextFields();
				if (verfiyeTextField()){
					//TODO make to threads
					GetLectureTweets lt = new GetLectureTweets();
					GetTagTweet tt = new GetTagTweet();
					WriteTable wt = new WriteTable();
					wt.writeTableToFile(lt,tt,tag,tagStartDate,
							tagStartTime,tagEndDate,tagEndTime,
							svTwUni,lectureStartDate,lectureStartTime,
							lectureEndDate,lectureEndTime, lectureName);
					//writeTable();
				}
			}
		});
		btnGenerate.setBounds(290, 143, 117, 25);
		mainForm.getContentPane().add(btnGenerate);
		
		JLabel lblWritingTweetsTo = new JLabel("Writing tweets to file");
		lblWritingTweetsTo.setBounds(12, 99, 387, 15);
		mainForm.getContentPane().add(lblWritingTweetsTo);
		
		
		writeProgressBar.setBounds(12, 116, 395, 15);
		writeProgressBar.setMinimum(0);
		mainForm.getContentPane().add(writeProgressBar);
	}


	
	private void getAllTextFields(){
		tag = removeChar(tagTextField.getText(), '#');
		tagStartDate = startDateTextField.getText();
		tagStartTime = startTimeTextField.getText();
		tagEndDate = endDateTextField.getText(); 
		tagEndTime = endTimeTextField.getText();
		//svTwUni = "dmutv";
		lectureName = lecturerTextField.getText();
		lectureStartDate = tagStartDate;
		lectureStartTime = tagStartTime;
		lectureEndDate = tagEndDate; 
		lectureEndTime = tagEndTime;

	}
	
	private boolean verfiyeTextField(){
		boolean r = true;
//		if ( tagEndDate.isEmpty() || tagEndTime.isEmpty()  ){
//			JOptionPane.showMessageDialog(mainForm,
//				    "Fields text missing ",
//				    "Inane error",
//				    JOptionPane.ERROR_MESSAGE);
//			return false;
//		}
		
		if (lectureName.isEmpty()){
			JOptionPane.showMessageDialog(mainForm,
				    "Lecture text is empty",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tag.isEmpty()){
			JOptionPane.showMessageDialog(mainForm,
				    "Tag text is empty",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		else if (tagEndTime.compareTo(lectureStartTime) == 0 && tagEndDate.compareTo(lectureStartDate)==0){
			JOptionPane.showMessageDialog(mainForm,
				    "Start date and time are equal to end date and time\n Can not get any tweets  ",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tagStartDate.isEmpty()){
			JOptionPane.showMessageDialog(mainForm,
				    "Start date is empty ",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tagStartTime.isEmpty()){
			JOptionPane.showMessageDialog(mainForm,
				    "Start time is missing",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tagEndDate.isEmpty()){
			JOptionPane.showMessageDialog(mainForm,
				    "end date is missing",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tagEndTime.isEmpty()){
			JOptionPane.showMessageDialog(mainForm,
				    "end time is missing",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return r;
	}
	
	public String removeChar(String s, char c) {
	    String r = "";
	    for (int i = 0; i < s.length(); i ++) {
	       if (s.charAt(i) != c) r += s.charAt(i);
	       }
	    return r;
	}
	
	
}

