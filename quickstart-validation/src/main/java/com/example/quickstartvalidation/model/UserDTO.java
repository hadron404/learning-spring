package com.example.quickstartvalidation.model;

import com.example.quickstartvalidation.constant.Status;
import com.example.quickstartvalidation.constranit.Exist;
import com.example.quickstartvalidation.model.provider.UserDTOGroupSequenceProvider;
import lombok.Data;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;



@Data
@GroupSequenceProvider(UserDTOGroupSequenceProvider.class)
public class UserDTO {


	@NotNull(message = "id must be not null")
	private Integer id;

	private Integer number;

	private String text;

	private List<String> texts;

	@Exist(message = "unknown status", target = Status.class)
	private Integer status;

	@Valid
	private PersonalInfoDTO personalInfo;

}


