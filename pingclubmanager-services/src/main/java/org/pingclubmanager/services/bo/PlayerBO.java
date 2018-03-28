package org.pingclubmanager.services.bo;

import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;

@Dto
public class PlayerBO {
	@DtoField
	private Integer id;
	@DtoField
	private String name;
	@DtoField
	private String firstname;
	@DtoField
	private String licence;
	@DtoField
	private Integer points;
	@DtoField
	private Boolean isTransferred;

	public PlayerBO() {
		super();
	}

	public PlayerBO(Integer id, String name, String firstname, String licence, Integer points, Boolean isTransferred) {
		super();
		this.id = id;
		this.name = name;
		this.firstname = firstname;
		this.licence = licence;
		this.points = points;
		this.isTransferred = isTransferred;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Boolean getIsTransferred() {
		return isTransferred;
	}

	public void setIsTransferred(Boolean isTransferred) {
		this.isTransferred = isTransferred;
	}

}
