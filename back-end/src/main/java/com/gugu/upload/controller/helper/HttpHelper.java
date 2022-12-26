package com.gugu.upload.controller.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * The type Http helper.
 * <p>If the filter has passed when processing the request, setting the header is invalid {@link FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)}</p>
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /11/07
 * @since 1.8
 */
@Slf4j
public class HttpHelper {
    private static final String FILE_STREAM_DISPOSITION_TEMP = "attachment; filename=%s";

    private HttpHelper() {
    }

    /**
     * Setting file stream header.
     *
     * @param response the response
     * @param fileName the file name
     */
    public static void settingFileStreamHeader(HttpServletResponse response, String fileName) {
        Optional<MediaType> optionalMediaType = MediaTypeFactory.getMediaType(fileName);
        if (optionalMediaType.isPresent()) {
            response.setContentType(optionalMediaType.get().toString());
        } else {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }
        response.setHeader("Content-Disposition", generateDisposition(fileName));

    }

    private static String generateDisposition(String fileName) {
        try {
            return String.format(FILE_STREAM_DISPOSITION_TEMP, URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to convert file name to URL encoding. Encoding has been discarded", e);
            return String.format(FILE_STREAM_DISPOSITION_TEMP, fileName);
        }
    }
}
