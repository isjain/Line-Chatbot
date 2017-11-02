public class Recommendation{
	private String menu;
	private float cal;
	
	public String setMenu(String m) {menu=m; }
	public float setCal(float c) {cal=c; }
	
	public String getMenu() {return menu; }
	public float getCal() {return cal; }

	public Recommendation[] sortedMenu(Recommendation r [int n] ){
		
		for(int i=1; i<n;i++)
		{
			int diff=abs(r[i].cal-x);
			// Insert arr[i] at correct place
	        int j = i - 1;
	        if (abs(r[i].cal - x) > diff) {
	            recommendation temp = r[i];
	            while (abs(r[j].cal - x) > diff && j >= 0) {
	                r[j + 1] = r[j];
	                j--;
	            }
	            r[j + 1] = temp;
	        }
	    }
	
	} 

} 

