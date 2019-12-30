package af.asr.otp.controller;

import af.asr.otp.data.model.OtpSetting;
import af.asr.otp.data.model.SentOTP;
import af.asr.otp.data.repository.OTPSettingsRepository;
import af.asr.otp.data.repository.SentOTPRepository;
import af.asr.otp.service.OTPGenerator;
import af.asr.otp.service.OTPSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/otp/v1")
public class OTPRestController {

    private OTPSettingsRepository otpSettingsRepository;

    private SentOTPRepository sentOTPRepository;

    private OTPGenerator otpGenerator;


    public OTPRestController(OTPSettingsRepository otpSettingsRepository,
                             SentOTPRepository sentOTPRepository,
                             OTPGenerator otpGenerator) {
        this.otpSettingsRepository = otpSettingsRepository;
        this.sentOTPRepository = sentOTPRepository;
        this.otpGenerator = otpGenerator;

    }

    @PostMapping("/{settingId}/send")
    public SendOTPResult send(@PathVariable("settingId") String settingId, @RequestBody SendOTPRequest sendOTPRequest) {
        OtpSetting otpSetting = otpSettingsRepository.getSetting(settingId);
        SentOTP sentOTP = otpGenerator.generateSentOTP(otpSetting);
        OTPSender otpSender = otpSetting.getOTPOtpSender();
        otpSender.sendOTP(otpSetting, sentOTP.getOTP(), sendOTPRequest.getDestination(), sendOTPRequest.getProperties());
        sentOTPRepository.save(sentOTP);
        return new SendOTPResult(sentOTP.getOperationId());
    }

    @PostMapping("/verify")
    public VerifyOTPResult verify(@RequestBody VerifyOTPRequest verifyOTPRequest) {
        SentOTP sentOTP = sentOTPRepository.getById(verifyOTPRequest.getOperationId());
        if(sentOTP == null) {
            throw new OperationNotFoundException();
        }

        boolean result = sentOTP.getExpireTime() > System.currentTimeMillis()
                && sentOTP.getOTP().equals(verifyOTPRequest.getOtp());

        return new VerifyOTPResult(result);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Opertaion not found")
    public static class OperationNotFoundException extends RuntimeException {

    }

    @Data
    public static class SendOTPRequest {
        private String destination;
        private Map<String, String> properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendOTPResult {
        private String operationId;
    }

    @Data
    public static class VerifyOTPRequest {
        public String operationId;
        public String otp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static  class VerifyOTPResult {
        private boolean verified;
    }

}