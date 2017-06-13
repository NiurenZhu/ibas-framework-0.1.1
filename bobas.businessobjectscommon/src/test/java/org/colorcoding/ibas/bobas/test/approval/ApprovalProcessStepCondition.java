package org.colorcoding.ibas.bobas.test.approval;

import org.colorcoding.ibas.bobas.approval.IApprovalProcessStepCondition;
import org.colorcoding.ibas.bobas.approval.ValueMode;
import org.colorcoding.ibas.bobas.data.emConditionOperation;
import org.colorcoding.ibas.bobas.data.emConditionRelationship;

public class ApprovalProcessStepCondition implements IApprovalProcessStepCondition {
	public ApprovalProcessStepCondition() {
		this.setOperation(emConditionOperation.EQUAL);
		this.setRelation(emConditionRelationship.AND);
	}

	private ValueMode propertyValueMode = ValueMode.DBField;

	@Override
	public ValueMode getPropertyValueMode() {
		return this.propertyValueMode;
	}

	public void setPropertyValueMode(ValueMode value) {
		this.propertyValueMode = value;
	}

	private String propertyName;

	@Override
	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String value) {
		this.propertyName = value;
	}

	private emConditionOperation operation;

	@Override
	public emConditionOperation getOperation() {
		return this.operation;
	}

	public void setOperation(emConditionOperation value) {
		this.operation = value;
	}

	private emConditionRelationship relation;

	@Override
	public emConditionRelationship getRelation() {
		return this.relation;
	}

	public void setRelation(emConditionRelationship value) {
		this.relation = value;
	}

	private ValueMode conditionValueMode = ValueMode.Input;

	@Override
	public ValueMode getConditionValueMode() {
		return this.conditionValueMode;
	}

	public void setConditionValueMode(ValueMode value) {
		this.conditionValueMode = value;
	}

	private String conditionValue;

	@Override
	public String getConditionValue() {
		return this.conditionValue;
	}

	public void setConditionValue(String value) {
		this.conditionValue = value;
	}

}
