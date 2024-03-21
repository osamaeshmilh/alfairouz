package ly.alfairouz.lab.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class SMSService {

    public SMSService() {
    }

    @Async
    public void sendSMS(String phone, String message) {
        try {
            System.out.println(phone + " " + message);
            String uri = buildUri(phone, message);

            if (!uri.isEmpty()) {
                System.out.println(uri);
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(uri, String.class);
                // Consider logging the response or handling it as needed
            } else {
                // Log unsupported number or handle it accordingly
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Consider implementing a robust error handling mechanism
        }
    }

    private String buildUri(String phone, String message) {
        String uri = "";
        phone = phone.substring(phone.length() - 9);
        if (phone.startsWith("+21891") || phone.startsWith("+21893")) {
            uri = "https://easysms.devs.ly/sms/api?action=send-sms&api_key=YWxmYWlyb3V6OmFsZmFpcm91eg==&unicode=1&to=" + phone + "&sms=" + message;
        } else if (phone.startsWith("+21892") || phone.startsWith("+21894") || phone.startsWith("+21895")) {
            uri = "https://easysms.devs.ly/sms/api?action=send-sms&api_key=YWxmYWlyb3V6OmFsZmFpcm91eg==&unicode=1&to=" + phone + "&sms=" + message;
        }
        // Adjust as needed for other cases or throw an exception for unsupported numbers
        return uri;
    }

    //SMS API URL FOR UNICODE OR UTF8 SMS:
    //https://easysms.devs.ly/sms/api?action=send-sms&api_key=YWxmYWlyb3V6OmFsZmFpcm91eg==&to=918800431&sms=YourMessage&unicode=1


}
