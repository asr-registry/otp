package af.asr.otp.data.repository;

import af.asr.otp.data.model.OtpSetting;

public interface OTPSettingsRepository {
    OtpSetting getSetting(String settingId);
}