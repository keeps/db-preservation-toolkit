/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/db-preservation-toolkit
 */
/**
 *
 */
package com.databasepreservation.model.modules;

import java.util.Map;
import java.util.Set;

import com.databasepreservation.model.data.Row;
import com.databasepreservation.model.exception.ModuleException;
import com.databasepreservation.model.exception.UnknownTypeException;
import com.databasepreservation.model.reporters.Reporter;
import com.databasepreservation.model.structure.DatabaseStructure;

/**
 * An export module exports the database (that it receives from a
 * DatabaseImportModule) to a database system or format.
 *
 * @author Luis Faria <lfaria@keep.pt>
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
public interface DatabaseExportModule extends ExceptionNormalizer {
  /**
   * Initialize the database, this will be the first method called
   *
   * @throws ModuleException
   */
  void initDatabase() throws ModuleException;

  /**
   * Set ignored schemas. Ignored schemas won't be exported. This method should be
   * called before handleStructure. However, if not called it will be assumed
   * there are not ignored schemas.
   *
   * @param ignoredSchemas
   *          the set of schemas to ignored
   */
  void setIgnoredSchemas(Set<String> ignoredSchemas);

  /**
   * Handle the database structure. This method will called after
   * setIgnoredSchemas.
   *
   * @param structure
   *          the database structure
   * @throws ModuleException
   * @throws UnknownTypeException
   */
  void handleStructure(DatabaseStructure structure) throws ModuleException;

  /**
   * Prepare to build the data of a new schema. This method will be called after
   * handleStructure or handleDataCloseSchema.
   *
   * @param schemaName
   *          the schema name
   * @throws ModuleException
   */
  void handleDataOpenSchema(String schemaName) throws ModuleException;

  /**
   * Prepare to build the data of a new table. This method will be called after
   * the handleDataOpenSchema, and before some calls to handleDataRow. If there
   * are no rows in the table, then handleDataCloseTable is called after this
   * method.
   *
   * @param tableId
   *          the table id
   * @throws ModuleException
   */
  void handleDataOpenTable(String tableId) throws ModuleException;

  /**
   * Handle a table row. This method will be called after the table was open and
   * before it was closed, by row index order.
   *
   * @param row
   *          the table row
   * @throws ModuleException
   */
  void handleDataRow(Row row) throws ModuleException;

  /**
   * Finish handling the data of a table. This method will be called after all
   * table rows for the table where requested to be handled.
   *
   * @param tableId
   *          the table id
   * @throws ModuleException
   */
  void handleDataCloseTable(String tableId) throws ModuleException;

  /**
   * Finish handling the data of a schema. This method will be called after all
   * tables of the schema were requested to be handled.
   *
   * @param schemaName
   *          the schema name
   * @throws ModuleException
   */
  void handleDataCloseSchema(String schemaName) throws ModuleException;

  /**
   * Finish the database. This method will be called when all data was requested
   * to be handled. This is the last method.
   *
   * @throws ModuleException
   */
  void finishDatabase() throws ModuleException;

  void updateModuleConfiguration(String moduleName, Map<String, String> properties,
    Map<String, String> remoteProperties);

  /**
   * Provide a reporter through which potential conversion problems should be
   * reported. This reporter should be provided only once for the export module
   * instance.
   *
   * @param reporter
   *          The initialized reporter instance.
   */
  void setOnceReporter(Reporter reporter);
}
