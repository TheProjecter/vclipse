package org.vclipse.vcml.generator;

import com.google.inject.Inject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IGenerator;
import org.vclipse.vcml.generator.VCMLOutputConfigurationProvider;

@SuppressWarnings("all")
public abstract class VCMLGenerator implements IGenerator {
  private static String VCML_FILE_EXTENSION = "vcml";
  
  @Inject
  private VCMLOutputConfigurationProvider configurationProvider;
  
  public URI getVcmlUri(final Resource resource) {
    URI uri = resource.getURI();
    URI _trimFileExtension = uri.trimFileExtension();
    URI _appendFileExtension = _trimFileExtension.appendFileExtension(VCMLGenerator.VCML_FILE_EXTENSION);
    uri = _appendFileExtension;
    final String foldername = this.configurationProvider.getCompileFolderName();
    URI _trimSegments = uri.trimSegments(1);
    URI _appendSegment = _trimSegments.appendSegment(foldername);
    String _lastSegment = uri.lastSegment();
    return _appendSegment.appendSegment(_lastSegment);
  }
}