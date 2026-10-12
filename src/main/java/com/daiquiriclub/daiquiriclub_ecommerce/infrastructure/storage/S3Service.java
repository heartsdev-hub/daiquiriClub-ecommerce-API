package com.daiquiriclub.daiquiriclub_ecommerce.infrastructure.storage;

import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.InvalidImageException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.StorageException;
import com.daiquiriclub.daiquiriclub_ecommerce.security.aws.S3Properties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final S3Properties properties;

    public S3Service(S3Client s3Client, S3Presigner s3Presigner, S3Properties properties) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.properties = properties;
    }
    // SUBIR IMAGEN
    public String upload(
            MultipartFile file,
            String directory
    ) {
        validateImage(file);
        validateDirectory(directory);

        try {
            byte[] bytes = file.getBytes();
            String contentType = detectImageType(bytes);

            if (contentType == null) {
                throw new InvalidImageException(
                        "El archivo no es una imagen permitida"
                );
            }

            String extension = switch (contentType) {
                case "image/jpeg" -> ".jpg";
                case "image/png" -> ".png";
                case "image/webp" -> ".webp";
                default -> throw new InvalidImageException(
                        "Formato no permitido"
                );
            };

            String key = directory + "/"
                    + UUID.randomUUID()
                    + extension;

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(properties.bucket())
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromBytes(bytes)
            );

            return key;

        } catch (IOException exception) {
            throw new StorageException(
                    "Error al leer la imagen",
                    exception
            );
        } catch (SdkException exception) {
            throw new StorageException(
                    "Error al subir la imagen a Amazon S3",
                    exception
            );
        }
    }

    // ELIMINAR IMAGEN
    public void delete(String key) {

        if (key == null || key.isBlank()) {
            return;
        }

        if (key.equals(properties.defaultProfileKey())) {
            throw new StorageException(
                    "No se puede eliminar la imagen predeterminada"
            );
        }

        if (!key.startsWith("empleados/")
                && !key.startsWith("productos/")) {
            throw new StorageException(
                    "No está permitido eliminar este archivo"
            );
        }

        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(properties.bucket())
                    .key(key)
                    .build();

            s3Client.deleteObject(request);

        } catch (SdkException exception) {
            throw new StorageException(
                    "Error al eliminar la imagen de S3",
                    exception
            );
        }
    }

    // GENERAR URL TEMPORAL
    public String generatePresignedUrl(
            String key,
            Duration duration
    ) {

        if (key == null || key.isBlank()) {
            throw new StorageException(
                    "La clave de la imagen es obligatoria"
            );
        }

        if (duration == null
                || duration.isZero()
                || duration.isNegative()
                || duration.compareTo(Duration.ofDays(7)) > 0) {
            throw new StorageException(
                    "Duración de URL inválida"
            );
        }

        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(properties.bucket())
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest =
                    GetObjectPresignRequest.builder()
                            .signatureDuration(duration)
                            .getObjectRequest(getRequest)
                            .build();

            return s3Presigner.presignGetObject(presignRequest)
                    .url()
                    .toString();

        } catch (SdkException exception) {
            throw new StorageException(
                    "Error al generar la URL temporal",
                    exception
            );
        }
    }

    // VALIDAR TAMAÑO DEL ARCHIVO
    private void validateImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new InvalidImageException(
                    "Debe seleccionar una imagen"
            );
        }

        if (file.getSize() > properties.maxFileSize().toBytes()) {
            throw new InvalidImageException(
                    "La imagen supera el tamaño permitido"
            );
        }
    }

    // VALIDAR RUTA DE ALMACENAMIENTO
    private void validateDirectory(String directory) {

        if (directory == null
                || !directory.matches(
                "[a-zA-Z0-9_-]+(/[a-zA-Z0-9_-]+)*"
        )) {
            throw new InvalidImageException(
                    "Directorio de almacenamiento inválido"
            );
        }

        if (!directory.startsWith("empleados/")
                && !directory.startsWith("productos/")) {
            throw new InvalidImageException(
                    "Directorio no permitido"
            );
        }
    }

    // IDENTIFICAR FORMATO MEDIANTE FIRMA
    private String detectImageType(byte[] bytes) {

        // JPEG
        if (bytes.length >= 3
                && (bytes[0] & 0xFF) == 0xFF
                && (bytes[1] & 0xFF) == 0xD8
                && (bytes[2] & 0xFF) == 0xFF) {
            return "image/jpeg";
        }

        // PNG
        if (bytes.length >= 8
                && (bytes[0] & 0xFF) == 0x89
                && bytes[1] == 0x50
                && bytes[2] == 0x4E
                && bytes[3] == 0x47
                && bytes[4] == 0x0D
                && bytes[5] == 0x0A
                && bytes[6] == 0x1A
                && bytes[7] == 0x0A) {
            return "image/png";
        }

        // WEBP
        if (bytes.length >= 12
                && bytes[0] == 'R'
                && bytes[1] == 'I'
                && bytes[2] == 'F'
                && bytes[3] == 'F'
                && bytes[8] == 'W'
                && bytes[9] == 'E'
                && bytes[10] == 'B'
                && bytes[11] == 'P') {
            return "image/webp";
        }

        return null;
    }
}
