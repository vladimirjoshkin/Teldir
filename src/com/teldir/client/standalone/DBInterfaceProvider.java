package com.teldir.client.standalone;

import com.teldir.core.LegalEntity;
import com.teldir.core.NaturalPerson;

public class DBInterfaceProvider {
    public static void saveNaturalPerson(NaturalPerson naturalPerson) {
        
    }

    public static NaturalPerson getNaturalPerson(int id) {
        return new NaturalPerson(-1);
    }

    public static void saveLegalEntity(LegalEntity legalEntity) {

    }

    public static LegalEntity getLegalEntity(int id) {
        return new LegalEntity(-1);
    }
}