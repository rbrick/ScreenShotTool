package me.rbrickis.screenshot.backend.backends.imgur;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
public class ImgurImage {
    private BufferedImage image;
    private String title, clientId, description;
}
