rule compareTables
	match newTable : NEW!RdbmsTable
	with oldTable : PREVIOUS!RdbmsTable {
	compare : newTable.uuid = oldTable.uuid
	}
	
rule compareTypes
	match newType : NEW!RdbmsFieldType
	with oldType : PREVIOUS!RdbmsFieldType {
	compare : newType.uuid = oldType.uuid
	}