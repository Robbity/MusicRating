package persistence;

import org.json.JSONObject;

// CITATION: Taken from JsonSerializationDemo writable file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}