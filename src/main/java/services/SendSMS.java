package services;

import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SendSMS {

    public  static  void sendSMS(){


        // Sender's phone number (a Twilio phone number)
        final String fromNumber = "+16125644425";

        // Recipient's phone number
        final String toNumber = "+21653848879";

        // Message content
        final String messageBody = "API MESSAGE";

        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(fromNumber),
                    messageBody
            ).create();

            System.out.println("Message sent with sid: " + message.getSid());
        } catch (TwilioException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}
