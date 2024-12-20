package com.javarush.kolosov.cryptoanalyzer.analyzers;

import com.javarush.kolosov.cryptoanalyzer.exceptions.AlphabetException;

import java.util.HashMap;
import java.util.Map;

public class CaesarCipher implements Cipher {

    private final char[] sourceAlphabet;
    private final Map<Character, Character> encodingAlphabet = new HashMap<>();
    private final Map<Character, Character> decodingAlphabet = new HashMap<>();

    public CaesarCipher(int key, char[]... alphabets) {
        sourceAlphabet = Alphabets.merge(alphabets);

        if (!Validator.validateAlphabet(sourceAlphabet)) {
            throw new AlphabetException("Не задан Алфавит");
        }

        int alphabetLength = this.sourceAlphabet.length;
        key = key % alphabetLength;
        if (key < 0) {
            key = alphabetLength + key;
        }

        prepareAlphabet(key);
    }

    private void prepareAlphabet(int key) {
        for (int i = 0; i < sourceAlphabet.length - key; i++) {
            this.encodingAlphabet.put(sourceAlphabet[i], sourceAlphabet[i + key]);
            this.decodingAlphabet.put(sourceAlphabet[i + key], sourceAlphabet[i]);
        }

        int startFrom = sourceAlphabet.length - key;
        for (int i = startFrom; i < sourceAlphabet.length; i++) {
            this.encodingAlphabet.put(sourceAlphabet[i], sourceAlphabet[i - startFrom]);
            this.decodingAlphabet.put(sourceAlphabet[i - startFrom], sourceAlphabet[i]);
        }
    }

    @Override
    public String encode(String text) {
        text = text.toLowerCase();
        char[] textChars = text.toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        for (char textChar : textChars) {
            Character sym = encodeSymbol(textChar);
            if (sym != null) {
                stringBuilder.append(sym.charValue());
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public String decode(String str) {
        str = str.toLowerCase();
        char[] textChars = str.toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        for (char textChar : textChars) {
            Character sym = decodeSymbol(textChar);
            if (sym != null) {
                stringBuilder.append(sym.charValue());
            }
        }

        return stringBuilder.toString();
    }

    private Character encodeSymbol(char symbol) {
        return encodingAlphabet.getOrDefault(symbol, null);
    }

    private Character decodeSymbol(char symbol) {
        return decodingAlphabet.getOrDefault(symbol, null);
    }

    public char[] getSourceAlphabet() {
        return sourceAlphabet;
    }
}
