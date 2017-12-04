package hu.blackbelt.judo.generator.maven.plugin.parsejson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.json.JSONObject;
import org.json.JSONTokener;

@Mojo(
	name = "mergeJson",
	defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)

public class MergeJson extends AbstractMojo {

	@Parameter(defaultValue = "", readonly = true, required = true)
    private File modifiedJson;

    @Parameter(defaultValue = "/hu.blackbelt.judo.generator.transformer.ui/target/resources.model", readonly = true, required = true)
    private File newJson;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			InputStream newJsonStream = new FileInputStream(newJson);
			InputStream modifiedJsonStream = new FileInputStream(modifiedJson);
			
			JSONTokener newJsonTokener = new JSONTokener(newJsonStream);
			HashMap<String, JSONObject> newJsonMap = new HashMap<String, JSONObject>();
			
			while (newJsonTokener.more()) {
				JSONObject object = (JSONObject) newJsonTokener.nextValue();
				newJsonMap.put((String) object.get("uuid"), object);
			}
			
			JSONTokener modifiedJsonTokener = new JSONTokener(modifiedJsonStream);
			HashMap<String, JSONObject> modifiedJsonMap = new HashMap<String, JSONObject>();
			HashMap<String, Boolean> modifiedJsonExistsMap = new HashMap<String, Boolean>();
			while (modifiedJsonTokener.more()) {
				JSONObject object = (JSONObject) modifiedJsonTokener.nextValue();
				modifiedJsonMap.put((String) object.get("uuid"), object);
				modifiedJsonExistsMap.put((String) object.get("uuid"), false);
			}
			
			for (String uuid : newJsonMap.keySet()) {
				JSONObject newObject = newJsonMap.get(uuid);
				JSONObject modifiedObject = modifiedJsonMap.get(uuid);
				if (modifiedObject != null) {
					String[] splitKey = modifiedObject.get("key").toString().split(".");
					if (splitKey[splitKey.length-1].equals(modifiedObject.get("value").toString())) {
						modifiedObject.put("value", newObject.get("value").toString());
					}
					modifiedObject.put("key", newObject.get("key").toString());
					modifiedJsonExistsMap.put(uuid, true);
				} else {
					modifiedJsonMap.put(uuid, newObject);
					modifiedJsonExistsMap.put(uuid, true);
				}
			}
			
			for (String uuid : modifiedJsonMap.keySet()) {
				if (modifiedJsonExistsMap.get(uuid) == false) {
					modifiedJsonMap.remove(uuid);
				}
			}
			
			StringBuilder builder = new StringBuilder();
			for (String uuid : modifiedJsonMap.keySet()) {
				builder.append(modifiedJsonMap.get(uuid).toString());
			}
			
			/*PrintWriter outputStream = new PrintWriter(newJson);
			outputStream.println(builder.toString());*/
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
