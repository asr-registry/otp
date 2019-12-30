package af.asr.otp.data.repository;


import af.asr.otp.data.model.OtpSetting;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOTPSettingsRepository implements OTPSettingsRepository {

    private Map<String, OtpSetting> otpSettingMap;

    public InMemoryOTPSettingsRepository() {
        otpSettingMap = new HashMap<>();
        OtpSetting smsSetting = new OtpSetting();
        smsSetting.setId("sms");
        smsSetting.setAccountId(null);
        smsSetting.setMessageTemplate("Code: ${otp}");
        smsSetting.setName("SMS sender");
        smsSetting.setOtpLength(6);
        smsSetting.setUseDigits(true);
        smsSetting.setUseLetters(false);
        smsSetting.setTtlMinutes(3);
        smsSetting.setSender("dummyOtpSender");

        otpSettingMap.put(smsSetting.getId(), smsSetting);
    }


    @Override
    public OtpSetting getSetting(String settingId) {
        return otpSettingMap.get(settingId);
    }
}