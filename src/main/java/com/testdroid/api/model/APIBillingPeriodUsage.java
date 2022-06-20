package com.testdroid.api.model;

import com.testdroid.api.APIEntity;

/**
 * @author Michał Szpruta <michal.szpruta@bitbar.com>
 */
public class APIBillingPeriodUsage extends APIEntity {

    private APIDeviceSession.Type type;

    private APIDevice.OsType osType;

    private long billableSeconds;

    private long nonBillableSeconds;

    public APIBillingPeriodUsage() {
    }

    public APIBillingPeriodUsage(
            APIDeviceSession.Type type, APIDevice.OsType osType, long billableSeconds, long nonBillableSeconds) {
        this.type = type;
        this.osType = osType;
        this.billableSeconds = billableSeconds;
        this.nonBillableSeconds = nonBillableSeconds;
    }

    public APIDeviceSession.Type getType() {
        return type;
    }

    public APIDevice.OsType getOsType() {
        return osType;
    }

    public long getBillableSeconds() {
        return billableSeconds;
    }

    public long getNonBillableSeconds() {
        return nonBillableSeconds;
    }

    public void setType(APIDeviceSession.Type type) {
        this.type = type;
    }

    public void setOsType(APIDevice.OsType osType) {
        this.osType = osType;
    }

    public void setBillableSeconds(long billableSeconds) {
        this.billableSeconds = billableSeconds;
    }

    public void setNonBillableSeconds(long nonBillableSeconds) {
        this.nonBillableSeconds = nonBillableSeconds;
    }

    @Override
    protected <T extends APIEntity> void clone(T from) {
        APIBillingPeriodUsage original = (APIBillingPeriodUsage) from;
        cloneBase(from);
        this.type = original.type;
        this.osType = original.osType;
        this.billableSeconds = original.billableSeconds;
        this.nonBillableSeconds = original.nonBillableSeconds;
    }
}
