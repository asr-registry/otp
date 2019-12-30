package af.asr.otp.otp.data.model;


import af.asr.otp.otp.configuration.SpringContext;
import af.asr.otp.otp.service.OTPSender;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OtpSetting {

    private String id;

    private String accountId;

    private String name;

    private String messageTitle;

    private String messageTemplate;

    private int otpLength;

    private boolean useLetters;

    private boolean useDigits;

    private long ttlMinutes; //OTP time to live

    private String sender;

    public OTPSender getOTPOtpSender() {

        return (OTPSender) SpringContext.getBean(sender);
    }
}