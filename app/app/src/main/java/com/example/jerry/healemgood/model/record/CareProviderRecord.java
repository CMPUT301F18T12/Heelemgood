/*
 *  Class Name: CareProviderRecord
 *
 *  Version: Version 1.0
 *
 *  Date: November 1, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */


package com.example.jerry.healemgood.model.record;

import com.example.jerry.healemgood.utils.LengthOutOfBoundException;

/**
 * Represents a CareProviderRecord
 *
 * @author xiacijie
 * @version 1.0
 * @see Record
 * @since 1.0
 */
public class CareProviderRecord extends Record {

    /**
     * Creates a CareProviderRecord
     *
     * @param pId
     * @param title
     */
    public CareProviderRecord(String pId, String title) throws LengthOutOfBoundException {
        super(pId, title, false);
    }

}
