package com.example.clbootstrap.helpers;

import android.content.Context;
import com.example.clbootstrap.models.Country;
import com.hbb20.CCPCountry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryHelper {
    public static List<Country> getCountries(Context context) {
        List<Country> countries = new ArrayList<>();
        
        // Get all countries from CCCP
        for (CCPCountry ccpCountry : CCPCountry.getLibraryMasterCountriesEnglish()) {
            boolean isPriority = "IN".equals(ccpCountry.getNameCode()) || "US".equals(ccpCountry.getNameCode());
            
            List<String> states = new ArrayList<>();
            if (isPriority) {
                if ("IN".equals(ccpCountry.getNameCode())) {
                    states = getIndianStates();
                } else if ("US".equals(ccpCountry.getNameCode())) {
                    states = getUSStates();
                }
            }
            
            countries.add(new Country(
                ccpCountry.getName(),
                ccpCountry.getNameCode(),
                "+" + ccpCountry.getPhoneCode(),
                ccpCountry.getFlagID(),
                isPriority,
                states
            ));
        }

        // Sort countries: priority first (India and USA), then alphabetically
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                if (c1.isPriority() && !c2.isPriority()) return -1;
                if (!c1.isPriority() && c2.isPriority()) return 1;
                if (c1.isPriority() && c2.isPriority()) {
                    // India first, then USA
                    if (c1.getCode().equals("IN")) return -1;
                    if (c2.getCode().equals("IN")) return 1;
                }
                return c1.getName().compareTo(c2.getName());
            }
        });

        return countries;
    }

    public static Country getCountryByCode(Context context, String countryCode) {
        List<Country> countries = getCountries(context);
        for (Country country : countries) {
            if (country.getDialCode().equals("+" + countryCode)) {
                return country;
            }
        }
        return null;
    }

    private static List<String> getIndianStates() {
        List<String> states = new ArrayList<>();
        states.add("Andhra Pradesh");
        states.add("Arunachal Pradesh");
        states.add("Assam");
        states.add("Bihar");
        states.add("Chhattisgarh");
        states.add("Goa");
        states.add("Gujarat");
        states.add("Haryana");
        states.add("Himachal Pradesh");
        states.add("Jharkhand");
        states.add("Karnataka");
        states.add("Kerala");
        states.add("Madhya Pradesh");
        states.add("Maharashtra");
        states.add("Manipur");
        states.add("Meghalaya");
        states.add("Mizoram");
        states.add("Nagaland");
        states.add("Odisha");
        states.add("Punjab");
        states.add("Rajasthan");
        states.add("Sikkim");
        states.add("Tamil Nadu");
        states.add("Telangana");
        states.add("Tripura");
        states.add("Uttar Pradesh");
        states.add("Uttarakhand");
        states.add("West Bengal");
        return states;
    }

    private static List<String> getUSStates() {
        List<String> states = new ArrayList<>();
        states.add("Alabama");
        states.add("Alaska");
        states.add("Arizona");
        states.add("Arkansas");
        states.add("California");
        states.add("Colorado");
        states.add("Connecticut");
        states.add("Delaware");
        states.add("Florida");
        states.add("Georgia");
        states.add("Hawaii");
        states.add("Idaho");
        states.add("Illinois");
        states.add("Indiana");
        states.add("Iowa");
        states.add("Kansas");
        states.add("Kentucky");
        states.add("Louisiana");
        states.add("Maine");
        states.add("Maryland");
        states.add("Massachusetts");
        states.add("Michigan");
        states.add("Minnesota");
        states.add("Mississippi");
        states.add("Missouri");
        states.add("Montana");
        states.add("Nebraska");
        states.add("Nevada");
        states.add("New Hampshire");
        states.add("New Jersey");
        states.add("New Mexico");
        states.add("New York");
        states.add("North Carolina");
        states.add("North Dakota");
        states.add("Ohio");
        states.add("Oklahoma");
        states.add("Oregon");
        states.add("Pennsylvania");
        states.add("Rhode Island");
        states.add("South Carolina");
        states.add("South Dakota");
        states.add("Tennessee");
        states.add("Texas");
        states.add("Utah");
        states.add("Vermont");
        states.add("Virginia");
        states.add("Washington");
        states.add("West Virginia");
        states.add("Wisconsin");
        states.add("Wyoming");
        return states;
    }
}
