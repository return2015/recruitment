package com.returnsoft.recruitment.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.returnsoft.recruitment.enumeration.UserTypeEnum;



@Converter(autoApply=true)
public class UserTypeConverter implements AttributeConverter<UserTypeEnum, Short> {

	@Override
	public Short convertToDatabaseColumn(UserTypeEnum attribute) {
		return (attribute==null) ? null :  attribute.getId();
	}

	@Override
	public UserTypeEnum convertToEntityAttribute(Short dbData) {
		return (dbData == null) ? null : UserTypeEnum.findById(dbData);
	}

}
