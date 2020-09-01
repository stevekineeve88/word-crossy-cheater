package tools.util.decoder.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Hashtable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.util.decoder.Decoder;

class DecoderTest {
	
	static Decoder decoder;
	static String word = "PUP";
	
	@BeforeAll
	static void init()
	{
		decoder = new Decoder(word);
	}
	
	@BeforeEach
	void initTest()
	{
		decoder.reset();
	}

	@Test
	void test_decoder_getCombinations() throws Exception 
	{
		this.checkList(new ArrayList<String>() {
			{
                add("PUP");
                add("UPP");
                add("PPU");
            } 
		});
	}
	
	@Test
	void test_decoder_getCombinationsWithPrefix() throws Exception
	{
		decoder.addPrefix("PU");
		this.checkList(new ArrayList<String>() {
			{ 
				add("PUP");
            } 
		});
	}
	
	@Test
	void test_decoder_getCombinationsWithSuffix() throws Exception
	{
		decoder.addSuffix("PU");
		this.checkList(new ArrayList<String>() {
			{ 
                add("PPU");
            } 
		});
	}
	
	@Test
	void test_decoder_getCombinationsWithContains() throws Exception
	{
		decoder.addContains("PU");
		this.checkList(new ArrayList<String>() {
			{ 
                add("PPU");
                add("PUP");
            } 
		});
	}
	
	@Test
	void test_decoder_getCombinationsWithRegex() throws Exception
	{
		decoder.addRegex("P-P");
		this.checkList(new ArrayList<String>() {
			{ 
                add("PUP");
            } 
		});
	}
	
	@Test
	void test_decoder_getCombinationsWithExcludes() throws Exception
	{
		decoder.addExclude("P-P");
		this.checkList(new ArrayList<String>() {
			{ 
				add("UPP");
                add("PPU");
            } 
		});
	}
	
	/**
	 * Check list with conditions.
	 * @param combinations
	 * @throws Exception 
	 */
	private void checkList(ArrayList<String> combinations) throws Exception
	{
		Hashtable<String, Integer> decoderHashTable = decoder.getCombinations(0);
		ArrayList<String> decoderCombinations = new ArrayList<String>(decoderHashTable.keySet());
		assertEquals(combinations.size(), decoderCombinations.size());
		for(String item: decoderCombinations)
		{
			assertTrue(combinations.contains(item));
		}
	}
}
