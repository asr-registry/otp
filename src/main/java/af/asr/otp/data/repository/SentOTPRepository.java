package af.asr.otp.data.repository;

import af.asr.otp.data.model.SentOTP;

public interface SentOTPRepository {
    void save(SentOTP sentOTP);
    SentOTP getById(String operationId);
}