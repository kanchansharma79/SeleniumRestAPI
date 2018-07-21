package googleAPIs;

public class GoogleMapsResources {

	public static String placeJsonGetData()
	{	
		String res="/maps/api/place/nearbysearch/json";
		return res;
	}
	
	//You can call method from the class name with className.method if that method is defined with static
	public static String placePostData()
	{	
		String res="/maps/api/place/add/json";
		return res;
	}
	
	public static String placeDeleteData()
	{	
		String res="/maps/api/place/delete/json";
		return res;
	}
	
	public static String placeXmlPostData()
	{	
		String res="/maps/api/place/add/xml";
		return res;
	}
	
}
