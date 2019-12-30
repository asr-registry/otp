package af.asr.otp.inftrastructure.configuration;


import af.asr.otp.data.repository.*;
import af.asr.otp.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailSender;

@Configuration
//@Import(SpringContext.class)
public class OtpConfiguration {

    @Bean
    public AccountRepository accountRepository() {
        return new InMemoryAccountRepository(); //TODO add persistence
    }

    @Bean
    public SentOTPRepository sentOTPRepository() {
        return new InMemorySentOTPRepository(); //TODO add persistence
    }

    @Bean
    public OTPSettingsRepository otpSettingsRepository(ResourceLoader resourceLoader) {
        return new FileBasedOTPSettingsRepository(resourceLoader);
    }

    @Bean
    public OTPSender dummyOTPSender() {
        return new DummyOTPSender();
    }

    @Bean
    @ConditionalOnProperty(value = "TWILIO_ACCOUNT_SID")
    public OTPSender twilioOTPSender() {
        return new TwilioOTPSender();
    }

    @Bean
    public OTPSender emailOTPSender(MailSender mailSender) {
        return new EmailOTPSender(mailSender);
    }

    @Bean
    public OTPGenerator otpGenerator() {
        return new OTPGenerator();
    }
}