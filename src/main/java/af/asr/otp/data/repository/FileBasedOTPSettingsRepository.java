package af.asr.otp.data.repository;

import af.asr.otp.data.model.OtpSetting;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Log4j2
public class FileBasedOTPSettingsRepository implements OTPSettingsRepository {


    @Value("${otp.settings.config:classpath:otp-settings.yaml}")
    private String configPath;

    private List<OtpSetting> otpSettings;

    private ResourceLoader resourceLoader;

    public FileBasedOTPSettingsRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void init() {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            otpSettings = objectMapper.readValue(resourceLoader.getResource(configPath).getInputStream(), new TypeReference<List<OtpSetting>>(){});
            log.info("loaded OTP settings: {}", otpSettings);
        } catch (IOException e) {
            log.error("error occurred : {}", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public OtpSetting getSetting(String settingId) {
        return otpSettings.stream()
                .filter(o -> StringUtils.equals(settingId, o.getId()))
                .findFirst().orElse(null);

    }
}