rule compareTables
	match newTable : MODIFIED!RdbmsTable
	with oldTable : ORIGINAL!RdbmsTable {
	compare : newTable.uuid = oldTable.uuid
	do {
		oldTable.name.println("Oldtable: ");
		}
	}
	