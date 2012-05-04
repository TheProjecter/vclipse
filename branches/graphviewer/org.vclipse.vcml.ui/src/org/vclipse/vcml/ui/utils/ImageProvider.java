package org.vclipse.vcml.ui.utils;

import java.util.Map;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.vclipse.vcml.ui.internal.VCMLActivator;
import org.vclipse.vcml.ui.labeling.VCMLLabelProvider;

import com.google.common.collect.Maps;

public class ImageProvider {

	private VCMLLabelProvider labelProvider;
	
	private static ImageProvider imageProvider;
	
	private Map<EObject, ImageFigure> cache;
	
	public static ImageProvider instance() {
		if(imageProvider == null) {
			imageProvider = new ImageProvider();
		}
		return imageProvider;
	}
	
	public ImageFigure imageFigure(EObject eobject) {
		ImageFigure imageFigure = cache.get(eobject);
		if(imageFigure == null) {
			Image image = labelProvider.getImage(eobject);
			if(image != null) {
				imageFigure = new ImageFigure(image, PositionConstants.WEST);
				cache.put(eobject, imageFigure);				
			}
		}
		return imageFigure;
	}
	
	protected ImageProvider() {
		this.labelProvider = VCMLActivator.getInstance().getInjector(VCMLActivator.ORG_VCLIPSE_VCML_VCML).getInstance(VCMLLabelProvider.class);
		cache = Maps.newHashMap();
	}
}

