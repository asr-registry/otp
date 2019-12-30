package af.asr.otp.sender;

import af.asr.otp.data.model.OtpSetting;
import af.asr.otp.data.model.SentOTP;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OTPGenerator {

    public SentOTP generateSentOTP(OtpSetting otpSetting) {
        String OTP = RandomStringUtils.random(otpSetting.getOtpLength(), otpSetting.isUseLetters(), otpSetting.isUseDigits());
        SentOTP sentOTP = new SentOTP();
        sentOTP.setOperationId(UUID.randomUUID().toString());
        sentOTP.setExpireTime(System.currentTimeMillis() + otpSetting.getTtlMinutes() * 60 * 1000);
        sentOTP.setOTP(OTP);
        return sentOTP;
    }
}