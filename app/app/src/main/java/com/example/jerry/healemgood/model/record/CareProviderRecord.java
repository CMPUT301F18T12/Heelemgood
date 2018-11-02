package com.example.jerry.healemgood.model.record;

/**
 * This is the class for care providers' record
 * It inherits from the base class Record
 * */
public class CareProviderRecord extends Record {

    public CareProviderRecord(int rId, String title, boolean isPatientRecord){
        super(rId, title, isPatientRecord);
    }

}
