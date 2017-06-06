package com.asciipic.search.services;

import java.sql.Blob;

public interface AsciiService {
    String transformImageToAscii(Blob Image);
}
