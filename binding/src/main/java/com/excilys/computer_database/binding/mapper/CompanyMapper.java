package com.excilys.computer_database.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.binding.dto.CompanyDTO;
import com.excilys.computer_database.binding.dto.CompanyDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Company;
import com.excilys.computer_database.core.model.CompanyBuilder;
import com.excilys.computer_database.persistence.CompanyDAO;

public class CompanyMapper implements RowMapper<Company> {

	public CompanyMapper() {}
	
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return resultSetCompany(rs);
	}

	public static Company resultSetCompany(ResultSet res) throws SQLException {
		return new CompanyBuilder()
				.id(res.getInt(CompanyDAO.ID_CN_ALIAS))
				.name(res.getString(CompanyDAO.NAME_CN_ALIAS))
				.build();
	}
	
	public static Company dtoToCompany(CompanyDTO dto) {
		return new CompanyBuilder()
				.id(Util.parseInt(dto.getId()).isEmpty() ? 0 : Util.parseInt(dto.getId()).get())	
				.name(dto.getName())
				.build();
	}
	
	public static CompanyDTO companyToDTO(Company company) {
		return new CompanyDTOBuilder()
				.id(Integer.toString(company.getId()))
				.name(company.getName())
				.build();
	}
}