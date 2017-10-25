public class AllStringPermutations
{
	char[] array = new char[3];

	public static void main(String[] args)
	{
		new AllStringPermutations();
	}
	
	public AllStringPermutations()
	{
		array[0] = 'A';
		array[1] = 'B';
		array[2] = 'C';
		permute(array, 0, array.length - 1);
	}
	
	public void permute(char[] arr, int startIndex, int endIndex)
	{
		array = arr;
		if(startIndex == endIndex)
		{
			for(int i = 0; i < array.length; i++)
				System.out.print(array[i]);
			System.out.println();
		}
		
		else
		{
			for(int i = startIndex; i < array.length; i++)
			{
				swap(array, startIndex, i);
				permute(array, startIndex + 1, endIndex);
				swap(array, startIndex, i);
			}
		}
	}
	
	public void swap(char[] arr, int x, int y)
	{
		char temp;
		temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}
	
}