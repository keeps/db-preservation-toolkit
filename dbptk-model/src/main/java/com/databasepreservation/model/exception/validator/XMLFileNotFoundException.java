/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/db-preservation-toolkit
 */
package com.databasepreservation.model.exception.validator;

import com.databasepreservation.model.exception.ModuleException;

/**
 *
 * @author Miguel Guimarães <mguimaraes@keep.pt>
 */
public class XMLFileNotFoundException extends ModuleException {

  private XMLFileNotFoundException() { super();}

  public XMLFileNotFoundException(String message) {
    this();
    withMessage(message);
  }
}
