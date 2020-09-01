package tools.util.decoder;

import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Class Decoder
 * Object for generating all combinations of a string.
 */
public class Decoder 
{
	private final char filler = '-';
	private String letters;
	private ArrayList<String> prefixes = new ArrayList<String>();
	private ArrayList<String> suffixes = new ArrayList<String>();
	private ArrayList<String> contains = new ArrayList<String>();
	private ArrayList<String> regexes = new ArrayList<String>();
	private ArrayList<String> excludes = new ArrayList<String>();
	
	/**
	 * Decoder constructor
	 * @param letters
	 */
	public Decoder(String letters)
	{
		this.letters = letters;
	}
	
	/**
	 * Set prefix
	 * @param String prefix
	 */
	public void addPrefix(String prefix)
	{
		this.prefixes.add(prefix);
	}
	
	/**
	 * Set suffix
	 * @param String suffix
	 */
	public void addSuffix(String suffix)
	{
		this.suffixes.add(suffix);
	}
	
	/**
	 * Set contains
	 * @param String contains
	 */
	public void addContains(String contain)
	{
		this.contains.add(contain);
	}
	
	/**
	 * Add regular expression.
	 * @param String regex
	 */
	public void addRegex(String regex)
	{
		this.regexes.add(regex);
	}
	
	/**
	 * Add exclude.
	 * @param String exclude
	 */
	public void addExclude(String exclude)
	{
		this.excludes.add(exclude);
	}
	
	/**
	 * Reset all conditions.
	 */
	public void reset()
	{
		this.prefixes.clear();
		this.suffixes.clear();
		this.contains.clear();
		this.regexes.clear();
		this.excludes.clear();
	}
	
	/**
	 * Get string combinations.
	 * @return ArrayList<String>
	 * @throws Exception 
	 */
	public Hashtable<String, Integer> getCombinations(int wordLength) throws Exception
	{
		return this.getPermutations("", this.letters, new Hashtable<String, Integer>(), wordLength);
	}
	
	/**
	 * Get string permutations.
	 * @param prefix
	 * @param letters
	 * @param permutations
	 * @return ArrayList<String>
	 * @throws Exception 
	 */
	private Hashtable<String, Integer> getPermutations(String prefix, String letters, Hashtable<String, Integer> permutations, int difference) throws Exception
	{
		int lettersLength = letters.length();
		if(lettersLength < difference || difference < 0) { throw new Exception("Unable to handle word size."); }
	    if (lettersLength == difference)
	    {
	    	if(this.isAllowed(prefix) && !permutations.containsKey(prefix)) 
	    	{
	    		if(!permutations.containsKey(prefix))
	    		{
	    			permutations.put(prefix, 1);
	    		}
	    		else
	    		{
	    			permutations.put(prefix, permutations.get(prefix)+1);
	    		}
	    	}
	    	return permutations;
	    }
	    for (int i = 0; i < lettersLength; i++)
	    {
	    	getPermutations(prefix + letters.charAt(i), letters.substring(0, i) + letters.substring(i+1, lettersLength), permutations, difference);
	    }
	    return permutations;
	}
	
	/**
	 * Check if permutation passes rules.
	 * @param String word
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean isAllowed(String word) throws Exception
	{
    	return this.isPrefixes(word)
    		&& this.isSuffixes(word)
    		&& this.isContains(word)
    		&& this.isRegexes(word)
    		&& this.isExcludes(word);
	}
	
	/**
	 * Has listed prefixes.
	 * @param String word
	 * @return boolean
	 */
	private boolean isPrefixes(String word)
	{
		ArrayList<String> prefixes = this.prefixes;
		for(String prefix: prefixes)
		{
			if(word.startsWith(prefix)) { return true; }
		}
		return prefixes.isEmpty() || false;
	}
	
	/**
	 * Has listed suffixes
	 * @param String word
	 * @return boolean
	 */
	private boolean isSuffixes(String word)
	{
		ArrayList<String> suffixes = this.suffixes;
		for(String suffix: suffixes)
		{
			if(word.endsWith(suffix)) { return true; }
		}
		return suffixes.isEmpty() || false;
	}
	
	/**
	 * Has listed contains.
	 * @param String word
	 * @return boolean
	 */
	private boolean isContains(String word)
	{
		ArrayList<String> contains = this.contains;
		for(String contain: contains)
		{
			if(word.contains(contain)) { return true; }
		}
		return contains.isEmpty() || false;
	}
	
	/**
	 * Has listed regular expressions.
	 * @param String word
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean isRegexes(String word) throws Exception
	{
		ArrayList<String> regexes = this.regexes;
		for(String regex: regexes)
		{
			if(this.hasMatching(word, regex)) { return true; }
		}
		return regexes.isEmpty() || false;
	}
	
	/**
	 * Is not in listed excludes list.
	 * @param String word
	 * @return boolean
	 * @throws Exception
	 */
	private boolean isExcludes(String word) throws Exception
	{
		ArrayList<String> excludes = this.excludes;
		for(String exclude: excludes)
		{
			if(this.hasMatching(word, exclude)) { return false; }
		}
		return true;
	}
	
	/**
	 * Has matching expression.
	 * @param String word
	 * @param String regex
	 * @return boolean
	 * @throws Exception
	 */
	private boolean hasMatching(String word, String regex) throws Exception
	{
		if(word.length() != regex.length()) { throw new Exception("Error: Regex length does not match word length."); }
		for(int i = 0; i < regex.length(); i++)
		{
			if(regex.charAt(i) == this.filler) { continue; }
			if(regex.charAt(i) != word.charAt(i)) { return false; }
		}
		return true;
	}
}
