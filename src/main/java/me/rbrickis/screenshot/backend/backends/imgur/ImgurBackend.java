package me.rbrickis.screenshot.backend.backends.imgur;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.rbrickis.aurora.http.HttpClient;
import me.rbrickis.aurora.http.HttpResponse;
import me.rbrickis.aurora.http.RequestType;
import me.rbrickis.aurora.http.impl.builder.HttpClientBuilder;
import me.rbrickis.aurora.http.impl.builder.HttpParameterBuilder;
import me.rbrickis.screenshot.backend.Backend;
import me.rbrickis.screenshot.backend.Result;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImgurBackend implements Backend<ImgurImage> {

    @Override
    public Result upload(ImgurImage img) {
        try {
            final ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(img.getImage(), "png", boas);
            boas.flush();
            boas.close();
            byte[] data = boas.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(data);

            HttpClient client = new HttpClientBuilder().url("https://api.imgur.com/3/upload")
                .agent("Aurora HttpClient v0.1")
                .property("Authorization", "Client-ID " + img.getClientId()).parameters(
                    new HttpParameterBuilder().addParameter("image", base64)
                        .addParameter("client_id", img.getClientId()).addParameter("type", "base64")
                        .addParameter("title", img.getTitle()).addParameter("description", ""))
                .method(RequestType.POST).build();
            HttpResponse response = client.execute(result -> {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Upload complete");
                return result;
            });
            return new ImgurResult(new Gson().fromJson(response.asString(), JsonObject.class));
        } catch (IOException e) {
            return Result.ERROR;

        }
    }

    @Override
    public String getName() {
        return "Imgur";
    }
}
