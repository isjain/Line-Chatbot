private void handleTextContent(String replyToken, Event event, TextMessageContent content)

    	    	String text = content.getText();
	           	String reply = null;
            	String[] arr = text.split(",")
            	ArrayList<String> arr2 = new ArrayList<String>();
            	for(String ss: arr){
            		arr2.add(getFOOD(ss));
            	}
            	reply = getRecommendation(arr2);
                // log.info("Returns echo message {}: {}", replyToken, reply);
                this.replyText(
                        replyToken,
                        reply
                );
                break;
        }
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

public String getFOOD(String s){
    	ArrayList<String> FOOD = new ArrayList<String>();

		try {

			Connection con = getConnection();
			PreparedStatement smt = con.prepareStatement("SELECT DESCRIPTION FROM TABLE_NAME ");
			ResultSet rs = smt.executeQuery();
			while(rs.next())
			{
				FOOD.add(rs.getString("COLUMN_NAME"));
			}
			rs.close();
			smt.close();
			con.close();
		}catch (Exception e) {
			System.out.println(e);
		}
		// TODO: find the word with minimum edit distance
		String closestFOOD = FOOD[0];
		int min_dist = dist(s, FOOD[0]);
		for(int index = 1; index < FOOD.length; index++){
			int dist = dist(s, FOOD[index]);
			if(dist < min_dist){
				min_dist = dist;
				closestFOOD = FOOD[index];
			}
		}
		if (min_dist <= 3) {
			return closestFOOD;
		}else{
			return null;
		}
	}