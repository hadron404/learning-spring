package com.example.quickstartvalidation.model;

import lombok.Data;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 多字段联合校验Provider
 */
class UserDTOGroupSequenceProvider implements DefaultGroupSequenceProvider<UserDTO> {
	@Override
	public List<Class<?>> getValidationGroups(UserDTO object) {
		List<Class<?>> defaultGroupSequence = new ArrayList<>();
		defaultGroupSequence.add(UserDTO.class);
		return defaultGroupSequence;
	}
}

@Data
@GroupSequenceProvider(UserDTOGroupSequenceProvider.class)
public class UserDTO {


	private Long id;

	private Integer number;

	private String text;

	private List<String> texts;

	@Valid
	private PersonalInfoDTO personalInfo;

}


