package net.magja.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * An individual item in an {@link OrderForm}.
 *
 * @author rudi
 */
public class OrderFormItem implements Serializable {

  private Long productId;
  private Double qty;
  private BigDecimal price;

  public OrderFormItem() {
    super();
  }

  /**
   * @param productId
   * @param qty
   */
  public OrderFormItem(Long productId, Double qty) {
    super();
    this.productId = productId;
    this.qty = qty;
  }

  /**
   * @return the productId
   */
  public Long getProductId() {
    return productId;
  }

  /**
   * @param productId the productId to set
   */
  public void setProductId(Long productId) {
    this.productId = productId;
  }

  /**
   * @return the qty
   */
  public Double getQty() {
    return qty;
  }

  /**
   * @param qty the qty to set
   */
  public void setQty(Double qty) {
    this.qty = qty;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /* (non-Javadoc)
       * @see java.lang.Object#toString()
       */
  @Override
  public String toString() {
    return "OrderFormItem [productId=" + productId + ", qty=" + qty + "]";
  }

}
