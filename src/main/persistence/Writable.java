package persistence;

import org.json.JSONObject;

public interface Writable {

    // Returns this object as a JSON object
    JSONObject toJson();
}
