package com.example.demo.procurement.application.services;


import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.common.domain.BusinessPeriod;
import com.example.demo.procurement.application.dto.Plant;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderAcceptDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderSupplierDTO;
import com.example.demo.procurement.domain.model.*;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.model.enums.PHRStatus;
import com.example.demo.procurement.domain.model.enums.POStatus;
import com.example.demo.procurement.domain.repository.*;
import com.example.demo.procurement.rest.controller.EmployeeRestController;
import com.example.demo.procurement.rest.controller.ProcurementRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProcurementService {


    @Autowired
    PlantHireRequestRepository plantHireRequestRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PlantHireRequestAssembler plantHireRequestAssembler;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;


    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    RentalService rentalService;

    /* Get All PHRs */
    public Resources<Resource<PlantHireRequestDTO>> getAllPlantHireRequests(){

        return plantHireRequestAssembler.toResources(plantHireRequestRepository.findAll());
    }

    /* Get One PHR By Id */
    public Resource<PlantHireRequestDTO> getPlantHireRequestById(long id)
    {
        return plantHireRequestAssembler.toResource(plantHireRequestRepository.findById(id));
    }

    /* Create PHR */
    public Resource<PlantHireRequestDTO> createPlantHireRequest(PlantHireRequestDTO phrDTO) {

        Employee siteEngineer = employeeRepository.getOne(phrDTO.getSiteEngineer().getContent().get_id());
        Employee worksEngineer = null;

        System.out.println(siteEngineer);
        ConstructionSite constructionSite = constructionSiteRepository.getOne(phrDTO.getConstructionSite().getContent().get_id());

        PlantInventoryEntry plant = null;
        //System.out.println(phrDTO.getPlantInventoryEntry());
        if(phrDTO.getPlantInventoryEntry() != null) {
            plant = PlantInventoryEntry.of(
                    phrDTO.getPlantInventoryEntry().get_id(),
                    phrDTO.getPlantInventoryEntry().getName(),
                    phrDTO.getPlantInventoryEntry().getDescription(),
                    phrDTO.getPlantInventoryEntry().getPrice(),
                    phrDTO.getPlantInventoryEntry().get_link(),
                    null);
            plantInventoryEntryRepository.save(plant);
        }
        else {
            return null;
        }


        BusinessPeriod rentalPeriod = null;
        if(phrDTO.getRentalPeriod() != null) {
            rentalPeriod = BusinessPeriod.of(
                    phrDTO.getRentalPeriod().getStartDate(),
                    phrDTO.getRentalPeriod().getEndDate());
        }
        else {
            return null;
        }

        PlantHireRequest request = PlantHireRequest.of(
                plant,
                rentalPeriod,
                siteEngineer,
                constructionSite
        );

        PlantHireRequest phr = plantHireRequestRepository.save(request);
        System.out.println("--------------"+request);
        return plantHireRequestAssembler.toResource(phr);

    }

    /* Update PHR (Both SiteEngineer and Word Engineer) */
    public Resource<PlantHireRequestDTO> updatePlantHireRequest(PlantHireRequestDTO plantHireRequestDTO){


        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(plantHireRequestDTO.get_id());

        if(plantHireRequest == null || plantHireRequest.getStatus() != PHRStatus.PENDING_APPROVAL) return null;

        Comment comment = new Comment(plantHireRequestDTO.getComments());
        System.out.println(comment);
        plantHireRequest.setComments(comment);
        plantHireRequest.setConstructionSite(constructionSiteRepository.getOne(plantHireRequestDTO.getConstructionSite().getContent().get_id()));
        plantHireRequest.setSiteEngineer(employeeRepository.getOne(plantHireRequestDTO.getConstructionSite().getContent().get_id()));
        //plantHireRequest.setStatus(plantHireRequestDTO.getStatus()); //TODO need to check later that status should be here or not
        //System.out.println(plantHireRequest);

        if(plantHireRequestDTO.getWorksEngineer() != null)
        {
            Employee worksEngineer = employeeRepository.getOne(plantHireRequestDTO.getWorksEngineer().getContent().get_id());
            plantHireRequest.setWorksEngineer(worksEngineer);
        }
        plantHireRequestRepository.save(plantHireRequest);


        return plantHireRequestAssembler.toResource(plantHireRequest);
    }

    /* Cancel PHR  */
    public Resource<PlantHireRequestDTO> cancelPlantHireRequest(Long id){

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(id);
        System.out.println(plantHireRequest);

        if(plantHireRequest == null) return null;

        // we are assuming here that if status is still PENDING_APPROVAL, no need to go for rentIT or check for dates.
        if(plantHireRequest.getStatus() == PHRStatus.PENDING_APPROVAL) {
            plantHireRequest.cancelPHR();
        } else {
            if(plantHireRequest.getRentalPeriod().getStartDate().isAfter(LocalDate.now().minusDays(1)) && !plantHireRequest.getRentalPeriod().getStartDate().isEqual(LocalDate.now())) {

                PurchaseOrderDTO purchaseOrderDTO = plantHireRequestAssembler.toResource(plantHireRequest).getContent().getOrder().getContent();
                PurchaseOrderDTO cancelledPO = rentalService.requestSupplierForPOCancellation(purchaseOrderDTO);
                plantHireRequest.setPurchaseOrder(cancelledPO.asPurchaseOrder());
                plantHireRequest.cancelPHR(); //Todo assuming that there is no concept of CancelPending

            } else return null;
        }

        plantHireRequestRepository.save(plantHireRequest);
        return plantHireRequestAssembler.toResource(plantHireRequest);
    }

    /* Approve PHR  */
    public Resource<PlantHireRequestDTO> approvePlantHireRequest(PlantHireRequestDTO plantHireRequestDTO){

        Employee worksEngineer = employeeRepository.getOne(plantHireRequestDTO.getWorksEngineer().getContent().get_id());
        if(worksEngineer==null)
        {
            //TODO throw exception or something here...
        }

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(plantHireRequestDTO.get_id());


        PurchaseOrderAcceptDTO po = PurchaseOrderAcceptDTO.of(
                Plant.of(
                        plantHireRequest.getPlantInventoryEntry().get_id(),
                        null,
                        null,
                        null
                ),
                plantHireRequestDTO.getRentalPeriod(),
                linkTo(methodOn(ProcurementRestController.class).acceptPO(plantHireRequest.getId())).toString(),
                linkTo(methodOn(ProcurementRestController.class).rejectPO(plantHireRequest.getId())).toString()
        );

        PurchaseOrderDTO rtnPo =  rentalService.createPurchaseOrder(po);

        PurchaseOrder phrPo = new PurchaseOrder();
        phrPo.setHref(rtnPo.getHref());
        phrPo.setPoStatus(POStatus.UNPAID);
        purchaseOrderRepository.save(phrPo);

        plantHireRequest.approvePHR(worksEngineer,phrPo);


        Comment comment = new Comment(plantHireRequestDTO.getComments());

        plantHireRequest.setComments(comment);

        plantHireRequestRepository.save(plantHireRequest);

        return plantHireRequestAssembler.toResource(plantHireRequest);
    }

    public Resource<PlantHireRequestDTO> extendPlantHireRequest(Long id, LocalDate endDate) {

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(id);
        if(plantHireRequest == null) return null;

        plantHireRequest.setRentalPeriod(BusinessPeriod.of(plantHireRequest.getRentalPeriod().getStartDate(), endDate));

        PurchaseOrderAcceptDTO po = PurchaseOrderAcceptDTO.of(
                Plant.of(
                        plantHireRequest.getPlantInventoryEntry().get_id(),
                        null,
                        null,
                        null
                ),
                BusinessPeriodDTO.of(
                        plantHireRequest.getRentalPeriod().getStartDate(),
                        plantHireRequest.getRentalPeriod().getEndDate()
                ),
                linkTo(methodOn(ProcurementRestController.class).acceptPO(plantHireRequest.getId())).toString(),
                linkTo(methodOn(ProcurementRestController.class).rejectPO(plantHireRequest.getId())).toString()
        );

        String linkPO = plantHireRequest.getPurchaseOrder().getHref();

        PurchaseOrderDTO rtnPo =  rentalService.extendPurchaseOrder(po, linkPO);

        PurchaseOrder phrPo = new PurchaseOrder();
        phrPo.setHref(rtnPo.getHref());
        purchaseOrderRepository.save(phrPo);

        plantHireRequest.extendPlantHireRequest(phrPo);

        plantHireRequestRepository.save(plantHireRequest);

        return plantHireRequestAssembler.toResource(plantHireRequest);

    }



    /* Reject PHR  */
    public Resource<PlantHireRequestDTO> rejectPlantHireRequest(PlantHireRequestDTO plantHireRequestDTO){


        Employee worksEngineer = employeeRepository.getOne(plantHireRequestDTO.getWorksEngineer().getContent().get_id());
        if(worksEngineer==null)
        {
            //TODO throw exception or something here...
        }

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(plantHireRequestDTO.get_id());


        plantHireRequest.rejectPHR(worksEngineer);
        Comment comment = new Comment(plantHireRequestDTO.getComments());
        System.out.println(comment);
        plantHireRequest.setComments(comment);
        plantHireRequestRepository.save(plantHireRequest);
        return plantHireRequestAssembler.toResource(plantHireRequest);
    }

    /* Accept PO  */
    public PurchaseOrderDTO acceptPO(Long id) {

        PlantHireRequest phr = plantHireRequestRepository.getOne(id);
        if(phr == null) return null;

        phr.acceptPO();

        plantHireRequestRepository.save(phr);

        // TODO return full representation of PO here
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

        return purchaseOrderDTO;
    }

    /* Reject PO  */
    public PurchaseOrderDTO rejectPO(Long id) {

        PlantHireRequest phr = plantHireRequestRepository.getOne(id);
        if(phr == null) return null;

        phr.rejectPO();

        plantHireRequestRepository.save(phr);

        // TODO return full representation of PO here
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

        return purchaseOrderDTO;
    }

    public List<PurchaseOrderDTO> findAllPurchaseOrder() {
        //Todo it throw error when there is any pending PHR as PO is not created
        Resources<Resource<PlantHireRequestDTO>> allPHR = plantHireRequestAssembler.toResources(plantHireRequestRepository.findAll());
        List<PurchaseOrderDTO> returnPO = allPHR.getContent().stream().map(x -> x.getContent().getOrder().getContent()).collect(Collectors.toList());
        return returnPO;
    }

    public PurchaseOrderSupplierDTO findOnePurchaseOrder(Long id) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).get();
        if(purchaseOrder == null) return null;
       return rentalService.getPurchaseOrder(purchaseOrder.getHref());

    }



}
