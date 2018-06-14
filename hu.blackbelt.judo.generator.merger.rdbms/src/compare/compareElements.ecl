post {
	'Match trace'.println();
	('There are: ' + matchTrace.getMatches().size() + ' elements').println();

	for (m in matchTrace.getMatches()) {
		if (not m.isMatching()) {
			(m.left.uuid + " " + m.right.uuid).println("Does not math: " + m.left.name );
		}
	}
}

rule compareModel
	match newModel : NEW!RdbmsModel
	with oldModel : PREVIOUS!RdbmsModel {
	compare : true
	}

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