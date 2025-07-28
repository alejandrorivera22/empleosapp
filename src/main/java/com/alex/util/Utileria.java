package com.alex.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {
    
    public static String guardarArchivo(MultipartFile multiPart, String ruta){

        //Obtenemos el nombre original del archivo
        String nombreOriginal = multiPart.getOriginalFilename();
        nombreOriginal.replace(" ", "-");
        String nombreFinal = randomAlphaNumeric(8) + nombreOriginal;
        try {
            //Formamos el nobre del archivo para guardarlo en el disco duro
            File imageFile = new File(ruta, nombreFinal);
            System.out.println("Ruta del archivo: " + imageFile.getAbsolutePath());
            //guardamos el archivo fisicamente en el HD
            multiPart.transferTo(imageFile);
            return nombreFinal;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String randomAlphaNumeric(int count){

        String caracteres = "ABCDEFGHIJKMNLSPQRSTUVXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * caracteres.length());
            builder.append(caracteres.charAt(character));
        }
        return builder.toString();
    }

}
