package com.policestrategies.calm_stop.citizen.beacon_detection;

/**
 * Encapsulates a given officer.
 * @author Talal Abou Haiba
 */
class BeaconObject {

    private String mOfficerName;
    private String mDepartmentNumber;
    private String mBadgeNumber;

    private String mOfficerUid;
    private String mInstanceId;

    BeaconObject(String name, String department, String uid, String instance, String badge) {
        mOfficerName = name;
        mDepartmentNumber = department;
        mOfficerUid = uid;
        mInstanceId = instance;
        mBadgeNumber = badge;
    }

    String getOfficerName() {
        return mOfficerName;
    }

    String getDepartmentNumber() {
        return mDepartmentNumber;
    }

    String getOfficerUid() {
        return mOfficerUid;
    }

    String getBeaconInstanceId() {
        return mInstanceId;
    }

    String getBadgeNumber() {
        return mBadgeNumber;
    }

} // end class BeaconObject
