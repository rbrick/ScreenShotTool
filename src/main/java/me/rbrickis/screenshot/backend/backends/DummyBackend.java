package me.rbrickis.screenshot.backend.backends;

import me.rbrickis.screenshot.backend.Backend;
import me.rbrickis.screenshot.backend.Result;

public class DummyBackend implements Backend<Object> {
    @Override
    public Result upload(Object file) {
        throw new UnsupportedOperationException("This is a dummy backend!");
    }

    @Override
    public String getName() {
        return "Dumbo";
    }
}
