/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Invoice;

import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
public interface InvoiceService {
    List<Invoice> findAllInvoicesByCustomerId(Long customerId);

    List<Invoice> findAllInvoicesByStaffId(Long staffId);

    Invoice create(Invoice invoice);

    Iterable<Invoice> findAll();

    boolean switchVisible(String invoiceId);

    Invoice findById(String invoiceId);

    boolean setPaid(Long staffId, String invoiceId);

    List<String> findOrderedTable();

    List<Invoice> findAllInvoicesByDate(String date);

    List<Invoice> findAllInvoicesBetweenDates(String beginningDate, String endDate);

    public Invoice update(Invoice invoice);
}
