package me.rbrickis.screenshot.backend.backends.imgur;

import com.google.gson.JsonObject;
import me.rbrickis.screenshot.backend.Result;

public class ImgurResult implements Result {

    private JsonObject response;

    public ImgurResult(JsonObject object) {
        this.response = object;
        response.addProperty("result-message", "Success!");
    }

    @Override
    public JsonObject getResponse() {
        return response;
    }
}
