package com.company.ecommerce.infrastructure.repository.purchaseorder;

import com.company.ecommerce.domain.purchaseorder.PurchaseOrder;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderId;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class H2PurchaseOrderRepository implements PurchaseOrderRepository {

    @Autowired
    private HibernatePurchaseOrderRepository repository;

    @Override
    public PurchaseOrderId generateId() {
        return new PurchaseOrderId(UUID.randomUUID().toString());
    }

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return repository.save(purchaseOrder);
    }

    @Override
    public void remove(PurchaseOrderId purchaseOrderId) {
        repository.deleteById(purchaseOrderId);
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> findById(PurchaseOrderId purchaseOrderId) {
        return repository.findById(purchaseOrderId);
    }
}
