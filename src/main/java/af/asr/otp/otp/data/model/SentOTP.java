package af.asr.otp.otp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SentOTP {

    private String operationId;

    private String OTP;

    private long expireTime;
}