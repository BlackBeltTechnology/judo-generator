pre {
	"Comapriosn started".println();
}

post {
	"Comapriosn finished".println();
	
	Target!JElement.all.size().println("ALL: ");
	Target!JClass.all.size().println("CLASSES: ");
	var cls = Target!JClass.all.asSequence();
	matchTrace.matches.size().println("MATCHES: ");
	matchTrace.reduced.matches.size().println("REDUCED: ");
	for (m in matchTrace.matches) { 
		//(m.matching + ": "+ m.left+ "<->" + m.right).println();
		
	}
	for (m in matchTrace.reduced.matches) { 
		(m.matching + ": "+ m.left+ "<->" + m.right).println();
		cls.remove(m.right);
	}
	cls.name.println();
}

@abstract
rule MatchJElement
  match s : Source!JElement
  with t : Target!JElement {
  
  compare {
    return t.name = s.name;
  }
}

rule MatchJModel
  match s : Source!JModel
  with t : Target!JModel {
  
  compare {
    return s.name.startsWith(t.name);
  }
}

rule MatchJPackage
  match s : Source!JPackage
  with t : Target!JPackage
  extends MatchJElement {
  
  compare {
    var r = true;
    if (t.ownerModel.isDefined()) {
    	r = r and s.ownerModel.matches(t.ownerModel);
    }
    if (t.parent.isDefined()) {
    	r =r and s.parent.matches(t.parent);
    }
    return r; 
  }
}

rule MatchJClasses
  match s : Source!JClass
  with t : Target!JClass
  extends MatchJElement {
  
  compare {
    return s.package.matches(t.package); 
  }
}