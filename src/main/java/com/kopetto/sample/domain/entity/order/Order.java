package com.kopetto.sample.domain.entity.order;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.kopetto.sample.domain.entity.BaseEntity;

/**
 * Entity for counter.
 * 
 */
@SuppressWarnings("serial")
@Document(collection = "Order")
public class Order extends BaseEntity{
    
    private String orderId;
    private Date createDt;
    private Date updatedDt;
    private String description;
    private String userName;
    
	public Order() {
		
	}
    
	public Order(String orderId, String description, String userName) {
		this.orderId=orderId;
		this.description=description;
		this.userName=userName;
	}
	
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public Date getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    
}
