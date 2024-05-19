package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Bill_Item")
@Getter
@Setter
@ToString
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

  @Column(name = "bill_item", nullable = false)
  private String billItem;

  @Column(name = "amount", nullable = false)
  private float amount;

  @ManyToOne private BillingE billing;
}
