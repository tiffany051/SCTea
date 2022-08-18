package csci201_project;

public class post {
	private int postID;
	private String content;
	private String userID;
	private String fakeName;
	private String timestamp;
	private int upvoteNum;
	private String title;

	public int get_postID() {return postID;}
	public String get_userID() {return userID;}
	public String get_content() {return content;}
	public String get_fakeName() {return fakeName;}
	public String get_timestamp() {return timestamp;}
	public int get_upvoteNum() {return upvoteNum;}
	public String get_title() {return title;}


	public void set_postID(int mpostID) {postID = mpostID;}
	public void set_userID(String muserID) {userID = muserID;}
	public void set_content(String mcontent) {content = mcontent;}
	public void set_fakeName(String mfakeName) {fakeName = mfakeName;}
	public void set_timestamp(String mtimestamp) {timestamp = mtimestamp;}
	public void set_upvoteNum(int mupvoteNum) {upvoteNum = mupvoteNum;}
	public void set_title(String mtitle) {title = mtitle;}


	public post(int postID, String userID, String content,  String fakeName, String timestamp, int upvoteNum, String title) {
		set_postID(postID);
		set_userID(userID);
		set_content(content);
		set_timestamp(timestamp);
		set_upvoteNum(upvoteNum);
		set_title(title);
	}
}
