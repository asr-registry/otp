package af.asr.otp.tests.model;

import lombok.Data;

@Data
public class Account {

    private String id;

    private String token;

    private String userName;

    private String password;

}