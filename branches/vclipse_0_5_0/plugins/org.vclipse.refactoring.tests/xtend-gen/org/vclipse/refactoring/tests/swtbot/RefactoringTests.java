package org.vclipse.refactoring.tests.swtbot;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.InputStream;
import java.util.ArrayList;
import junit.framework.Assert;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipselabs.xtext.utils.unittesting.XtextTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vclipse.refactoring.tests.utils.RefactoringResourcesLoader;
import org.vclipse.refactoring.tests.utils.RefactoringTestModule;
import org.vclipse.refactoring.utils.EntrySearch;
import org.vclipse.refactoring.utils.Extensions;
import org.vclipse.vcml.VCMLRuntimeModule;
import org.vclipse.vcml.refactoring.VCMLRefactoring;

@RunWith(value = SWTBotJunit4ClassRunner.class)
@SuppressWarnings("all")
public class RefactoringTests extends XtextTest {
  private RefactoringResourcesLoader resourcesLoader;
  
  private Extensions extensions;
  
  private VCMLRefactoring vcmlRefactoring;
  
  private EntrySearch search;
  
  private SWTWorkbenchBot bot;
  
  public RefactoringTests() {
    super(new Function0<String>() {
      public String apply() {
        String _simpleName = RefactoringTests.class.getSimpleName();
        return _simpleName;
      }
    }.apply());
  }
  
  public void before() {
    super.before();
    SWTWorkbenchBot _sWTWorkbenchBot = new SWTWorkbenchBot();
    this.bot = _sWTWorkbenchBot;
    SWTBotPerspective _perspectiveByLabel = this.bot.perspectiveByLabel("Java");
    _perspectiveByLabel.activate();
    final SWTBotView welcomeView = this.bot.viewByTitle("Welcome");
    boolean _notEquals = (!Objects.equal(welcomeView, null));
    if (_notEquals) {
      welcomeView.close();
    }
    RefactoringTestModule _refactoringTestModule = new RefactoringTestModule();
    final RefactoringTestModule refactoringModule = _refactoringTestModule;
    VCMLRuntimeModule _vCMLRuntimeModule = new VCMLRuntimeModule();
    final VCMLRuntimeModule vcmlRuntimeModule = _vCMLRuntimeModule;
    final Injector injector = Guice.createInjector(refactoringModule, vcmlRuntimeModule);
    RefactoringResourcesLoader _instance = injector.<RefactoringResourcesLoader>getInstance(RefactoringResourcesLoader.class);
    this.resourcesLoader = _instance;
    Extensions _instance_1 = injector.<Extensions>getInstance(Extensions.class);
    this.extensions = _instance_1;
    VCMLRefactoring _instance_2 = injector.<VCMLRefactoring>getInstance(VCMLRefactoring.class);
    this.vcmlRefactoring = _instance_2;
    EntrySearch _instance_3 = injector.<EntrySearch>getInstance(EntrySearch.class);
    this.search = _instance_3;
  }
  
  public void after() {
    this.bot.sleep(1000);
  }
  
  private void loadResources() {
    try {
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      final IWorkspaceRoot root = _workspace.getRoot();
      IProject project = root.getProject("org.vclipse.refactoring");
      NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
      IProgressMonitor monitor = ((IProgressMonitor) _nullProgressMonitor);
      boolean _isAccessible = project.isAccessible();
      boolean _not = (!_isAccessible);
      if (_not) {
        IWorkbench _workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow _activeWorkbenchWindow = _workbench.getActiveWorkbenchWindow();
        IWorkbenchPage _activePage = _activeWorkbenchWindow.getActivePage();
        final IEditorPart activeEditor = _activePage.getActiveEditor();
        boolean _notEquals = (!Objects.equal(activeEditor, null));
        if (_notEquals) {
          final IWorkbenchPartSite site = activeEditor.getSite();
          if ((site instanceof IEditorSite)) {
            final IActionBars actionBars = ((IEditorSite) site).getActionBars();
            IStatusLineManager _statusLineManager = actionBars.getStatusLineManager();
            IProgressMonitor _progressMonitor = _statusLineManager.getProgressMonitor();
            monitor = _progressMonitor;
          }
        }
        project.create(monitor);
        project.open(monitor);
      }
      final ArrayList<String> natures = Lists.<String>newArrayList(JavaCore.NATURE_ID, "org.eclipse.pde.PluginNature");
      final IProjectDescription description = project.getDescription();
      description.setNatureIds(((String[])Conversions.unwrapArray(natures, String.class)));
      project.setDescription(description, monitor);
      final IFolder folder = project.getFolder("car_description-dep");
      boolean _isAccessible_1 = folder.isAccessible();
      boolean _not_1 = (!_isAccessible_1);
      if (_not_1) {
        folder.create(true, true, monitor);
      }
      IFile file = project.getFile("car_description.vcml");
      boolean _isAccessible_2 = file.isAccessible();
      boolean _not_2 = (!_isAccessible_2);
      if (_not_2) {
        final InputStream stream = this.resourcesLoader.getInputStream("car_description.vcml");
        file.create(stream, true, monitor);
      }
      IFile _file = folder.getFile("CS_CAR1.cons");
      file = _file;
      boolean _isAccessible_3 = file.isAccessible();
      boolean _not_3 = (!_isAccessible_3);
      if (_not_3) {
        final InputStream stream_1 = this.resourcesLoader.getInputStream("CS_CAR1.cons");
        file.create(stream_1, true, monitor);
      }
      IFile _file_1 = folder.getFile("PRECOND.pre");
      file = _file_1;
      boolean _isAccessible_4 = file.isAccessible();
      boolean _not_4 = (!_isAccessible_4);
      if (_not_4) {
        final InputStream stream_2 = this.resourcesLoader.getInputStream("PRECOND.pre");
        file.create(stream_2, true, monitor);
      }
      IFile _file_2 = folder.getFile("PROC.proc");
      file = _file_2;
      boolean _isAccessible_5 = file.isAccessible();
      boolean _not_5 = (!_isAccessible_5);
      if (_not_5) {
        final InputStream stream_3 = this.resourcesLoader.getInputStream("PROC.proc");
        file.create(stream_3, true, monitor);
      }
      IFile _file_3 = folder.getFile("SEL_COND.sel");
      file = _file_3;
      boolean _isAccessible_6 = file.isAccessible();
      boolean _not_6 = (!_isAccessible_6);
      if (_not_6) {
        final InputStream stream_4 = this.resourcesLoader.getInputStream("SEL_COND.sel");
        file.create(stream_4, true, monitor);
      }
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void refactoring_Split() {
    this.loadResources();
    final EObject description_root = this.resourcesLoader.getResourceRoot("/car_description.vcml");
    Assert.assertNotNull(description_root);
  }
}
