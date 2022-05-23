package serializer;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.alibaba.fastjson.JSON;

import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class FieldOrderTest extends TestCase {
	
	/*
	 * OLD TEST
	 * 
	 *  public void test_field_order() throws Exception {
        	Person p = new Person();
        	p.setName("njb");
        	School s = new School();
        	s.setName("llyz");
        	p.setSchool(s);
        	String json = JSON.toJSONString(p);
        	assertEquals("{\"name\":\"njb\",\"school\":{\"name\":\"llyz\"}}", json);
    	}

    	public static class Person {
        	private String name;
        	private School school;

        	public boolean isSchool() {
            	return false;
        	}

        	public School getSchool() {
            	return school;
        	}

        	public void setSchool(School school) {
            	this.school = school;
        	}

        	public String getName() {
            	return name;
        	}

        	public void setName(String name) {
            	this.name = name;
        	}

    	}

    	public static class School {
        	private String name;

        	public String getName() {
            	return name;
        	}

        	public void setName(String name) {
            	this.name = name;
        	}
    	}
     *
	 */
	
	private String personName;
	private String schoolName;
	private String expected;
	
	private Person p;
	private School s;
	
	// Constructor
	public FieldOrderTest(String personName, String schoolName, String expected) {
		configure(personName, schoolName, expected);
	}
	
	private void configure(String personName, String schoolName, String expected) {
		this.personName = personName;
		this.schoolName = schoolName;
		this.expected = expected;
		
		this.p = new Person();
		this.p.setName(this.personName);
		this.s = new School();
		this.s.setName(this.schoolName);
		this.p.setSchool(this.s);
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{"njb", "llyz", "{\"name\":\"njb\",\"school\":{\"name\":\"llyz\"}}"}
		// pName	sName		expected
		});
	}
	
	@Test
	public void test_field_order() throws Exception {
		String json = JSON.toJSONString(this.p);
		assertEquals(this.expected, json);
	}
	
    public static class Person {
        private String name;
        private School school;

        public boolean isSchool() {
            return false;
        }

        public School getSchool() {
            return school;
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class School {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
	
}
