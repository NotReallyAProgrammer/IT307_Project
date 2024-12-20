package com.example.it307_project.Adapter;

public class StringToByte {
    public static byte[] stringToByteArray(String str) {

        if (str == null || str.isEmpty()) {
            return new byte[0];
        }

        // Remove unwanted characters (brackets, spaces, etc.)
        str = str.replace("[", "").replace("]", "").replace(" ", "");

        // Split the string by commas to get individual byte values
        String[] byteStrings = str.split(",");

        byte[] byteArray = new byte[byteStrings.length];


        for (int i = 0; i < byteStrings.length; i++) {
            try {
                byteArray[i] = Byte.parseByte(byteStrings[i]);
            } catch (NumberFormatException e) {
                return new byte[0];
            }
        }

        return byteArray;
    }
}
