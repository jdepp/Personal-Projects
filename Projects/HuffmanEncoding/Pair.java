public class Pair
{
	public final Character key;		//Character
	public final String value;		//Binary string that represents the Character
	
	public Pair(Character aKey, String aValue)	//constructor
	{
		key = aKey;
		value = aValue;
	}
	
	public Character getKey()
	{
		return key;
	}
	
	public String getValue()
	{
		return value;
	}
}