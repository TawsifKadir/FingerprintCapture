package com.faisal.fingerprintcapture.callback;

import com.faisal.fingerprintcapture.model.Fingerprint;

public interface FingerprintCaptureCallback {
    public void onCaptureStart(Fingerprint fp);
    public void onCaptureEnd(Fingerprint fp);
    public void onCaptureStop(Fingerprint fp);
    public void onCaptureFailed(Fingerprint fp);

}
