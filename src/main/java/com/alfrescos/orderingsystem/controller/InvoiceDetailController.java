package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.InvoiceDetailUtil;
import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import com.alfrescos.orderingsystem.service.InvoiceDetailService;
import com.alfrescos.orderingsystem.service.InvoiceService;
import com.alfrescos.orderingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liger on 16-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/invoice-detail")
public class InvoiceDetailController {

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private FoodAndDrinkService foodAndDrinkService;

    @Autowired
    private InvoiceService invoiceService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{invoiceId}")
    public ResponseEntity<?> getInvoiceDetailByInvoiceId(@PathVariable String invoiceId){
        List<InvoiceDetail> invoiceDetailList = invoiceDetailService.findAllInvoiceDetailsByInvoiceId(invoiceId);
        if (invoiceDetailList != null){
            return new ResponseEntity<Object>(invoiceDetailList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("No content for invoice id: " + invoiceId, HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/update")
    public ResponseEntity<?> addInvoiceDetailToInvoice(@RequestBody Map<String, String> data){
        System.out.println("11111");
        String invoiceId = data.get("invoiceId").trim();
        Invoice invoice = invoiceService.findInvoiceByInvoiceId(invoiceId);
        if (invoice == null){
            return new ResponseEntity<Object>("Can't find invoice with id: " + invoiceId, HttpStatus.NOT_ACCEPTABLE);
        } else if (!invoice.getCustomerUser().getAccountCode().equals(UserUtil.getAccountCodeByAuthorization())){
            return new ResponseEntity<Object>("You can't modify this invoice as you're not the owner.", HttpStatus.NOT_ACCEPTABLE);
        }
        int numberOfInvoiceDetails = Integer.parseInt(data.get("numberOfInvoiceDetails").trim());
        System.out.println(numberOfInvoiceDetails + "-" + invoiceId);
        Date timeOrdered = new Date();
        if (invoice != null && InvoiceDetailUtil.addInvoiceDetail(numberOfInvoiceDetails, data, invoice, timeOrdered, foodAndDrinkService, invoiceDetailService)){
            return new ResponseEntity<Object>("Added successfully.", HttpStatus.CREATED);
        }
        return new ResponseEntity<Object>("Couldn't add new invoice detail due to errors.", HttpStatus.NO_CONTENT);
    }
}