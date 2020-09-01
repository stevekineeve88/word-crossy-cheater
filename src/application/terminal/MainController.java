package application.terminal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import tools.util.decoder.Decoder;

public class MainController 
{
	private final static String PREFIX = "-prefix";
	private final static String SUFFIX = "-suffix";
	private final static String CONTAINS = "-contains";
	private final static String REGEX = "-regex";
	private final static String EXCLUDE = "-exclude";
	
	/**
	 * Main controller for decoder in terminal.
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		try
		{
			if(args.length < 2) { throw new Exception("Not enough arguments provided."); }
			String wordInput = args[0].toUpperCase();
			Decoder decoder = new Decoder(wordInput);
			String command = "";
			for(int i = 2; i < args.length; i++)
			{
				if(command.equals(""))
				{
					command = args[i];
					continue;
				}
				String arg = args[i].toUpperCase();
				String[] argArray = arg.split(",");
				switch(command)
				{
					case MainController.PREFIX:
						addRule(decoder, argArray, "addPrefix");
						break;
					case MainController.SUFFIX:
						addRule(decoder, argArray, "addSuffix");
						break;
					case MainController.CONTAINS:
						addRule(decoder, argArray, "addContains");
						break;
					case MainController.REGEX:
						addRule(decoder, argArray, "addRegex");
						break;
					case MainController.EXCLUDE:
						addRule(decoder, argArray, "addExclude");
						break;
					default:
						throw new Exception("Command " + command + " not found.");
				}
				command = "";
			}
			Hashtable<String, Integer> combinations = decoder.getCombinations(wordInput.length() - Integer.parseInt(args[1]));
			ArrayList<String> words = new ArrayList<String>(combinations.keySet());
			System.out.println("------------");
			for(String word: words)
			{
				System.out.println(word);
			}
			System.out.println("------------");
		}
		catch(Exception e)
		{
			System.out.println("ERROR: "+e.getMessage());
			System.out.println("------------");
			System.out.println("The following is acceptable criteria:");
			System.out.println("<WORD> <WORDLENGTH> -prefix <LIST> -suffix <LIST> -contains <LIST> -regex <LIST> -exclude <LIST>");
		}
	}
	
	/**
	 * 
	 * @param decoder
	 * @param args
	 * @param method
	 * @throws Exception
	 */
	private static void addRule(Decoder decoder, String[] args, String method) throws Exception
	{
		Method invokedMethod = Decoder.class.getDeclaredMethod(method, String.class);
		for(String arg: args)
		{
			invokedMethod.invoke(decoder, arg);
		}
	}
}
