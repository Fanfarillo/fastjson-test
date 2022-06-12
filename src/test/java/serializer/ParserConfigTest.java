package serializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;

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
    	
    	public void test_0() throws Exception {	
        	ParserConfig config = new ParserConfig();
        	config.getDeserializers();
    	}
     *
	 */
	
	private String inputString;
	private Type classType;
	private ParserConfig config0;
	private ParserConfig config1;
	private ParseProcess processor;
	private int featureValues;
	private Feature features;

	// Constructor
	public ParserConfigTest(String inputString, ParserConfig config0, ParserConfig config1, boolean isProcessor, int featureValues, Feature features) {
		configure(inputString, config0, config1, isProcessor, featureValues, features);
	}
	
	private void configure(String inputString, ParserConfig config0, ParserConfig config1, boolean isProcessor, int featureValues, Feature features) {
		this.inputString = inputString;
		this.config0 = config0;
		this.config1 = config1;
		this.featureValues = featureValues;
		this.features = features;
		
		if(isProcessor) this.processor = this.new MyExtraProcessor();
		else this.processor = null;
		
		this.classType = Model.class;
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{"{\"value\":123}", new ParserConfig(), new ParserConfig(Thread.currentThread().getContextClassLoader()), true, 0, Feature.AllowArbitraryCommas}
			// inputString			config_0		    	config_1											 processor	val		feat
		});
	}
	
	@Test
	public void test_0() throws Exception {
		this.config0.getDeserializers();
	}
	
	@Test
	public void test_1() throws Exception {
		JSONObject jo = new JSONObject(this.inputString);
		int expected = jo.getInt("value");
		
		Model model = JSON.parseObject(this.inputString, this.classType, this.config1, this.processor, this.featureValues, this.features);
		Assert.assertEquals(expected, model.value);
	}
	
	public static class Model {
    	public int value;
	}	
	
    public class MyExtraProcessor implements ExtraProcessor, ExtraTypeProvider {    	
        public void processExtra(Object object, String key, Object value) {
            VO vo = (VO) object;
            vo.getAttributes().put(key, value);
        }

        public Type getExtraType(Object object, String key) {
            if ("value".equals(key)) {
                return int.class;
            }
            return null;
        }
    }
    
    public static class VO {

        private int id;
        private Map<String, Object> attributes = new HashMap<String, Object>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

    }
	
}
