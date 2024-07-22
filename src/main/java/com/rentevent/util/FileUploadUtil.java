package com.rentevent.util;

import com.rentevent.exception.FuncErrorException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {
    public static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String FILE_NAME_FORMAT = "%s_%s"; // fileName_date

    /**
     * Verifica si la extensión de un archivo es permitida basándose en una expresión regular.
     * Este método compila la expresión regular proporcionada en un patrón, ignorando mayúsculas y minúsculas,
     * y luego crea un matcher con el nombre del archivo. Retorna verdadero si el nombre del archivo
     * coincide con el patrón, indicando que la extensión del archivo es permitida.
     *
     * @param fileName El nombre del archivo a verificar.
     * @param pattern  La expresión regular que define las extensiones permitidas.
     * @return true si la extensión del archivo es permitida, false en caso contrario.
     */
    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    /**
     * Verifica si un archivo cumple con los criterios de tamaño máximo y extensión permitida.
     * Este método primero verifica que el tamaño del archivo no exceda el límite máximo establecido (2MB).
     * Luego, extrae la extensión del nombre del archivo y verifica que esta coincida con las extensiones permitidas,
     * definidas en una expresión regular. Si el archivo excede el tamaño máximo o si su extensión no está permitida,
     * se lanza una excepción personalizada.
     *
     * @param file    El archivo a verificar.
     * @param pattern La expresión regular que define las extensiones de archivo permitidas.
     * @throws FuncErrorException Si el tamaño del archivo excede el límite permitido o si la extensión del archivo no está permitida.
     */
    public static void assertAllowed(MultipartFile file, String pattern) {
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            throw new FuncErrorException("Tamaño de archivo excede el límite permitido (2MB)");
        }

        final String fileName = file.getOriginalFilename();
        assert fileName != null;
        final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!pattern.contains(extension)) {
            throw new FuncErrorException("Extensión de archivo no , permitida (jpg, png, gif, bmp)");
        }
    }

    /**
     * Genera un nombre de archivo único basado en un nombre base y la fecha y hora actual.
     * Este método utiliza un formato de fecha y hora predefinido para generar una cadena de texto
     * que representa la fecha y hora actuales. Luego, combina esta cadena con el nombre base proporcionado
     * para formar un nombre de archivo único. Esto es útil para evitar colisiones de nombres de archivos
     * al guardar archivos en un sistema de archivos donde cada archivo debe tener un nombre único.
     *
     * @param name El nombre base para el archivo, al cual se le añadirá la fecha y hora actual.
     * @return Un nombre de archivo único que combina el nombre base y la fecha y hora actuales.
     */
    public static String getFileName(final String name) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, name, date);
    }
}
