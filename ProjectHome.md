VClipse is an Eclipse-based integrated development environment (IDE) for product modeling for the [SAP Variant Configurator (VC)](http://help.sap.com/printdocu/core/Print46c/en/Data/pdf/LOVC/LOVC.pdf). VClipse supports the specification of objects and dependencies in a textual domain specific language called [VCML](VCMLLanguage.md). This enables the use of standard text comparison tools and version control systems in the model development process.

VClipse offers two interfaces to SAP systems. The VC objects and dependencies can be transferred to or retrieved from a SAP system using SAP's remote function calls ([RFC](http://en.wikipedia.org/wiki/Remote_Function_Call)). Furthermore, the VC objects and dependencies can be transferred using product data replication (PDR) via [IDocs](http://help.sap.com/saphelp_nw70/helpdata/en/0b/2a6095507d11d18ee90000e8366fc2/frameset.htm).

VClipse is implemented using the [Xtext](http://www.eclipse.org/Xtext/) framework and the [Java Pretty Printer Library](http://jpplib.sourceforge.net/).

VClipse is available from the update site http://update.vclipse.org for Eclipse Kepler and Xtext 2.5.4. See [Installing VClipse](InstallingVClipse.md) for installation instructions.

VClipse has been mainly developed by [webXcerpt Software GmbH](http://www.webxcerpt.com/).

## News ##

  * Since Google Code will be discontinued in near future, we will migrate the VClipse repository to GitHub.
    * The source code has been moved to https://github.com/webXcerpt/VClipse
    * These Wiki pages will soon be migrated to GitHub.
    * We will migrate the code to Eclipse Mars and Xtext 2.8.4

  * Several use cases for VClipse have been presented at [2013 Configuration Workgroup Conference in Vienna, 5th - 8th May 2013](http://www.configuration-workgroup.com/node/3054). The presentation slides are available [here](http://vclipse.eclipselabs.org.codespot.com/files/webXcerpt-TimGeisler-VClipseUseCases-CWG-2013-05-07.pdf).

  * The [VCML language documentation](VCMLLanguage.md) has been reworked.

  * VClipse has been ported to Xtext 2.5.4 and Eclipse Kepler (4.3)