package com.company.ecommerce.infrastructure.repository.purchaseorder;

import com.company.ecommerce.domain.purchaseorder.PurchaseOrder;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HibernatePurchaseOrderRepository extends JpaRepository<PurchaseOrder, PurchaseOrderId> {
}
