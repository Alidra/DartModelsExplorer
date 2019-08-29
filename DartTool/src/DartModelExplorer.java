import java.awt.List;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import dart.DartPackage;
import dart.impl.ProjectImpl;

public class DartModelExplorer {

	public static void main(String[] args) {
		DartModelExplorer aDartModelExplorer = new DartModelExplorer();
		aDartModelExplorer.initializeResource();
		ProjectImpl aProject = aDartModelExplorer.load("model/dartlang.dartspec");
		System.out.print(aDartModelExplorer.startOfLine()+1);
		aDartModelExplorer.showElement(aProject);
		TreeIterator<EObject> allElements = aProject.eAllContents();
		aDartModelExplorer.exploreElements(allElements);
		
	}
	public void exploreElements(TreeIterator<EObject> allElements) {
		EObject anElement;
		int i=2;
		while (allElements.hasNext()) {
			anElement = allElements.next();
			System.out.print(startOfLine()+i);
			showElement(anElement);
			i++;
		}
	}
	private void showElementInformation(EObject anElement) {
		
		ArrayList<String> eAttributes = new ArrayList();
		ArrayList<String> instanceAttributes = new ArrayList();
		eAttributes.add("eContainer");
		eAttributes.add("eClass");
		instanceAttributes.add("name");
		showElementInformation(anElement,eAttributes,instanceAttributes);
		}
	private void showElementInformation(EObject anElement, ArrayList<String> eAttributes,
			ArrayList<String> instanceAttributes) {
		// TODO Auto-generated method stub
		
	}
	private String startOfLine() {
		return "Element Number ";
	}
	private void showContainingElement(EObject anElement) {
		EClass elementClass;
		elementClass = anElement.eClass();
		String msg = ", of type " + elementClass.getName();
		System.out.print(msg);
		System.out.println(" is Contained in : "+anElement.eContainer());
	}
	public void showElement(EObject anElement) {
		EClass elementClass;
		elementClass = anElement.eClass();
		String msg = ", of type " + elementClass.getName();
		System.out.print(msg);
		EAttribute nameAttribute = nameAttribute(anElement);
		if(nameAttribute!=null)
			System.out.println(" has label : "+anElement.eGet(nameAttribute));
		else 
			System.out.println(" has no label : ");
	}
	
	private EAttribute nameAttribute(EObject anElement) {
		EList<EAttribute> elementAttributes = anElement.eClass().getEAllAttributes();
		for(EAttribute att : elementAttributes) {
			if (att.getName() == "name") return att;
		}

		return null;
	}
	public void initializeResource() {
		ResourceSet aResourceSet = new ResourceSetImpl();
		aResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dartspec", new XMIResourceFactoryImpl());
		aResourceSet.getPackageRegistry().put
		(DartPackage.eNS_URI, 
		 DartPackage.eINSTANCE);
	}
	public ProjectImpl load(String fileName) {
		ResourceSet aResourceSet = new ResourceSetImpl();
		aResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dartspec", new XMIResourceFactoryImpl());
		URI fileURI = URI.createFileURI(new File(fileName).getAbsolutePath());
		Resource aResource = aResourceSet.getResource(fileURI, true);
		ProjectImpl theSameWebpage = (ProjectImpl) aResource.getContents().get(0);
		return theSameWebpage;
		
	}

}
