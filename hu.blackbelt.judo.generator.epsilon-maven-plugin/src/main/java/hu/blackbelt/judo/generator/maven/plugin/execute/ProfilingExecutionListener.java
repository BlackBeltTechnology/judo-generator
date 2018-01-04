package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;
import org.eclipse.epsilon.profiling.Profiler;

public class ProfilingExecutionListener implements IExecutionListener {
    public ProfilingExecutionListener() {
    }

    public void aboutToExecute(ModuleElement ast, IEolContext context) {
        Profiler.INSTANCE.start(this.getLabel(ast), "", ast);
    }

    public void finishedExecuting(ModuleElement ast, Object evaluatedAst, IEolContext context) {
        Profiler.INSTANCE.stop(this.getLabel(ast));
    }

    public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context) {
    }

    protected String getLabel(ModuleElement ast) {
        return ast.getClass().getSimpleName() + " (" + ast.getRegion().getStart().getLine() + ":" + ast.getRegion().getStart().getColumn() + ")";
    }
}
