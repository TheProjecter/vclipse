package org.vclipse.vcml.ui.refactoring;

import java.util.List;

import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.widgets.Label;
import org.vclipse.refactoring.ui.DefaultInputPage;
import org.vclipse.refactoring.ui.IUIRefactoringContext;
import org.vclipse.refactoring.ui.RefactoringUICustomisation;
import org.vclipse.vcml.vcml.PFunction;

import com.google.common.collect.Lists;

public class VCMLUICustomisation extends RefactoringUICustomisation {

	public List<? extends UserInputWizardPage> pages_Replace_PFunction_values(IUIRefactoringContext context, PFunction pfunction) {
		return Lists.newArrayList(new DefaultInputPage(context));
	}
	
	public void switch_widgets_Replace_PFunction_values(IUIRefactoringContext context, PFunction pfunction) {
		for(UserInputWizardPage page : context.getPages()) {
			if(page instanceof DefaultInputPage) {
				DefaultInputPage dip = (DefaultInputPage)page;
				Label label = dip.getWidget("label", Label.class);
				if(label != null) {
					label.setText("New literal value: ");
				}
			}
		}
	}
}