package com.returnsoft.recruitment.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.returnsoft.recruitment.enumeration.MonthEnum;
@Converter(autoApply=true)
public class MonthConverter implements AttributeConverter<MonthEnum, Short>{
	
	@Override
	public Short convertToDatabaseColumn(MonthEnum attribute) {
		return (attribute==null) ? null :  attribute.getId();
	}

	@Override
	public MonthEnum convertToEntityAttribute(Short dbData) {
		return (dbData == null) ? null : MonthEnum.findById(dbData);
	}
	

}
