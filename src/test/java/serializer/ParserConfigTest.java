package serializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class ParserConfigTest extends TestCase {

	/*
	 * OLD TEST
	 * 
	 *  public void test_1() throws Exception {
        	ParserConfig config = new ParserConfig(Thread.currentThread().getContextClassLoader());
        
        	Model model = JSON.parseObject("{\"value\":123}", Model.class, config);
        	Assert.assertEquals(123, model.value);
    	}
    	
    	public static class Model {
        	public int value;
    	}
     *
	 */
	
	private String inputString;
	private Type classType;
	private ParserConfig config;
	private int expected;
	
	// Constructor
	public ParserConfigTest(String inputString, Type classType, ParserConfig config, int expected) {
		configure(inputString, classType, config, expected);
	}
	
	private void configure(String inputString, Type classType, ParserConfig config, int expected) {
		this.inputString = inputString;
		this.classType = classType;
		this.config = config;
		this.expected = expected;
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{"{\"value\":123}", Model.class, new ParserConfig(Thread.currentThread().getContextClassLoader()), 123}
			// inputString		classType			config													expected
		});
	}
	
	@Test
	public void test_1() throws Exception {
		Model model = JSON.parseObject(this.inputString, this.classType, this.config);
		Assert.assertEquals(this.expected, model.value);
	}
	
	public static class Model {
    	public int value;
	}	
	
}
