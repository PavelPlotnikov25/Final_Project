package com.plotnikov.project.repository;

import com.plotnikov.project.model.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String > {

    Invoice findInvoiceById(String id);
}
