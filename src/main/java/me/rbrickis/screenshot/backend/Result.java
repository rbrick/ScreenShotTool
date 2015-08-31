package me.rbrickis.screenshot.backend;

import com.google.gson.JsonObject;

public interface Result {

        public Result ERROR = new Result() {
            @Override
            public JsonObject getResponse() {
                JsonObject object = new JsonObject();
                object.addProperty("result-message", "An error occurred.");
                return null;
            }
        };

        JsonObject getResponse();
    }
