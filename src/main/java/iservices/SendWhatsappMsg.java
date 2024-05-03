package iservices;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import models.Personne;

public class SendWhatsappMsg {
    public static void sendWhatsappMsg(Personne personne){
        // Find your Account Sid and Token at twilio.com/console
        final String ACCOUNT_SID = "AC9da0c90730010d8d2a6cbf453ad8cd1f";
        final String AUTH_TOKEN = "e4bd0fd7e9cb763517268ef0487c09c3";


            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:+21628113435"),
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    "You have a new complaint! Sent by "+personne.getNom()+" "+personne.getPrenom()

            ).create();

            System.out.println(message.getSid());

    }

}
