package com.example.phonenumbersviewer.enums;

public enum  CountriesNumberRegexes {
    CAMEROON("\\(237\\)\\ ?[2368]\\d{7,8}$"),
    ETHIOPIA("\\(251\\)\\ ?[1-59]\\d{8}$"),
    MOROCCO("\\(212\\)\\ ?[5-9]\\d{8}$"),
    MOZAMBIQUE("\\(258\\)\\ ?[28]\\d{7,8}$"),
    UGANDA("\\(256\\)\\ ?\\d{9}$");


    private final String regex;

    CountriesNumberRegexes(final String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }

    public static Boolean isExist(String name) {
        if (name.isEmpty()) return false;

        for (CountriesNumberRegexes country : CountriesNumberRegexes.values()){
            if (name.equals(country.name())){
                return true;
            }
        }
        return false;
    }
}
