package com.daiquiriclub.daiquiriclub_ecommerce.security.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.aws.s3")
public record S3Properties(
        String region,
        String bucket,
        String defaultProfileKey,
        Duration presignedUrlDuration,
        DataSize maxFileSize
) {
}
