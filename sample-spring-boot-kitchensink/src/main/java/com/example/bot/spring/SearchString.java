
private void handleTextContent(String replyToken, Event event, TextMessageContent content)
{
	String text = content.getText();
    Recommendation[] robject = new Recommendation[];
    	String[] arr = text.split(",");
    	Recommendation[] sortedObject = new Recommendation[];
    	robject = database.search(arr);

    	sortedObject = sortedMenu(robject);
        // log.info("Returns echo message {}: {}", replyToken, reply);
        this.replyText(
                replyToken,
                robject.getMenu()
        );
        break;
}
  
    
    
public class WagnerFischer {
    private char[] s1;
   	private char[] s2;

  	public WagnerFischer(String s1, String s2) {
      	  this.s1 = s1.toLowerCase().toCharArray();
      	  this.s2 = s2.toLowerCase().toCharArray();
    }

	private int min(int a, int b, int c) {
	       	 return Math.min(a, Math.min(b, c));
	}

    /**
     * Using Dynamic Programming, the Wagner-Fischer algorithm is able to 
     * calculate the edit distance between two strings.
     * @return edit distance between s1 and s2
     */
    public int getDistance() {
      	  int[][] dp = new int[s1.length + 1][s2.length + 1];
	        for (int i = 0; i <= s1.length; dp[i][0] = i++);
	        for (int j = 0; j <= s2.length; dp[0][j] = j++);

	        for (int i = 1; i <= s1.length; i++) {
	            for (int j = 1; j <= s2.length; j++) {
	                if (s1[i - 1] == s2[j - 1]) {
	                    dp[i][j] = dp[i - 1][j - 1];
	                } else {
	                    dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, 
	                    		dp[i - 1][j - 1] + 1);
	                }
	            }
	        }
	        return dp[s1.length][s2.length];
    }
	public int dist(String input, String knownDrink){
		return (new WagnerFischer(input, knownDrink)).getDistance();
	}


// robject = database.search(arr);

	@Slf4j
public class SQLDatabaseEngine extends DatabaseEngine {
	@Override
	Recommendation[] search(String[] text) throws Exception {
		Recommendation[] result = new Recommendation[];;
		try {
			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT * FROM nutrienttable");
			smt.setString(1,text);
			ResultSet rs = smt.executeQuery();
			int i=0;
			while(rs.next())
			{ 
				result.setMenu(rs.getString("Description"));
				result.setCal(rs.getString("Energy"));
				i++;
			}
			rs.close();
			smt.close();
			con.close();
		}catch (Exception e) {
			System.out.println(e);
		}

		// To find closest ingredients possible
		Recommendation[] ingred = new Recommendation[];
		for(String s:text)
		{
			String closestFOOD = result[0].getMenu();
			Recommendation closestMatch = null;
			int min_dist = dist(s, result[0].getMenu());
			for(int index = 1; index < result.length; index++){
				int dist = dist(s, result[index].getMenu());
				if(dist < min_dist){
					min_dist = dist;
					closestFOOD = result[index].getMenu();
					closestMatch = result[index];
				}
				else if(dist ==0)
				{
					closestFOOD = result[index].getMenu();
					closestMatch = result[index];
					break;
				}
			}
			if (min_dist <= 3) {
				ingred.setMenu(closestFOOD);
				ingred.setCal(closestMatch.getCal());
			}
			else {
				ingred.setMenu(null);
				ingred.setCal(null);
			}
		}
		return ingred;
		
	}
	
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql:" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

}
