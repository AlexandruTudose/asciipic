package com.asciipic.crawl.services.external;


import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class ImageRetrieverImpl implements ImageRetriever {

    InputStream inputStream;

    @Override
    public byte[] getImage(String link) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null) {
            try {
                inputStream = url.openStream();
                byte[] bytes = IOUtils.toByteArray(inputStream);
                return bytes;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
