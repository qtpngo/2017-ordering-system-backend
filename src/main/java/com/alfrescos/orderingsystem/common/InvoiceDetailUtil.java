/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.common;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import com.alfrescos.orderingsystem.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by Liger on 16-Mar-17.
 */
public class InvoiceDetailUtil {
    public static boolean addInvoiceDetail(Map<String, String> data, Invoice invoice, Date timeOrdered, FoodAndDrinkService foodAndDrinkService, InvoiceDetailService invoiceDetailService) {
        boolean isInvoiceDetailAllCreated = true;
        try {
            String[] foodAndDrinkIdList = data.get("foodAndDrinkId").trim().split(",");
            String[] quantityList = data.get("quantity").trim().split(",");
            FoodAndDrink foodAndDrink;
            int quantity;
            if (foodAndDrinkIdList.length == quantityList.length) {
                for (int i = 0; i < foodAndDrinkIdList.length; i++) {
                    quantity = Integer.parseInt(quantityList[i].trim());
                    foodAndDrink = foodAndDrinkService.findById(Long.parseLong(foodAndDrinkIdList[i].trim()));
                    if (foodAndDrink != null){
                        InvoiceDetail invoiceDetail = invoiceDetailService.create(new InvoiceDetail(invoice, foodAndDrink,
                                quantity, timeOrdered, foodAndDrink.getPrice()));
                        if (invoiceDetail == null){
                            isInvoiceDetailAllCreated = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        return isInvoiceDetailAllCreated;
    }
}
