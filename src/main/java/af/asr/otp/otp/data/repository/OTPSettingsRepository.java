package af.asr.otp.otp.data.repository;

import af.asr.otp.otp.data.model.OtpSetting;

public interface OTPSettingsRepository {
    OtpSetting getSetting(String settingId);
}