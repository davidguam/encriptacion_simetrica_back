package com.david.encriptacion_cimetrica.services;

// Importaciones de Spring para usar anotaciones y valores desde el archivo de propiedades
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Importaciones necesarias para realizar encriptación con AES
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

// Marca esta clase como un "servicio" para que Spring la gestione como un componente inyectable
@Service
public class DesencriptationService {

    // Inyecta la clave secreta definida en application.properties o application.yml
    @Value("${aes.secret.key}")
    private String SECRET_KEY;

    /**
     * Método que desencripta un texto cifrado usando AES en modo CBC con padding PKCS5.
     *
     * @param textoCifradoBase64 Texto cifrado codificado en Base64
     * @param ivBase64 Vector de inicialización (IV) codificado en Base64
     * @return Texto original desencriptado
     * @throws Exception si hay error en el proceso de desencriptación
     */
    public String decryptFromClient(String textoCifradoBase64, String ivBase64) throws Exception {
        // Decodifica el IV de base64 a bytes y lo convierte a un objeto IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(ivBase64));

        // Crea la clave secreta en bytes desde la cadena y la especifica como tipo "AES"
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        // Obtiene una instancia del cifrador AES en modo CBC con padding PKCS5
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Inicializa el cifrador en modo desencriptación, usando la clave secreta y el IV
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        // Decodifica el texto cifrado desde Base64 a bytes reales cifrados
        byte[] textoCifradoBytes = Base64.getDecoder().decode(textoCifradoBase64);

        // Desencripta los bytes y obtiene los datos en texto plano
        byte[] textoPlano = cipher.doFinal(textoCifradoBytes);

        // Convierte el texto plano en bytes a String usando UTF-8
        return new String(textoPlano, StandardCharsets.UTF_8);
    }

    /**
     * Método que encripta un texto plano usando AES en modo CBC con padding PKCS5.
     *
     * @param textoPlano Texto a encriptar
     * @param ivBase64 Vector de inicialización (IV) codificado en Base64
     * @return Texto encriptado codificado en Base64
     * @throws Exception si hay error en el proceso de encriptación
     */
    public String encryptForClient(String textoPlano, String ivBase64) throws Exception {
        // Decodifica el IV de base64 a bytes y lo convierte a un objeto IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(ivBase64));

        // Crea la clave secreta en bytes desde la cadena y la especifica como tipo "AES"
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        // Obtiene una instancia del cifrador AES en modo CBC con padding PKCS5
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Inicializa el cifrador en modo encriptación, usando la clave secreta y el IV
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // Convierte el texto plano a bytes
        byte[] textoPlanoBytes = textoPlano.getBytes(StandardCharsets.UTF_8);

        // Encripta los bytes y obtiene los datos cifrados
        byte[] textoCifradoBytes = cipher.doFinal(textoPlanoBytes);

        // Devuelve el texto cifrado codificado en Base64
        return Base64.getEncoder().encodeToString(textoCifradoBytes);
    }
}



