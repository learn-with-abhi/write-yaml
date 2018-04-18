package io.letstry.yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

@SpringBootApplication
public class WriteYamlApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriteYamlApplication.class, args);
		try {
			outputYaml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void outputYaml() throws IOException {
		Assembly assembly = new Assembly();
		assembly.setFamily("some family");
		assembly.setOrgId("some org");
		Types types = new Types();
		types.setName("name@abc");
		types.setType("String");
		//types.setUrl("asddff");
		assembly.setTypes(Arrays.asList(types));
		//Representer representer = new Representer();
		Representer representer = new Representer() {
		    @Override
		    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
		        // if value of property is null, ignore it.
		        if (propertyValue == null) {
		            return null;
		        }  
		        else {
		            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
		        }
		    }
		};
		representer.addClassTag(Assembly.class, new Tag("!!assembly"));
		// representer.addClassTag(Types.class, Tag.MAP);
		DumperOptions options = new DumperOptions();
	    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    options.setPrettyFlow(true);
	    
		Constructor constructor = new Constructor(Assembly.class);//Car.class is root
		TypeDescription carDescription = new TypeDescription(Assembly.class);
		constructor.addTypeDescription(carDescription);
		Yaml yaml = new Yaml(representer, options);
		Map<String, Object> data = new HashMap<String, Object>();
		   data.put("name", "Silenthand Olleander");
		   data.put("race", "Human");
		   data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });
		StringWriter sw = new StringWriter();
		FileWriter writer = new FileWriter("./src/main/resources/assembly.yml");
		yaml.dump(assembly, writer);
		//System.out.println(sw.toString());
	}
}
