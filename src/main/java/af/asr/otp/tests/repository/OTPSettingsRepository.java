package af.asr.otp.tests.repository;

import af.asr.otp.tests.model.OtpSetting;

public interface OTPSettingsRepository {
    OtpSetting getSetting(String settingId);
}