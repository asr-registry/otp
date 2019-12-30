package af.asr.otp.sender;

import af.asr.otp.data.model.OtpSetting;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class DummyOTPSender implements OTPSender {

    @Override
    public void sendOTP(OtpSetting otpSetting, String otp, String destination, Map<String, String> properties) {
        String message = createMessage(otpSetting, otp, destination, properties);
        log.info("message: {}", message);

    }
}