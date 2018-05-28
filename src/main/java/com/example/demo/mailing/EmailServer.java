package com.example.demo.mailing;

import com.example.demo.invoice.application.dto.InvoiceDTO;
import com.example.demo.invoice.application.services.InvoiceService;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderNotificationDTO;
import com.example.demo.procurement.application.services.ProcurementService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Resource;


import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.util.Properties;

public class EmailServer {
    String host = "pop.gmail.com";// change accordingly
    String mailStoreType = "pop3";
    String username = "esiteam12b@gmail.com";// change accordingly
    String password = "esiteam123";// change accordingly


    public void check()
    {
        try {
            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, username, password);
            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // search for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.search(unseenFlagTerm);
            System.out.println("Latest Emails: --->" + messages.length);


            for (Message message : messages) {
                MimeMessage source = (MimeMessage) message;
                MimeMessage copy = new MimeMessage(source);
                Multipart multipart = (Multipart) message.getContent();
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = (BodyPart)multipart.getBodyPart(i);

                    if (bodyPart.getFileName() != null && bodyPart.getFileName().startsWith("invoice")) {
                        String result = IOUtils.toString(bodyPart.getInputStream(), "UTF-8");
                        //get application context
                        ApplicationContext context = SpringContext.getAppContext();
                        // get instance of MainSpringClass (Spring Managed class)
                        System.out.println(result);
                        InvoiceService invoiceService = (InvoiceService) context.getBean("invoiceService");

                        Resource<InvoiceDTO> invoiceDTO = invoiceService.stringToResource(result);
//                        System.out.println("j");
                        System.out.println(invoiceDTO);
                        invoiceService.processInvoice(invoiceDTO);
                    }
                    else if(bodyPart.getFileName() != null && bodyPart.getFileName().startsWith("po"))
                    {

                        String result = IOUtils.toString(bodyPart.getInputStream(), "UTF-8");
                        //get application context
                        ApplicationContext context = SpringContext.getAppContext();
                        // get instance of MainSpringClass (Spring Managed class)
                        System.out.println(result);
                        ProcurementService procurementService = (ProcurementService) context.getBean("procurementService");

                        Resource<PurchaseOrderNotificationDTO> poDTO = procurementService.stringToResource(result);
                        System.out.println(poDTO);


                        procurementService.UpdatePO(poDTO);

                    }
                }
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
