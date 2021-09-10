package com.example.EncurtadorDeLinks.service;

import org.springframework.stereotype.Service;

@Service
public class ConverterBase62 {

    private static final String stringDeCaracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private char[] caracteresPermitidos = stringDeCaracteres.toCharArray();

    private int base = caracteresPermitidos.length;

    public String codificar(long valor){
        StringBuilder stringCodificada = new StringBuilder();

        if(valor == 0) {
            return String.valueOf(caracteresPermitidos[0]);
        }

        while (valor > 0) {
            stringCodificada.append(caracteresPermitidos[(int) (valor % base)]);
            valor = valor / base;
        }

        return stringCodificada.reverse().toString();
    }

    public long decodificar(String valor) {
        char[] caracteres = valor.toCharArray();
        int length = caracteres.length;

        int decodificado = 0;

        //usado para evitar a reversão da string de entrada (valor)
        int contador = 1;

        for (int i = 0; i < length; i++) {
            decodificado += stringDeCaracteres.indexOf(caracteres[i]) * Math.pow(base, length - contador);
            contador++;
        }

        return decodificado;
    }
}
