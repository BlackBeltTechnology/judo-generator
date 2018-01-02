rule compareTables
	match newTable : NEW!RdbmsTable
	with oldTable : PREVIOUS!RdbmsTable {
	compare : newTable.uuid = oldTable.uuid
	}