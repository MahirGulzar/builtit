package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.PurchaseOrderAcceptDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderSupplierDTO;
import com.example.demo.procurement.domain.model.PurchaseOrder;
import com.example.demo.procurement.integration.gateways.RentalGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
public class RentalService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RentalGateway rentalGateway;

    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderAcceptDTO poDTO) {

        String json = null;
        try {
            json = mapper.writeValueAsString(poDTO);
            System.out.println(json);
            rentalGateway.createPurchaseOrder(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PurchaseOrderDTO();

//        System.out.println(poDTO);
//        ResponseEntity<PurchaseOrderDTO> result;
//        System.out.println(new HttpEntity<>(createHeaders("customer", "customer")));
//        try {
//            result = restTemplate.exchange
//                    ("http://localhost:8090/api/sales/orders",
//                            HttpMethod.POST, new HttpEntity<>(createHeaders("customer", "customer")),
//                            PurchaseOrderDTO.class, poDTO);
//        }catch (Exception ex){
//            return new PurchaseOrderDTO();
//        }
//        //System.out.println(result);
////        ResponseEntity<PurchaseOrderDTO> result = restTemplate.postForEntity("http://localhost:8090/api/sales/orders",
////                new HttpEntity<>(createHeaders("customer", "customer"),
////                        poDTO,
////                        PurchaseOrderDTO.class
////                );
//
//        if (result == null &&  result.getStatusCode() != HttpStatus.CREATED)
//            return new PurchaseOrderDTO();
//        PurchaseOrderDTO  po= new PurchaseOrderDTO();
//        po.setHref(result.getHeaders().getLocation().toString());
//        return po;
    }


    public PurchaseOrderDTO requestSupplierForPOCancellation(PurchaseOrderDTO purchaseOrderDTO) {
        ResponseEntity<PurchaseOrderDTO> response;

        ResponseEntity<PurchaseOrderDTO> result = restTemplate.getForEntity(purchaseOrderDTO.getHref()+ "/cancel", PurchaseOrderDTO.class);

        if (result == null &&  result.getStatusCode() != HttpStatus.OK)
            return new PurchaseOrderDTO();
        PurchaseOrderDTO  po= new PurchaseOrderDTO();
        po.setHref(result.getHeaders().getLocation().toString());
        return po;
    }

    public PurchaseOrderSupplierDTO getPurchaseOrder(String linkPO) {

        ResponseEntity<PurchaseOrderSupplierDTO> result = restTemplate.getForEntity(linkPO, PurchaseOrderSupplierDTO.class);

        if (result == null &&  result.getStatusCode() != HttpStatus.OK)
            return new PurchaseOrderSupplierDTO();
        return result.getBody();
    }


    public PurchaseOrderDTO extendPurchaseOrder(PurchaseOrderAcceptDTO purchaseOrderDTO, String linkPO) {
        ResponseEntity<PurchaseOrderDTO> result = restTemplate.postForEntity(linkPO+"/extension", purchaseOrderDTO, PurchaseOrderDTO.class);

        if (result == null &&  result.getStatusCode() != HttpStatus.CREATED)
            return new PurchaseOrderDTO();

        PurchaseOrderDTO  po= new PurchaseOrderDTO();

        po.setHref(result.getHeaders().getLocation().toString());
        return po;
    }

    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}


