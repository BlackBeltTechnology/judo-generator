@abstract
rule compareElements
	match newEl : MODIFIED!RdbmsElement
	with oldEl : ORIGINAL!RdbmsElement {
	
	compare : newEl.uuid = oldEl.uuid
	
	}

rule compareFields
	match newField : MODIFIED!RdbmsField
	with oldField : ORIGINAL!RdbmsField 
	extends compareElements {
	}
	
rule compareTables
	match newTable : MODIFIED!RdbmsTable
	with oldTable : ORIGINAL!RdbmsTable 
	extends compareElements {
	}
	