package com.example.quickstartvalidation.model.provider;

import com.example.quickstartvalidation.model.UserDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 多字段联合校验Provider
 * 如果不是public修饰的，会遇到 Unable to instantiate the default group sequence provider
 */
public class UserDTOGroupSequenceProvider implements DefaultGroupSequenceProvider<UserDTO> {
	@Override
	public List<Class<?>> getValidationGroups(UserDTO object) {
		List<Class<?>> defaultGroupSequence = new ArrayList<>();
		defaultGroupSequence.add(UserDTO.class);
		return defaultGroupSequence;
	}
}
