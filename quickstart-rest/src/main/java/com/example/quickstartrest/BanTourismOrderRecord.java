package com.example.quickstartrest;

import jakarta.persistence.*;

@Entity(name = "tp_ban_tourism_order_record")
public class BanTourismOrderRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "`Id`", nullable = false, insertable = false, updatable = false)
	private Long id;

	@Column(name = "CustomerId")
	private Long customerId;

	@Column(name = "MarkInfo")
	private String markInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getMarkInfo() {
		return markInfo;
	}

	public void setMarkInfo(String markInfo) {
		this.markInfo = markInfo;
	}
}
