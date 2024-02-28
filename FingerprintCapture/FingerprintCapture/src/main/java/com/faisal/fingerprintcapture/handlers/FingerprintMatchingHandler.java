package com.faisal.fingerprintcapture.handlers;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.faisal.fingerprintcapture.model.FingerprintID;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import SecuGen.FDxSDKPro.JSGFPLib;
import SecuGen.FDxSDKPro.SGFDxErrorCode;
import SecuGen.FDxSDKPro.SGFDxSecurityLevel;
import SecuGen.FDxSDKPro.SGFDxTemplateFormat;
import SecuGen.FDxSDKPro.SGFingerInfo;
import SecuGen.FDxSDKPro.SGImpressionType;

public class FingerprintMatchingHandler {
    String TAG = "FingerprintMatchingHandler";
    private Activity mActivity;
    private JSGFPLib sgfplib;
    private byte[] mRegisterTemplate;

    private boolean isInitialized;
    private HashMap<Integer,byte[]> templateList;
    private int[] mMaxTemplateSize;

    public FingerprintMatchingHandler(Activity mActivity) {
        this.mActivity = mActivity;
        this.sgfplib = null;
        this.mRegisterTemplate = null;
        this.mMaxTemplateSize = null;
        this.isInitialized = false;
        this.templateList = null;

    }
    public long init(){
        try{
            this.sgfplib = new JSGFPLib(mActivity, (UsbManager)mActivity.getSystemService(Context.USB_SERVICE));
            mMaxTemplateSize = new int[1];
            sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_ISO19794);
            sgfplib.GetMaxTemplateSize(mMaxTemplateSize);
            mRegisterTemplate = new byte[(int)mMaxTemplateSize[0]];
            templateList = new HashMap<>();
            this.isInitialized = true;
        }catch(Exception exc){
            Log.e(TAG,"FingerprintMatchingHandler initialization error : "+exc.getMessage());
            exc.printStackTrace();
            return -1;
        }
        return 0;
    }

    public boolean isInitialized(){
        return isInitialized;
    }
    public long verifyFingerPrint(FingerprintID nowID,byte[] nowImage, int nowWidth, int nowHeight,boolean[] matched){

        long result = -1;

        Log.d(TAG, "Entered verifiyFingerprint");

        if(!isInitialized()) return result;
        if(matched==null) return result;

        matched[0] = false;

        try {
            result = sgfplib.SetTemplateFormat(SecuGen.FDxSDKPro.SGFDxTemplateFormat.TEMPLATE_FORMAT_ISO19794);

            int quality[] = new int[1];
            result = sgfplib.GetImageQuality(nowWidth, nowHeight, nowImage, quality);
            Log.d(TAG, "GetImageQuality() ret:" + result + "quality [" + quality[0] + "]\n");

            SGFingerInfo fpInfo = new SGFingerInfo();
            fpInfo.FingerNumber = 1;
            fpInfo.ImageQuality = quality[0];
            fpInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
            fpInfo.ViewNumber = 1;

            for (int i = 0; i < mRegisterTemplate.length; ++i)
                mRegisterTemplate[i] = 0;

            result = sgfplib.CreateTemplate(fpInfo, nowImage, mRegisterTemplate);

            if (result == SGFDxErrorCode.SGFDX_ERROR_NONE) {
                templateList.put(nowID.getID(), mRegisterTemplate);
                Log.e(TAG, "Template Generated !!!!");
            }

           Iterator nowIterator = templateList.entrySet().iterator();
            while(nowIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)nowIterator.next();

                if(((Integer)mapElement.getKey())==nowID.getID()) continue;

                byte[] nowTemplate = (byte[])mapElement.getValue();

                result = sgfplib.MatchTemplate(mRegisterTemplate, nowTemplate, SGFDxSecurityLevel.SL_NORMAL, matched);

                if(matched[0]) break;
            }

            fpInfo = null;

        }catch(Exception exc){
            Log.e(TAG, "Verify Fingerprint Error : "+exc.getMessage());
            return -1;
        }

        Log.d(TAG, "Leaving verifiyFingerprint");

        return 0;
    }

}
