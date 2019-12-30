package af.asr.otp.tests;

import af.asr.otp.inftrastructure.configuration.OtpConfiguration;
import af.asr.otp.resource.OtpApiResource;
import af.asr.otp.tests.model.SentOTP;
import af.asr.otp.tests.repository.SentOTPRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@WebMvcTest(OtpApiResource.class)
@Import({OtpConfiguration.class, MailSenderAutoConfiguration.class})
public class ApiResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SentOTPRepository sentOTPRepository;


    @Test
    public void testSend() throws Exception {
        String requestBody = "{\"destination\": \"+93794035544\"}";

        mvc.perform(post("/otp/v1/{settingId}/send", "sms")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationId", notNullValue()));

    }

    @Test
    public void testVerify() throws Exception {

        String operationId = UUID.randomUUID().toString();
        String otp = "12345";
        SentOTP sentOTP = new SentOTP();
        sentOTP.setOperationId(operationId);
        sentOTP.setOTP(otp);
        sentOTP.setExpireTime(System.currentTimeMillis() + 1000);
        sentOTPRepository.save(sentOTP);

        String requestBody = "{\"operationId\": \""+operationId+"\", \"otp\" : \""+otp+"\"}";

        mvc.perform(post("/otp/v1/verify", "sms")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verified", is(true)));
    }

}