/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.enterprise.deploy.model;

import javax.enterprise.deploy.shared.ModuleType;
import java.util.Enumeration; 
import java.io.InputStream; 
import java.io.IOException;

/**
 * The DeployableObject interface is an abstract representation 
 * of a Java EE deployable module (JAR, WAR, RAR, EAR). A 
 * DeployableObject provides access to the module's deployment
 * descriptor and class files.
 *
 * @author gfink
 * @version 0.1
 */
public interface DeployableObject 
{

   /**
    * Return the ModuleType of deployment descriptor (i.e., EAR, 
    * JAR, WAR, RAR) this deployable object represents.
    * Values are found in DeploymentManager.
    *
    * @return The ModuleType of deployable object
    */   
   public ModuleType getType();
   
   /**
    * Return the top level standard bean representing
    * the root of the deployment descriptor.
    *
    * @return A standard bean representing the deployment 
    *         descriptor.
    */
    public DDBeanRoot getDDBeanRoot();

    /**
     * Return an array of standard beans representing the
     * XML content returned based upon the XPath.
     *
     * @param xpath An XPath string identifying the data to
     *              be extracted from the deployment descriptor.
     * @return a array of DDBeans or 'null' if no matching data found.
     *
     */   
   public DDBean[] getChildBean(String xpath);
   
   /**
    * Return the XML content associated with the  XPath
    * from a deployment descriptor.
    *
    * @param xpath An xpath string referring to a location in the
    *          deployment descriptor
    * @return a list XML content or 'null' if no matching data found.
    */   
   public String[] getText(String xpath);
   
    /** 
     * Retrieve the specified class from this deployable module.
     * <p>
     * One use: to get all finder methods from an EJB
     *
     * If the tool is attempting to package an module 
     * and retrieve a class from the package, the class
     * request may fail.  The class may not yet be 
     * available.  The tool should respect the manifest
     * cross-path entries.
     *  
     * @param className Class to retrieve.
     * @return Class representation of the class
     */
    public Class getClassFromScope(String className);

   /**
    * Returns the DTD version number given in the XML
    * DOCTYPE text provided in every standard Java EE module's
    * deployment descriptor file.
    * @return a string containing the DTD version number
    * 
	* <PRE>
    * A module's deployment descriptor file always contains
	* a document type identifier, DOCTYPE.  The DOCTYPE statement 
	* contains the module DTD version number in the label of the 
	* statement.
	*
	* 	The format of the DOCTYPE statement is:
	*<ul>
	*	&lt;!DOCTYPE root_element PUBLIC 
	*	"-//organization//label//language" "location"&gt;
	*</ul>
	*
	* root_element - is the name of the root document in the DTD.
	* organization  - is the name of the organization responsible 
	*                 for the creation and maintenance of the DTD 
	*                 being referenced.
	* label - is a unique descriptive name for the public text being 
	*                 referenced.  
	* language - is the ISO 639 language id representing the natural 
	*                 language encoding of th DTD.
	* location - is the URL of the DTD.
	*
	* An example Java EE deployment descriptor DOCTYPE statement is:
	*<ul>
	*   &lt;!DOCTYPE application-client PUBLIC
    *   "-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN"
    *   "http://java.sun.com/dtd/application-client_1_3.dtd"&gt;
	*</ul>
	* In this example the label is, "DTD J2EE Application Client 1.3", 
	* and the DTD version number is 1.3. A call to getModuleDTDVersion 
	* would return a string containing, "1.3".
	* </PRE>
    *
    * This method is being deprecated.  With the addition of multiple
    * deployment descritors in components for J2EE 1.4 this method is
    * being replaced by DDBeanRoot.getDDBeanRootVersion.
    *
    * @deprecated As of version 1.1 replaced by 
    *  DDBeanRoot.getDDBeanRootVersion() 
    */   
   public String getModuleDTDVersion();

    /**
     * Returns a DDBeanRoot object for the XML instance document named.  
     * This method should be used to return DDBeanRoot objects for non 
     * deployment descriptor XML instance documents such as WSDL files.
     *
     * @return a DDBeanRoot object for the XML data. 
     * @throws java.io.FileNotFoundException, if the named file can not 
     *  be found 
     * @throws javax.enterprise.deploy.model.exceptions.DDBeanCreateException 
     *  if an error is encountered creating the DDBeanRoot object. 
     */ 
    public DDBeanRoot getDDBeanRoot(String filename) throws 
         java.io.FileNotFoundException, 
         javax.enterprise.deploy.model.exceptions.DDBeanCreateException; 

    /** 
     * Returns an enumeration of the module file entries.  All elements 
     * in the enumeration are of type String.  Each String represents a 
     * file name relative to the root of the module. 
     * 
     * @return an enumeration of the archive file entries. 
     */ 
    public Enumeration entries(); 

    /** 
     * Returns the InputStream for the given entry name 
     * The file name must be relative to the root of the module. 
     * 
     * @param name the file name relative to the root of the module. 
     * 
     * @return the InputStream for the given entry name or null if not found. 
     */ 
    public InputStream getEntry(String name); 
 }

