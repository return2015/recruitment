package com.returnsoft.recruitment.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.returnsoft.recruitment.enumeration.YearEnum;

@Converter(autoApply=true)
public class YearConverter implements AttributeConverter<YearEnum, Short>{
	
	
	@Override
	public Short convertToDatabaseColumn(YearEnum attribute) {
		return (attribute==null) ? null :  attribute.getId();
	}

	@Override
	public YearEnum convertToEntityAttribute(Short dbData) {
		return (dbData == null) ? null : YearEnum.findById(dbData);
	}
	


}
