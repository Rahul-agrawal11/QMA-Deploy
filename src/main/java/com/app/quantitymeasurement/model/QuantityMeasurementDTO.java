package com.app.quantitymeasurement.model;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class QuantityMeasurementDTO {

	private Double thisValue;
	private String thisUnit;
	private String thisMeasurementType;

	private Double thatValue;
	private String thatUnit;
	private String thatMeasurementType;

	private String operation;

	private String resultString;
	private Double resultValue;
	private String resultUnit;
	private String resultMeasurementType;

	private String errorMessage;

	@JsonProperty("error")
	private boolean error;

	public QuantityMeasurementDTO(Double thisValue, String thisUnit, String thisMeasurementType, Double thatValue,
			String thatUnit, String thatMeasurementType, String operation, String resultString, Double resultValue,
			String resultUnit, String resultMeasurementType, String errorMessage, boolean error) {

		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;

		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;

		this.operation = operation;

		this.resultString = resultString;
		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;

		this.errorMessage = errorMessage;
		this.error = error;
	}

	// ------------------- ENTITY → DTO -------------------
	public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
		if (entity == null)
			return null;

		return QuantityMeasurementDTO.builder()
				.thisValue(entity.getThisValue())
				.thisUnit(entity.getThisUnit())
				.thisMeasurementType(entity.getThisMeasurementType())
				.thatValue(entity.getThatValue())
				.thatUnit(entity.getThatUnit())
				.thatMeasurementType(entity.getThatMeasurementType())
				.operation(entity.getOperation())
				.resultString(entity.getResultString())
				.resultValue(entity.getResultValue())
				.resultUnit(entity.getResultUnit())
				.resultMeasurementType(entity.getResultMeasurementType())
				.errorMessage(entity.getErrorMessage())
				.error(entity.isError())
				.build();
	}

	// ------------------- DTO → ENTITY -------------------
	public QuantityMeasurementEntity toEntity() {
		return QuantityMeasurementEntity.builder()
				.thisValue(thisValue)
				.thisUnit(thisUnit)
				.thisMeasurementType(thisMeasurementType)
				.thatValue(thatValue)
				.thatUnit(thatUnit)
				.thatMeasurementType(thatMeasurementType)
				.operation(operation)
				.resultString(resultString)
				.resultValue(resultValue)
				.resultUnit(resultUnit)
				.resultMeasurementType(resultMeasurementType)
				.errorMessage(errorMessage)
				.isError(error)
				.build();
	}

	// ------------------- LIST MAPPERS -------------------
	public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
		if (entities == null)
			return List.of();
		return entities.stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
	}

	public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
		if (dtos == null)
			return List.of();
		return dtos.stream().map(QuantityMeasurementDTO::toEntity).collect(Collectors.toList());
	}
}