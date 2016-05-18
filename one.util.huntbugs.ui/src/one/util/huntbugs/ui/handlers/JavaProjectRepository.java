package one.util.huntbugs.ui.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.strobel.assembler.metadata.CompositeTypeLoader;
import com.strobel.assembler.metadata.ITypeLoader;
import com.strobel.assembler.metadata.JarTypeLoader;

import one.util.huntbugs.repo.AuxRepository;
import one.util.huntbugs.repo.CompositeRepository;
import one.util.huntbugs.repo.DirRepository;
import one.util.huntbugs.repo.Repository;

public class JavaProjectRepository {

	private final IJavaProject project;

	public JavaProjectRepository(IJavaProject project) {
		this.project = project;
	}
	
	public CompositeRepository construct() throws JavaModelException {
		IPath projectOutputLocation = project.getOutputLocation();
		IPath fileSystemLocation = ResourcesPlugin.getWorkspace().getRoot().getFolder(projectOutputLocation).getRawLocation();
		
		Repository projectRepository = new DirRepository(fileSystemLocation.toFile().toPath());
		List<ITypeLoader> typeLoaders = collectTypeLoaders(project);
		Repository dependenciesRepository = new AuxRepository(new CompositeTypeLoader(typeLoaders.toArray(new ITypeLoader[0])));
		
		return new CompositeRepository(Arrays.asList(projectRepository, dependenciesRepository));
	}
	
	private List<ITypeLoader> collectTypeLoaders(IJavaProject project) throws JavaModelException {
		List<ITypeLoader> dependencyLoaders = new ArrayList<>();
		boolean ignoreUnresolvedEntry = true;
		
		for (IClasspathEntry classpathEntry : project.getResolvedClasspath(ignoreUnresolvedEntry)) {
			switch (classpathEntry.getEntryKind()) {
			case IClasspathEntry.CPE_SOURCE:
				// TODO support later
				break;
			case IClasspathEntry.CPE_PROJECT:
				IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(classpathEntry.getPath().lastSegment());
				try {
					if (requiredProject.isOpen() && requiredProject.hasNature(JavaCore.NATURE_ID)) {
						dependencyLoaders.addAll(collectTypeLoaders(JavaCore.create(requiredProject)));
					}
				} catch (CoreException e) {
					new ErrorEvent(e).log();
				}
				break;
			case IClasspathEntry.CPE_LIBRARY:
				try {
					dependencyLoaders.add(new JarTypeLoader(new JarFile(classpathEntry.getPath().toFile())));
				} catch (IOException e) {
					new ErrorEvent(e).log();
				}
				break;
			default:
				throw new AssertionError(); // can't contain any other entry kinds due to resolution
			}
		}
		
		return dependencyLoaders;
	}
	
}
