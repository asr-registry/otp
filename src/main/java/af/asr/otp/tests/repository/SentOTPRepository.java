package af.asr.otp.tests.repository;

import af.asr.otp.tests.model.SentOTP;

public interface SentOTPRepository {
    void save(SentOTP sentOTP);
    SentOTP getById(String operationId);
}