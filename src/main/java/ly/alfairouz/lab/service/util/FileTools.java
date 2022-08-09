package ly.alfairouz.lab.service.util;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FileTools {

    private static final String uploadsDir = "~/uploads/alfairouz/";

    public static String upload(byte[] imageBytes, String fileContentType, String name) {
        String generatedName = "";

        if (imageBytes != null) {
            try {
                if (fileContentType.equals(MediaType.IMAGE_PNG_VALUE)) generatedName =
                    name + "_" + System.currentTimeMillis() + new Random().nextInt(100) + ".png";
                else if (
                    fileContentType.equals(MediaType.IMAGE_JPEG_VALUE)
                ) generatedName = name + "_" + System.currentTimeMillis() + new Random().nextInt(100) + ".jpeg";
                else if (
                    fileContentType.equals(MediaType.IMAGE_GIF_VALUE)
                ) generatedName = name + "_" + System.currentTimeMillis() + new Random().nextInt(100) + ".gif";
                else generatedName =
                        name + "_" + System.currentTimeMillis() + new Random().nextInt(100) + ".jpeg";

                String path = uploadsDir + generatedName;
                FileUtils.writeByteArrayToFile(new File(path), imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return generatedName;
    }

    public static byte[] download(String fileName) {
        try {
            File file = new File(uploadsDir + fileName);
            return java.nio.file.Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
