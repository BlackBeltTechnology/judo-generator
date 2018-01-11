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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@Mojo(
	name = "mergeJson",
	defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)

public class MergeJson extends AbstractMojo {

	@Parameter(defaultValue = "", readonly = true, required = true)
    private File modifiedJson;

    @Parameter(defaultValue = "", readonly = true, required = true)
    private File newJson;
    
	@SuppressWarnings("resource")
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			InputStream newJsonStream = new FileInputStream(newJson);
			InputStream modifiedJsonStream = new FileInputStream(modifiedJson);
			
			JSONTokener newJsonTokener = new JSONTokener(newJsonStream);
			HashMap<String, JSONObject> newJsonMap = new HashMap<String, JSONObject>();
			
			newJsonTokener.skipTo('[');
			while (newJsonTokener.more()) {
				JSONObject object;
				newJsonTokener.skipTo('{');
				try {
					object = (JSONObject) newJsonTokener.nextValue();
				} catch(JSONException e) {
					break;
				}
				try {
					newJsonMap.put((String) object.get("id"), object);
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
			
			JSONTokener modifiedJsonTokener = new JSONTokener(modifiedJsonStream);
			HashMap<String, JSONObject> modifiedJsonMap = new HashMap<String, JSONObject>();
			HashMap<String, Boolean> modifiedJsonExistsMap = new HashMap<String, Boolean>();
			
			modifiedJsonTokener.skipTo('[');
			while (modifiedJsonTokener.more()) {
				JSONObject object;
				modifiedJsonTokener.skipTo('{');
				try {
					object = (JSONObject) modifiedJsonTokener.nextValue();
				} catch (JSONException e) {
					break;
				}
				try {
					modifiedJsonMap.put((String) object.get("id"), object);
					modifiedJsonExistsMap.put((String) object.get("id"), false);
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
			
			for (String uuid : newJsonMap.keySet()) {
				JSONObject newObject = newJsonMap.get(uuid);
				JSONObject modifiedObject = modifiedJsonMap.get(uuid);
				if (modifiedObject != null) {
					String[] splitKey = modifiedObject.get("key").toString().split("\\.");
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
			
			HashMap<String, JSONObject> finalJsonMap = new HashMap();
			for (String uuid : modifiedJsonMap.keySet()) {
				if (modifiedJsonExistsMap.get(uuid) == true) {
					finalJsonMap.put(uuid, modifiedJsonMap.get(uuid));
				}
			}
			
			newJsonStream.close();
			modifiedJsonStream.close();
			
			PrintWriter outputStream = new PrintWriter(modifiedJson);
			outputStream.println("{ \"resources\": [");
			int index = 0;
			for (String uuid : finalJsonMap.keySet()) {
				outputStream.append(finalJsonMap.get(uuid).toString());
				if (index != finalJsonMap.size()-1) {
					outputStream.append(",\n");
					index++;
				}
			}
			outputStream.append("] }");
			
			outputStream.flush();
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
