package hu.blackbelt.judo.workaround.ant.hutn.parser;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.hutn.HutnContext;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;

public class Main {

	private static class Arguments {
		private String hutnFile;
		private String transformedBaseDir;
		private String transformedFileName;
		private String metaModelUri;
		private String metaModelLocaltion;
		private String psmTypesModelLocation;
	}

	public static void main(String[] args) throws Exception {
		Arguments arguments = parseArguments(args);
		HutnModule module = new HutnModule();
		setHutnContext(module);
		registerMetaModel(arguments);
		module.getContext().getModelRepository().addModel(loadPsmTypesModel(arguments));
		parseHutnAndStoreModel(arguments, module);
	}

	private static void parseHutnAndStoreModel(Arguments arguments, HutnModule module)
			throws Exception, HutnGenerationException {
		System.out.println("Start parsing HUTN file");
		File hutnFile = new File(arguments.hutnFile);
		if (module.parse(hutnFile)) {
			System.out.println("Parsing successfull, storing transformed emf model");
			List<File> files = module.storeEmfModel(new File(arguments.transformedBaseDir), arguments.transformedFileName, "any");
			for (File file : files) {
				System.out.println("Transformed: " + file);
			}
		} else {
			System.out.println("Failure");
			for (ParseProblem pb : module.getParseProblems()) {
				System.out.println(pb.toString());
			}
		}
	}

	private static void setHutnContext(HutnModule module) {
		HutnContext context = new HutnContext(module);
		module.setContext(context);
		context.setModelRepository(new ModelRepository());
	}

	private static Arguments parseArguments(String[] args) {
		Arguments ret = new Arguments();
		if (args.length == 6) {
			ret.hutnFile = args[0];
			ret.transformedBaseDir = args[1];
			ret.transformedFileName = args[2];
			ret.metaModelUri = args[3];
			ret.metaModelLocaltion = args[4];
			ret.psmTypesModelLocation = args[5];
		}
		return ret;
	}

	private static EmfModel loadPsmTypesModel(Arguments arguments) {
		System.out.println("Loading PSM TYpes");
		final EmfModel model = new EmfModel();

		final StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, "PSMTYPES");
		properties.put(EmfModel.PROPERTY_READONLOAD, "True");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, "False");
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, arguments.metaModelUri);
		URI modelUri = org.eclipse.emf.common.util.URI.createFileURI(arguments.psmTypesModelLocation);
		properties.put(EmfModel.PROPERTY_MODEL_URI, modelUri);

		try {
			model.load(properties);
		} catch (EolModelLoadingException e) {
			e.printStackTrace();
		}

		return model;
	}

	private static void registerMetaModel(Arguments arguments) throws Exception {
		System.out.println("Registering meta model");
		EmfUtil.register(URI.createFileURI(new File(arguments.metaModelLocaltion).getAbsolutePath()),
				EPackage.Registry.INSTANCE);
	}
}
