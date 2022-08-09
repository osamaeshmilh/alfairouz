package ly.alfairouz.lab.config;

import ly.alfairouz.lab.service.util.JasperReportsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportsConfig {

    @Bean
    public JasperReportsUtil jasperReportsUtil() {
        return new JasperReportsUtil("reports/");
    }
}
