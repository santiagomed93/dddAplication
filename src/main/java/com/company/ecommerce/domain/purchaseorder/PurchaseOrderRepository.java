package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.BaseRepository;

public interface PurchaseOrderRepository extends BaseRepository<PurchaseOrder, PurchaseOrderId> {
    PurchaseOrderId generateId();
}
