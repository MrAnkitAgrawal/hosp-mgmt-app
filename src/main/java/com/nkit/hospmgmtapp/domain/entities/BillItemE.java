package com.nkit.hospmgmtapp.domain.entities;

import com.nkit.hospmgmtapp.resources.models.BillItemDto;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.*;

@Entity
@Table(name = "Bill_Item")
@Getter
@Setter
@ToString(exclude = {"billing"})
@NoArgsConstructor
public class BillItemE implements Serializable {
  @Serial private static final long serialVersionUID = -1853525879774366183L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_item_seq_gen")
  @SequenceGenerator(
      name = "bill_item_seq_gen",
      sequenceName = "bill_item_seq_gen",
      initialValue = 1)
  @Column(name = "bill_item_id")
  private Long itemId;

  @Column(name = "bill_item_type", nullable = false)
  private String billItemType;

  @Column(name = "bill_item_name", nullable = false)
  private String billItemName;

  @Column(name = "item_quantity")
  private Integer itemQuantity;

  @Column(name = "amount", nullable = false)
  private Float amount;

  @ManyToOne
  @JoinColumn(
      referencedColumnName = "billing_id",
      name = "billing_id",
      nullable = false,
      updatable = false)
  private BillingE billing;

  public BillItemE(BillItemDto billItemDto) {
    this.itemId = billItemDto.getItemId();
    this.billItemType = billItemDto.getBillItemType();
    this.billItemName = billItemDto.getBillItemName();
    this.itemQuantity = billItemDto.getItemQuantity();
    this.amount = billItemDto.getAmount();
  }
}
