package com.example.phonenumbersviewer.enums;

public enum CountriesCodes {
    CAMEROON("237"),
    ETHIOPIA("251"),
    MOROCCO("212"),
    MOZAMBIQUE("258"),
    UGANDA("256");

    private final String code;
    CountriesCodes(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static boolean isValidCode(String code){
        if (code.isEmpty()) return false;
        for (CountriesCodes country: CountriesCodes.values()){
            if (code.equals(country.getCode())){
                return true;
            }
        }
        return false;
    }
}
