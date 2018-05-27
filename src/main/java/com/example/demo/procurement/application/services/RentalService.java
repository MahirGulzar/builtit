package com.example.demo.procurement.application.services;

import com.example.demo.common.utils.FlowsHelper;
import com.example.demo.procurement.application.dto.PlantInventoryEntryDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderAcceptDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderSupplierDTO;
import com.example.demo.procurement.domain.model.PurchaseOrder;
import com.example.demo.procurement.domain.model.enums.POStatus;
import com.example.demo.procurement.integration.gateways.RentalGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
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

    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderAcceptDTO poDTO, String callUrl) {

        String json = null;
        Resource<PurchaseOrderDTO> returnedPO=null;
        try {
            json = mapper.writeValueAsString(poDTO);
            returnedPO=(Resource<PurchaseOrderDTO>)rentalGateway.createPurchaseOrder(json, callUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PurchaseOrderDTO  po= new PurchaseOrderDTO();
        po.setHref(returnedPO.getLink("self").get().getHref());
        po.setPoStatus(POStatus.UNPAID);
        return po;
    }

    public PurchaseOrderDTO requestSupplierForPOCancellation(PurchaseOrderDTO purchaseOrderDTO) {

        String json = null;
        Resource<PurchaseOrderDTO> returnedPO=null;
        try {
            json = mapper.writeValueAsString(purchaseOrderDTO);

            returnedPO = (Resource<PurchaseOrderDTO>)rentalGateway.requestSupplierForPOCancellation(json, purchaseOrderDTO.getHref()+ "/cancel" );

        } catch (Exception e) {
            e.printStackTrace();
        }

        PurchaseOrderDTO  po= new PurchaseOrderDTO();
        po.setHref(returnedPO.getLink("self").get().getHref());
        return po;
    }

    public PurchaseOrderSupplierDTO getPurchaseOrder(String linkPO) {

        String json = null;
        Resource<PurchaseOrderSupplierDTO> returnedPO=null;
        try {
            returnedPO = (Resource<PurchaseOrderSupplierDTO>)rentalGateway.getSupplierPO(linkPO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnedPO.getContent();
    }

    public PurchaseOrderDTO extendPurchaseOrder(PurchaseOrderAcceptDTO purchaseOrderDTO, String linkPO) {

        String json = null;
        Resource<PurchaseOrderDTO> returnedPO=null;
        try {
            json = mapper.writeValueAsString(purchaseOrderDTO);
            FlowsHelper.destinationURI = linkPO+"/extension";
            returnedPO = (Resource<PurchaseOrderDTO>)rentalGateway.requestPOExtension(json, linkPO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PurchaseOrderDTO  po= new PurchaseOrderDTO();
        po.setHref(returnedPO.getLink("self").get().getHref());
        return po;
    }

    public PlantInventoryEntryDTO getPlant(String link) {
        Resource<PlantInventoryEntryDTO> returnedPlant=null;
        try {

            returnedPlant = (Resource<PlantInventoryEntryDTO>)rentalGateway.getPlantHTTP(link);

        } catch (Exception e) {
            e.printStackTrace();
        }
        returnedPlant.getContent().setHref(returnedPlant.getLink("self").get().getHref());
        return returnedPlant.getContent();
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


