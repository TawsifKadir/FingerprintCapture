package com.faisal.fingerprintcapture.manager;

public interface IDeviceManager {
    long initDevice();
    long openDevice();
    long startCapture();
    long closeDevice();
    long deInitDevice();
    boolean isDeviceOpen();
    boolean isPermissionAcquired();
}
